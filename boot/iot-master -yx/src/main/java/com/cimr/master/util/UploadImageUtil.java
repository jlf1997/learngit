package com.cimr.master.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class UploadImageUtil {
//	public static Integer MAXLOGO = ConfigUtil.getIntValue("smbdp.maxLogo");//默认
//	public static Integer MIDDLELOGO = ConfigUtil.getIntValue("smbdp.middleLogo");//默认
//	public static Integer MINLOGO = ConfigUtil.getIntValue("smbdp.minLogo");//默认
//
//	public static Integer MAXGOODSLOGO = ConfigUtil.getIntValue("smbdp.goods.maxLogo");//产品大图
//	public static Integer MIDDLEGOODSLOGO = ConfigUtil.getIntValue("smbdp.goods.middleLogo");//产品中图
//	public static Integer MINGOODSLOGO = ConfigUtil.getIntValue("smbdp.goods.minLogo");//产品小图
//
//	public static Integer MAXCREDLOGO = ConfigUtil.getIntValue("smbdp.credentials.maxLogo");//产品大图
//	public static Integer MIDDLECREDLOGO = ConfigUtil.getIntValue("smbdp.credentials.middleLogo");//产品中图
//	public static Integer MINCREDLOGO = ConfigUtil.getIntValue("smbdp.credentials.minLogo");//产品小图
//
//	public static String Watermark_1 = ConfigUtil.getStringValue("smbdp.watermark_1_Path");//水印
//	public static String Watermark_2 = ConfigUtil.getStringValue("smbdp.watermark_2_Path");//水印
	public static String WatermarkKey = "watermark";
	private static Map<String, Object> logoWHMap = null;	//普通logo图片尺寸MAP
//	private static Map<String, Object> goodsLogoWHMap = null;	//产品相关的图片尺寸MAP
//	private static Map<String, Object> credLogoWHMap = null;	//证照相关的图片尺寸MAP
	
	static{
//		initLogoMap();
//		initGoodsLogoMap();
//		initCredLogoMap();
	}
	
	/**
	 * 普通logo尺寸组装MAP
	 * @author lkl
	 */
//	private static void initLogoMap(){
//		logoWHMap = new HashMap<String, Object>();
//		logoWHMap.put("MAXLOGO", MAXLOGO);
//		logoWHMap.put("MIDDLELOGO", MIDDLELOGO);
//		logoWHMap.put("MINLOGO", MINLOGO);
//	}
	
//	/**
//	 * 产品相关的图片尺寸组装MAP
//	 * @author lkl
//	 */
//	private static void initGoodsLogoMap(){
//		goodsLogoWHMap = new HashMap<String, Object>();
//		goodsLogoWHMap.put("MAXGOODSLOGO", MAXGOODSLOGO);
//		goodsLogoWHMap.put("MIDDLEGOODSLOGO", MIDDLEGOODSLOGO);
//		goodsLogoWHMap.put("MINGOODSLOGO", MINGOODSLOGO);
//	}
//	
//	/**
//	 * 证照相关的图片尺寸组装MAP
//	 * @author lkl
//	 */
//	private static void initCredLogoMap(){
//		credLogoWHMap = new HashMap<String, Object>();
//		credLogoWHMap.put("MAXLOGO", MAXCREDLOGO);
//		credLogoWHMap.put("MIDDLELOGO", MIDDLECREDLOGO);
//		credLogoWHMap.put("MINLOGO", MINCREDLOGO);
//	}

	/**
	 * 上传图片到本地(相册)
     * @param filePath	要处理图片的地址
     * @param filePath	本地服务器根目录
     * @param imageType	要处理图片类型：1为普通图片（白色水印、0.8透明度），2为证照图片（黑色水印、0.5透明度）
     * @param ipPress	是否打水印：true为打; false为不打;
     * @return
     */
    public static Map imageUpload(String filePath, String realPath, Integer imageType, boolean ipPress){
    	Map res = new HashMap();
    	File file = new File(filePath);
    	if (!file.exists()) {
            System.out.println("文件不存在");
//            res.setMes("文件不存在");
//            res.setResult(0);
            return res;
        }
    	Map<String, Object> map = new HashMap<String, Object>();
        try {
        	//
        	Map<String, Object> logoMap = logoWHMap;
        	if (new Integer(1).equals(imageType)) {
        		logoMap = logoWHMap;
//			} else if(new Integer(2).equals(imageType)){
//				logoMap = credLogoWHMap;
			}
			for (String key : logoMap.keySet()) {
				String path = "";
				if (ipPress) {
					String pressImg = "";
//					String pressImg = Watermark_1;
					String pressImgKey = "Watermark_key";
					int height = (Integer)logoMap.get(key);
					float alpha = 0.8f;
					if(new Integer(1).equals(imageType)){
						pressImg = "";
//						pressImg = Watermark_1;
						height = (Integer)logoMap.get(key);
						alpha = 0.8f;
					} else if(new Integer(2).equals(imageType)){
						pressImg = "";
//						pressImg = Watermark_2;
						height = (int)(((Integer)logoMap.get(key)) * 3/4);
						alpha = 0.5f;
					}
					path = UploadImageUtil.newFilePath(filePath,logoMap.get(key).toString(),pressImgKey);
					ImageUtil.scale2PressImage(realPath + pressImg, filePath, path, (Integer)logoMap.get(key), height, 1, 0, 0, alpha, true, ipPress);
				}else {
					path = UploadImageUtil.newFilePath(filePath,logoMap.get(key).toString(), null);
					ImageUtil.scale(filePath, path, (Integer)logoWHMap.get(key), (Integer)logoWHMap.get(key), true);
				}
				map.put(key, path); 
			}
//			res.setResult(1);
//			res.setModel(map);
		} catch (Exception e) {
			e.printStackTrace();
//			res.setResult(0);
//			res.setMes("文件上传失败");
			return res;
		}
    	return res;
    }

    /**
     * 获取新图片名
     * @param filePath	地址
     * @param str	图片大小关键字
     * @param press	图片是否存在水印关键字
     * @return
     */
    public static String newFilePath(String filePath, String str, String press) {
    	String kz = filePath.substring(filePath.lastIndexOf("."), filePath.length());
    	String fileState = filePath.substring(0, filePath.lastIndexOf("."));
    	if(press != null && !"".equals(press)){
    		return fileState + "_" + str + "_" + press + kz;
    	}
    	return fileState + "_" + str + kz;
    }

}
