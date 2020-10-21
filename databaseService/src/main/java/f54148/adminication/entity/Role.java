package f54148.adminication.entity;

import java.io.Serializable;

public enum Role implements Serializable{
	ROLE_STUDENT,ROLE_PARENT,ROLE_TEACHER, ROLE_ADMIN;
	
	public String getRole() {
		return this.name();
	}
}
