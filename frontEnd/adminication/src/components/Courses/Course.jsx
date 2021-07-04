import React, { Component } from "react";
import BackButton from "../../common/BackButton";
import CourseDetailsBadge from "../../common/CourseDetailsBadge";
import LevelBadge from "../../common/LevelBadge";
import { getCourseWithDetails } from "../../services/courseService";

import "./course.css";

class Course extends Component {
  state = {
    course: {},
    loading: true,
    parentView: this.props.parentView,
  };

  getMinDate() {
    const dates = this.state.course.startDate.map((dateStr) => {
      return new Date(dateStr);
    });
    var minDate = new Date(Math.min.apply(null, dates));
    return minDate.toDateString();
  }

  convertDateToDay(str) {
    const weekdays = [
      "Sunday",
      "Monday",
      "Tuesday",
      "Wednesday",
      "Thursday",
      "Friday",
      "Saturday",
    ];
    const date = new Date(str);
    return weekdays[date.getDay()];
  }

  async loadCourse() {
    const { data } = await getCourseWithDetails(
      this.props.match.params.courseId
    );
    this.setState({ course: data, loading: false });
  }

  componentDidMount = () => {
    this.loadCourse();
  };

  render() {
    const { course, parentView } = this.state;

    if (this.state.loading) {
      return <h1>Loading...</h1>;
    } else {
      return (
        <React.Fragment>
          <div className="card courseContainer rounded-lg">
            <div className="card-body">
              <div className="card-title">
                <h1>Course #{course.id}</h1>
                <h2 className="courseTitle">{course.title}</h2>
              </div>

              <div className="row info-group">
                <div className="col-sm-12 col-md-3 dataLabel">
                  <h5>Description: </h5>
                </div>
                <div className="col-sm-12 col-md-9 courseData">
                  {course.description}
                </div>
              </div>
              <div className="row info-group">
                <div className="col-sm-12 col-md-3 dataLabel">
                  <h5>Level: </h5>
                </div>
                <div className=" col-sm-12 col-md-9 courseData">
                  <LevelBadge level={course.level}></LevelBadge>
                </div>
              </div>
              <div className="row info-group space">
                <div className="col-sm-12 col-md-3 dataLabel">
                  <h5>Teachers: </h5>
                </div>
                <div className="col-sm-12 col-md-9 courseData">
                  {course.teachers.map((teacher) => {
                    return (
                      <p key={teacher} className="mb-3">
                        {teacher}
                      </p>
                    );
                  })}
                </div>
              </div>
              <div className="row info-group">
                <div className="col-sm-12 col-md-3 dataLabel">
                  <h5>Classes per week: </h5>
                </div>
                <div className="col-sm-12 col-md-3 courseData">
                  {course.startDate.length}
                </div>
                <div className="col-sm-12 col-md-3 dataLabel">
                  <h5>Duration: </h5>
                </div>
                <div className="col-sm-12 col-md-3 courseData">
                  {course.duration} Weeks
                </div>
              </div>
              <div className="row info-group">
                <div className="col-sm-12 col-md-3 dataLabel">
                  <h5>Start date: </h5>
                </div>
                <div className="col-sm-12 col-md-9 courseData">
                  {this.getMinDate()}
                </div>
              </div>
              <div className="row info-group space">
                <div className="col-sm-12 col-md-3 dataLabel">
                  <h5>Shedule: </h5>
                </div>
                <div className="col-sm-12 col-md-9 courseData">
                  {course.startDate.map((startD, index) => {
                    return (
                      <p key={startD} className="mb-3">
                        {this.convertDateToDay(startD)}{" "}
                        {course.startTime[index].slice(0, -3)} -
                        {course.endTime[index].slice(0, -3)}{" "}
                      </p>
                    );
                  })}
                </div>
              </div>
              <div className="row info-group">
                <div className="col-sm-12 col-md-3 dataLabel">
                  <h5>Number of signed up: </h5>
                </div>
                <div className="col-sm-12 col-md-3 courseData">
                  {course.signedUp}
                </div>
                <div className="col-sm-12 col-md-3 dataLabel">
                  <h5>Maximum number of students: </h5>
                </div>
                <div className="col-sm-12 col-md-3 courseData">
                  {course.signUpLimit}
                </div>
              </div>

              <div className="row info-group">
                <div className="col-sm-12 col-md-3 dataLabel">
                  <h5>Tags: </h5>
                </div>
                <div className="col-sm-12 col-md-9 courseData">
                  {course.details.map((detail) => {
                    return (
                      <CourseDetailsBadge
                        key={detail}
                        text={detail}
                      ></CourseDetailsBadge>
                    );
                  })}
                </div>
              </div>
            </div>
          </div>
          {parentView ? (
            <div>
              <h1>Wololo</h1>
            </div>
          ) : null}
          <div>
            <BackButton></BackButton>
          </div>
        </React.Fragment>
      );
    }
  }
}
export default Course;
