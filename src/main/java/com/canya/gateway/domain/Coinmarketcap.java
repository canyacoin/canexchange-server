//package com.canya.gateway.domain;
//
//import java.io.Serializable;
//
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
///**
// * A Transaction.
// */
//
//@Entity
//@Table(name = "coinmarketcap")
//public class Coinmarketcap extends AbstractAuditingEntity implements Serializable {
//
//	private static final long serialVersionUID = 1L;
//
//	private String id;
//
//	private String name;
//
//	private String symbol;
//
//	private String website_slug;
//
//	private boolean status;
//
//	public boolean isStatus() {
//		return status;
//	}
//
//	public void setStatus(boolean status) {
//		this.status = status;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public String getSymbol() {
//		return symbol;
//	}
//
//	public void setSymbol(String symbol) {
//		this.symbol = symbol;
//	}
//
//	public String getWebsite_slug() {
//		return website_slug;
//	}
//
//	public void setWebsite_slug(String website_slug) {
//		this.website_slug = website_slug;
//	}
//
//	@Override
//	public String toString() {
//		return "Coinmarketcap [id=" + id + ", name=" + name + ", symbol=" + symbol + ", website_slug=" + website_slug
//				+ "]";
//	}
//
//}
