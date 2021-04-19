import React from "react";
import LevelBadge from "../../common/LevelBadge";
import CourseDetailsBadge from "../../common/CourseDetailsBadge";
import { getMinDate, textToDayOfTheWeek } from "../../common/helper";
import "./course.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBan, faCheck } from "@fortawesome/free-solid-svg-icons";

const CourseCard = ({ course, scheduleConflict }) => {
  let sheduleOverLap;

  if (scheduleConflict !== undefined) {
    sheduleOverLap = scheduleConflict.scheduleOverlap;
  } else {
    sheduleOverLap = false;
  }

  const scheduleOkBadge = (
    <span className="mx-3">
      <FontAwesomeIcon icon={faCheck}></FontAwesomeIcon>
    </span>
  );
  const scheduleAlertBadge = (
    <span className="mx-3">
      <FontAwesomeIcon icon={faBan}></FontAwesomeIcon> Shedule conflict
    </span>
  );

  let courseClasses = ["card-header", "cardHeaderBackground"];
  if (sheduleOverLap) {
    courseClasses.push("scheduleOverlap");
  } else {
    courseClasses.push("noScheduleOverlap");
  }

  return (
    <div className="card text-left mb-5">
      <div className={courseClasses.join(" ")}>
        <h5 className="m-0 d-inline-block">{course.title}</h5>
        {sheduleOverLap ? scheduleAlertBadge : scheduleOkBadge}
      </div>
      <div className="card-body">
        <h6 className="card-subtitle mb-2 text-muted">
          {course.duration} Weeks x {course.startDate.length} Lessons
        </h6>
        <LevelBadge level={course.level}></LevelBadge>
        <div className="my-2">
          {course.details.map((d) => (
            <CourseDetailsBadge key={d} text={d}></CourseDetailsBadge>
          ))}
        </div>

        <p className="card-text mt-2">{course.description}</p>

        <p className="card-text">Start date : {getMinDate(course.startDate)}</p>

        <p className="card-text">
          Free places : {course.signUpLimit - course.signedUp}/
          {course.signUpLimit}
        </p>
        <div className="miniTable">
          <div className="card-header px-0">
            <span className="mx-2">Shedule</span>
          </div>
          <ul className="list-group list-group-flush">
            {course.startDate.map((d, index) => (
              <li className="list-group-item" key={d}>
                <span>{textToDayOfTheWeek(d)}</span>
                <span> {course.startTime[index].slice(0, -3)} - </span>
                <span>{course.endTime[index].slice(0, -3)} </span>
              </li>
            ))}
          </ul>
        </div>
        <div className="miniTable">
          <div className="card-header px-0">
            <span className="mx-2">Teachers</span>
          </div>
          <ul className="list-group list-group-flush">
            {course.teachers.map((teacher) => (
              <li className="list-group-item" key={teacher}>
                <span>{teacher}</span>
              </li>
            ))}
          </ul>
        </div>
      </div>
      <div
        className={
          "card-footer " +
          (course.signUpLimit - course.signedUp > 0
            ? "enoughSpace"
            : "notEnoughSpace")
        }
      >
        Free places : {course.signUpLimit - course.signedUp}/
        {course.signUpLimit}
      </div>
    </div>
  );
};

export default CourseCard;
