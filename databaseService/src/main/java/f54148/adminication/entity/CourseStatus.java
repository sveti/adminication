package f54148.adminication.entity;

import java.io.Serializable;

public enum CourseStatus implements Serializable {
	
	UPCOMMING, STARTED, ENDED, POSTPONED, CANCELED;

	public String getCourseStatus() {
		return this.name();
	}
}
