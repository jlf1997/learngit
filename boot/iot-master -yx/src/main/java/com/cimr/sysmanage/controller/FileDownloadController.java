package com.cimr.sysmanage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@Controller
@RequestMapping(value = "/login/file")
public class FileDownloadController {
private static Logger logger = LoggerFactory.getLogger(FileDownloadController.class);


	
	@RequestMapping(value = "/download/{id}")
	public void downloadFile(@RequestParam(required = false) Long id, HttpSession session,
                             HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		try {
//			SysFile sysFile = sysFileService.getById(id);
//			if (sysFile == null) {
//				logger.error("The file is null when id = " + id);
//				throw new RuntimeException("下载的文件不存在");
//
//			}
			
//			if (sysFile.getSourceUrl() == null) {
//				logger.error("The file SourceUrl is null when id = " + id);
//				throw new RuntimeException("下载的文件链接不存在");
//
//			}
			byte[] returnByte = null;
//			byte[] returnByte = FastDFSService.downloadFile(sysFile.getSourceUrl());
			ServletOutputStream sout;

			sout = resp.getOutputStream();
			if (null != returnByte) {
//				String title = sysFile.getTitle();
//				String ext = "."+sysFile.getExt();
				String title = "";
				String ext = ".";
				if(title.indexOf(ext)==-1){
					title += ext;
				}
				
				title = toUtf8String(req,title);
				resp.setContentType("APPLICATION/OCTET-STREAM");
				resp.setCharacterEncoding("utf-8");
				resp.setHeader("Content-Disposition","attachment;filename=\""+title+"\"");
				sout.write(returnByte);
				sout.flush();
				sout.close();
			} else {
				logger.error("从分布式文件系统下载文件出错了.. " );
				throw new RuntimeException("从分布式文件系统下载文件出错了..");
				//System.out.println("从分布式文件系统下载文件出错了..");
			}
			
			// 更新下载次数，累加1
//
// .updateDownloadNum(id);
			
		} catch (Exception e) {
			logger.error("downloadFile'errors", e);
			logger.error("userid:{}的用户从分布式文件系统下载文件出错了..",id);
			e.printStackTrace();
			throw new RuntimeException("从分布式文件系统下载文件出错了..",e);
		}
	}
	
	
	
	public static String toUtf8String(String s) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    /**
     * 根据不同浏览器将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
     * 
     * @param s
     *            原文件名
     * @return 重新编码后的文件名
     */
    public static String toUtf8String(HttpServletRequest request, String s) {
        String agent = request.getHeader("User-Agent");
        try {
            boolean isFireFox = (agent != null && agent.toLowerCase().indexOf("firefox") != -1);
            if (isFireFox) {
                s = new String(s.getBytes("UTF-8"), "ISO8859-1");
            } else {
                s = toUtf8String(s);
                if ((agent != null && agent.indexOf("MSIE") != -1)) {
                    // see http://support.microsoft.com/default.aspx?kbid=816868
                    if (s.length() > 150) {
                        // 根据request的locale 得出可能的编码
                    	
                        //s = new String(s.getBytes("UTF-8"), "ISO8859-1");
                    }
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

	
}
