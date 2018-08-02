package com.cloud.message.sub.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class EquipLog implements Serializable{
	
	private static final long serialVersionUID = 2138627417739319453L;
	private String LogID;
	private String TerminalID;
	private Timestamp AccOn;
	private Timestamp AccOff;
	private int LogTime;
	private String Builder;
	private Timestamp BuildTime;
	private String Modifier;
	private Timestamp ModifyTime;
	private int Version;
	private int Lifecycle;
	public String getLogID() {
		return LogID;
	}
	public void setLogID(String logID) {
		LogID = logID;
	}
	public String getTerminalID() {
		return TerminalID;
	}
	public void setTerminalID(String terminalID) {
		TerminalID = terminalID;
	}
	public Timestamp getAccOn() {
		return AccOn;
	}
	public void setAccOn(Timestamp accOn) {
		AccOn = accOn;
	}
	public Timestamp getAccOff() {
		return AccOff;
	}
	public void setAccOff(Timestamp accOff) {
		AccOff = accOff;
	}
	public int getLogTime() {
		return LogTime;
	}
	public void setLogTime(int logTime) {
		LogTime = logTime;
	}
	public String getBuilder() {
		return Builder;
	}
	public void setBuilder(String builder) {
		Builder = builder;
	}
	public Timestamp getBuildTime() {
		return BuildTime;
	}
	public void setBuildTime(Timestamp buildTime) {
		BuildTime = buildTime;
	}
	public String getModifier() {
		return Modifier;
	}
	public void setModifier(String modifier) {
		Modifier = modifier;
	}
	public Timestamp getModifyTime() {
		return ModifyTime;
	}
	public void setModifyTime(Timestamp modifyTime) {
		ModifyTime = modifyTime;
	}
	public int getVersion() {
		return Version;
	}
	public void setVersion(int version) {
		Version = version;
	}
	public int getLifecycle() {
		return Lifecycle;
	}
	public void setLifecycle(int lifecycle) {
		Lifecycle = lifecycle;
	}
	
}
