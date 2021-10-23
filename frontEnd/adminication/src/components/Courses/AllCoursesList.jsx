import React, { Component } from "react";
import CoursesSearchBar from "./CoursesSearchBar";
import CourseCard from "./CourseCard";
import {
  getAllCourses,
  getAllCourseDetails,
  getStartedCoursesOfStudent,
  getUpcommingCoursesOfStudent,
} from "../../services/courseService";

import { addEnrollment } from "../../services/enrollmentService";
import {
  addCourseWaitingListSignUp,
  getWaitingListCoursesOfStudent,
  deleteCourseWaitingList,
} from "../../services/waitingListService";

import { getScheduleOfStudent } from "../../services/scheduleService";
import { toast } from "react-toastify";

import {
  dynamicSort,
  getMinDateAsDate,
  textToDate,
  isAfterDate,
  textToDayOfTheWeekNumber,
  getMaxDate,
} from "../../common/helper";

import "./course.css";

class AllCoursesList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      user: this.props.user,
      allCourses: [],
      courses: [],
      allCourseDetails: [],
      studentSchedule: [],
      scheduleConflict: [],
      waitingList: [],
      parentView: false,
    };
    if (!this.props.user) {
      this.state.user = this.props.location.state.user;
      this.state.parentView = this.props.location.state.parentView;
    }
  }

  isOutsideOfTimeRange = (start1, end1, start2, end2) => {
    ////https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap

    return (start1 > start2 ? start1 : start2) > (end1 < end2 ? end1 : end2);
  };

  scheduleOverlap = (dates, startTimes, endTimes) => {
    const { studentSchedule } = this.state;
    let courseStartDate = getMaxDate(dates);
    // courseStartDate = courseStartDate.replace(/\./g, "-");

    //https://stackoverflow.com/questions/11343939/how-to-add-weeks-to-date-using-javascript#:~:text=You%20can%20do%20this%20%3A,7)%3B%20alert(now)%3B

    let conflictingStudentShed = [];

    studentSchedule.forEach((sched) => {
      let endDateAsDate = new Date(sched.startDate);
      endDateAsDate.setDate(endDateAsDate.getDate() + sched.duration * 7);
      if (courseStartDate > endDateAsDate) {
        //console.log(courseStartDate + " is after " + endDateAsDate);
      } else {
        //console.log(courseStartDate + " is before " + endDateAsDate);
        conflictingStudentShed.push(sched);
      }
    });

    if (conflictingStudentShed.length === 0) return false;

    let dayOfTheWeek = [];

    dates.forEach((element) => {
      dayOfTheWeek.push(textToDayOfTheWeekNumber(element));
    });

    let sameDayOfTheWeek = conflictingStudentShed.filter((schedule) =>
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

  onCourseSignUp = async (course) => {
    const response = await addEnrollment({
      studentId: this.state.user.id,
      courseId: course.id,
    }).catch(function (error) {
      if (error.response) {
        // Request made and server responded
        toast.error(error.response.data.error, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else if (error.request) {
        // The request was made but no response was received
        toast.error("An error occured! Try again later", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else {
        // Something happened in setting up the request that triggered an Error
        toast.error(error.message, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      }
    });

    if (response && response.status === 200) {
      const message =
        this.state.user.name +
        " " +
        this.state.user.lastName +
        " has been successfully enrolled in " +
        course.title;
      toast.success(message, {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
      this.props.increase();
    }
  };

  onWaitingListSignUp = async (course) => {
    const currentDate = new Date();
    const response = await addCourseWaitingListSignUp({
      studentId: this.state.user.id,
      courseId: course.id,
      signed: currentDate.toISOString().slice(0, -5),
    }).catch(function (error) {
      if (error.response) {
        // Request made and server responded
        toast.error(error.response.data.error, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else if (error.request) {
        // The request was made but no response was received
        toast.error("An error occured saving your lesson! Try again later", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else {
        // Something happened in setting up the request that triggered an Error
        toast.error(error.message, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      }
    });

    if (response && response.status === 200) {
      const message =
        this.state.user.name +
        " " +
        this.state.user.lastName +
        " has been successfully added in the waiting list for " +
        course.title;
      toast.success(message, {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
      this.props.increase();
    }
  };

  onRemoveFromWaitingList = async (waitingList) => {
    const response = await deleteCourseWaitingList(
      waitingList.courseWaitingListId
    ).catch(function (error) {
      if (error.response) {
        // Request made and server responded
        toast.error(error.response.data.error, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else if (error.request) {
        // The request was made but no response was received
        toast.error("An error occured saving your lesson! Try again later", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else {
        // Something happened in setting up the request that triggered an Error
        toast.error(error.message, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      }
    });

    if (response && response.status === 200) {
      toast.success(response.data, {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
    }
  };

  componentDidMount = () => {
    this.getCourseDetails();
    this.getScheduleOfStudent();
    this.getStudentWaitingList();
  };

  getStudentWaitingList = async () => {
    const { data } = await getWaitingListCoursesOfStudent(this.state.user.id);
    this.setState({ waitingList: data });
  };

  getScheduleOfStudent = async () => {
    const { data } = await getScheduleOfStudent(this.state.user.id);
    this.setState({ studentSchedule: data });
    this.getCourses();
  };

  getCourses = async () => {
    const { user } = this.state;

    let { data } = await getAllCourses();

    ///remove the courses the student has already signed up for
    const { data: startedCourses } = await getStartedCoursesOfStudent(user.id);
    const { data: upcommingCourses } = await getUpcommingCoursesOfStudent(
      user.id
    );

    data = data.filter(
      (course) => !startedCourses.some((c) => c.id === course.id)
    );
    data = data.filter(
      (course) => !upcommingCourses.some((c) => c.id === course.id)
    );
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
    scheduleConflict,
    waitingList
  ) => {
    const { allCourses } = this.state;
    if (
      levels.length === 0 &&
      courseDetails.length === 0 &&
      title.length === 0 &&
      avialable === false &&
      startDate === "" &&
      endDate === "" &&
      scheduleConflict === false &&
      waitingList === false
    ) {
      this.setState({ courses: allCourses });
    } else {
      let titleFiltered = [...allCourses],
        levelsFiltered = [...allCourses],
        courseDetailsFiltered = [...allCourses],
        avialableFiltered = [...allCourses],
        dateFiltered = [...allCourses],
        sheduleFiltered = [...allCourses],
        waitingListFiltered = [...allCourses];

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
      if (waitingList !== false) {
        waitingListFiltered = allCourses.filter((course) =>
          this.state.waitingList.some((c) => c.courseId === course.id)
        );
      }

      let merged = titleFiltered
        .filter((n) => levelsFiltered.includes(n))
        .filter((n) => courseDetailsFiltered.includes(n))
        .filter((n) => avialableFiltered.includes(n))
        .filter((n) => dateFiltered.includes(n))
        .filter((n) => sheduleFiltered.includes(n))
        .filter((n) => waitingListFiltered.includes(n));
      this.setState({ courses: merged });
    }
  };

  render() {
    const {
      courses,
      allCourses,
      allCourseDetails,
      scheduleConflict,
      parentView,
      waitingList,
    } = this.state;

    return (
      <div className="container">
        <div className="lessonsOfCourseContainer row">
          <div className="col-sm-12 col-md-4">
            <div className="p-3 border rounded m-2 mb-5 filters">
              <CoursesSearchBar
                allCourseDetails={allCourseDetails}
                handleSubmit={(
                  levels,
                  courseDetails,
                  title,
                  avialable,
                  startDate,
                  endDate,
                  scheduleConflict,
                  waitingList
                ) =>
                  this.handleSubmit(
                    levels,
                    courseDetails,
                    title,
                    avialable,
                    startDate,
                    endDate,
                    scheduleConflict,
                    waitingList
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
                        parentView={parentView}
                        waitingList={waitingList.filter(
                          (e) => e.courseId === course.id
                        )}
                        onCourseSignUp={(course) => this.onCourseSignUp(course)}
                        onWaitingListSignUp={(course) =>
                          this.onWaitingListSignUp(course)
                        }
                        onRemoveFromWaitingList={(waitingList) =>
                          this.onRemoveFromWaitingList(waitingList)
                        }
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
