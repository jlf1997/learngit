package com.cimr.master.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageUtil
{
  public static final void scale(String srcImageFile, String result, int height, int width, boolean bb)
  {
    try
    {
      double ratio = 0.0D;
      File f = new File(srcImageFile);
      BufferedImage bi = ImageIO.read(f);
      Image itemp = bi.getScaledInstance(width, height, 4);
      
      if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
        if (bi.getHeight() > bi.getWidth()) {
          ratio = new Integer(height).doubleValue() / bi.getHeight();
        }
        else {
          ratio = new Integer(width).doubleValue() / bi.getWidth();
        }
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
        itemp = op.filter(bi, null);
      }
      if (bb) {
        BufferedImage image = new BufferedImage(width, height, 1);
        
        Graphics2D g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        if (width == itemp.getWidth(null)) {
          g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
        }
        else
        {
          g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
        }
        
        g.dispose();
        itemp = image;
      }
      ImageIO.write((BufferedImage)itemp, "JPEG", new File(result));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  












  public static final void scalePressImage(String pressImg, String srcImageFile, String destImageFile, int height, int width, float quality, int x, int y, float alpha, boolean bb)
  {
    try
    {
      double ratio = 0.0D;
      File f = new File(srcImageFile);
      BufferedImage bi = ImageIO.read(f);
      Image itemp = bi.getScaledInstance(width, height, 4);
      
      if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
        if (bi.getHeight() > bi.getWidth()) {
          ratio = new Integer(height).doubleValue() / bi.getHeight();
        }
        else {
          ratio = new Integer(width).doubleValue() / bi.getWidth();
        }
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
        
        itemp = op.filter(bi, null);
      }
      if (bb)
      {
        BufferedImage image = new BufferedImage(width, height, 1);
        Graphics2D g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        if (width == itemp.getWidth(null)) {
          g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
        } else {
          g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
        }
        

        Image src_biao = ImageIO.read(new File(pressImg));
        int wideth_biao = src_biao.getWidth(null);
        int height_biao = src_biao.getHeight(null);
        g.setComposite(AlphaComposite.getInstance(10, alpha));
        
        g.drawImage(src_biao, (width - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);
        
        g.dispose();
        itemp = image;
      }
      ImageIO.write((BufferedImage)itemp, "JPEG", new File(destImageFile));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  












  public static final void scale2PressImage(String pressImg, String srcImageFile, String destImageFile, int width, int height, float quality, int x, int y, float alpha, boolean bb, boolean isPress)
  {
    try
    {
      double ratio = 0.0D;
      File f = new File(srcImageFile);
      BufferedImage bi = ImageIO.read(f);
      int imgw = bi.getWidth();
      int imgh = bi.getHeight();
      
      if (width + height == 0) {
        width = imgw;
        height = imgh;
      } else if (height == 0) {
        height = imgh * width / imgw;
      }
      Image itemp = bi.getScaledInstance(width, height, 4);
      
      if ((imgw < width) && (imgh < height)) {
        scale3PressImage(pressImg, srcImageFile, destImageFile, width, height, quality, x, y, alpha, bb, isPress);
      }
      else {
        if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
          if (bi.getHeight() > bi.getWidth()) {
            ratio = new Integer(height).doubleValue() / bi.getHeight();
          }
          else {
            ratio = new Integer(width).doubleValue() / bi.getWidth();
          }
          AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
          
          itemp = op.filter(bi, null);
        }
        if (bb)
        {
          BufferedImage image = new BufferedImage(width, height, 1);
          Graphics2D g = image.createGraphics();
          g.setColor(Color.white);
          g.fillRect(0, 0, width, height);
          if (width == itemp.getWidth(null)) {
            g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
          } else {
            g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
          }
          if (isPress)
          {
            Image src_biao = ImageIO.read(new File(pressImg));
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(10, alpha));
            
            g.drawImage(src_biao, (width - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);
          }
          
          g.dispose();
          itemp = image;
        }
        ImageIO.write((BufferedImage)itemp, "JPEG", new File(destImageFile));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  












  public static final void scale3PressImage(String pressImg, String srcImageFile, String destImageFile, int width, int height, float quality, int x, int y, float alpha, boolean bb, boolean isPress)
  {
    try
    {
      File f = new File(srcImageFile);
      BufferedImage bi = ImageIO.read(f);
      BufferedImage image = new BufferedImage(width, height, 1);
      
      Graphics2D g = image.createGraphics();
      g.setColor(Color.white);
      g.fillRect(0, 0, width, height);
      g.drawImage(bi, (width - bi.getWidth()) / 2, (height - bi.getHeight()) / 2, bi.getWidth(), bi.getHeight(), Color.white, null);
      

      if (isPress) {
        Image src_biao = ImageIO.read(new File(pressImg));
        int wideth_biao = src_biao.getWidth(null);
        int height_biao = src_biao.getHeight(null);
        g.setComposite(AlphaComposite.getInstance(10, alpha));
        g.drawImage(src_biao, (width - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);
      }
      

      g.dispose();
      
      ImageIO.write(image, "JPEG", new File(destImageFile));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  








  public static void cutCenterImage(String src, String dest, int w, int h)
    throws IOException
  {
    Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
    ImageReader reader = (ImageReader)iterator.next();
    InputStream in = new FileInputStream(src);
    ImageInputStream iis = ImageIO.createImageInputStream(in);
    reader.setInput(iis, true);
    ImageReadParam param = reader.getDefaultReadParam();
    int imageIndex = 0;
    Rectangle rect = new Rectangle((reader.getWidth(imageIndex) - w) / 2, (reader.getHeight(imageIndex) - h) / 2, w, h);
    
    param.setSourceRegion(rect);
    BufferedImage bi = reader.read(0, param);
    ImageIO.write(bi, "jpg", new File(dest));
  }
  





  public static void cutHalfImage(String src, String dest)
    throws IOException
  {
    Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
    ImageReader reader = (ImageReader)iterator.next();
    InputStream in = new FileInputStream(src);
    ImageInputStream iis = ImageIO.createImageInputStream(in);
    reader.setInput(iis, true);
    ImageReadParam param = reader.getDefaultReadParam();
    int imageIndex = 0;
    int width = reader.getWidth(imageIndex) / 2;
    int height = reader.getHeight(imageIndex) / 2;
    Rectangle rect = new Rectangle(width / 2, height / 2, width, width);
    param.setSourceRegion(rect);
    BufferedImage bi = reader.read(0, param);
    ImageIO.write(bi, "jpg", new File(dest));
  }
  















  public static final void cardImage(String srcImageFile, String destImageFile, String name, String sex, String title, String mobile, String companyName, String telPhone, String address, String fontName, float alpha)
  {
    try
    {
      File img = new File(srcImageFile);
      Image src = ImageIO.read(img);
      int width = src.getWidth(null);
      int height = src.getHeight(null);
      BufferedImage image = new BufferedImage(width, height, 1);
      
      Graphics2D g = image.createGraphics();
      g.drawImage(src, 0, 0, width, height, null);
      Color color = new Color(70, 175, 215);
      
      int x = 50;
      int y = 60;
      Integer fontSize = Integer.valueOf(25);
      g.setColor(color);
      g.setFont(new Font(fontName, 1, fontSize.intValue()));
      g.setComposite(AlphaComposite.getInstance(10, alpha));
      
      g.drawString(name, x, y);
      


      int sexX = 50 + getLength(name) * fontSize.intValue() + 20;
      y = 60;
      fontSize = Integer.valueOf(20);
      g.setColor(new Color(70, 175, 215));
      g.setFont(new Font(fontName, 1, fontSize.intValue()));
      g.setComposite(AlphaComposite.getInstance(10, alpha));
      
      g.drawString(sex, sexX, y);
      


      x = sexX + 50;
      y = 60;
      fontSize = Integer.valueOf(20);
      g.setColor(color);
      g.setFont(new Font(fontName, 1, fontSize.intValue()));
      g.setComposite(AlphaComposite.getInstance(10, alpha));
      
      g.drawString(title, x, y);
      


      x = 50;
      y = 105;
      fontSize = Integer.valueOf(20);
      g.setColor(Color.black);
      g.setFont(new Font(fontName, 1, fontSize.intValue()));
      g.setComposite(AlphaComposite.getInstance(10, alpha));
      mobile = "手机：" + mobile;
      
      g.drawString(mobile, x, y);
      


      x = 50;
      y = 175;
      fontSize = Integer.valueOf(25);
      g.setColor(Color.white);
      g.setFont(new Font(fontName, 1, fontSize.intValue()));
      g.setComposite(AlphaComposite.getInstance(10, alpha));
      
      g.drawString(companyName, x, y);
      


      x = 50;
      y = 240;
      fontSize = Integer.valueOf(20);
      g.setColor(Color.black);
      g.setFont(new Font(fontName, 1, fontSize.intValue()));
      g.setComposite(AlphaComposite.getInstance(10, alpha));
      telPhone = "电话：" + telPhone;
      
      g.drawString(telPhone, x, y);
      


      x = 50;
      y = 280;
      fontSize = Integer.valueOf(20);
      g.setColor(Color.black);
      g.setFont(new Font(fontName, 1, fontSize.intValue()));
      g.setComposite(AlphaComposite.getInstance(10, alpha));
      address = "地址：" + address;
      
      g.drawString(address, x, y);
      

      g.dispose();
      ImageIO.write(image, "JPEG", new File(destImageFile));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  


  public static void cutImage(String src, String dest, int x, int y, int w, int h)
    throws IOException
  {
    Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
    ImageReader reader = (ImageReader)iterator.next();
    InputStream in = new FileInputStream(src);
    ImageInputStream iis = ImageIO.createImageInputStream(in);
    reader.setInput(iis, true);
    ImageReadParam param = reader.getDefaultReadParam();
    Rectangle rect = new Rectangle(x, y, w, h);
    param.setSourceRegion(rect);
    BufferedImage bi = reader.read(0, param);
    ImageIO.write(bi, "jpg", new File(dest));
  }
  









  public static final void pressImage(String pressImg, String srcImageFile, String destImageFile, int x, int y, float alpha)
  {
    try
    {
      File img = new File(srcImageFile);
      Image src = ImageIO.read(img);
      int wideth = src.getWidth(null);
      int height = src.getHeight(null);
      BufferedImage image = new BufferedImage(wideth, height, 1);
      
      Graphics2D g = image.createGraphics();
      g.drawImage(src, 0, 0, wideth, height, null);
      
      Image src_biao = ImageIO.read(new File(pressImg));
      int wideth_biao = src_biao.getWidth(null);
      int height_biao = src_biao.getHeight(null);
      g.setComposite(AlphaComposite.getInstance(10, alpha));
      
      g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);
      

      g.dispose();
      ImageIO.write(image, "JPEG", new File(destImageFile));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  













  public static void pressText(String pressText, String targetImg, String fontName, int fontStyle, Color color, int fontSize, int x, int y, float alpha)
  {
    try
    {
      File img = new File(targetImg);
      Image src = ImageIO.read(img);
      int width = src.getWidth(null);
      int height = src.getHeight(null);
      BufferedImage image = new BufferedImage(width, height, 1);
      
      Graphics2D g = image.createGraphics();
      g.drawImage(src, 0, 0, width, height, null);
      g.setColor(color);
      g.setFont(new Font(fontName, fontStyle, fontSize));
      g.setComposite(AlphaComposite.getInstance(10, alpha));
      
      g.drawString(pressText, (width - getLength(pressText) * fontSize) / 2 + x, (height - fontSize) / 2 + y);
      
      g.dispose();
      ImageIO.write(image, "jpg", img);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  public static int getLength(String text) {
    int length = 0;
    for (int i = 0; i < text.length(); i++) {
      if (new String(text.charAt(i) + "").getBytes().length > 1) {
        length += 2;
      } else {
        length++;
      }
    }
    return length / 2;
  }
  






  public static String newFilePath(String filePath, String str)
  {
    String kz = filePath.substring(filePath.lastIndexOf("."), filePath.length());
    
    String fileState = filePath.substring(0, filePath.lastIndexOf("."));
    return fileState + "_" + str + kz;
  }
  
  public static Integer[] newImageWH(Integer isWH, String str) {
    String[] strs = str.split("x");
    Integer[] whArr = new Integer[strs.length];
    if (isWH.equals(Integer.valueOf(1))) {
      whArr[0] = Integer.valueOf(strs[0]);
      whArr[1] = null;
    } else {
      whArr[0] = Integer.valueOf(strs[1]);
      whArr[1] = null;
    }
    return whArr;
  }
  
  public static Integer getImageNewWH(Integer isWH, String str) {
    String[] strs = str.split("x");
    Integer wh = Integer.valueOf(0);
    if (isWH.equals(Integer.valueOf(1))) {
      wh = Integer.valueOf(strs[0]);
    } else {
      wh = Integer.valueOf(strs[1]);
    }
    return wh;
  }
  






  public static Integer isWvsH(Integer width, Integer height)
  {
    Integer iswh = Integer.valueOf(1);
    if (width.intValue() > height.intValue()) {
      iswh = Integer.valueOf(1);
    } else if (width.intValue() < height.intValue()) {
      iswh = Integer.valueOf(2);
    } else {
      iswh = Integer.valueOf(1);
    }
    return iswh;
  }
  





  public static void rotateImage(String targetImg, int angel)
  {
    try
    {
      File img = new File(targetImg);
      Image src = ImageIO.read(img);
      ImageIO.write(rotate(src, angel), "jpg", img);
    } catch (Exception e) {
      System.out.println("旋转图片出现错误ImageUtil.rotateImage");
    }
  }
  









  public static BufferedImage rotate(String targetImg, int angel)
    throws IOException
  {
    File img = new File(targetImg);
    
    return rotate(img, angel);
  }
  







  public static BufferedImage rotate(File img, int angel)
    throws IOException
  {
    Image src = ImageIO.read(img);
    
    return rotate(src, angel);
  }
  








  public static BufferedImage rotate(Image src, int angel)
  {
    int src_width = src.getWidth(null);
    int src_height = src.getHeight(null);
    if (angel > 360) {
      angel %= 360;
    }
    if (angel < 0) {
      angel += 360;
    }
    
    Rectangle rect_des = CalcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);
    

    BufferedImage res = null;
    res = new BufferedImage(rect_des.width, rect_des.height, 1);
    
    Graphics2D g2 = res.createGraphics();
    
    g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
    
    g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
    
    g2.drawImage(src, null, null);
    return res;
  }
  
  public static Rectangle CalcRotatedSize(Rectangle src, int angel)
  {
    if (angel >= 90) {
      if (angel / 90 % 2 == 1) {
        int temp = src.height;
        src.height = src.width;
        src.width = temp;
      }
      angel %= 90;
    }
    
    double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2.0D;
    double len = 2.0D * Math.sin(Math.toRadians(angel) / 2.0D) * r;
    double angel_alpha = (3.141592653589793D - Math.toRadians(angel)) / 2.0D;
    double angel_dalta_width = Math.atan(src.height / src.width);
    double angel_dalta_height = Math.atan(src.width / src.height);
    
    int len_dalta_width = (int)(len * Math.cos(3.141592653589793D - angel_alpha - angel_dalta_width));
    
    int len_dalta_height = (int)(len * Math.cos(3.141592653589793D - angel_alpha - angel_dalta_height));
    
    int des_width = src.width + len_dalta_width * 2;
    int des_height = src.height + len_dalta_height * 2;
    return new Rectangle(new Dimension(des_width, des_height));
  }
  
//  public static void main(String[] args) throws IOException
//  {
//    scale2PressImage("E:/images/testZipImage/zipimgtest/watermark2.png", "E:/images/testZipImage/zipimgtest/test5/1.jpg", "E:/images/testZipImage/zipimgtest/test5/1-test2-(4-3)-1000X750.jpg", 1000, 750, 1.0F, 0, 0, 0.5F, true, false);
//    scale2PressImage("E:/images/testZipImage/zipimgtest/watermark2.png", "E:/images/testZipImage/zipimgtest/test5/1.jpg", "E:/images/testZipImage/zipimgtest/test5/a1-test2-(4-3)-1000X750.0.5.jpg", 1000, 750, 1.0F, 0, 0, 0.5F, true, true);
//    scale2PressImage("E:/images/testZipImage/zipimgtest/watermark2.png", "E:/images/testZipImage/zipimgtest/test5/1-test2-(4-3)-1000X750.jpg", "E:/images/testZipImage/zipimgtest/test5/a1-test2-(4-3)-600X450.0.5.jpg", 600, 450, 1.0F, 0, 0, 0.5F, true, true);
//    scale2PressImage("E:/images/testZipImage/zipimgtest/watermark1.png", "E:/images/testZipImage/zipimgtest/test5/1-test2-(4-3)-1000X750.jpg", "E:/images/testZipImage/zipimgtest/test5/a1-test1-(4-3)-400X300.jpg", 400, 300, 1.0F, 0, 0, 0.5F, true, false);
//    scale2PressImage("E:/images/testZipImage/zipimgtest/watermark2.png", "E:/images/testZipImage/zipimgtest/test5/1.jpg", "E:/images/testZipImage/zipimgtest/test5/a1-test2-(4-3)-1000X750.0.5.1.jpg", 1000, 750, 1.0F, 0, 0, 0.5F, true, true);
//    scale2PressImage("E:/images/testZipImage/zipimgtest/watermark2.png", "E:/images/testZipImage/zipimgtest/test5/1.jpg", "E:/images/testZipImage/zipimgtest/test5/a1-test2-(4-3)-600X450.0.5.1.jpg", 600, 450, 1.0F, 0, 0, 0.5F, true, true);
//    scale2PressImage("E:/images/testZipImage/zipimgtest/watermark1.png", "E:/images/testZipImage/zipimgtest/test5/1.jpg", "E:/images/testZipImage/zipimgtest/test5/a1-test1-(4-3)-400X300.1.jpg", 400, 300, 1.0F, 0, 0, 0.5F, true, false);
//  }
}
