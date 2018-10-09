package com.cimr.demo.controller;

/**
 * JAVA实体类TdCustomer
 * @author suhuanzhao 2018-09-20 18:03:51
 */
import com.cimr.comm.base.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;
import java.util.Date;

public class TdCustomer extends BaseEntity{
    
    public TdCustomer() {
        super();
    }
	

    private String id;


    /**
    * 客户名称 ,不能为空
    */
    @NotEmpty(message = "客户名称 不能为空")
    private String name;


    /**
    * 父节点id ,不能为空
    */
    @NotEmpty(message = "父节点id 不能为空")
    private String parentId;


    /**
    * 所有上级,不能为空
    */
    @NotEmpty(message = "所有上级不能为空")
    private String parentIds;


    /**
    * 客户类型 ,不能为空
    */
    @NotEmpty(message = "客户类型 不能为空")
    private String type;


    /**
    * 客户编号 ,不能为空
    */
    @NotEmpty(message = "客户编号 不能为空")
    private String numbers;


    /**
    * 省,不能为空
    */
    @NotEmpty(message = "省不能为空")
    private String province;


    /**
    * 市,不能为空
    */
    @NotEmpty(message = "市不能为空")
    private String city;


    /**
    * 区,不能为空
    */
    @NotEmpty(message = "区不能为空")
    private String area;


    /**
    * 详细地址,不能为空
    */
    @NotEmpty(message = "详细地址不能为空")
    private String address;


    /**
    * 备注,不能为空
    */
    @NotEmpty(message = "备注不能为空")
    private String remarks;

    private Double addrLat;

    private Double addrLng;


    /**
    * 联系人,不能为空
    */
    @NotEmpty(message = "联系人不能为空")
    private String contactPerson;


    /**
    * 联系电话,不能为空
    */
    @NotEmpty(message = "联系电话不能为空")
    private String contactPhone;


    /**
    * 负责人,不能为空
    */
    @NotEmpty(message = "负责人不能为空")
    private String managerPerson;


    /**
    * 负责人电话,不能为空
    */
    @NotEmpty(message = "负责人电话不能为空")
    private String managerPhone;


    /**
    * 所属行业,不能为空
    */
    @NotEmpty(message = "所属行业不能为空")
    private String industry;
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getId() {
        return this.id;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    public String getParentId() {
        return this.parentId;
    }
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }
    
    public String getParentIds() {
        return this.parentIds;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public String getType() {
        return this.type;
    }
    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
    
    public String getNumbers() {
        return this.numbers;
    }
    public void setProvince(String province) {
        this.province = province;
    }
    
    public String getProvince() {
        return this.province;
    }
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getCity() {
        return this.city;
    }
    public void setArea(String area) {
        this.area = area;
    }
    
    public String getArea() {
        return this.area;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getAddress() {
        return this.address;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public String getRemarks() {
        return this.remarks;
    }
    public void setAddrLat(Double addrLat) {
        this.addrLat = addrLat;
    }
    
    public Double getAddrLat() {
        return this.addrLat;
    }
    public void setAddrLng(Double addrLng) {
        this.addrLng = addrLng;
    }
    
    public Double getAddrLng() {
        return this.addrLng;
    }
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    
    public String getContactPerson() {
        return this.contactPerson;
    }
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
    public String getContactPhone() {
        return this.contactPhone;
    }
    public void setManagerPerson(String managerPerson) {
        this.managerPerson = managerPerson;
    }
    
    public String getManagerPerson() {
        return this.managerPerson;
    }
    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }
    
    public String getManagerPhone() {
        return this.managerPhone;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    
    public String getIndustry() {
        return this.industry;
    }
}