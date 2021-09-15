import React, { useState, useEffect } from "react";
import "./reports.css";

export default function GeneralStudentReport(props) {
  const { student, data } = props;

  const [total, setTotal] = useState(0);

  useEffect(() => {
    data.lessons.forEach((lesson) => {
      setTotal(
        (total) => total + lesson.attendances.length * lesson.pricePerAttendance
      );
    });
  }, [data]);

  function dateFormatter(date) {
    return (
      date[date.length - 2] +
      date[date.length - 1] +
      "." +
      date[date.length - 5] +
      date[date.length - 4] +
      "." +
      date[0] +
      date[1] +
      date[2] +
      date[3]
    );
  }

  function multipleAttendances(lessonsArray) {
    let message =
      "There are " +
      lessonsArray.length +
      " attendances on the following dates: ";

    lessonsArray.forEach((element, index) => {
      if (index < lessonsArray.length - 2) {
        message += dateFormatter(element.date);
        message += " ,  ";
      } else if (index === lessonsArray.length - 2) {
        message += dateFormatter(element.date);
        message += " and ";
      } else {
        message += dateFormatter(element.date);
      }
    });
    return message;
  }

  let parentViewClasses = "col-sm-12 col-md-9";
  let studenttViewClasses = "col-sm-12 col-md-12";

  return (
    <div className="row singleStudent">
      {student ? (
        <div className="col-sm-12 col-md-3">
          <h5>
            {student.name} {student.lastName}
          </h5>
        </div>
      ) : null}
      <div className={student ? parentViewClasses : studenttViewClasses}>
        {data ? (
          data.lessons.map((lesson) => (
            <div className="row mb-5 coursesStats" key={lesson.courseId}>
              <div className="col-sm-12 col-md-3">
                <h5>{lesson.courseTitle}</h5>
                <p>
                  {lesson.attendances.length} x {lesson.pricePerAttendance}$ ={" "}
                  {lesson.attendances.length * lesson.pricePerAttendance}$
                </p>
              </div>
              {lesson.attendances.length > 0 ? (
                <p className="col-sm-12 col-md-9">
                  {lesson.attendances.length === 1
                    ? "There is one attended lesson at " +
                      dateFormatter(lesson.attendances[0].date)
                    : multipleAttendances(lesson.attendances)}
                </p>
              ) : (
                <p className="col-sm-12 col-md-9">
                  There are no attendances for this course for the selected time
                  period
                </p>
              )}
            </div>
          ))
        ) : (
          <h3>
            This student has no active courses for the selected time period
          </h3>
        )}
      </div>
      <div className="col-sm-12 total">
        <p>Total for this student for the selected time period: {total} $</p>
      </div>
    </div>
  );
}
