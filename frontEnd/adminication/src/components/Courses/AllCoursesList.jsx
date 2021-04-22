import React, { Component } from "react";

import {
  getAllCourses,
  getAllCourseDetails,
} from "../../services/courseService";

import { getScheduleOfStudent } from "../../services/scheduleService";

import {
  dynamicSort,
  getMinDateAsDate,
  textToDate,
  isAfterDate,
  textToDayOfTheWeekNumber,
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
    studentSchedule: [],
    scheduleConflict: [],
  };

  isOutsideOfTimeRange = (start1, end1, start2, end2) => {
    ////https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap

    return (start1 > start2 ? start1 : start2) > (end1 < end2 ? end1 : end2);
  };

  scheduleOverlap = (dates, startTimes, endTimes) => {
    const { studentSchedule } = this.state;

    let dayOfTheWeek = [];

    dates.forEach((element) => {
      dayOfTheWeek.push(textToDayOfTheWeekNumber(element));
    });

    let sameDayOfTheWeek = studentSchedule.filter((schedule) =>
      dayOfTheWeek.includes(textToDayOfTheWeekNumber(schedule.startDate))
    );

    if (sameDayOfTheWeek.length === 0) return false;
    else {
      var indicator = false;
      sameDayOfTheWeek.some((schedule) => {
        startTimes.some((element, index) => {
          if (
            !this.isOutsideOfTimeRange(
              schedule.startTime,
              schedule.endTime,
              startTimes[index],
              endTimes[index]
            )
          ) {
            indicator = true;
            return true;
          }
          return false;
        });
        return indicator;
      });
      return indicator;
    }
  };

  componentDidMount = () => {
    this.getCourseDetails();
    this.getScheduleOfStudent();
  };

  getScheduleOfStudent = async () => {
    const { data } = await getScheduleOfStudent(this.state.user.id);
    this.setState({ studentSchedule: data });
    this.getCourses();
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

    let scheduleConflict = [];
    data.forEach((course) => {
      let sheduleOverLap = this.scheduleOverlap(
        course.startDate,
        course.startTime,
        course.endTime
      );
      scheduleConflict.push({
        courseId: course.id,
        scheduleOverlap: sheduleOverLap,
      });
    });

    this.setState({
      allCourses: data,
      courses: data,
      scheduleConflict: scheduleConflict,
    });
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
    endDate,
    scheduleConflict
  ) => {
    const { allCourses } = this.state;
    if (
      levels.length === 0 &&
      courseDetails.length === 0 &&
      title.length === 0 &&
      avialable === false &&
      startDate === "" &&
      endDate === "" &&
      scheduleConflict === false
    ) {
      this.setState({ courses: allCourses });
    } else {
      let titleFiltered = [...allCourses],
        levelsFiltered = [...allCourses],
        courseDetailsFiltered = [...allCourses],
        avialableFiltered = [...allCourses],
        dateFiltered = [...allCourses],
        sheduleFiltered = [...allCourses];

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
          isAfterDate(textToDate(endDate), getMinDateAsDate(course.startDate))
        );
      }
      if (scheduleConflict !== false) {
        sheduleFiltered = allCourses.filter(
          (course) =>
            !this.state.scheduleConflict.find(
              (conflicts) => course.id === conflicts.courseId
            ).scheduleOverlap
        );
      }

      let merged = titleFiltered
        .filter((n) => levelsFiltered.includes(n))
        .filter((n) => courseDetailsFiltered.includes(n))
        .filter((n) => avialableFiltered.includes(n))
        .filter((n) => dateFiltered.includes(n))
        .filter((n) => sheduleFiltered.includes(n));
      this.setState({ courses: merged });
    }
  };

  render() {
    const {
      courses,
      allCourses,
      allCourseDetails,
      scheduleConflict,
    } = this.state;

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
                  endDate,
                  scheduleConflict
                ) =>
                  this.handleSubmit(
                    levels,
                    courseDetails,
                    title,
                    avialable,
                    startDate,
                    endDate,
                    scheduleConflict
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
                      <CourseCard
                        course={course}
                        key={course.id}
                        scheduleConflict={scheduleConflict.find(
                          (conflicts) => course.id === conflicts.courseId
                        )}
                      ></CourseCard>
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
