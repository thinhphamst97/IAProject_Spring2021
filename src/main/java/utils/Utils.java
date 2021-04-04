/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
	public static String getFirstPartOfMenu(String menuDirPath) {
		String menuPath = menuDirPath + File.separator + "1.ipxe";
		try {
			return Files.readString(Paths.get(menuPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getThirdPartOfMenu(String menuDirPath) {
		String menuPath = menuDirPath + File.separator + "3.ipxe";
		try {
			return Files.readString(Paths.get(menuPath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String createMenu(String menuDirPath, String imageName, String imageType) {
		String menu = "";
		if (imageType.equals("windows")) {
			menu += "#!ipxe\n";
			menu += "isset ${proxydhcp/dhcp-server} && set srv ${proxydhcp/dhcp-server} || set srv ${next-server}\n";
			menu += "kernel http://${srv}/pxeboot/image/wimboot\n";
			menu += String.format("module http://${srv}/pxeboot/image/%s/bcd       BCD\n", imageName);
			menu += String.format("module http://${srv}/pxeboot/image/%s/boot.sdi  boot.sdi\n", imageName);
			menu += String.format("module http://${srv}/pxeboot/image/%s/boot.wim  boot.wim\n", imageName);
			menu += "boot || goto failed\n\n";
			menu += ":failed";
			menu += "boot || goto failed";
		} else if (imageType.equals("linux")) {
			menu += "#!ipxe\n";
			menu += "isset ${proxydhcp/dhcp-server} && set srv ${proxydhcp/dhcp-server} || set srv ${next-server}\n";
			menu += String.format("set cmdline_method root=/dev/nfs nfsroot=${srv}:/srv/ltsp "
					+ "ltsp.image=images/%s.img loop.max_part=9\n", imageName);
			menu += "set cmdline ${cmdline_method} ${cmdline_ltsp} ${cmdline_client}\n";
			menu += String.format("kernel http://${srv}/pxeboot/image/%s/vmlinuz "
					+ "initrd=ltsp.img initrd=initrd.img ${cmdline}\n", imageName);
			menu += "initrd http://${srv}/pxeboot/image/ltsp.img\n";
			menu += String.format("initrd http://${srv}/pxeboot/image/%s/initrd.img\n", imageName);
			menu += "boot || goto failed\n\n";
			menu += ":failed";
			menu += "boot || goto failed";
		}
		return menu;
	}

	public static String getMd5OfFile(String filePath) {
		MessageDigest digest;
		File file;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		file = new File(filePath);
		try {
			// Get file input stream for reading the file content
			FileInputStream fis = new FileInputStream(file);

			// Create byte array to read data in chunks
			byte[] byteArray = new byte[1024];
			int bytesCount = 0;

			// Read file data and update in message digest
			while ((bytesCount = fis.read(byteArray)) != -1) {
				digest.update(byteArray, 0, bytesCount);
			}
			;

			// close the stream; We don't need it now.
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		// Get the hash's bytes
		byte[] bytes = digest.digest();

		// This bytes[] has bytes in decimal format;
		// Convert it to hexadecimal format
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		// return complete hash
		return sb.toString();
	}

	/* size in MB */
	public static float getImageSize(String[] paths) {
		float size = 0;
		for (int i = 0; i < paths.length; i++) {
			try {
				size += Files.size(Paths.get(paths[i])) / 1024.0 / 1024;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return size;
	}

	public static String executeCommand(String[] cmdArray) {
		String output = "";
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(cmdArray);
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//			while (process.isAlive()) {
//				try {
//					TimeUnit.SECONDS.sleep(1);
//					System.out.println("Running...");
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
			String line = "";
			while ((line = inputReader.readLine()) != null) {
				output += line + "\n";
				System.out.println(line);
			}
			while ((line = errorReader.readLine()) != null) {
				output += line + "\n";
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return output;
	}

	public static String executeCommand(String[] cmdArray, String logFilePath) {
		String output = "";
		Process process = null;
		try {
			Files.deleteIfExists(Paths.get(logFilePath));
			Files.createFile(Paths.get(logFilePath));
			process = Runtime.getRuntime().exec(cmdArray);
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//			while (process.isAlive()) {
//				try {
//					TimeUnit.SECONDS.sleep(1);
//					System.out.println("Running...");
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
			String line = "";
			while ((line = inputReader.readLine()) != null) {
				output += line + "\n";
				Files.writeString(Paths.get(logFilePath), line + "\n", StandardOpenOption.WRITE,
						StandardOpenOption.APPEND);
				System.out.println(line);
			}
			while ((line = errorReader.readLine()) != null) {
				output += line + "\n";
				Files.writeString(Paths.get(logFilePath), line + "\n", StandardOpenOption.WRITE,
						StandardOpenOption.APPEND);
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return output;
	}

	public static void main(String[] args) {
		// executeCommand(new String[]{"/bin/sh", "-c", "ping 1.1.1.1 -c 2"},
		// "/root/Desktop/log.txt");
//		System.out.println("abcsajkfdA_12341 oi23u_".matches("(\\w)+"));
//		try {
//			Files.move(Paths.get("/srv/tftp/ltsp/kalix"), Paths.get("/var/www/html/ltsp/image/kalix"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
