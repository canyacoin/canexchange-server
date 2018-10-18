package com.canya.gateway.service.dto;

import java.util.List;

public class Currencies {

	private Boolean includeTotal;
	private Integer skip;
	private Integer limit;
	private List<Page> page = null;

	public Boolean getIncludeTotal() {
		return includeTotal;
	}

	public void setIncludeTotal(Boolean includeTotal) {
		this.includeTotal = includeTotal;
	}

	public Integer getSkip() {
		return skip;
	}

	public void setSkip(Integer skip) {
		this.skip = skip;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public List<Page> getPage() {
		return page;
	}

	public void setPage(List<Page> page) {
		this.page = page;
	}

}
