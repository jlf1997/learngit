package com.cimr.sysmanage.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringEscapeUtils;

import com.cimr.sysmanage.model.UserInfo;

/**
 * Created by suhuanzhao on 2018/1/8.
 */
public class UserInfoDto{

    private String id;
    private String username;
    private String pswd;
    private int status;
    private String fullname;
    private String phone;
    private String email;
    private String comment;
    private Date lastLoginTime;
    private Float orderId;
    private String theme;

    private String nickName;    //昵称
    private String realName;    //真实姓名
    private String userType;    //用户类型
    private String province;    //省份
    private String city;        //城市
    private String area;        //地区
    private String address;     //详细地址
    private String sex;         //性别
    private String loginIp; //登陆IP
    private String orgId;   //组织ID
    private String userId;  //userID
    private String idCard;  //身份证号码
    private String headImgId;   //头像
    private String attachId;    //附件ID
    private String remarks; //备注信息
    private String flag;    //标志或者状态
    private String createBy;    //创建者
    private Date createTime;    //创建时间
    private String updateBy;    //更新者
    private Date updateTime;    //更新时间

    private String fileJson; //上传的图片json对象数组字符串
    private String realPath;//真实路径（服务器缓存路径）
    private String createTimeStr;
    private String updateTimeStr;

    public UserInfoDto(){

    }

    public UserInfoDto(UserInfo userInfo) {
        this.id = userInfo.getId();
        this.username = userInfo.getUsername();
//        this.pswd = userInfo.getPswd();
        this.status = userInfo.getStatus();
        this.fullname = userInfo.getFullname();
        this.phone = userInfo.getPhone();
        this.email = userInfo.getEmail();
        this.comment = userInfo.getComment();
        this.lastLoginTime = userInfo.getLastLoginTime();
        this.orderId = userInfo.getOrderId();
        this.theme = userInfo.getTheme();
        this.nickName = userInfo.getNickName();
        this.realName = userInfo.getRealName();
        this.userType = userInfo.getUserType();
        this.province = userInfo.getProvince();
        this.city = userInfo.getCity();
        this.area = userInfo.getArea();
        this.address = userInfo.getAddress();
        this.sex = userInfo.getSex();
        this.loginIp = userInfo.getLoginIp();
        this.orgId = userInfo.getOrgId();
        this.userId = userInfo.getUserId();
        this.idCard = userInfo.getIdCard();
        this.headImgId = userInfo.getHeadImgId();
        this.attachId = userInfo.getAttachId();
        this.remarks = userInfo.getRemarks();
        this.flag = userInfo.getFlag();
        this.createBy = userInfo.getCreateBy();
        this.createTime = userInfo.getCreateTime();
        this.updateBy = userInfo.getUpdateBy();
        this.updateTime = userInfo.getUpdateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (createTime != null) {
            this.createTimeStr = sdf.format(createTime);
        }
        if (updateTime != null) {
            this.updateTimeStr = sdf.format(updateTime);
        }
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getHeadImgId() {
        return headImgId;
    }

    public void setHeadImgId(String headImgId) {
        this.headImgId = headImgId;
    }

    public String getAttachId() {
        return attachId;
    }

    public void setAttachId(String attachId) {
        this.attachId = attachId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFileJson() {
        return fileJson;
    }

    public void setFileJson(String fileJson) {
        String jsonStr = StringEscapeUtils.unescapeHtml4(fileJson);
        this.fileJson = jsonStr;
    }

    public String getRealPath() {
        return realPath;
    }

    public void setRealPath(String realPath) {
        this.realPath = realPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Float getOrderId() {
        return orderId;
    }

    public void setOrderId(Float orderId) {
        this.orderId = orderId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }
}
