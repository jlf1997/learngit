package com.cimr.api.dev.model.mgr;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="tenant")
public class Tenant extends BaseEntity{

	
	@Id
    private String tenantId;
	
    private String tenantName;
    private String remark;
    private Float orderId;
    private String legalPerson;
    private String tenantType;
    private String industry;
    private String contactPerson;
    private String contactPhone;
    private String contactEmail;
    private String license;
    private String creditCode;
    private String province;
    private String city;
    private String area;
    private String address;
    private String tenantTitle;
    private String tenantLogo;
    private String checkStatus;
    private Date checkTime;
    private String parentId;
    private String parentIds;
    private Double addrLat;

    private Double addrLng;
    public Tenant() {
    }

    public Tenant(Tenant tenant) {
        this.tenantId = tenant.getTenantId();
        this.tenantName = tenant.getTenantName();
        this.remark = tenant.getRemark();
        this.orderId = tenant.getOrderId();
        this.legalPerson = tenant.getLegalPerson();
        this.tenantType = tenant.getTenantType();
        this.industry = tenant.getIndustry();
        this.contactPerson = tenant.getContactPerson();
        this.contactPhone = tenant.getContactPhone();
        this.contactEmail = tenant.getContactEmail();
        this.license = tenant.getLicense();
        this.creditCode = tenant.getCreditCode();
        this.province = tenant.getProvince();
        this.city = tenant.getCity();
        this.area = tenant.getArea();
        this.address = tenant.getAddress();
        this.tenantTitle = tenant.getTenantTitle();
        this.tenantLogo = tenant.getTenantLogo();
        this.checkStatus = tenant.getCheckStatus();
        this.checkTime = tenant.getCheckTime();
        this.parentId = tenant.getParentId();
        this.parentIds = tenant.getParentIds();
        this.addrLat=tenant.getAddrLat();
        this.addrLng=tenant.getAddrLng();
        setCreateTime(tenant.getCreateTime());
        setCreateBy(tenant.getCreateBy());
        setUpdateTime(tenant.getUpdateTime());
        setUpdateBy(tenant.getUpdateBy());
    }

    public String getTenantId() {
        return tenantId;
    }
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }
    public String getTenantName() {
        return tenantName;
    }
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName == null ? null : tenantName.trim();
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
    public Float getOrderId() {
        return orderId;
    }
    public void setOrderId(Float orderId) {
        this.orderId = orderId;
    }
    public String getLegalPerson() {
        return legalPerson;
    }
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }
    public String getTenantType() {
        return tenantType;
    }
    public void setTenantType(String tenantType) {
        this.tenantType = tenantType == null ? null : tenantType.trim();
    }
    public String getIndustry() {
        return industry;
    }
    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }
    public String getContactPerson() {
        return contactPerson;
    }
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson.trim();
    }
    public String getContactPhone() {
        return contactPhone;
    }
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }
    public String getContactEmail() {
        return contactEmail;
    }
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail == null ? null : contactEmail.trim();
    }
    public String getLicense() {
        return license;
    }
    public void setLicense(String license) {
        this.license = license == null ? null : license.trim();
    }
    public String getCreditCode() {
        return creditCode;
    }
    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode == null ? null : creditCode.trim();
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
    public String getTenantTitle() {
        return tenantTitle;
    }
    public void setTenantTitle(String tenantTitle) {
        this.tenantTitle = tenantTitle == null ? null : tenantTitle.trim();
    }
    public String getTenantLogo() {
        return tenantLogo;
    }
    public void setTenantLogo(String tenantLogo) {
        this.tenantLogo = tenantLogo == null ? null : tenantLogo.trim();
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Double getAddrLat() {
        return addrLat;
    }

    public void setAddrLat(Double addrLat) {
        this.addrLat = addrLat;
    }

    public Double getAddrLng() {
        return addrLng;
    }

    public void setAddrLng(Double addrLng) {
        this.addrLng = addrLng;
    }
}