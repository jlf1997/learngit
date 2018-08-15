package com.cimr.boot.comm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageModel<T> {
	
	private List<T> content;
	
	private Integer pageSize;
	
	private Long totalCount;
	
	private Integer totalPage;

	public PageModel() {
		init();
	}
	public PageModel(List<T> content,Integer pageSize,Integer totalPage,Long totalCount) {
		this.content = content;
		this.pageSize = pageSize;
		this.totalPage =  totalPage;
		this.totalCount = totalCount;
	}
	
	public PageModel(Page<T> page) {
		if(page!=null) {
			content = page.getContent();
			pageSize = page.getSize();
			totalPage = page.getTotalPages();
			totalCount = page.getTotalElements();
		}else {
			init();
		}
		
		
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}



	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	
	public void init() {
		content = new ArrayList<>();
		pageSize = 0;
		totalPage = 0;
		totalCount = 0L;
	}
	
	
	public static <T> PageModel<T> addNewPageModel(int pageSize,PageModel<T> page1,PageModel<T> page2) {
		PageModel<T> res = new PageModel<>();
		if(page1!=null) {
			res.totalCount+=page1.getTotalCount();
			res.totalPage+=page1.getTotalPage();
			for(T t :page1.getContent()) {
				res.content.add(t);
			}
		}
		if(page2!=null) {
			res.totalCount+=page2.getTotalCount();
			res.totalPage+=page2.getTotalPage();
			for(T t :page2.getContent()) {
				res.content.add(t);
			}	
			
		}
		res.setPageSize(pageSize);
		if(res.getTotalCount()%pageSize==0) {
			
		}
		res.setTotalPage(getTotlaPage(res.getTotalCount(),pageSize));
		return res;
	}
	
	public static int getTotlaPage(long totalCount,int pageSize) {
		if(totalCount%pageSize==0) {
			return (int) (totalCount/pageSize);
		}else {
			return (int)(totalCount/pageSize)+1;
		}
	}
	
}
