package f54148.adminication.entity;

import java.io.Serializable;

public enum CourseStatus implements Serializable {
	
	UPCOMMING, STARTED, FINISHED, POSTPONED, CANCELED;

	public String getCourseStatus() {
		return this.name();
	}
}
