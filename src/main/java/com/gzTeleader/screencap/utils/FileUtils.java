package com.gzTeleader.screencap.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class FileUtils {
	/**
	 * * 输出文件 * @param fis * @param toDocPath
	 */
	public static void saveByte(byte[] buffer, String filePath) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(filePath);
			fos.write(buffer);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除文件
	 * @param list
	 */
	public static void deleteFile(List<String> list) {
		File file = null;
		for(String path : list) {
			file = new File(path);
			if(file.exists()) {
				file.delete();
			}
		}
	}
	/**
	  * 关闭输入流
	  * @param is
	  */
	public static void closeStream(InputStream is) {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	  * 关闭输出流
	  * @param os
	  */
	public static void closeStream(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
