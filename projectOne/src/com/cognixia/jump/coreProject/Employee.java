package com.cognixia.jump.coreProject;

public class Employee extends Person {
	/**
	 * Automatically gets interface from parent so needs its own serial version
	 */
	private static final long serialVersionUID = -7865053232028300844L;

	private String  title;
	private Department department;
	
	public Employee( ) {
		//Default
	}
	
	public Employee(String name, String title, Department d) {
		super.setName(name);
		this.title = title;
		this.department = d;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@Override
	public String toString() {
		return(super.getName() + ", " + this.title + ", " + this.department);
	}
}
