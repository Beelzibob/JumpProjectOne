package com.cognixia.jump.coreProject;

import java.io.Serializable;

//simple person class to inherit from
public class Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5826063788524746901L;
	//can't be private or it won't be inherited
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
