package com.cimr.manage.model;

import java.util.Date;

import com.cimr.comm.token.TokenUtil;
import com.cimr.manage.dto.UserInfoDto;
import com.cimr.sysmanage.model.User;

/**
 * Created by suhuanzhao on 2018/1/3.
 */
public class UserInfo extends User{
    private String id;
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

    public UserInfo(){

    }

    public UserInfo(String id, String nickName, String realName, String userType, String province, String city, String area,
                    String address, String sex, String loginIp, String orgId, String userId, String idCard, String headImgId, String attachId,
                    String remarks, String flag, String createBy, Date createTime, String updateBy, Date updateTime) {
        this.id = id;
        this.nickName = nickName;
        this.realName = realName;
        this.userType = userType;
        this.province = province;
        this.city = city;
        this.area = area;
        this.address = address;
        this.sex = sex;
        this.loginIp = loginIp;
        this.orgId = orgId;
        this.userId = userId;
        this.idCard = idCard;
        this.headImgId = headImgId;
        this.attachId = attachId;
        this.remarks = remarks;
        this.flag = flag;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
    }

    public UserInfo(UserInfoDto userInfoDto){
        this.id = userInfoDto.getId();
        this.nickName = userInfoDto.getNickName();
        this.realName = userInfoDto.getRealName();
        this.userType = userInfoDto.getUserType();
        this.province = userInfoDto.getProvince();
        this.city = userInfoDto.getCity();
        this.area = userInfoDto.getArea();
        this.address = userInfoDto.getAddress();
        this.sex = userInfoDto.getSex();
        this.loginIp = userInfoDto.getLoginIp();
        this.orgId = userInfoDto.getOrgId();
        this.userId = userInfoDto.getUserId();
        this.idCard = userInfoDto.getIdCard();
        this.remarks = userInfoDto.getRemarks();
        if(id == null || "".equals(id)){
            this.createBy = TokenUtil.getUserId();
            this.createTime = new Date();
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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
