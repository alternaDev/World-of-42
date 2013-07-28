package org.alternativedev.wo42.map;

/*
 * TODO LIST
 * Port over existing functions that use ID's and offsets to using names
 * Create function that gets array of all blocks of particular type etc.
 * */
/**
 TiledMapPlus
 An improved version, of Slick2d's TiledMap class, with new features, and improvements, to make coding more
 efficient
 @version 0.4 In Developement
 @author liamzebedee

 Created by Liam Edwards-Playne
 Cryptum Technologies - http://cryptum.net/
 Licensed under the MIT license

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 */

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.Layer;
import org.newdawn.slick.tiled.TileSet;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TiledMapPlus extends TiledMap {
	HashMap<String, Integer> objectGroupNameToOffset = new HashMap<String, Integer>();
	HashMap<String, HashMap<String, Integer>> objectGroupToGroupObjectOffsetMap = new HashMap<String, HashMap<String, Integer>>();
	HashMap<String, Integer> layerNameToIDMap = new HashMap<String, Integer>();
	HashMap<String, Integer> tilesetNameToIDMap = new HashMap<String, Integer>();

	/**
	 * Create a new tile map based on a given TMX file
	 * 
	 * @param ref
	 *            The location of the tile map to load
	 * @throws SlickException
	 *             Indicates a failure to load the tilemap
	 */
	public TiledMapPlus(String ref) throws SlickException {
		super(ref, true);
		processNameToObjectMap();
		processLayerMap();
		processTilesetMap();
	}

	/**
	 * Create a new tile map based on a given TMX file
	 * 
	 * @param ref
	 *            The location of the tile map to load
	 * @param loadTileSets
	 *            True if we want to load tilesets - including their image data
	 * @throws SlickException
	 *             Indicates a failure to load the tilemap
	 */
	public TiledMapPlus(String ref, boolean loadTileSets) throws SlickException {
		super(ref, loadTileSets);
		processNameToObjectMap();
		processLayerMap();
		processTilesetMap();
	}

	/**
	 * Create a new tile map based on a given TMX file
	 * 
	 * @param ref
	 *            The location of the tile map to load
	 * @param tileSetsLocation
	 *            The location where we can find the tileset images and other
	 *            resources
	 * @throws SlickException
	 *             Indicates a failure to load the tilemap
	 */
	public TiledMapPlus(String ref, String tileSetsLocation)
			throws SlickException {
		super(ref, tileSetsLocation);
		processNameToObjectMap();
		processLayerMap();
		processTilesetMap();
	}

	/**
	 * Load a tile map from an arbitary input stream
	 * 
	 * @param in
	 *            The input stream to load from
	 * @throws SlickException
	 *             Indicates a failure to load the tilemap
	 */
	public TiledMapPlus(InputStream in) throws SlickException {
		super(in);
		processNameToObjectMap();
		processLayerMap();
		processTilesetMap();
	}

	/**
	 * Load a tile map from an arbitary input stream
	 * 
	 * @param in
	 *            The input stream to load from
	 * @param tileSetsLocation
	 *            The location at which we can find tileset images
	 * @throws SlickException
	 *             Indicates a failure to load the tilemap
	 */
	public TiledMapPlus(InputStream in, String tileSetsLocation)
			throws SlickException {
		super(in, tileSetsLocation);
		processNameToObjectMap();
		processLayerMap();
		processTilesetMap();
	}

	/**
	 * Puts a property to an object
	 * 
	 * @param groupName
	 *            The name of the object
	 * @param objectName
	 *            The offset of the group this object is to be put to
	 * @param propertyKey
	 *            The key of the property to be put to the object
	 * @param propertyValue
	 *            The value mappped to the key of the property to be put to the
	 *            object
	 */
	public void putObjectProperty(String groupName, String objectName,
			String propertyKey, String propertyValue) {
		ObjectGroup objectGroup = (ObjectGroup) objectGroups
				.get(this.objectGroupNameToOffset.get(groupName));
		GroupObject object = (GroupObject) objectGroup.objects
				.get(this.objectGroupToGroupObjectOffsetMap.get(groupName).get(
						objectName));
		object.props.put(propertyKey, propertyValue);
	}

	/**
	 * Removes a property from an object
	 * 
	 * @param groupName
	 *            The name of the object
	 * @param objectName
	 *            The offset of the group this object is to be removed from
	 * @param propertyKey
	 *            The key of the property to be removed from the object
	 */
	public void removeObjectProperty(String groupName, String objectName,
			String propertyKey, String propertyValue) {
		ObjectGroup objectGroup = (ObjectGroup) objectGroups
				.get(this.objectGroupNameToOffset.get(groupName));
		GroupObject object = (GroupObject) objectGroup.objects
				.get(this.objectGroupToGroupObjectOffsetMap.get(groupName).get(
						objectName));
		object.props.remove(propertyKey);
	}

	/**
	 * Populates the objectGroupName to objectGroupOffset map and the
	 * groupObjectName to groupObjectOffset map
	 * 
	 */
	public void processNameToObjectMap() {
		for (int i = 0; i < this.getObjectGroupCount(); i++) { // Goes through
																// each object
																// group
			// Add group name to group offset conversion
			// Create a hashmap of name to objects for this group
			ObjectGroup g = (ObjectGroup) this.objectGroups.get(i);
			this.objectGroupNameToOffset.put(g.name, i);
			HashMap<String, Integer> nameToObjectMap = new HashMap<String, Integer>();
			for (int ib = 0; ib < this.getObjectCount(i); ib++) {
				nameToObjectMap.put(this.getObjectName(i, ib), ib);
			}
			this.objectGroupToGroupObjectOffsetMap.put(g.name, nameToObjectMap);
		}
	}

	/**
	 * Populates the tileSet name to offset map
	 * 
	 */
	public void processLayerMap() {
		for (int l = 0; l < layers.size(); l++) {
			Layer layer = (Layer) layers.get(l);
			this.layerNameToIDMap.put(layer.name, l);
		}
	}

	/**
	 * Populates the tileSet name to offset map
	 * 
	 */
	public void processTilesetMap() {
		for (int t = 0; t < this.getTileSetCount(); t++) {
			TileSet tileSet = this.getTileSet(t);
			this.tilesetNameToIDMap.put(tileSet.name, t);
		}
	}

	/**
	 * Gets an object
	 * 
	 * @param objectName
	 *            The name of the object
	 * @param groupName
	 *            The name of the group from which the object is being retrieved
	 *            from
	 */
	public GroupObject getObject(String groupName, String objectName) {
		GroupObject object;
		int groupOffset = this.objectGroupNameToOffset.get(groupName);
		int objectOffset = (int) this.objectGroupToGroupObjectOffsetMap.get(
				groupName).get(objectName);
		ObjectGroup group = (ObjectGroup) this.objectGroups.get(groupOffset);
		object = (GroupObject) group.objects.get(objectOffset);
		return object;
	}

	/**
	 * Removes an object
	 * 
	 * @param objectName
	 *            The name of the object
	 * @param groupName
	 *            The name of the group from which the object is being removed
	 *            from
	 */
	public void removeObject(String groupName, String objectName) {
		int groupOffset = this.objectGroupNameToOffset.get(groupName);
		int objectOffset = this.objectGroupToGroupObjectOffsetMap.get(
				((Integer) groupOffset).toString()).get(groupName);
		ObjectGroup group = (ObjectGroup) this.objectGroups.get(groupOffset);
		group.objects.remove(objectOffset);
	}

	/**
	 * Adds a new object to the TiledMap
	 * 
	 * @param name
	 *            The name of the object
	 * @param x
	 *            The x co-ordinate of the object (in pixels)
	 * @param y
	 *            The y co-ordinate of the object (in pixels)
	 * @param type
	 *            The type of this object
	 * @param width
	 *            The width of this object (in pixels)
	 * @param height
	 *            The height of this object (in pixels)
	 * @param props
	 *            The properties of this object
	 * @param group
	 *            The name group this object is to be added to. Note that they
	 *            are called object layers in the TilED editor
	 * @throws ParserConfigurationException
	 */
	@SuppressWarnings("unchecked")
	public void addObject(int x, int y, String type, int width, int height,
			Properties props, String group, String name, boolean inPixels)
			throws ParserConfigurationException {
		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;

		docBuilder = dbfac.newDocumentBuilder();

		Document doc = docBuilder.newDocument();

		// create the root element and add it to the document
		Element object = doc.createElement("object");
		object.setAttribute("name", name);
		object.setAttribute("type", type);
		if (inPixels) {
			object.setAttribute("x", String.valueOf(x));
			object.setAttribute("y", String.valueOf(y));
			object.setAttribute("width", String.valueOf(width));
			object.setAttribute("height", String.valueOf(height));
		} else {
			object.setAttribute("x", String.valueOf(x * 32));
			object.setAttribute("y", String.valueOf(y * 32));
			object.setAttribute("width", String.valueOf(width * 32));
			object.setAttribute("height", String.valueOf(height * 32));
		}
		if (props != null) {
			Element properties = doc.createElement("properties");
			for (String key : props.stringPropertyNames()) {
				String value = props.getProperty(key);
				Element p = doc.createElement("property");
				p.setAttribute(key, value);
				properties.appendChild(p);
				// Appends this property node to the <properties> parent
			}
			object.appendChild(properties);
		}
		GroupObject g = null;
		try {
			g = new GroupObject(object);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		ObjectGroup objectGroup = (ObjectGroup) objectGroups
				.get(this.objectGroupNameToOffset.get(group));
		objectGroup.objects.add(g);
		this.objectGroupToGroupObjectOffsetMap.get(group).put(name,
				this.objectGroupToGroupObjectOffsetMap.size());
		// gets the object name to offset conversion table
	}

	/**
	 * Gets an objects id by its name and group
	 * 
	 * @param objectName
	 *            The name of the object
	 * @param groupName
	 *            The name of the group of which the object belongs
	 */
	public int getObjectID(String objectName, String groupName) {
		return this.objectGroupToGroupObjectOffsetMap.get(
				((Integer) this.getObjectGroupID(groupName)).toString()).get(
				objectName);
	}

	/**
	 * Gets a groups ID by its name
	 * 
	 * @param groupName
	 *            The name of the group
	 */
	public int getObjectGroupID(String groupName) {
		return this.objectGroupNameToOffset.get(groupName);
	}

	/**
	 * Get all tiles from all layers that are part of a specific tileset
	 * 
	 * @param tilesetName
	 *            The name of the tileset that the tiles are part of
	 */
	public ArrayList<Tile> getAllTilesFromAllLayers(String tilesetName) {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		int tilesetID = this.tilesetNameToIDMap.get(tilesetName);
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				for (int l = 0; l < this.getLayerCount(); l++) {
					Layer layer = (Layer) this.layers.get(l);

					if (layer.data[x][y][0] == tilesetID) {
						Tile t = new Tile(x, y, layer.name, layer.data[x][y][2]);
						tiles.add(t);
					}
				}
			}
		}
		return tiles;
	}

	/**
	 * Get all tiles from a specific layer that are part of a specific tileset
	 * 
	 * @param tilesetName
	 *            The name of the tileset that the tiles are part of
	 * @param layerName
	 *            The name of the layer the tiles are placed
	 */
	public ArrayList<Tile> getAllTilesFromLayer(String tilesetName,
			String layerName) {
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		int layerID = this.layerNameToIDMap.get(layerName);
		int tilesetID = this.tilesetNameToIDMap.get(tilesetName);
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getHeight(); y++) {
				Layer layer = (Layer) this.layers.get(layerID);
				if (layer.data[x][y][0] == tilesetID) {
					Tile t = new Tile(x, y, layerName, layer.data[x][y][1]);
					tiles.add(t);
				}
			}
		}

		return tiles;
	}

	/**
	 * Removes a tile
	 * 
	 * @param x
	 *            Tile X
	 * @param y
	 *            Tile Y
	 * @param layerName
	 *            The name of the layer to remove the tile from
	 */
	public void removeTile(int x, int y, String layerName) {
		int layerID = this.layerNameToIDMap.get(layerName);
		Layer layer = (Layer) layers.get(layerID);
		layer.data[x][y][0] = -1;
	}

	/**
	 * Sets a tile's tileSet
	 * 
	 * @param x
	 *            Tile X
	 * @param y
	 *            Tile Y
	 * @param tileID
	 *            The id of the tile, within the tileSet to set this tile to,
	 *            ordered in rows
	 * @param layerName
	 *            The name of the layer to remove the tile from
	 * @param tilesetName
	 *            The name of the tileset to set the tile to
	 */
	public void setTile(int x, int y, int tileID, String layerName,
			String tilesetName) {
		int layerID = this.layerNameToIDMap.get(layerName);
		Layer layer = (Layer) layers.get(layerID);
		int tilesetID = this.tilesetNameToIDMap.get(tilesetName);
		TileSet tileset = this.getTileSet(tilesetID);
		System.out.println("Tileset " + tileset.name + " has firstGID of "
				+ tileset.firstGID);
		layer.data[x][y][0] = tileset.index; // tileSetIndex
		layer.data[x][y][1] = tileID; // localID
		layer.data[x][y][2] = tileset.firstGID + tileID; // globalID
		System.out.println("Setting tile " + x + ":" + y + " to tilesetgid "
				+ (tileset.firstGID + tileID));
	}

	/**
	 * Writes the current TiledMap to a stream
	 * 
	 * @param o
	 *            The stream in which the TiledMap is to be written to
	 * @throws SlickException
	 */
	public void write(OutputStream o) throws SlickException {
		try {

			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.newDocument();

			Element map = doc.createElement("map");
			map.setAttribute("version", "1.0");
			map.setAttribute("orientation", "orthogonal");
			map.setAttribute("tilewidth", "" + this.tileWidth);
			map.setAttribute("tileheight", "" + this.tileHeight);
			map.setAttribute("width", "" + this.width);
			map.setAttribute("height", "" + this.height);
			doc.appendChild(map);
			for (int i = 0; i < this.tileSets.size(); i++) { // Loop through all
																// tilesets
				TileSet tilesetData = (TileSet) this.tileSets.get(i);
				Element tileset = doc.createElement("tileset");
				tileset.setAttribute("firstgid", "" + tilesetData.firstGID);
				tileset.setAttribute("name", tilesetData.name);
				tileset.setAttribute("tilewidth", "" + tilesetData.tileWidth);
				tileset.setAttribute("tileheight", "" + tilesetData.tileHeight);
				tileset.setAttribute("spacing",
						"" + tilesetData.getTileSpacing());
				tileset.setAttribute("margin", "" + tilesetData.getTileMargin());
				Element image = doc.createElement("image");

				image.setAttribute("width", "" + tilesetData.tiles.getWidth());
				image.setAttribute("height", "" + tilesetData.tiles.getHeight());
				tileset.appendChild(image);
				int tileCount = tilesetData.tiles.getHorizontalCount()
						* tilesetData.tiles.getVerticalCount();
				if (tileCount == 1) {
					tileCount++;
				}
				for (int tileI = 0; tileI < tileCount; tileI++) {
					Properties tileProperties = tilesetData
							.getProperties(tileI);
					if (tileProperties != null) {
						Element tile = doc.createElement("tile");
						int tileID = tileI - tilesetData.firstGID;
						tile.setAttribute("id", "" + tileID);
						Element tileProps = doc.createElement("properties");

						Enumeration<?> propertyEnum = tilesetData
								.getProperties(tileI).propertyNames();
						while (propertyEnum.hasMoreElements()) {
							String key = (String) propertyEnum.nextElement();
							Element tileProperty = doc
									.createElement("property");
							tileProperty.setAttribute("name", key);
							tileProperty.setAttribute("value",
									tileProperties.getProperty(key));
							tileProps.appendChild(tileProperty);
						}
						tile.appendChild(tileProps);
						tileset.appendChild(tile);
					}
				}
				map.appendChild(tileset);
			}

			for (int i = 0; i < this.layers.size(); i++) {
				Element layer = doc.createElement("layer");
				Layer layerData = (Layer) layers.get(i);
				layer.setAttribute("name", layerData.name);
				layer.setAttribute("width", "" + layerData.width);
				layer.setAttribute("height", "" + layerData.height);
				Element data = doc.createElement("data");

				ByteArrayOutputStream os = new ByteArrayOutputStream();
				for (int tileY = 0; tileY < layerData.height; tileY++) {
					for (int tileX = 0; tileX < layerData.width; tileX++) {
						int tileGID = layerData.data[tileX][tileY][2];
						os.write(tileGID);
						os.write(tileGID << 8);
						os.write(tileGID << 16);
						os.write(tileGID << 24);
					}
				}
				os.flush();

				data.setAttribute("encoding", "base64");
				data.setAttribute("compression", "gzip");

				layer.appendChild(data);
				map.appendChild(layer);
			}
			for (int objectGroupI = 0; objectGroupI < this.objectGroups.size(); objectGroupI++) {
				Element objectgroup = doc.createElement("objectgroup");
				ObjectGroup objectGroup = (ObjectGroup) objectGroups
						.get(objectGroupI);
				objectgroup.setAttribute("color", "white");
				// It doesn't appear we use a color value,
				// but its in the format so...
				objectgroup.setAttribute("name", objectGroup.name);
				objectgroup.setAttribute("width", "" + objectGroup.width);
				objectgroup.setAttribute("height", "" + objectGroup.height);

				for (int groupObjectI = 0; groupObjectI < objectGroup.objects
						.size(); groupObjectI++) {
					Element object = doc.createElement("object");
					GroupObject groupObject = (GroupObject) objectGroup.objects
							.get(groupObjectI);
					object.setAttribute("name", groupObject.name);
					object.setAttribute("type", groupObject.type);
					object.setAttribute("x", "" + groupObject.x);
					object.setAttribute("y", "" + groupObject.y);
					object.setAttribute("width", "" + groupObject.width);
					object.setAttribute("height", "" + groupObject.height);
					if (groupObject.props != null) {
						Element objectProps = doc.createElement("properties");
						Enumeration<?> propertyEnum = groupObject.props
								.propertyNames();
						while (propertyEnum.hasMoreElements()) {
							String key = (String) propertyEnum.nextElement();
							Element objectProperty = doc
									.createElement("property");
							objectProperty.setAttribute("name", key);
							objectProperty.setAttribute("value",
									groupObject.props.getProperty(key));
							objectProps.appendChild(objectProperty);
						}
						object.appendChild(objectProps);
					}
					objectgroup.appendChild(object);
				}

				map.appendChild(objectgroup);
			}
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(o);
			transformer.transform(source, result);

		} catch (Exception e) {
			Log.error(e);
			throw new SlickException("Failed to write tiledmap", e);
		}
	}

	/**
	 * A class for holding information about a particular tile on a particular
	 * layer
	 * 
	 * @author liamzebedee
	 */
	public class Tile {

		// The x co-ordinate of the tile
		public int x;
		// The y co-ordinate of the tile
		public int y;
		// The layer name on which this tile is on
		public String layerName;
		// The Global ID of the Tile
		public int id;

		/**
		 * Constructor for a Tile
		 * 
		 * @param x
		 *            The x co-ordinate of the tile
		 * @param y
		 *            The y co-ordinate of the tile
		 * @param layerName
		 *            The layer name on which this tile is on
		 * @param id
		 *            The Global ID of the Tile
		 */
		public Tile(int x, int y, String layerName, int id) {
			this.x = x;
			this.y = y;
			this.layerName = layerName;
			this.id = id;
		}

	}

}