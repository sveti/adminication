import React from "react";
import BackButton from "../../../common/BackButton";
import { textToDayOfTheWeek } from "../../../common/helper";
import "./lessons.css";
const StudentLessonsOfCourse = (props) => {
  const { lessons, course } = props.location.lessonProps;

  const attendedBadge = (
    <div className="badge alert-success attendanceBadge">Attended</div>
  );

  const missingBadge = (
    <div className="badge alert-danger attendanceBadge">Not Attended</div>
  );

  return (
    <div className="lessonsOfCourseContainer">
      <h1 className="courseTitle">{course.title}</h1>
      <div className="courseInfo">
        {course.days.map((date, index) => {
          return (
            <div className="center my-3 " key={date}>
              <h5 className="day">{textToDayOfTheWeek(date)}</h5>
              <h5 className="startTime">
                {course.startTime[index].slice(0, -3)}
              </h5>
              <h5 className="startTime">:</h5>
              <h5 className="endTime">{course.endTime[index].slice(0, -3)}</h5>
            </div>
          );
        })}
      </div>
      <div>
        <BackButton></BackButton>
      </div>
      {lessons.length === 0 ? (
        <h3>No lessons for this course yet, try later!</h3>
      ) : (
        lessons.map((lesson) => {
          return (
            <div
              className="lessonPerCourse card rounded studentLesson"
              key={lesson.id}
            >
              <div className="card-body">
                <h4 className="card-title">
                  {lesson.date}
                  {lesson.attended ? attendedBadge : missingBadge}
                </h4>
                <h3 className="card-body">{lesson.description}</h3>
              </div>
            </div>
          );
        })
      )}
    </div>
  );
};

export default StudentLessonsOfCourse;
