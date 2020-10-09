package com.fl.tools.common.utils;

import java.io.File;
import java.io.FileInputStream;
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
}
