package com.landal.jee.jaxb.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String surname;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

}
