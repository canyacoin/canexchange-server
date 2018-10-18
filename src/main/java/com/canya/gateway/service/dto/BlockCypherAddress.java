package com.canya.gateway.service.dto;

import java.util.List;

public class BlockCypherAddress {

	private String address;
	private Double total_received;
	private Double total_sent;
	private Double balance;
	private Double unconfirmed_balance;
	private Double final_balance;
	private Double n_tx;
	private Double unconfirmed_n_tx;
	private Double final_n_tx;
	private Double nonce;
	private Double pool_nonce;
	private String tx_url;
	private Boolean hasMore;

	private List<Txrefs> txrefs;

	private List<Unconfirmed_Txrefs> unconfirmed_txrefs;

	public Boolean getHasMore() {
		return hasMore;
	}

	public void setHasMore(Boolean hasMore) {
		this.hasMore = hasMore;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getTotal_received() {
		return total_received;
	}

	public void setTotal_received(Double total_received) {
		this.total_received = total_received;
	}

	public Double getTotal_sent() {
		return total_sent;
	}

	public void setTotal_sent(Double total_sent) {
		this.total_sent = total_sent;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getUnconfirmed_balance() {
		return unconfirmed_balance;
	}

	public void setUnconfirmed_balance(Double unconfirmed_balance) {
		this.unconfirmed_balance = unconfirmed_balance;
	}

	public Double getFinal_balance() {
		return final_balance;
	}

	public void setFinal_balance(Double final_balance) {
		this.final_balance = final_balance;
	}

	public Double getN_tx() {
		return n_tx;
	}

	public void setN_tx(Double n_tx) {
		this.n_tx = n_tx;
	}

	public Double getUnconfirmed_n_tx() {
		return unconfirmed_n_tx;
	}

	public void setUnconfirmed_n_tx(Double unconfirmed_n_tx) {
		this.unconfirmed_n_tx = unconfirmed_n_tx;
	}

	public Double getFinal_n_tx() {
		return final_n_tx;
	}

	public void setFinal_n_tx(Double final_n_tx) {
		this.final_n_tx = final_n_tx;
	}

	public Double getNonce() {
		return nonce;
	}

	public void setNonce(Double nonce) {
		this.nonce = nonce;
	}

	public Double getPool_nonce() {
		return pool_nonce;
	}

	public void setPool_nonce(Double pool_nonce) {
		this.pool_nonce = pool_nonce;
	}

	public String getTx_url() {
		return tx_url;
	}

	public void setTx_url(String tx_url) {
		this.tx_url = tx_url;
	}

	public List<Txrefs> getTxrefs() {
		return txrefs;
	}

	public void setTxrefs(List<Txrefs> txrefs) {
		this.txrefs = txrefs;
	}

	public List<Unconfirmed_Txrefs> getUnconfirmed_txrefs() {
		return unconfirmed_txrefs;
	}

	public void setUnconfirmed_txrefs(List<Unconfirmed_Txrefs> unconfirmed_txrefs) {
		this.unconfirmed_txrefs = unconfirmed_txrefs;
	}

	@Override
	public String toString() {
		return "BlockCypherAddress [address=" + address + ", total_received=" + total_received + ", total_sent="
				+ total_sent + ", balance=" + balance + ", unconfirmed_balance=" + unconfirmed_balance
				+ ", final_balance=" + final_balance + ", n_tx=" + n_tx + ", unconfirmed_n_tx=" + unconfirmed_n_tx
				+ ", final_n_tx=" + final_n_tx + ", nonce=" + nonce + ", pool_nonce=" + pool_nonce + ", tx_url="
				+ tx_url + ", hasMore=" + hasMore + ", txrefs=" + txrefs + ", unconfirmed_txrefs=" + unconfirmed_txrefs
				+ "]";
	}

}
