package com.cimr.sysmanage.bo;

import java.io.Serializable;
import java.util.Date;

import com.cimr.sysmanage.model.User;

public class UserOnlineBo
  extends User
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String sessionId;
  private String host;
  private Date startTime;
  private Date lastAccess;
  private long timeout;
  private boolean sessionStatus = Boolean.TRUE.booleanValue();
  
  public UserOnlineBo() {}
  
  public UserOnlineBo(User user)
  {
    super(user);
  }
  
  public String getSessionId()
  {
    return this.sessionId;
  }
  
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
  
  public String getHost() {
    return this.host;
  }
  
  public void setHost(String host) {
    this.host = host;
  }
  
  public Date getStartTime() {
    return this.startTime;
  }
  
  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }
  
  public Date getLastAccess() {
    return this.lastAccess;
  }
  
  public void setLastAccess(Date lastAccess) {
    this.lastAccess = lastAccess;
  }
  
  public long getTimeout() {
    return this.timeout;
  }
  
  public void setTimeout(long timeout) {
    this.timeout = timeout;
  }
  
  public boolean isSessionStatus() {
    return this.sessionStatus;
  }
  
  public void setSessionStatus(boolean sessionStatus) {
    this.sessionStatus = sessionStatus;
  }
}
