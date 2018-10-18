package com.canya.gateway.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.persistence.Id;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
public class Transaction extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 1, max = 100)
	private String address;

	@NotNull
	@Size(min = 1, max = 100)
	private String amount;

	@NotNull
	@Size(min = 1, max = 100)
	private String currency;

	@NotNull
	@Size(min = 1, max = 100)
	private String email;

	private boolean accept;

	private String usd;

	private String orderid;

	private String status;

	private String hash;

	private String date;

	private String eth;

	private String hashethertoaccount;

	public String getHashethertoaccount() {
		return hashethertoaccount;
	}

	public void setHashethertoaccount(String hashethertoaccount) {
		this.hashethertoaccount = hashethertoaccount;
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

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
				+ ", email=" + email + ", accept=" + accept + ", usd=" + usd + ", orderid=" + orderid + ", status="
				+ status + ", hash=" + hash + ", date=" + date + ", eth=" + eth + ", hashethertoaccount="
				+ hashethertoaccount + "]";
	}

}
