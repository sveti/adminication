import React, { useState } from "react";
import LevelBadge from "../../common/LevelBadge";
import CourseDetailsBadge from "../../common/CourseDetailsBadge";
import { getMinDate, textToDayOfTheWeek } from "../../common/helper";
import "./course.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBan, faCheck } from "@fortawesome/free-solid-svg-icons";

const CourseCard = ({
  course,
  scheduleConflict,
  parentView,
  onCourseSignUp,
  onWaitingListSignUp,
  waitingList,
  onRemoveFromWaitingList,
}) => {
  let sheduleOverLap;

  const [disable, setDisable] = useState(false);

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
      <FontAwesomeIcon icon={faBan}></FontAwesomeIcon> Schedule conflict
    </span>
  );

  let courseClasses = ["card-header", "cardHeaderBackground"];
  if (sheduleOverLap) {
    courseClasses.push("scheduleOverlap");
  } else {
    courseClasses.push("noScheduleOverlap");
  }

  function onSignUpButtonClick() {
    setDisable(true);
    onCourseSignUp(course);
  }

  function onWaitingListButtonClick() {
    setDisable(true);
    onWaitingListSignUp(course);
  }

  function onRemoveFromWaitingListClick() {
    setDisable(true);
    onRemoveFromWaitingList(waitingList[0]);
  }

  const signUpButton = (
    <button
      className="btn float-right signUpButton"
      disabled={disable}
      onClick={onSignUpButtonClick}
    >
      Sign up
    </button>
  );

  const courseWaitingList = (
    <button
      className="btn float-right waitingListButton"
      disabled={disable}
      onClick={onWaitingListButtonClick}
    >
      Join waiting list
    </button>
  );

  const removeFromWaitingList = (
    <span>
      <button
        className="btn float-right removeWaitingListButton"
        disabled={disable}
        onClick={onRemoveFromWaitingListClick}
      >
        Remove from waiting list
      </button>
    </span>
  );

  return (
    <div className="card text-left mb-5">
      <div className={courseClasses.join(" ")}>
        <h5 className="mt-2 d-inline-block">{course.title}</h5>
        {sheduleOverLap ? scheduleAlertBadge : scheduleOkBadge}
        {parentView
          ? waitingList.length > 0
            ? removeFromWaitingList
            : course.signUpLimit - course.signedUp > 0
            ? signUpButton
            : courseWaitingList
          : null}
        {waitingList.length > 0 && disable !== true ? (
          <h6 className="card-subtitle mb-2 numberInLine">
            Number in Line: {waitingList[0].numberInLine + 1}
          </h6>
        ) : null}
      </div>
      <div className="card-body">
        <h5 className="card-text my-2 pb-3">
          Price: {course.pricePerStudent}$
        </h5>
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
