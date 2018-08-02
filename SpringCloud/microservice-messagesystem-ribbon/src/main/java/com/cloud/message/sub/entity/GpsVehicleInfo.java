package com.cloud.message.sub.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class GpsVehicleInfo implements Serializable{
	
	private static final long serialVersionUID = 1632946850666431197L;
	private int ID;
	private String VehicleId;
	private String PlateNo;
	private Timestamp SendTime;
	private float Longitude;
	private float Latitude;
	private float Velocity;
	private int Direction;
	private String Status;
	private float Mileage;
	private float Oil;
	private float RecordVelocity;
	private String Location;
	private double BaiduLongitude;
	private double BaiduLatitude;
	private short Acc;
	private int Positive;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getVehicleId() {
		return VehicleId;
	}
	public void setVehicleId(String vehicleId) {
		VehicleId = vehicleId;
	}
	public String getPlateNo() {
		return PlateNo;
	}
	public void setPlateNo(String plateNo) {
		PlateNo = plateNo;
	}
	public Timestamp getSendTime() {
		return SendTime;
	}
	public void setSendTime(Timestamp sendTime) {
		SendTime = sendTime;
	}
	public float getLongitude() {
		return Longitude;
	}
	public void setLongitude(float longitude) {
		Longitude = longitude;
	}
	public float getLatitude() {
		return Latitude;
	}
	public void setLatitude(float latitude) {
		Latitude = latitude;
	}
	public float getVelocity() {
		return Velocity;
	}
	public void setVelocity(float velocity) {
		Velocity = velocity;
	}
	public int getDirection() {
		return Direction;
	}
	public void setDirection(int direction) {
		Direction = direction;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public float getMileage() {
		return Mileage;
	}
	public void setMileage(float mileage) {
		Mileage = mileage;
	}
	public float getOil() {
		return Oil;
	}
	public void setOil(float oil) {
		Oil = oil;
	}
	public float getRecordVelocity() {
		return RecordVelocity;
	}
	public void setRecordVelocity(float recordVelocity) {
		RecordVelocity = recordVelocity;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public double getBaiduLongitude() {
		return BaiduLongitude;
	}
	public void setBaiduLongitude(double baiduLongitude) {
		BaiduLongitude = baiduLongitude;
	}
	public double getBaiduLatitude() {
		return BaiduLatitude;
	}
	public void setBaiduLatitude(double baiduLatitude) {
		BaiduLatitude = baiduLatitude;
	}
	public short getAcc() {
		return Acc;
	}
	public void setAcc(short acc) {
		Acc = acc;
	}
	public int getPositive() {
		return Positive;
	}
	public void setPositive(int positive) {
		Positive = positive;
	}
	
}
