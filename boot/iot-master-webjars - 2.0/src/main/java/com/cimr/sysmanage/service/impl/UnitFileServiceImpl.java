package com.cimr.sysmanage.service.impl;

import com.cimr.comm.config.AppFileProperties;
import com.cimr.sysmanage.dao.UnitFileDao;
import com.cimr.sysmanage.dto.FileDto;
import com.cimr.sysmanage.model.UnitFile;
import com.cimr.sysmanage.service.UnitFileService;
import com.cimr.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by suhuanzhao on 2017/12/25.
 */
@Service
public class UnitFileServiceImpl implements UnitFileService {

	@Autowired
	private UnitFileDao dao;

	@Override
	public List<FileDto> saveFileTemp(MultipartFile[] mfs, String realPath) {
		// 获取文件的缓存路径
		String upath = AppFileProperties.getTempPath();
		File uploadFilePath = new File(realPath + upath);
		if (!uploadFilePath.exists()) {
			uploadFilePath.mkdirs();
		}
		String minLogoUrl = "";
		List<FileDto> fileDtoList = new ArrayList<FileDto>();
		FileDto fileDto = null;
		try {
			// 循环每一张上传的图片
			for (MultipartFile mf : mfs) {
				fileDto = new FileDto();
				// 获取文件的原文件名
				String fname = mf.getOriginalFilename();
				System.out.println("类型:" + mf.getContentType());
				// 缓存文件的原文件名
				String realName = fname;
				// 文件格式
				String ext;
				if (fname.lastIndexOf(".") > 0) {
					ext = fname.substring(fname.lastIndexOf(".") + 1, fname.length());
				} else {
					ext = "";
				}
				// 生成服务器上的文件名称
				fname = System.currentTimeMillis() + RandomUtil.random(6) + "_" + fname;
				fileDto.setOriginalName(realName);
				fileDto.setNewFileName(fname);
				fname = upath + fname;
				// 保存文件到服务器本地指定路径
				boolean b = FileUtil.SaveFileByPhysicalDir(realPath, fname, mf.getInputStream());
				fileDto.setSourceUrl(realPath + fname);
				// 如果是图片，则获取该图片的高宽以及做一些压缩处理
//				if (b && mf.getContentType().indexOf("image") != -1) {
//					BufferedImage img = ImageIO.read(mf.getInputStream());
//					int width = img.getWidth();
//					int height = img.getHeight();
//					System.out.println("width:" + width + ",height:" + height);
//					fileDto.setImgWidth(width);
//					fileDto.setImgHeight(height);
//					if (width >= 1920 && height >= 1080) {
//						minLogoUrl = realPath + fname.substring(0, fname.lastIndexOf(".")) + "MinLogo." + ext;
//						System.out.println("resultUrl:" + minLogoUrl);
//						// 图片按照给定的宽度和高度裁剪压缩
//						ImageUtil.scale(realPath + fname, minLogoUrl, width / 2, 1080 / 2, false);
//						fileDto.setMinLogoUrl(minLogoUrl);
//					}
//				}

				fileDtoList.add(fileDto);
			}
			return fileDtoList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileDtoList;
	}

	@Override
	public List<UnitFile> uploadFilesToServer(List<FileDto> fileDtos, String realPath, String userId, String userType, String resId, String resType, String opeType) {
		List<UnitFile> unitFileList = new ArrayList<UnitFile>();
		try {
			String ext;
			UnitFile unitFile = null;
			for (FileDto fileDto : fileDtos) {
				String fname = fileDto.getNewFileName();
				if (fname.lastIndexOf(".") > 0) {
					ext = fname.substring(fname.lastIndexOf(".") + 1, fname.length());
				} else {
					ext = "";
				}
				File sourceFile = null;
				File minLogoFile = null;
				File middleLogoFile = null;
				File maxLogoFile = null;
				unitFile = new UnitFile();
				unitFile.setId(IdUtil.getId());
				unitFile.setExt(ext);
				unitFile.setDescript(fileDto.getDescript());
				unitFile.setTitle(fileDto.getOriginalName());
				unitFile.setUserId(userId);
				unitFile.setResId(resId);
				unitFile.setResType(resType);
				unitFile.setUserType(userType);
				unitFile.setOpeType(opeType);
				unitFile.setCreateTime(new Date());
				unitFile.setUpdateTime(new Date());
				// 如果是图片则存储图片的高宽
				if (fileDto.getImgHeight() != null && fileDto.getImgHeight() > 0) {
					unitFile.setHeight(fileDto.getImgHeight());
				}

				if (fileDto.getImgWidth() != null && fileDto.getImgWidth() > 0) {
					unitFile.setWidth(fileDto.getImgWidth());
				}

				// 上传图片源文件,上传后删除源文件
				if (fileDto.getSourceUrl() != null && !"".equals(fileDto.getSourceUrl())) {
					String path = LocalFileService.SaveFileByPhysicalDir(fileDto.getSourceUrl(), ext);
					unitFile.setSourceUrl(path);
					sourceFile = new File(fileDto.getSourceUrl());
					if (sourceFile.exists()) {
						sourceFile.delete();
					}
				}
				// 上传小图LOGO
				if (fileDto.getMinLogoUrl() != null && !"".equals(fileDto.getMinLogoUrl())) {
//					String path = LocalFileService.SaveFileByPhysicalDir(fileDto.getMinLogoUrl(), ext);
//					unitFile.setMinLogoUrl(path);
//					minLogoFile = new File(fileDto.getMinLogoUrl());
//					if (minLogoFile.exists()) {
//						minLogoFile.delete();
//					}
				}
				// 上传中图LOGO
				if (fileDto.getMiddleLogoUrl() != null && !"".equals(fileDto.getMiddleLogoUrl())) {
//					String path = LocalFileService.SaveFileByPhysicalDir(fileDto.getMiddleLogoUrl(), ext);
//					unitFile.setMiddleLogoUrl(path);
//					middleLogoFile = new File(fileDto.getMiddleLogoUrl());
//					if (middleLogoFile.exists()) {
//						middleLogoFile.delete();
//					}
				}
				// 上传大图片LOGO
				if (fileDto.getMaxLogoUrl() != null && !"".equals(fileDto.getMaxLogoUrl())) {
//					String path = LocalFileService.SaveFileByPhysicalDir(fileDto.getMaxLogoUrl(), ext);
//					unitFile.setMaxLogoUrl(path);
//					maxLogoFile = new File(fileDto.getMaxLogoUrl());
//					if (maxLogoFile.exists()) {
//						maxLogoFile.delete();
//					}
				}
				this.insertNonEmptyObj_common(unitFile);
				unitFileList.add(unitFile);
			}
			return unitFileList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unitFileList;
	}

	@Override
	public boolean deleteFiles(String[] sysFileIds, String userId) {
		if (sysFileIds == null) {
			return false;
		}
		UnitFile sf = null;
		for (String id : sysFileIds) {
			sf = this.selectObjById_common(id);
			if (sf == null) {
				return false;
			}
			// TODO 如果文件不属于当前用户则不可以进行删除
			// if(!sf.getUserId().equals(TokenUtil.getUserId())){
			// return false;
			// }
			LocalFileService.deleteFile(sf.getSourceUrl());
			LocalFileService.deleteFile(sf.getMaxLogoUrl());
			LocalFileService.deleteFile(sf.getMiddleLogoUrl());
			LocalFileService.deleteFile(sf.getMinLogoUrl());
			this.deleteObjById_common(sf.getId());
		}
		return true;
	}

	@Override
	public boolean deleteFile(String unitFileId, String userId) {
		if (unitFileId == null || "".equals(unitFileId)) {
			return false;
		}
		UnitFile sf = null;
		sf = this.selectObjById_common(unitFileId);
		if (sf == null) {
			return false;
		}
		// TODO 如果文件不属于当前用户则不可以进行删除
		// if (!sf.getUserId().equals(TokenUtil.getUserId())) {
		// return false;
		// }
		LocalFileService.deleteFile(sf.getSourceUrl());
		LocalFileService.deleteFile(sf.getMaxLogoUrl());
		LocalFileService.deleteFile(sf.getMiddleLogoUrl());
		LocalFileService.deleteFile(sf.getMinLogoUrl());
		this.deleteObjById_common(sf.getId());
		return true;
	}

	@Override
	public List<UnitFile> uploadFiles(String fileJson, String realPath, String resId, String resType, String opeType, String userId) {
		// 非空判断
		if (StringUtil.empty(fileJson) || StringUtil.empty(realPath) || StringUtil.empty(userId)) {
			return null;
		}
		// 对json字符串进行转码处理
		String jsonStr = StringEscapeUtils.unescapeHtml4(fileJson);
		// 转为json数组
		JSONArray jarray =JSONArray.fromObject(jsonStr);
		List<FileDto> fileDtoList = new ArrayList<FileDto>();
		FileDto fileDto = null;
		List<UnitFile> files = null;
		if (jarray != null) {
			// 循环每一张图片并添加到集合中
			for (int i = 0; i < jarray.size(); i++) {
				JSONObject jobj = jarray.getJSONObject(i);
				fileDto = new FileDto(jobj);
				fileDtoList.add(fileDto);
			}

			if (!fileDtoList.isEmpty()) {
				// 上传文件到文件服务器
				files = this.uploadFilesToServer(fileDtoList, realPath, userId, null, resId, resType, opeType);
			}
		}
		return files;
	}

	// 公共方法-----开始

	@Override
	public long getObjRowCount_common(Assist assist) {
		if (null == assist) {
    		assist = new Assist();
    	}
		assist.setRequires(Assist.andEq("del_flag", 0));
		return dao.getObjRowCount_common(assist);
	}

	@Override
	public List<UnitFile> selectObj_common(Assist assist) {
		if (null == assist) {
    		assist = new Assist();
    	}
		assist.setRequires(Assist.andEq("del_flag", 0));
		assist.setOrder(Assist.order("order_id, id", true));
		return dao.selectObj_common(assist);
	}

	@Override
	public PageData<UnitFile> selectObj_common(Assist assist, int page, int limit) {
		if (null == assist) {
    		assist = new Assist();
    	}
		PageData<UnitFile> pageData = new PageData<>();
		Long count = this.getObjRowCount_common(assist);
		int startRow = limit * (page - 1);
		assist.setStartRow(startRow);
		assist.setRowSize(limit);
		assist.setOrder(Assist.order("order_id, id", true));
		List<UnitFile> list = dao.selectObj_common(assist);
		pageData.setCount(count.intValue());
		pageData.setList(list);
		return pageData;
	}

	@Override
	public UnitFile selectObjByObj_common(UnitFile obj) {
		return dao.selectObjByObj_common(obj);
	}

	@Override
	public UnitFile selectObjById_common(String id) {
		return dao.selectObjById_common(id);
	}

	@Override
	public int insertObj_common(UnitFile value) {
		value.preInsert();
		return dao.insertObj_common(value);
	}

	@Override
	public int insertNonEmptyObj_common(UnitFile value) {
		value.preInsert();
		return dao.insertNonEmptyObj_common(value);
	}

	@Override
	public int insertObjByBatch_common(List<UnitFile> value) {
		for (UnitFile temp : value) {
			temp.preInsert();
		}
		return dao.insertObjByBatch_common(value);
	}

	@Override
	public int deleteObjById_common(String id) {
		UnitFile temp = new UnitFile();
		temp.setId(id);
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObjById_common(temp);
	}

	@Override
	public int deleteObj_common(Assist assist) {
		UnitFile temp = new UnitFile();
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObj_common(temp, assist);
	}

	@Override
	public int updateObjById_common(UnitFile enti) {
		enti.preUpdate();
		return dao.updateObjById_common(enti);
	}

	@Override
	public int updateObj_common(UnitFile value, Assist assist) {
		value.preUpdate();
		return dao.updateObj_common(value, assist);
	}

	@Override
	public int updateNonEmptyObjById_common(UnitFile enti) {
		enti.preUpdate();
		return dao.updateNonEmptyObjById_common(enti);
	}

	@Override
	public int updateNonEmptyObj_common(UnitFile value, Assist assist) {
		value.preUpdate();
		return dao.updateNonEmptyObj_common(value, assist);
	}

	@Override
	public int deleteObjById_common(UnitFile enti) {
		return 0;
	}

	@Override
	public int deleteObj_common(UnitFile enti, Assist assist) {
		return 0;
	}
}
