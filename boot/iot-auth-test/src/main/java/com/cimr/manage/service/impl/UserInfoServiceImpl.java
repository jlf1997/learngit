package com.cimr.manage.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimr.manage.dao.UserInfoDao;
import com.cimr.manage.dto.UserInfoDto;
import com.cimr.manage.model.UserInfo;
import com.cimr.manage.service.UserInfoService;
import com.cimr.sysmanage.model.User;
import com.cimr.sysmanage.service.UnitFileService;
import com.cimr.sysmanage.service.UserService;
import com.cimr.util.Assist;
import com.cimr.util.PageData;

/**
 * Created by suhuanzhao on 2017/12/25.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired
    private UserInfoDao dao;

    @Autowired
    private UnitFileService unitFileService;

    @Autowired
    private UserService userService;

    @Override
    public String delete(String userId) {
//        UserInfo ui = this.selectById_common(userInfoId);
        User user = userService.selectObjById_common(userId);
        if(user == null){
            return "删除失败，不存在该用户!";
        }
        UserInfo ui = this.getByUserId(userId);
        //删除user对象
        userService.deleteObjById_common(userId);

        //删除userInfo对象以及相关的文件和图片
        if(ui != null){
            //删除头像
            if (ui.getHeadImgId() != null && !"".equals(ui.getHeadImgId())) {
                unitFileService.deleteFile(ui.getHeadImgId(), userId);
            }
            //删除附件
            if (ui.getAttachId() != null && !"".equals(ui.getAttachId())) {
                unitFileService.deleteFile(ui.getAttachId(), userId);
            }
            //删除userInfo对象
            this.deleteObjById_common(ui.getId());
        }
        return "删除成功";
    }

    @Override
    public String insertByObj(UserInfoDto userInfoDto) {

        //账号唯一性检查
        /*if(userInfoDto.getUsername() == null || "".equals(userInfoDto.getUsername())){
            return "添加失败，用户名不能为空";
        }

        //上传头像
        List<UnitFile> files = unitFileService.uploadFiles(userInfoDto.getFileJson(),userInfoDto.getRealPath(),userInfoDto.getUserId(),null,null,userInfoDto.getUserId());
        String headImgId = null;
        //保存头像图片的文件ID
        if(files != null && !files.isEmpty()){
            headImgId = files.get(0).getId();
        }
        User u = userService.getByUsername(userInfoDto.getUsername());
        //新增user对象
        if (userInfoDto.getUserId() == null || "".equals(userInfoDto.getUserId())) {
            String userId = IdUtil.getId().toString();
            userInfoDto.setUserId(userId);
            userService.insertUser(userId, userInfoDto.getUsername(),
                    userInfoDto.getPswd(),null,null,1, userInfoDto.getFullname(), userInfoDto.getPhone(), userInfoDto.getEmail(),
                    userInfoDto.getComment(), userInfoDto.getOrderId(), userInfoDto.getTheme(),headImgId);
        } else {
            //更新user对象
            User u1 = userService.getUserById(userInfoDto.getUserId());
            if(!u1.getUsername().equals(u.getUsername())){
                if (u != null) {
                    return "该账号已经存在！";
                }
            }
            //先删除以前的图片
            unitFileService.deleteFile(u1.getAvatar(), u1.getId());
            userService.updateUser(userInfoDto.getUserId(), userInfoDto.getUsername(),
                    userInfoDto.getPswd(), null,null,1, userInfoDto.getFullname(), userInfoDto.getPhone(), userInfoDto.getEmail(),
                    userInfoDto.getComment(), userInfoDto.getOrderId(), userInfoDto.getTheme(),headImgId);
        }
        //新增userInfo对象
        UserInfo userInfo = new UserInfo(userInfoDto);
        userInfo.setId(IdUtil.getId().toString());
        userInfo.setCreateTime(new Date());
        userInfo.setCreateBy(TokenUtil.getUserId());
        userInfo.setUpdateTime(new Date());
        userInfo.setUpdateBy(TokenUtil.getUserId());

//        //上传头像
//        List<UnitFile> files = unitFileService.uploadFiles(userInfoDto.getFileJson(),userInfoDto.getRealPath(),userInfo.getId(),null,null,userInfo.getUserId());
//
//        //保存头像图片的文件ID
//        if(files != null && !files.isEmpty()){
//            userInfo.setHeadImgId(files.get(0).getId());
//        }
        this.insertNonEmptyObj_common(userInfo);*/
        return "添加成功";
    }

    @Override
    public String updateByObj(UserInfoDto userInfoDto) {
        /*User u1 = userService.selectObjById_common(userInfoDto.getUserId());
        if(u1 == null){
            return "更新失败，不存在该用户";
        }
        //上传头像
        List<UnitFile> files = unitFileService.uploadFiles(userInfoDto.getFileJson(),userInfoDto.getRealPath(),userInfoDto.getUserId(),null,null,userInfoDto.getUserId());
        String headImgId = null;
        //保存头像图片的文件ID
        if(files != null && !files.isEmpty()){
            headImgId = files.get(0).getId();
        }
        //先删除以前的图片
        unitFileService.deleteFile(u1.getAvatar(), u1.getId());
        //更新user对象
        userService.updateUser(userInfoDto.getUserId(), userInfoDto.getUsername(),
                null, null,null,1, userInfoDto.getFullname(), userInfoDto.getPhone(), userInfoDto.getEmail(),
                userInfoDto.getComment(), userInfoDto.getOrderId(), userInfoDto.getTheme(),headImgId);
        //账号唯一性检查
        if(userInfoDto.getUsername() == null || "".equals(userInfoDto.getUsername())){
            return "添加失败，用户名不能为空";
        }
        User u = userService.getByUsername(userInfoDto.getUsername());
        if(!u1.getUsername().equals(u.getUsername())){
            if (u != null) {
                return "该账号已经存在！";
            }
        }
        //更新userInfo对象
        UserInfo userInfo = new UserInfo(userInfoDto);
        userInfo.setUpdateTime(new Date());
        userInfo.setUpdateBy(TokenUtil.getUserId());

//        UserInfo userInfo1 = this.selectById_common(userInfo.getId());


//        if(userInfoDto.getFileJson() != null && !"".equals(userInfoDto.getFileJson())
//                && !"[]".equals(userInfoDto.getFileJson())){
//            //更新图片之前，先删除旧图片
//            unitFileService.deleteFile(userInfo1.getHeadImgId(), userInfo.getUserId());
//            //重新上传新的图片
//            //上传头像
//            List<UnitFile> files = unitFileService.uploadFiles(userInfoDto.getFileJson(),userInfoDto.getRealPath(),userInfo.getId(),null,null,userInfo.getUserId());
//
//            //保存头像图片的文件ID
//            if(files != null && !files.isEmpty()){
//                userInfo.setHeadImgId(files.get(0).getId());
//            }
//
//        }
        this.updateNonEmptyObjById_common(userInfo);*/
        return "更新成功";
    }
    
    @Override
    public UserInfo getByUserId(String userId) {
    	UserInfo temp = new UserInfo();
    	temp.setUserId(userId);
    	return this.selectObjByObj_common(temp);
    }
    
    //公共方法
    
    @Override
	public long getObjRowCount_common(Assist assist) {
    	if (null == assist) {
    		assist = new Assist();
    	}
    	assist.setRequires(Assist.andEq("del_flag", 0));
		return dao.getObjRowCount_common(assist);
	}

	@Override
	public List<UserInfo> selectObj_common(Assist assist) {
		if (null == assist) {
    		assist = new Assist();
    	}
		assist.setRequires(Assist.andEq("del_flag", 0));
		assist.setOrder(Assist.order("order_id, id", true));
		return dao.selectObj_common(assist);
	}
	
	@Override
	public PageData<UserInfo> selectObj_common(Assist assist, int page, int limit) {
		if (null == assist) {
    		assist = new Assist();
		}
		PageData<UserInfo> pageData = new PageData<>();
		Long count = this.getObjRowCount_common(assist);
		int startRow = limit * (page - 1);
		assist.setStartRow(startRow);
		assist.setRowSize(limit);
		assist.setOrder(Assist.order("order_id, id", true));
		List<UserInfo> list = dao.selectObj_common(assist);
		pageData.setCount(count.intValue());
		pageData.setList(list);
		return pageData;
	}


	@Override
	public UserInfo selectObjByObj_common(UserInfo obj) {
		return dao.selectObjByObj_common(obj);
	}

	@Override
	public UserInfo selectObjById_common(String id) {
		return dao.selectObjById_common(id);
	}

	@Override
	public int insertObj_common(UserInfo value) {
		value.preInsert();
		return dao.insertObj_common(value);
	}

	@Override
	public int insertNonEmptyObj_common(UserInfo value) {
		value.preInsert();
		return dao.insertNonEmptyObj_common(value);
	}

	@Override
	public int insertObjByBatch_common(List<UserInfo> value) {
		for (UserInfo temp : value) {
			temp.preInsert();
		}
		return dao.insertObjByBatch_common(value);
	}

	@Override
	public int deleteObjById_common(String id) {
		UserInfo temp = new UserInfo();
		temp.setId(id);
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObjById_common(temp);
	}

	@Override
	public int deleteObj_common(Assist assist) {
		UserInfo temp = new UserInfo();
		temp.setDelFlag(1);
		temp.preUpdate();
		return dao.updateNonEmptyObj_common(temp, assist);
	}

	@Override
	public int updateObjById_common(UserInfo enti) {
		enti.preUpdate();
		return dao.updateObjById_common(enti);
	}

	@Override
	public int updateObj_common(UserInfo value, Assist assist) {
		value.preUpdate();
		return dao.updateObj_common(value, assist);
	}

	@Override
	public int updateNonEmptyObjById_common(UserInfo enti) {
		enti.preUpdate();
		return dao.updateNonEmptyObjById_common(enti);
	}

	@Override
	public int updateNonEmptyObj_common(UserInfo value, Assist assist) {
		value.preUpdate();
		return dao.updateNonEmptyObj_common(value, assist);
	}

	@Override
	public int deleteObjById_common(UserInfo enti) {
		return 0;
	}

	@Override
	public int deleteObj_common(UserInfo enti, Assist assist) {
		return 0;
	}
}
