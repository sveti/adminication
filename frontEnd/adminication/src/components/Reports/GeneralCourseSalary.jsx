import React from "react";
import DetailedCourseSalary from "./DetailedCourseSalary";

function GeneralCourseSalary(props) {
  const { lessonsOfCourse, teaching: t, salaryForCourse } = props;

  return lessonsOfCourse.length > 0 ? (
    <div className="row coursesStats" key={t.id}>
      <div className="col-sm-12 col-md-3">
        <h5 key={t.id}>{t.courseTitle}</h5>
      </div>
      <div className="col-sm-12 col-md-9">
        <div className="row">
          <div className="col-sm-12 col-md-3">
            <p>Total number of lessons:</p>
          </div>
          <div className="col-sm-12 col-md-3">
            <p>{lessonsOfCourse.length}</p>
          </div>
          <div className="col-sm-12 col-md-3">
            <p>Salary for this course:</p>
          </div>
          <div className="col-sm-12 col-md-3">
            <p>{salaryForCourse}$</p>
          </div>
        </div>
      </div>
      <div className="col-12 mb-5 detailedCourseSalaryContainer">
        <DetailedCourseSalary
          lessonsOfCourse={lessonsOfCourse}
          courseSignedUp={t.courseSignedUp}
          salaryPerStudent={t.salaryPerStudent}
        ></DetailedCourseSalary>
      </div>
    </div>
  ) : null;
}

export default GeneralCourseSalary;
