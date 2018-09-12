package com.cimr.boot.swagger;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;

/**
 * @author 翟永超
 * Create date ：2017/8/7.
 * My blog： http://blog.didispace.com
 */
@ConfigurationProperties("swagger")
public class SwaggerProperties {

    /**是否开启swagger**/
    private Boolean enabled;

    /**标题**/
    private String title = "api 文档 ";
    /**描述**/
    private String description = "";
    /**版本**/
    private String version = "";
    /**许可证**/
    private String license = "Apache 2.0";
    /**许可证URL**/
    private String licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.html";
    /**服务条款URL**/
    private String termsOfServiceUrl = "";

    /**忽略的参数类型**/
    private List<Class> ignoredParameterTypes = new ArrayList<>();

    private Contact contact = new Contact();

    /**swagger会解析的包路径**/
    private String basePackage = "";

    /**swagger会解析的url规则**/
    private List<String> basePath = new ArrayList<>();
    /**在basePath基础上需要排除的url规则**/
    private List<String> excludePath = new ArrayList<>();

    /**分组文档**/
    private Map<String, DocketInfo> docket = new LinkedHashMap<>();

    /**host信息**/
    private String host = "";

    /**全局参数配置**/
    private List<GlobalOperationParameter> globalOperationParameters;
    
    
    private List<SecuritySchemesParameter> securitySchemesParameters;
    


    /** 页面功能配置 **/
    private UiConfig uiConfig = new UiConfig();

    /** 是否使用默认预定义的响应消息 ，默认 true **/
    private Boolean applyDefaultResponseMessages = true;

    /** 全局响应消息 **/
    private GlobalResponseMessage globalResponseMessage;


    

    public Boolean getEnabled() {
		return enabled;
	}


	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getLicense() {
		return license;
	}


	public void setLicense(String license) {
		this.license = license;
	}


	public String getLicenseUrl() {
		return licenseUrl;
	}


	public void setLicenseUrl(String licenseUrl) {
		this.licenseUrl = licenseUrl;
	}


	public String getTermsOfServiceUrl() {
		return termsOfServiceUrl;
	}


	public void setTermsOfServiceUrl(String termsOfServiceUrl) {
		this.termsOfServiceUrl = termsOfServiceUrl;
	}


	public List<Class> getIgnoredParameterTypes() {
		return ignoredParameterTypes;
	}


	public void setIgnoredParameterTypes(List<Class> ignoredParameterTypes) {
		this.ignoredParameterTypes = ignoredParameterTypes;
	}


	public Contact getContact() {
		return contact;
	}


	public void setContact(Contact contact) {
		this.contact = contact;
	}


	public String getBasePackage() {
		return basePackage;
	}


	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}


	public List<String> getBasePath() {
		return basePath;
	}


	public void setBasePath(List<String> basePath) {
		this.basePath = basePath;
	}


	public List<String> getExcludePath() {
		return excludePath;
	}


	public void setExcludePath(List<String> excludePath) {
		this.excludePath = excludePath;
	}


	public Map<String, DocketInfo> getDocket() {
		return docket;
	}


	public void setDocket(Map<String, DocketInfo> docket) {
		this.docket = docket;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public List<GlobalOperationParameter> getGlobalOperationParameters() {
		return globalOperationParameters;
	}


	public void setGlobalOperationParameters(List<GlobalOperationParameter> globalOperationParameters) {
		this.globalOperationParameters = globalOperationParameters;
	}


	public List<SecuritySchemesParameter> getSecuritySchemesParameters() {
		return securitySchemesParameters;
	}


	public void setSecuritySchemesParameters(List<SecuritySchemesParameter> securitySchemesParameters) {
		this.securitySchemesParameters = securitySchemesParameters;
	}


	public UiConfig getUiConfig() {
		return uiConfig;
	}


	public void setUiConfig(UiConfig uiConfig) {
		this.uiConfig = uiConfig;
	}


	public Boolean getApplyDefaultResponseMessages() {
		return applyDefaultResponseMessages;
	}


	public void setApplyDefaultResponseMessages(Boolean applyDefaultResponseMessages) {
		this.applyDefaultResponseMessages = applyDefaultResponseMessages;
	}


	public GlobalResponseMessage getGlobalResponseMessage() {
		return globalResponseMessage;
	}


	public void setGlobalResponseMessage(GlobalResponseMessage globalResponseMessage) {
		this.globalResponseMessage = globalResponseMessage;
	}


	public static class GlobalOperationParameter{
        /**参数名**/
        private String name;

        /**描述信息**/
        private String description;

        /**指定参数类型**/
        private String modelRef;

        /**参数放在哪个地方:header,query,path,body.form**/
        private String parameterType;

        /**参数是否必须传**/
        private String required;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getModelRef() {
			return modelRef;
		}

		public void setModelRef(String modelRef) {
			this.modelRef = modelRef;
		}

		public String getParameterType() {
			return parameterType;
		}

		public void setParameterType(String parameterType) {
			this.parameterType = parameterType;
		}

		public String getRequired() {
			return required;
		}

		public void setRequired(String required) {
			this.required = required;
		}
        
        

    }
    
    
   
    public static class SecuritySchemesParameter{

    	private String name;
    	
    	private String type;
    	
    	private String in;
    	
    	private String keyName;  
    	
    	private List<SecurityReferenceParameter> securityReferences;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getIn() {
			return in;
		}

		public void setIn(String in) {
			this.in = in;
		}

		public String getKeyName() {
			return keyName;
		}

		public void setKeyName(String keyName) {
			this.keyName = keyName;
		}

		public List<SecurityReferenceParameter> getSecurityReferences() {
			return securityReferences;
		}

		public void setSecurityReferences(List<SecurityReferenceParameter> securityReferences) {
			this.securityReferences = securityReferences;
		}
    	

    }
    
   
    public static class SecurityReferenceParameter{
    	private String scope;
    	private String description;
		public String getScope() {
			return scope;
		}
		public void setScope(String scope) {
			this.scope = scope;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
    	
    	
    }
    
 


    public static class DocketInfo {

        /**标题**/
        private String title = "";
        /**描述**/
        private String description = "";
        /**版本**/
        private String version = "";
        /**许可证**/
        private String license = "";
        /**许可证URL**/
        private String licenseUrl = "";
        /**服务条款URL**/
        private String termsOfServiceUrl = "";

        private Contact contact = new Contact();

        /**swagger会解析的包路径**/
        private String basePackage = "";

        /**swagger会解析的url规则**/
        private List<String> basePath = new ArrayList<>();
        /**在basePath基础上需要排除的url规则**/
        private List<String> excludePath = new ArrayList<>();

        private List<GlobalOperationParameter> globalOperationParameters;

        /**忽略的参数类型**/
        private List<Class> ignoredParameterTypes = new ArrayList<>();

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getLicense() {
			return license;
		}

		public void setLicense(String license) {
			this.license = license;
		}

		public String getLicenseUrl() {
			return licenseUrl;
		}

		public void setLicenseUrl(String licenseUrl) {
			this.licenseUrl = licenseUrl;
		}

		public String getTermsOfServiceUrl() {
			return termsOfServiceUrl;
		}

		public void setTermsOfServiceUrl(String termsOfServiceUrl) {
			this.termsOfServiceUrl = termsOfServiceUrl;
		}

		public Contact getContact() {
			return contact;
		}

		public void setContact(Contact contact) {
			this.contact = contact;
		}

		public String getBasePackage() {
			return basePackage;
		}

		public void setBasePackage(String basePackage) {
			this.basePackage = basePackage;
		}

		public List<String> getBasePath() {
			return basePath;
		}

		public void setBasePath(List<String> basePath) {
			this.basePath = basePath;
		}

		public List<String> getExcludePath() {
			return excludePath;
		}

		public void setExcludePath(List<String> excludePath) {
			this.excludePath = excludePath;
		}

		public List<GlobalOperationParameter> getGlobalOperationParameters() {
			return globalOperationParameters;
		}

		public void setGlobalOperationParameters(List<GlobalOperationParameter> globalOperationParameters) {
			this.globalOperationParameters = globalOperationParameters;
		}

		public List<Class> getIgnoredParameterTypes() {
			return ignoredParameterTypes;
		}

		public void setIgnoredParameterTypes(List<Class> ignoredParameterTypes) {
			this.ignoredParameterTypes = ignoredParameterTypes;
		}
        
        

    }

   
    public static class Contact {

        /**联系人**/
        private String name = "";
        /**联系人url**/
        private String url = "";
        /**联系人email**/
        private String email = "";
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
        
        

    }

   
    public static class GlobalResponseMessage {

        /** POST 响应消息体 **/
        List<GlobalResponseMessageBody> post = new ArrayList<>();

        /** GET 响应消息体 **/
        List<GlobalResponseMessageBody> get = new ArrayList<>();

        /** PUT 响应消息体 **/
        List<GlobalResponseMessageBody> put = new ArrayList<>();

        /** PATCH 响应消息体 **/
        List<GlobalResponseMessageBody> patch = new ArrayList<>();

        /** DELETE 响应消息体 **/
        List<GlobalResponseMessageBody> delete = new ArrayList<>();

        /** HEAD 响应消息体 **/
        List<GlobalResponseMessageBody> head = new ArrayList<>();

        /** OPTIONS 响应消息体 **/
        List<GlobalResponseMessageBody> options = new ArrayList<>();

        /** TRACE 响应消息体 **/
        List<GlobalResponseMessageBody> trace = new ArrayList<>();

		public List<GlobalResponseMessageBody> getPost() {
			return post;
		}

		public void setPost(List<GlobalResponseMessageBody> post) {
			this.post = post;
		}

		public List<GlobalResponseMessageBody> getGet() {
			return get;
		}

		public void setGet(List<GlobalResponseMessageBody> get) {
			this.get = get;
		}

		public List<GlobalResponseMessageBody> getPut() {
			return put;
		}

		public void setPut(List<GlobalResponseMessageBody> put) {
			this.put = put;
		}

		public List<GlobalResponseMessageBody> getPatch() {
			return patch;
		}

		public void setPatch(List<GlobalResponseMessageBody> patch) {
			this.patch = patch;
		}

		public List<GlobalResponseMessageBody> getDelete() {
			return delete;
		}

		public void setDelete(List<GlobalResponseMessageBody> delete) {
			this.delete = delete;
		}

		public List<GlobalResponseMessageBody> getHead() {
			return head;
		}

		public void setHead(List<GlobalResponseMessageBody> head) {
			this.head = head;
		}

		public List<GlobalResponseMessageBody> getOptions() {
			return options;
		}

		public void setOptions(List<GlobalResponseMessageBody> options) {
			this.options = options;
		}

		public List<GlobalResponseMessageBody> getTrace() {
			return trace;
		}

		public void setTrace(List<GlobalResponseMessageBody> trace) {
			this.trace = trace;
		}
        
        

    }

  
    public static class GlobalResponseMessageBody {

        /** 响应码 **/
        private int code;

        /** 响应消息 **/
        private String message;

        /** 响应体 **/
        private String modelRef;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getModelRef() {
			return modelRef;
		}

		public void setModelRef(String modelRef) {
			this.modelRef = modelRef;
		}
        
        

    }


    public static class UiConfig {

        private String validatorUrl;
        private DocExpansion docExpansion = DocExpansion.NONE;    // none | list
//        private String docExpansion = "none";
        private String apiSorter = "alpha";       // alpha
        private ModelRendering defaultModelRendering = ModelRendering.MODEL;   // schema
//        private String defaultModelRendering = "schema";

        /** 是否启用json编辑器 **/
        private Boolean jsonEditor = false;
        /** 是否显示请求头信息 **/
        private Boolean showRequestHeaders = true;
        /** 支持页面提交的请求类型 **/
        private String submitMethods = "get,post,put,delete,patch";
        /** 请求超时时间 **/
        private Long requestTimeout = 10000L;
        
        /**是否启用 deep link **/               
        private Boolean deepLinking = false;
        /**Controls the display of operationId in operations list. The default is false **/
        private Boolean displayOperationId = false ;
        /** The default expansion depth for models (set to -1 completely hide the models)**/
        private Integer defaultModelsExpandDepth = 1;
        /** The default expansion depth for the model on the model-example section **/
        private Integer defaultModelExpandDepth = 1;
        /** Controls the display of the request duration (in milliseconds) for Try-It-Out requests**/
        private Boolean displayRequestDuration = false;
        /**  If set, enables filtering. The top bar will show an edit box that you can use to
   *                                 filter the tagged operations that are shown. Can be Boolean to enable or disable,
   *                                 or a string, in which case filtering will be enabled using that string as the
   *                                 filter expression. Filtering is case sensitive matching the filter expression
   *                                 anywhere inside the tag**/
        private Object filter = false;
        /** If set, limits the number of tagged operations displayed to at most this many. The
   *                                 default is to show all operations**/
        private Integer maxDisplayedTags;
        /**Apply a sort to the operation list of each API. It can be 'alpha' (sort by paths
   *                                 alphanumerically), 'method' (sort by HTTP method) or a function (see
   *                                 Array.prototype.sort() to know how sort function works). Default is the order
   *                                 returned by the server unchanged **/
        private OperationsSorter operationsSorter;
        /** Controls the display of vendor extension (x-) fields and values for Operations,
   *                                 Parameters, and Schema **/
        private Boolean showExtensions;
        /**Apply a sort to the tag list of each API. It can be 'alpha' (sort by paths
   *                                 alphanumerically) or a function (see Array.prototype.sort() to learn how to write a
   *                                 sort function). Two tag name strings are passed to the sorter for each pass.
   *                                 Default is the order determined by Swagger-UI **/
        private TagsSorter tagsSorter;
		public String getValidatorUrl() {
			return validatorUrl;
		}
		public void setValidatorUrl(String validatorUrl) {
			this.validatorUrl = validatorUrl;
		}
		public DocExpansion getDocExpansion() {
			return docExpansion;
		}
		public void setDocExpansion(DocExpansion docExpansion) {
			this.docExpansion = docExpansion;
		}
		public String getApiSorter() {
			return apiSorter;
		}
		public void setApiSorter(String apiSorter) {
			this.apiSorter = apiSorter;
		}
		public ModelRendering getDefaultModelRendering() {
			return defaultModelRendering;
		}
		public void setDefaultModelRendering(ModelRendering defaultModelRendering) {
			this.defaultModelRendering = defaultModelRendering;
		}
		public Boolean getJsonEditor() {
			return jsonEditor;
		}
		public void setJsonEditor(Boolean jsonEditor) {
			this.jsonEditor = jsonEditor;
		}
		public Boolean getShowRequestHeaders() {
			return showRequestHeaders;
		}
		public void setShowRequestHeaders(Boolean showRequestHeaders) {
			this.showRequestHeaders = showRequestHeaders;
		}
		public String getSubmitMethods() {
			return submitMethods;
		}
		public void setSubmitMethods(String submitMethods) {
			this.submitMethods = submitMethods;
		}
		public Long getRequestTimeout() {
			return requestTimeout;
		}
		public void setRequestTimeout(Long requestTimeout) {
			this.requestTimeout = requestTimeout;
		}
		public Boolean getDeepLinking() {
			return deepLinking;
		}
		public void setDeepLinking(Boolean deepLinking) {
			this.deepLinking = deepLinking;
		}
		public Boolean getDisplayOperationId() {
			return displayOperationId;
		}
		public void setDisplayOperationId(Boolean displayOperationId) {
			this.displayOperationId = displayOperationId;
		}
		public Integer getDefaultModelsExpandDepth() {
			return defaultModelsExpandDepth;
		}
		public void setDefaultModelsExpandDepth(Integer defaultModelsExpandDepth) {
			this.defaultModelsExpandDepth = defaultModelsExpandDepth;
		}
		public Integer getDefaultModelExpandDepth() {
			return defaultModelExpandDepth;
		}
		public void setDefaultModelExpandDepth(Integer defaultModelExpandDepth) {
			this.defaultModelExpandDepth = defaultModelExpandDepth;
		}
		public Boolean getDisplayRequestDuration() {
			return displayRequestDuration;
		}
		public void setDisplayRequestDuration(Boolean displayRequestDuration) {
			this.displayRequestDuration = displayRequestDuration;
		}
		public Object getFilter() {
			return filter;
		}
		public void setFilter(Object filter) {
			this.filter = filter;
		}
		public Integer getMaxDisplayedTags() {
			return maxDisplayedTags;
		}
		public void setMaxDisplayedTags(Integer maxDisplayedTags) {
			this.maxDisplayedTags = maxDisplayedTags;
		}
		public OperationsSorter getOperationsSorter() {
			return operationsSorter;
		}
		public void setOperationsSorter(OperationsSorter operationsSorter) {
			this.operationsSorter = operationsSorter;
		}
		public Boolean getShowExtensions() {
			return showExtensions;
		}
		public void setShowExtensions(Boolean showExtensions) {
			this.showExtensions = showExtensions;
		}
		public TagsSorter getTagsSorter() {
			return tagsSorter;
		}
		public void setTagsSorter(TagsSorter tagsSorter) {
			this.tagsSorter = tagsSorter;
		}
        
        
    }

}


