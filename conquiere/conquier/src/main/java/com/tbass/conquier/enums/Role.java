package com.tbass.conquier.enums;

public enum Role {

	USER("USER"), ADMIN("ADMIN");

	private String value;

	private Role(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
