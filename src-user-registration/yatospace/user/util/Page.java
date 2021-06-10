package yatospace.user.util;

import java.io.Serializable;

/**
 * Подаци о страничењу. 
 * @author MV
 * @version 1.0
 */
public class Page implements Serializable{
	private static final long serialVersionUID = 5175525575635561040L;
	private int pageNo = 1; 
	private int pageSize = 10; 
	private String startFilter = "";
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		if(pageNo<0) pageNo = 0; 
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		if(pageSize<1) pageSize = 1; 
		this.pageSize = pageSize;
	}
	public String getStartFilter() {
		return startFilter;
	}
	public void setStartFilter(String startFilter) {
		if(startFilter==null) startFilter=""; 
		this.startFilter = startFilter;
	}
	
	public void reset() {
		pageNo = 1;
		pageSize = 10; 
		startFilter = ""; 
	}
	
	public void next() {
		setPageNo(pageNo+1); 
	}
	
	public void previous() {
		setPageNo(pageNo-1); 
	}
}
