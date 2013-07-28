package org.alternativedev.wo42.natives;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.swing.JOptionPane;

public class NativeCopier {
	private String tmp = System.getProperty("java.io.tmpdir")
			+ "/wo42/natives/";
	private static String OS = System.getProperty("os.name").toLowerCase();

	public void copyNatives() throws IOException {
		new File(tmp).mkdirs();
		String[] filesWin = new String[] { "lwjgl.dll", "lwjgl64.dll",
				"jinput-dx8_64.dll", "jinput-dx8.dll", "jinput-raw.dll",
				"jinput-raw_64.dll", "OpenAL32.dll", "OpenAL64.dll" };
		String[] filesLinux = new String[] { "libjinput-linux.so",
				"libjinput-linux64.so", "liblwjgl.so", "liblwjgl64.so",
				"libopenal64.so", "libopenal.so" };
		String[] filesOSX = new String[] { "libjinput-osx.jnilib",
				"liblwjgl.jnilib", "openal.dylib" };

		if (isWindows())
			copyFiles(filesWin);
		else if (isMac())
			copyFiles(filesOSX);
		else if (isUnix())
			copyFiles(filesLinux);
		else
			JOptionPane.showMessageDialog(null,
					"Ha, dein Betriebssystem ist uncool!");

	}

	private void copyFiles(String[] files) {
		for (String file : files)
			try {
				copyFile(f(file), tmp + file);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}

	public static boolean isWindows() {

		return (OS.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (OS.indexOf("mac") >= 0);

	}

	public static boolean isUnix() {

		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS
				.indexOf("aix") > 0);

	}

	public static boolean isSolaris() {

		return (OS.indexOf("sunos") >= 0);

	}

	InputStream f(String k) throws UnsupportedEncodingException {
		return this.getClass().getResourceAsStream(
				URLDecoder.decode(k, "UTF-8"));
	}

	private void copyFile(InputStream inFile, String outFile)
			throws IOException {
		try {
			copy(inFile,
					new FileOutputStream(URLDecoder.decode(outFile, "UTF-8")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void copy(InputStream in, OutputStream out) throws IOException {
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

}
