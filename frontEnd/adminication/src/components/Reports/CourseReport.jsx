import React, { Component } from "react";
import { dynamicSort } from "../../common/helper";
import { getCourseReport } from "../../services/courseService";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import BackButton from "../../common/BackButton";

import {
  faCheckCircle,
  faTimesCircle,
} from "@fortawesome/free-solid-svg-icons";
import StatusBadge from "../../common/StausBadge";

class CourseReport extends Component {
  constructor(props) {
    super(props);
    this.state = {
      courseId: null,
      stats: [],
      showStats: false,
      title: null,
      showEnrollments: false,
      showLessons: false,
      showWaitingList: false,
    };
    if (props.location && props.location.statisticsProps) {
      this.state.courseId = props.location.statisticsProps.courseId;
    } else {
      this.state.courseId = props.courseId;
    }
    if (
      props.location &&
      props.location.statisticsProps &&
      props.location.statisticsProps.courseTitle
    ) {
      this.state.title = props.location.statisticsProps.courseTitle;
    }
  }

  formatDate = (d) => {
    return d[8] + d[9] + "." + d[5] + d[6] + "." + d[0] + d[1] + d[2] + d[3];
  };

  loadCourseStats = async () => {
    let { data } = await getCourseReport(this.state.courseId);
    data.teachers = data.teachers.sort(dynamicSort("teacherName"));
    data.courseReportLessons = data.courseReportLessons.sort(
      dynamicSort("lessonId")
    );
    data.finalGradesDTO = data.finalGradesDTO.sort(dynamicSort("studentName"));
    data.studentsSignedUp = data.studentsSignedUp.sort(
      dynamicSort("studentName")
    );

    return data;
  };

  componentDidMount = async () => {
    const stats = await this.loadCourseStats();
    this.setState({ stats, showStats: true });
  };

  calculateTotalAttended = (lesson) => {
    let attended = 0;
    lesson.attendanceList.forEach((element) => {
      if (element.attended) attended++;
    });
    return attended;
  };

  createTeacher = (teacher) => {
    return (
      <div key={teacher.teacherId}>
        <div className="row">
          <p className="col-sm-6 col-md-2">#{teacher.teacherId}</p>
          <p className="col-sm-6 col-md-3">{teacher.name}</p>
          <p className="col-sm-12 col-md-3">
            {teacher.pricePerLesson} $/lesson
          </p>
          {teacher.subId ? (
            <p className="col-sm-12 col-md-4">
              Substituted by #{teacher.subId} {teacher.subName}
            </p>
          ) : (
            <p className="col-sm-12 col-md-4">No substitute</p>
          )}
        </div>
        <hr></hr>
      </div>
    );
  };

  createAttendance = (attendance) => {
    return (
      <div className="col-12" key={attendance.attendanceId}>
        <div className="row">
          <p className="col-2">#{attendance.studentId}</p>
          <p className="col-8">{attendance.studentName}</p>
          <p className="col-2">
            {attendance.attended ? (
              <FontAwesomeIcon className="present" icon={faCheckCircle} />
            ) : (
              <FontAwesomeIcon className="absent" icon={faTimesCircle} />
            )}
          </p>
        </div>
        <hr></hr>
      </div>
    );
  };

  createLesson = (lesson, index) => {
    lesson.attendanceList = lesson.attendanceList.sort(
      dynamicSort("studentName")
    );
    return (
      <div key={lesson.lessonId}>
        <div className="row">
          <h5 className="col-sm-6 col-md-6">
            Lesson# {index + 1} on {this.formatDate(lesson.date)}
          </h5>
          <h5 className="col-sm-6 col-md-6">by {lesson.teacherName}</h5>
          <p className="col-sm-12 col-md-12 my-3">{lesson.description}</p>
          <h5 className="col-sm-12 col-md-12 mb-3">
            Attendances: {this.calculateTotalAttended(lesson)} total
          </h5>
          {lesson.attendanceList.map((attendance) => {
            return this.createAttendance(attendance);
          })}
        </div>
      </div>
    );
  };

  createFinalGrading = (grading) => {
    return (
      <div className="col-12" key={grading.studentId}>
        <div className="row">
          <p className="col-2">#{grading.studentId}</p>
          <p className="col-8">{grading.studentName}</p>
          <p className="col-2">
            {grading.grade > 0 ? grading.grade : "Not set"}
          </p>
        </div>
        <hr></hr>
      </div>
    );
  };

  createStudentEnrollment = (enrollment) => {
    return (
      <div className="row" key={enrollment.studentId}>
        <div className="col-12">
          <div className="row">
            <p className="col-2">#{enrollment.studentId}</p>
            <p className="col-8">{enrollment.studentName}</p>
          </div>
          <hr></hr>
        </div>
      </div>
    );
  };

  render() {
    const {
      courseId,
      title,
      showStats,
      stats,
      showEnrollments,
      showLessons,
      showWaitingList,
    } = this.state;

    return (
      <div className="salaryDiv container">
        {title ? (
          <h3 className=" col-12 mb-5">
            Report for #{courseId} {title}
          </h3>
        ) : null}
        <div className="statsResult">
          {showStats ? (
            <div>
              {
                <React.Fragment>
                  <div className="row">
                    <h3 className="col-8">{stats.title}</h3>
                    <h3 className="col-4 mb-3">
                      <StatusBadge status={stats.status} />
                    </h3>
                    <p className="col-12 my-5 left">{stats.description}</p>
                  </div>
                  <div className="row mb-5">
                    <h4 className="col-10 mb-5">
                      Students signed up: {stats.studentsSignedUp.length}
                    </h4>
                    <p className="col-2">
                      {stats.studentsSignedUp.length > 0 ? (
                        <button
                          className="btn selectBtn"
                          onClick={() =>
                            this.setState({ showEnrollments: !showEnrollments })
                          }
                        >
                          {showEnrollments
                            ? "Hide enrollments"
                            : "Show enrollments"}
                        </button>
                      ) : null}
                    </p>
                    {showEnrollments ? (
                      <div className="col-12 text-left fadeIn">
                        {stats.studentsSignedUp.map((enrollment) => {
                          return this.createStudentEnrollment(enrollment);
                        })}
                      </div>
                    ) : null}
                  </div>
                  <div className="row mb-5">
                    <h4 className="col-10 mb-5">
                      Students waiting : {stats.studentsWaitingList.length}
                    </h4>
                    <p className="col-2">
                      {stats.studentsWaitingList.length > 0 ? (
                        <button
                          className="btn selectBtn"
                          onClick={() =>
                            this.setState({ showWaitingList: !showWaitingList })
                          }
                        >
                          {showWaitingList
                            ? "Hide waiting list"
                            : "Show waiting list"}
                        </button>
                      ) : null}
                    </p>
                    {showWaitingList ? (
                      <div className="col-12 text-left fadeIn">
                        {stats.studentsWaitingList.map((enrollment) => {
                          return this.createStudentEnrollment(enrollment);
                        })}
                      </div>
                    ) : null}
                  </div>
                  <div className="row">
                    <div className="col-12 mb-3">
                      <h4>Teachers: </h4>
                    </div>
                    <div className="col-12 text-left">
                      {stats.teachers.map((teacher) => {
                        return this.createTeacher(teacher);
                      })}
                    </div>
                  </div>

                  {stats.finalGradesDTO.length ? (
                    <div className="row">
                      <div className="col-12 mb-3">
                        <h4>Final grades:</h4>
                      </div>
                      <div className="col-12 text-left">
                        {stats.finalGradesDTO.map((grade) => {
                          return this.createFinalGrading(grade);
                        })}
                      </div>
                    </div>
                  ) : null}
                  {stats.courseReportLessons.length ? (
                    <div className="row">
                      <h4 className="col-10 mb-3">
                        Lessons: {stats.courseReportLessons.length} total
                      </h4>
                      <p className="col-2">
                        <button
                          className="btn selectBtn"
                          onClick={() =>
                            this.setState({ showLessons: !showLessons })
                          }
                        >
                          {showLessons ? "Hide lessons" : "Show lessons"}
                        </button>
                      </p>
                      {showLessons ? (
                        <div className="col-12 text-left fadeIn">
                          {stats.courseReportLessons.map((lesson, index) => {
                            return this.createLesson(lesson, index);
                          })}
                          <p className="col-12 text-right">
                            <button
                              className="btn selectBtn"
                              onClick={() =>
                                this.setState({ showLessons: !showLessons })
                              }
                            >
                              {showLessons ? "Hide lessons" : "Show lessons"}
                            </button>
                          </p>
                        </div>
                      ) : null}
                    </div>
                  ) : null}
                </React.Fragment>
              }
            </div>
          ) : null}
        </div>
        <BackButton></BackButton>
      </div>
    );
  }
}

export default CourseReport;
