package com.canya.gateway.service.dto;


public class Page {

private String id;
private String symbol;
private String type;
private String code;
private Details details;
private String status;
private String stage;
private String primaryCommunityImageName;
private String createdAt;
private Integer numDecimalDigits;
private String name;
private Integer order;
private Boolean isDeleted;
private String lowerCaseSymbol;
private String lowerCaseCode;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getSymbol() {
return symbol;
}

public void setSymbol(String symbol) {
this.symbol = symbol;
}

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

public Details getDetails() {
return details;
}

public void setDetails(Details details) {
this.details = details;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getStage() {
return stage;
}

public void setStage(String stage) {
this.stage = stage;
}

public String getPrimaryCommunityImageName() {
return primaryCommunityImageName;
}

public void setPrimaryCommunityImageName(String primaryCommunityImageName) {
this.primaryCommunityImageName = primaryCommunityImageName;
}

public String getCreatedAt() {
return createdAt;
}

public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

public Integer getNumDecimalDigits() {
return numDecimalDigits;
}

public void setNumDecimalDigits(Integer numDecimalDigits) {
this.numDecimalDigits = numDecimalDigits;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public Integer getOrder() {
return order;
}

public void setOrder(Integer order) {
this.order = order;
}

public Boolean getIsDeleted() {
return isDeleted;
}

public void setIsDeleted(Boolean isDeleted) {
this.isDeleted = isDeleted;
}

public String getLowerCaseSymbol() {
return lowerCaseSymbol;
}

public void setLowerCaseSymbol(String lowerCaseSymbol) {
this.lowerCaseSymbol = lowerCaseSymbol;
}

public String getLowerCaseCode() {
return lowerCaseCode;
}

public void setLowerCaseCode(String lowerCaseCode) {
this.lowerCaseCode = lowerCaseCode;
}

}