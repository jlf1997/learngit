package com.cimr.api.routers.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cimr.boot.model.BaseModel;


@Table(name="td_s_zuul")
@Entity
public class ZuulRoute extends BaseModel{


	 /**
     * The ID of the route (the same as its map key by default).
     */
	@Id
    private String id;

    /**
     * The path (pattern) for the route, e.g. /foo/**.
     */
    private String path;

    /**
     * The service ID (if any) to map to this route. You can specify a physical URL or
     * a service, but not both.
     */
    private String serviceId;

    /**
     * A full physical URL to map to the route. An alternative is to use a service ID
     * and service discovery to find the physical address.
     */
    private String url;

    /**
     * Flag to determine whether the prefix for this route (the path, minus pattern
     * patcher) should be stripped before forwarding.
     */
    private boolean stripPrefix = true;

    /**
     * Flag to indicate that this route should be retryable (if supported). Generally
     * retry requires a service ID and ribbon.
     */
    private Boolean retryable;

    private Boolean enabled;
  
    private String urlStart;
    
    
    @OneToMany(fetch=FetchType.EAGER,cascade={},mappedBy="exId")
    @org.hibernate.annotations.ForeignKey(name = "none")
    private List<ExcludeUrl> excludeUrls;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isStripPrefix() {
		return stripPrefix;
	}

	public void setStripPrefix(boolean stripPrefix) {
		this.stripPrefix = stripPrefix;
	}

	public Boolean getRetryable() {
		return retryable;
	}

	public void setRetryable(Boolean retryable) {
		this.retryable = retryable;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getUrlStart() {
		return urlStart;
	}

	public void setUrlStart(String urlStart) {
		this.urlStart = urlStart;
	}

	public List<ExcludeUrl> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<ExcludeUrl> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}
    
    
    
    
}
