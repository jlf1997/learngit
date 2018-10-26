package com.cimr.sysmanage.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cimr.comm.base.BaseService;
import com.cimr.sysmanage.dto.FileDto;
import com.cimr.sysmanage.model.UnitFile;

/**
 * Created by lenovo on 2017/12/25.
 */
public interface UnitFileService extends BaseService<UnitFile, String> {

    /**
     * 缓存文件到本地
     * @param mfs
     * @param realPath
     * @return
     */
     List<FileDto> saveFileTemp(MultipartFile[] mfs, String realPath);

    /**
     * 上传文件到文件服务器
     * @param files 需要上传的文件集合
     * @param realPath 文件真实路径
     * @param userId 当前操作用户
     * @param userType 用户类型
     * @param resId 资源ID
     * @param resType 资源类型
     * @param opeType 业务字典表
     */
     List<UnitFile> uploadFilesToServer(List<FileDto> files, String realPath,
                                              String userId, String userType, String resId, String resType, String opeType);

    /**
     * 删除多个文件
     * @param unitFileIds
     * @param userId
     * @return
     */
     boolean deleteFiles(String[] unitFileIds,String userId);

    /**
     * 删除单个文件
     * @param unitFileId
     * @param userId
     * @return
     */
     boolean deleteFile(String unitFileId,String userId);

    /**
     *  外部调用此接口，可以把文件上传到文件服务器（可以是多个文件），并且返回UnitFile对象。
     *  如果上传失败，则返回null对象
     * @author shz
     * @param fileJson 前台穿过来的FileDTO的json字符串 (必传)
     * @param realPath 真实路径 (必传)
     * @param resId 资源ID
     * @param resType 资源类型
     * @param opeType 预留字段
     * @param userId 用户 (必传)
     * @return
     */
     List<UnitFile> uploadFiles(String fileJson,String realPath,String resId,String resType,String opeType,String userId);

}
