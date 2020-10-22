package f54148.adminication.entity;

import java.io.Serializable;

public enum Level implements Serializable{
	A1,A2,B1,B2,C1;
	
	public String getLevel() {
		return this.name();
	}

}
