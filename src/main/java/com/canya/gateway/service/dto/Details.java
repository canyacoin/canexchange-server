package com.canya.gateway.service.dto;


public class Details {

private String type;
private String contractAddress;
private String supply;
private Changer changer;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public String getContractAddress() {
return contractAddress;
}

public void setContractAddress(String contractAddress) {
this.contractAddress = contractAddress;
}

public String getSupply() {
return supply;
}

public void setSupply(String supply) {
this.supply = supply;
}

public Changer getChanger() {
return changer;
}

public void setChanger(Changer changer) {
this.changer = changer;
}

}

