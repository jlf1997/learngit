package com.cimr.master.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cimr.comm.config.AppFileProperties;

/**
 * 类描述:本地文件管理工具类 作者:admin 创建时间:2018年4月25日 下午2:15:24
 */
@Component
public class LocalFileService {

	private static final String DIR_SPLIT = "/";
	
	public static final String LOCATION = AppFileProperties.getBasePath();

//	private static final Log LOG = LogFactory.get();
	
	private static final Logger log = LoggerFactory.getLogger(LocalFileService.class);

	
	/**
	 * 方法描述: 获取文件存储路径
	 * @return
	 * 		String
	 * 作者:    admin
	 * 创建时间: 2018年4月25日 下午3:19:55
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	private static String getLocationPath(){
        String root = LocalFileService.class.getResource("/").toString();
        String dir = root.substring(6, root.length() - 17) + DIR_SPLIT + "fileUpload";
        return dir;
    }

	/**
	 * 方法描述: 分类保存文件到本地
	 * 
	 * @param dir
	 * @param fileName
	 * @param istream
	 * @return String 作者: admin 创建时间: 2018年4月25日 下午2:27:53 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	public static String SaveFileByPhysicalDir(String fileName, InputStream istream) {
		// 文件格式
		String ext = "";
		if (fileName.lastIndexOf(".") > 0) {
			ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		}
		String dirPath = LOCATION + DIR_SPLIT + ext;
		String simpleFileName = UUIDGenerator.createUUID() + "." + ext;
		String realFileName = dirPath + DIR_SPLIT + simpleFileName;
		try {
			if (!CreateDirectory(dirPath)) {
				return null;
			}

			System.out.println(realFileName);
			OutputStream os = new FileOutputStream(realFileName);
			int readBytes = 0;
			byte[] buffer = new byte[' '];
			while ((readBytes = istream.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, readBytes);
			}
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String sourcefileName = ext + DIR_SPLIT + simpleFileName;
		return sourcefileName;
	}
	
	/**
	 * 方法描述: 分类保存文件到本地
	 *
	 * @return String 作者: admin 创建时间: 2018年4月25日 下午2:27:53 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	public static String SaveFileByPhysicalDir(String originalPath, String ext) {
		String dirPath = LOCATION + DIR_SPLIT ;
		String simpleFileName = UUIDGenerator.createUUID() + "." + ext;
		String realFileName = dirPath + DIR_SPLIT + simpleFileName;
		try {
			if (!CreateDirectory(dirPath)) {
				return null;
			}
			File file = new File(originalPath);
			FileInputStream fis = new FileInputStream(file);
			System.out.println(realFileName);
			OutputStream os = new FileOutputStream(realFileName);
			int readBytes = 0;
			byte[] buffer = new byte[' '];
			while ((readBytes = fis.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, readBytes);
			}
			fis.close();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		String sourcefileName = ext + DIR_SPLIT + simpleFileName;
		String sourcefileName = simpleFileName;
		return sourcefileName;
	}
	
	/**
	 * 方法描述: 删除文件
	 * @param dir
	 * @param fileName
	 * @return
	 * 		boolean
	 * 作者:    admin
	 * 创建时间: 2018年4月25日 下午2:48:11
	 * 修改人:
	 * 修改时间:
	 * 修改内容:
	 * 修改次数: 0
	 */
	public static boolean deleteFile(String sourcefileName) {
		if (StringUtil.empty(sourcefileName)) {
			return false;
		}
		String realFileName = LOCATION + DIR_SPLIT + sourcefileName;
		return deleteFromName(realFileName);
	}

	/**
	 * 方法描述: 创建文件夹
	 * 
	 * @param dir
	 * @return boolean 作者: admin 创建时间: 2018年4月25日 下午2:29:45 修改人: 修改时间: 修改内容:
	 *         修改次数: 0
	 */
	public static boolean CreateDirectory(String dir) {
		File f = new File(dir);
		if (!f.exists()) {
			f.mkdirs();
		}
		return true;
	}

	public static void saveAsFileOutputStream(String physicalPath, String content) {
		File file = new File(physicalPath);
		boolean b = file.getParentFile().isDirectory();
		if (!b) {
			File tem = new File(file.getParent());

			tem.mkdirs();
		}
		log.info(file.getParent() + ";" + file.getParentFile().isDirectory());
		FileOutputStream foutput = null;
		try {
			foutput = new FileOutputStream(physicalPath);

			foutput.write(content.getBytes("UTF-8"));

			try {
				foutput.flush();
				foutput.close();
			} catch (IOException ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}

			log.info("文件保存成功:" + physicalPath);
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				foutput.flush();
				foutput.close();
			} catch (IOException ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}
	}

	public boolean copyToFile(String srcFile, String desFile) {
		File scrfile = new File(srcFile);
		if (scrfile.isFile() == true) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(scrfile);
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
			File desfile = new File(desFile);

			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(desfile, false);
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
			desfile = null;
			int length = (int) scrfile.length();
			byte[] b = new byte[length];
			try {
				fis.read(b);
				fis.close();
				fos.write(b);
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			scrfile = null;
			return false;
		}
		scrfile = null;
		return true;
	}

	public boolean copyDir(String sourceDir, String destDir) {
		File sourceFile = new File(sourceDir);

		File[] files = sourceFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i].getName();
			String tempSource = sourceDir + "/" + fileName;
			String tempDest = destDir + "/" + fileName;
			if (files[i].isFile()) {
				copyToFile(tempSource, tempDest);
			} else {
				copyDir(tempSource, tempDest);
			}
		}
		sourceFile = null;
		return true;
	}

	public boolean deleteDirectory(File dir) {
		File[] entries = dir.listFiles();
		int sz = entries.length;
		for (int i = 0; i < sz; i++) {
			if (entries[i].isDirectory()) {
				if (!deleteDirectory(entries[i])) {
					return false;
				}
			} else if (!entries[i].delete()) {
				return false;
			}
		}

		if (!dir.delete()) {
			return false;
		}
		return true;
	}

	public static boolean checkExist(String sFileName) {
		boolean result = false;
		try {
			File f = new File(sFileName);

			if ((f.exists()) && (f.isFile())) {
				result = true;
			} else {
				result = false;
			}
		} catch (Exception e) {
			result = false;
		}

		return result;
	}

	public static long getSize(String sFileName) {
		long lSize = 0L;
		try {
			File f = new File(sFileName);

			if (f.exists()) {
				if ((f.isFile()) && (f.canRead())) {
					lSize = f.length();
				} else {
					lSize = -1L;
				}
			} else {
				lSize = 0L;
			}
		} catch (Exception e) {
			lSize = -1L;
		}

		return lSize;
	}

	public static boolean deleteFromName(String sFileName) {
		boolean bReturn = true;
		try {
			File oFile = new File(sFileName);

			if (oFile.exists()) {
				boolean bResult = oFile.delete();

				if (!bResult) {
					bReturn = false;
				}

			}
		} catch (Exception e) {
			bReturn = false;
		}
		return bReturn;
	}

	public static void releaseZip(String sToPath, String sZipFile) throws Exception {
		if ((null == sToPath) || ("".equals(sToPath.trim()))) {
			File objZipFile = new File(sZipFile);
			sToPath = objZipFile.getParent();
		}
		ZipFile zfile = new ZipFile(sZipFile);
		java.util.Enumeration zList = zfile.entries();
		ZipEntry ze = null;
		byte[] buf = new byte['Ѐ'];
		while (zList.hasMoreElements()) {
			ze = (ZipEntry) zList.nextElement();
			if (!ze.isDirectory()) {

				OutputStream os = new java.io.BufferedOutputStream(new FileOutputStream(getRealFileName(sToPath, ze.getName())));
				InputStream is = new java.io.BufferedInputStream(zfile.getInputStream(ze));
				int readLen = 0;
				while ((readLen = is.read(buf, 0, 1024)) != -1) {
					os.write(buf, 0, readLen);
				}
				is.close();
				os.close();
			}
		}
		zfile.close();
	}

	private static File getRealFileName(String baseDir, String absFileName) throws Exception {
		File ret = null;

		List dirs = new java.util.ArrayList();
		StringTokenizer st = new StringTokenizer(absFileName, System.getProperty("file.separator"));
		while (st.hasMoreTokens()) {
			dirs.add(st.nextToken());
		}

		ret = new File(baseDir);
		if (dirs.size() > 1) {
			for (int i = 0; i < dirs.size() - 1; i++) {
				ret = new File(ret, (String) dirs.get(i));
			}
		}
		if (!ret.exists()) {
			ret.mkdirs();
		}
		ret = new File(ret, (String) dirs.get(dirs.size() - 1));
		return ret;
	}

	public static void copyFile(String srcFile, String targetFile) throws IOException {
		FileInputStream reader = new FileInputStream(srcFile);
		FileOutputStream writer = new FileOutputStream(targetFile);

		byte[] buffer = new byte['က'];

		try {
			reader = new FileInputStream(srcFile);
			writer = new FileOutputStream(targetFile);
			int len;
			while ((len = reader.read(buffer)) > 0) {
				writer.write(buffer, 0, len);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (writer != null)
				writer.close();
			if (reader != null) {
				reader.close();
			}
		}
	}

	public static void renameFile(String srcFile, String targetFile) throws IOException {
		try {
			copyFile(srcFile, targetFile);
			deleteFromName(srcFile);
		} catch (IOException e) {
			throw e;
		}
	}

	public static void write(String tivoliMsg, String logFileName) {
		try {
			byte[] bMsg = tivoliMsg.getBytes();
			FileOutputStream fOut = new FileOutputStream(logFileName, true);
			fOut.write(bMsg);
			fOut.close();
		} catch (IOException e) {
		}
	}

	public static void writeLog(String logFile, String batchId, String exceptionInfo) {
		java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance(2, 2, java.util.Locale.JAPANESE);

		Object[] args = { df.format(new java.util.Date()), batchId, exceptionInfo };

		String fmtMsg = java.text.MessageFormat.format("{0} : {1} : {2}", args);

		try {
			File logfile = new File(logFile);
			if (!logfile.exists()) {
				logfile.createNewFile();
			}

			FileWriter fw = new FileWriter(logFile, true);
			fw.write(fmtMsg);
			fw.write(System.getProperty("line.separator"));

			fw.flush();
			fw.close();
		} catch (Exception e) {
		}
	}

	public static String readTextFile(String realPath) throws Exception {
		File file = new File(realPath);
		if (!file.exists()) {
			System.out.println("File not exist!");
			return null;
		}
		BufferedReader br = new BufferedReader(new java.io.FileReader(realPath));
		String temp = "";
		String txt = "";
		while ((temp = br.readLine()) != null) {
			txt = txt + temp;
		}
		br.close();
		return txt;
	}

	/**
	 * 获得指定文件的byte数组
	 */
	public static byte[] getBytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * 根据byte数组，生成文件
	 */
	public static void getFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath + "\\" + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
