package com.canya.gateway.service.dto;

public class Txrefs {

	private String tx_hash;
	private Double block_height;
	private Double tx_input_n;
	private Double tx_output_n;
	private Double value;
	private Double ref_balance;
	private Double confirmations;
	private String confirmed;
	private boolean double_spend;

	public String getTx_hash() {
		return tx_hash;
	}

	public void setTx_hash(String tx_hash) {
		this.tx_hash = tx_hash;
	}

	public Double getBlock_height() {
		return block_height;
	}

	public void setBlock_height(Double block_height) {
		this.block_height = block_height;
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

	public Double getRef_balance() {
		return ref_balance;
	}

	public void setRef_balance(Double ref_balance) {
		this.ref_balance = ref_balance;
	}

	public Double getConfirmations() {
		return confirmations;
	}

	public void setConfirmations(Double confirmations) {
		this.confirmations = confirmations;
	}

	public String getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}

	public boolean isDouble_spend() {
		return double_spend;
	}

	public void setDouble_spend(boolean double_spend) {
		this.double_spend = double_spend;
	}

}
