import React, { Component } from "react";

import {
  getAllCourses,
  getAllCourseDetails,
} from "../../services/courseService";

import {
  dynamicSort,
  getMinDateAsDate,
  textToDate,
  isAfterDate,
} from "../../common/helper";

import "./course.css";
import CoursesSearchBar from "./CoursesSearchBar";
import CourseCard from "./CourseCard";

class AllCoursesList extends Component {
  state = {
    user: this.props.user,
    allCourses: [],
    courses: [],
    allCourseDetails: [],
  };

  componentDidMount = () => {
    this.getCourses();
    this.getCourseDetails();
  };

  getCourses = async () => {
    const { data } = await getAllCourses();
    ///use a set they said
    ///its more efficient they said
    ///little did they know
    ///order DOES matter
    data.sort(dynamicSort("id"));
    data.forEach((element) => {
      element.details.sort();
      element.teachers.sort();
    });
    this.setState({ allCourses: data, courses: data });
  };

  getCourseDetails = async () => {
    const { data } = await getAllCourseDetails();
    this.setState({ allCourseDetails: data });
  };

  handleSubmit = (
    levels,
    courseDetails,
    title,
    avialable,
    startDate,
    endDate
  ) => {
    const { allCourses } = this.state;
    if (
      levels.length === 0 &&
      courseDetails.length === 0 &&
      title.length === 0 &&
      avialable === false &&
      startDate === "" &&
      endDate === ""
    ) {
      this.setState({ courses: allCourses });
    } else {
      let titleFiltered = [...allCourses],
        levelsFiltered = [...allCourses],
        courseDetailsFiltered = [...allCourses],
        avialableFiltered = [...allCourses],
        dateFiltered = [...allCourses];

      if (title.length !== 0) {
        titleFiltered = allCourses.filter((course) =>
          course.title.toLowerCase().includes(title.toLowerCase())
        );
      }
      if (levels.length !== 0) {
        levelsFiltered = allCourses.filter((course) =>
          levels.includes(course.level)
        );
      }
      if (courseDetails.length !== 0) {
        courseDetailsFiltered = allCourses.filter(function (e) {
          return e.details.some(function (a) {
            return courseDetails.indexOf(a) !== -1;
          });
        });
      }
      if (avialable) {
        avialableFiltered = allCourses.filter(
          (course) => course.signUpLimit - course.signedUp > 0
        );
      }
      if (startDate !== "") {
        dateFiltered = allCourses.filter((course) =>
          isAfterDate(getMinDateAsDate(course.startDate), textToDate(startDate))
        );
      }
      if (endDate !== "") {
        dateFiltered = dateFiltered.filter((course) =>
          isAfterDate(textToDate(startDate), getMinDateAsDate(course.startDate))
        );
      }

      let merged = titleFiltered
        .filter((n) => levelsFiltered.includes(n))
        .filter((n) => courseDetailsFiltered.includes(n))
        .filter((n) => avialableFiltered.includes(n))
        .filter((n) => dateFiltered.includes(n));
      this.setState({ courses: merged });
    }
  };

  render() {
    const { courses, allCourses, allCourseDetails } = this.state;

    return (
      <div className="container">
        <div className="lessonsOfCourseContainer row">
          <div className="col-sm-12 col-md-4">
            <div className="p-3 border rounded m-2 mb-5">
              <CoursesSearchBar
                allCourseDetails={allCourseDetails}
                handleSubmit={(
                  levels,
                  courseDetails,
                  title,
                  avialable,
                  startDate,
                  endDate
                ) =>
                  this.handleSubmit(
                    levels,
                    courseDetails,
                    title,
                    avialable,
                    startDate,
                    endDate
                  )
                }
              ></CoursesSearchBar>
            </div>
          </div>
          <div className="col-sm-12 col-md-8">
            {allCourses.length > 0 ? (
              courses.length > 0 ? (
                <React.Fragment>
                  <h3 className="mb-5">
                    Showing {courses.length} out of {allCourses.length} courses
                  </h3>
                  <div>
                    {courses.map((course) => (
                      <CourseCard course={course} key={course.id}></CourseCard>
                    ))}
                  </div>
                </React.Fragment>
              ) : (
                <h3>No courses match your search criteria!</h3>
              )
            ) : (
              <h3>No active courses, try again later!</h3>
            )}
          </div>
        </div>
      </div>
    );
  }
}

export default AllCoursesList;
