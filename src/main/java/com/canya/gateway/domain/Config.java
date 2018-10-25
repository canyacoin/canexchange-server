package com.canya.gateway.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Id;

@Entity
@Table(name = "config")
public class Config implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	private String id;

	private String key;
	private String value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
