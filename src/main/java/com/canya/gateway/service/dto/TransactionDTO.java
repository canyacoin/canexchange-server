package com.canya.gateway.service.dto;

/**
 * A Transaction.
 */

public class TransactionDTO {

	private String id;

	private String address;

	private String amount;

	private String currency;

	private String email;

	private boolean accept;

	private String usd;

	private String key;

	private String status;

	private String hash;

	private String date;

	private String eth;

	private String hashEthertoAccount;

	public String getHashEthertoAccount() {
		return hashEthertoAccount;
	}

	public void setHashEthertoAccount(String hashEthertoAccount) {
		this.hashEthertoAccount = hashEthertoAccount;
	}

	public String getEth() {
		return eth;
	}

	public void setEth(String eth) {
		this.eth = eth;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isAccept() {
		return accept;
	}

	public void setAccept(boolean accept) {
		this.accept = accept;
	}

	public String getUsd() {
		return usd;
	}

	public void setUsd(String usd) {
		this.usd = usd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", address=" + address + ", amount=" + amount + ", currency=" + currency
				+ ", email=" + email + ", accept=" + accept + ", usd=" + usd + ", key=" + key + "]";
	}

}
