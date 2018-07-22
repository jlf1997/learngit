package com.cimr.api.video.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/video")
public class VideoPlayerController {

	

    @RequestMapping(value="/play",method=RequestMethod.GET)
    public FileSystemResource play() {
        File file = new File("C://001.mp4");
        return new FileSystemResource(file);
    }
    
    @RequestMapping(value = "/testDownload", method = RequestMethod.GET)
    public void Download(HttpServletResponse res) {
      String fileName = "001.mp4";
      res.setHeader("content-type", "application/octet-stream");
      res.setContentType("application/octet-stream");
      res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
      byte[] buff = new byte[1024];
      BufferedInputStream bis = null;
      OutputStream os = null;
      try {
        os = res.getOutputStream();
        bis = new BufferedInputStream(new FileInputStream(new File("C://"
            + fileName)));
        int i = bis.read(buff);
        while (i != -1) {
          os.write(buff, 0, buff.length);
          os.flush();
          i = bis.read(buff);
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (bis != null) {
          try {
            bis.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
      System.out.println("success");
    }
	
}
