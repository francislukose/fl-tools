package com.fl.tools.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtil {
	public static InputStream getFileStream(String fileLocation) {
		InputStream in = null;

		try {
			in = new FileInputStream(new File(fileLocation));
		} catch (Exception e) {
			in = FileUtil.class.getResourceAsStream(fileLocation);
		}
		return in;
	}

	public static void saveToDisk(byte[] data, String fileName, String dir) {
		if (data == null) {
			return;
		}
		File theDir = new File(dir);
		if (!theDir.exists()) {
			theDir.mkdirs();
		}

		File theFile = new File(dir, fileName);
		try (FileOutputStream fos = new FileOutputStream(theFile)) {
			fos.write(data);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static byte[] toBytes(InputStream is) {

		try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			int nRead;
			byte[] data = new byte[1024];
			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();

			return buffer.toByteArray();
		} catch (Exception e) {

		}

		return null;
	}
}
