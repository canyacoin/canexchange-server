
package com.canya.gateway.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ETHP {

	private String address;
	private String name;
	private String decimals;
	private String symbol;
	private String totalSupply;
	private String owner;
	private long transfersCount;
	private long lastUpdated;
	private long issuancesCount;
	private long holdersCount;
	private long ethTransfersCount;
	private long countOps;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDecimals() {
		return decimals;
	}

	public void setDecimals(String decimals) {
		this.decimals = decimals;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getTotalSupply() {
		return totalSupply;
	}

	public void setTotalSupply(String totalSupply) {
		this.totalSupply = totalSupply;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public long getTransfersCount() {
		return transfersCount;
	}

	public void setTransfersCount(long transfersCount) {
		this.transfersCount = transfersCount;
	}

	public long getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(long lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public long getIssuancesCount() {
		return issuancesCount;
	}

	public void setIssuancesCount(long issuancesCount) {
		this.issuancesCount = issuancesCount;
	}

	public long getHoldersCount() {
		return holdersCount;
	}

	public void setHoldersCount(long holdersCount) {
		this.holdersCount = holdersCount;
	}

	public long getEthTransfersCount() {
		return ethTransfersCount;
	}

	public void setEthTransfersCount(long ethTransfersCount) {
		this.ethTransfersCount = ethTransfersCount;
	}

	public long getCountOps() {
		return countOps;
	}

	public void setCountOps(long countOps) {
		this.countOps = countOps;
	}

}
