package com.canya.gateway.service.dto;

public class Unconfirmed_Txrefs implements Comparable<Unconfirmed_Txrefs>{

	private String address;
	private String tx_hash;
	private Double tx_input_n;
	private Double tx_output_n;
	private Double value;
	private String received;
	private Double confirmations;
	private boolean double_spend;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTx_hash() {
		return tx_hash;
	}

	public void setTx_hash(String tx_hash) {
		this.tx_hash = tx_hash;
	}

	public Double getTx_input_n() {
		return tx_input_n;
	}

	public void setTx_input_n(Double tx_input_n) {
		this.tx_input_n = tx_input_n;
	}

	public Double getTx_output_n() {
		return tx_output_n;
	}

	public void setTx_output_n(Double tx_output_n) {
		this.tx_output_n = tx_output_n;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getReceived() {
		return received;
	}

	public void setReceived(String received) {
		this.received = received;
	}

	public Double getConfirmations() {
		return confirmations;
	}

	public void setConfirmations(Double confirmations) {
		this.confirmations = confirmations;
	}

	public boolean isDouble_spend() {
		return double_spend;
	}

	public void setDouble_spend(boolean double_spend) {
		this.double_spend = double_spend;
	}

	@Override
	public int compareTo(Unconfirmed_Txrefs arg0) {
		return getReceived().compareTo(arg0.getReceived());
	}

}
