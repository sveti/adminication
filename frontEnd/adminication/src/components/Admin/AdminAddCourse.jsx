import React, { Component } from "react";
import Select from "react-select";
import BackButton from "../../common/BackButton";
import CourseDetailsBadge from "../../common/CourseDetailsBadge";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSave } from "@fortawesome/free-solid-svg-icons";
import { TimePicker } from "react-tempusdominus-bootstrap";
import {
  getAllCourseDetails,
  getEditCourse,
  editCourse,
} from "../../services/courseService";
import {
  getTodaysDate,
  textToDayOfTheWeekNumber,
  addDays,
} from "../../common/helper";
import { getTeachersForCourse } from "../../services/teacherAdministrationService";
import { addNewCourse } from "../../services/courseService";
import { toast } from "react-toastify";
import moment from "moment";

import "./admin.css";

class AdminAddCourse extends Component {
  constructor(props) {
    super(props);
    this.state = {
      mode: this.props.mode ? this.props.mode : "save",
      course: {
        startDate: getTodaysDate(),
        details: [],
        scheudles: [
          { dayOfTheWeek: textToDayOfTheWeekNumber(getTodaysDate()) },
        ],
        lessonsPerWeek: 1,
        teachers: [],
      },
      showCharLimitWarning: false,
      charLimit: 2048,
      allCourseDetails: [],
      currentNewDetail: "",
      newCourseDetails: [],
      allTeachers: [],
    };
  }

  convertFromIdToValue = (teachers) => {
    ///replace id from array with value for view
    const newArrayOfObj = teachers.map(({ id: value, ...rest }) => ({
      value,
      ...rest,
    }));

    return newArrayOfObj;
  };

  convertFromTeacherIdToValue = (teachers) => {
    ///replace teacherId from array with value
    const newArrayOfObj = teachers.map(
      ({ teacherId: value, teacherName: label, ...rest }) => ({
        value,
        label,
        ...rest,
      })
    );
    return newArrayOfObj;
  };

  convertTeachersFromViewToDTO = (teachers) => {
    let convertedArray = [];

    teachers.forEach((teacher) => {
      let teacherForArray = {};
      if (teacher.id) {
        teacherForArray.id = teacher.id;
        teacherForArray.teacherId = teacher.value;
      } else {
        teacherForArray.teacherId = teacher.value;
      }
      teacherForArray.teacherName = teacher.label;
      teacherForArray.salary = teacher.salary;
      if (teacher.substituteId !== null) {
        teacherForArray.substituteId = teacher.substituteId;
      }
      convertedArray.push(teacherForArray);
    });
    return convertedArray;
  };

  convertDaysToSchedule = () => {
    let { course } = this.state;

    const start = course.startDate;
    let firstLesson = textToDayOfTheWeekNumber(start);

    course.scheudles.forEach((schedule, index) => {
      let diff = schedule.dayOfTheWeek - firstLesson;
      if (diff === -6) {
        diff = 1;
      }
      let startDateFull = addDays(start, diff);
      course.scheudles[index].startDate = startDateFull
        .toISOString()
        .split("T")[0];
    });

    return course;
  };

  convertScheduleToDays = (course) => {
    course.scheudles.forEach((schedule, index) => {
      course.startDate = course.scheudles[0].startDate;
      course.scheudles[index].dayOfTheWeek = textToDayOfTheWeekNumber(
        schedule.startDate
      );
    });

    return course;
  };

  getTeachersForCourse = async () => {
    let { data } = await getTeachersForCourse();
    data = this.convertFromIdToValue(data);
    this.setState({ allTeachers: data });
  };

  getCourseDetails = async () => {
    const { data } = await getAllCourseDetails();
    this.setState({ allCourseDetails: data });
  };

  filterCourseDetails = () => {
    const { course, allCourseDetails } = this.state;

    const myArrayFiltered = allCourseDetails.filter((el) => {
      return !course.details.some((f) => {
        return f.id === el.id;
      });
    });
    return myArrayFiltered;
  };

  getCourse = async () => {
    const { data } = await getEditCourse(this.props.location.state.courseId);
    let converted = this.convertScheduleToDays(data);
    converted.teachers = this.convertFromTeacherIdToValue(converted.teachers);
    return converted;
  };

  componentDidMount = async () => {
    if (this.state.mode === "edit") {
      const course = await this.getCourse();
      this.setState({ course });
    }
    this.getCourseDetails();
    this.getTeachersForCourse();
  };

  handleInputChange = (event, property) => {
    let course = { ...this.state.course };
    course[property] = event.target.value;
    if (property === "startDate") {
      course.scheudles[0].dayOfTheWeek = textToDayOfTheWeekNumber(
        event.target.value
      );
      course.scheudles[0].startDate = event.target.value;
    }
    this.setState({ course });

    if (
      property === "description" &&
      course.description.length > this.state.charLimit
    ) {
      this.setState({ showCharLimitWarning: true });
    } else {
      this.setState({ showCharLimitWarning: false });
    }
  };

  handleNewCourseDetail = (event) => {
    this.setState({ currentNewDetail: event.target.value });
  };

  handleTimeChange = (e, property, i) => {
    let time = moment(e, ["h:mm A"]).format("HH:mm");
    time += ":00";
    let { course } = this.state;
    // eslint-disable-next-line no-extend-native
    Array.prototype.insert = function (index) {
      this.splice.apply(
        this,
        [index, 0].concat(Array.prototype.slice.call(arguments, 1))
      );
      return this;
    };

    if (course.scheudles[i] === undefined) {
      let obj = {};
      obj[property] = time;
      course.scheudles.insert(i, obj);
    } else {
      let obj = { ...course.scheudles[i] };
      obj[property] = time;
      course.scheudles[i] = obj;
    }
    this.setState({ course });
  };

  handleScheduleDayChange = (event, i) => {
    // eslint-disable-next-line no-extend-native
    Array.prototype.insert = function (index) {
      this.splice.apply(
        this,
        [index, 0].concat(Array.prototype.slice.call(arguments, 1))
      );
      return this;
    };

    let { course } = this.state;
    if (course.scheudles[i] === undefined) {
      course.scheudles.insert(i, {
        dayOfTheWeek: parseInt(event.target.value),
      });
    } else {
      course.scheudles[i].dayOfTheWeek = parseInt(event.target.value);
    }
    this.setState({ course });
  };

  handleTeacherChange = (selectedOption) => {
    let { course } = this.state;
    course.teachers = selectedOption;
    this.setState({ course });
  };

  handleLevelChange = (selectedOption) => {
    let { course } = this.state;
    course.level = selectedOption.value;
    this.setState({ course });
  };

  handleStatusChange = (selectedOption) => {
    let { course } = this.state;
    course.status = selectedOption.value;
    this.setState({ course });
  };

  clearCourse = () => {
    let { course } = this.state;
    course.title = "";
    course.description = "";
    course.pricePerStudent = null;
    course.maxStudents = null;
    course.level = null;
    course.startDate = getTodaysDate();
    course.details = [];
    course.scheudles = [
      {
        dayOfTheWeek: textToDayOfTheWeekNumber(getTodaysDate()),
        startTime: null,
        endTime: null,
      },
    ];
    course.teachers = [];
    course.lessonsPerWeek = 1;
    course.teachers.length = 0;
    course.teachers = [];
    course.duration = 1;
    let { allTeachers } = this.state;

    allTeachers.forEach((t) => delete t.salary);

    let newCourseDetails = [];

    this.setState({ course, allTeachers, newCourseDetails: newCourseDetails });
  };

  saveCourse = async () => {
    let { course, mode } = this.state;

    let updatedCourse = { ...course };

    updatedCourse.scheudles.length = updatedCourse.lessonsPerWeek;

    updatedCourse = this.convertDaysToSchedule();
    updatedCourse.newCourseDetails = this.state.newCourseDetails;

    updatedCourse.teachers = this.convertTeachersFromViewToDTO(
      updatedCourse.teachers
    );

    if (mode === "save") {
      // console.log("=====Save====");
      // console.log(updatedCourse);
      const { data } = await addNewCourse(updatedCourse);
      if (data) {
        toast.success("The course has been added!", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        this.clearCourse();
        this.getCourseDetails();
        window.scrollTo(0, 0);
      } else {
        toast.error("An error has occured", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      }
    } else {
      // console.log(updatedCourse);
      const { data } = await editCourse(updatedCourse);
      if (data) {
        toast.success("The course has been updated!", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        this.getCourseDetails();
        const c = await this.getCourse();
        this.setState({ course: c });
      } else {
        toast.error("An error has occured", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      }
    }
  };

  checkIfAllIsFilled = () => {
    const { course } = this.state;

    if (!course.title || course.title.length < 5) {
      return true;
    }

    if (
      !course.description ||
      course.description.length > this.state.charLimit
    ) {
      return true;
    }

    if (!course.pricePerStudent || course.pricePerStudent <= 0) {
      return true;
    }

    if (!course.level) {
      return true;
    }

    if (!course.duration || course.duration <= 0) {
      return true;
    }

    if (
      course.details.length === 0 &&
      this.state.newCourseDetails.length === 0
    ) {
      return true;
    }
    if (!course.startDate) {
      return true;
    }
    if (!course.scheudles[0].startTime || !course.scheudles[0].endTime) {
      return true;
    }
    if (!course.teachers || course.teachers.length === 0) {
      return true;
    }

    return false;
  };

  addCourseDetail = (courseDetail) => {
    let { course } = this.state;
    if (!course.details) {
      course.details = [courseDetail];
    } else {
      course.details.push(courseDetail);
    }

    this.setState({ course });
  };

  removeCourseDetail = (courseDetail) => {
    let { course } = this.state;

    course.details = course.details.filter((el) => el.id !== courseDetail.id);

    this.setState({ course });
  };

  removeNewCourseDetail = (newCourseDetail) => {
    let { newCourseDetails } = this.state;

    newCourseDetails = newCourseDetails.filter((el) => el !== newCourseDetail);

    this.setState({ newCourseDetails });
  };

  addNewCourseDetail = () => {
    const { currentNewDetail } = this.state;
    let { newCourseDetails } = this.state;
    if (!newCourseDetails.includes(currentNewDetail)) {
      newCourseDetails.push(currentNewDetail);
    }

    this.setState({ currentNewDetail: "", newCourseDetails: newCourseDetails });
  };

  handleTeacherSalaryUpdate = (event, teacherId) => {
    let { course } = this.state;
    let teacherIndex = course.teachers.findIndex(
      (el) => el.value === teacherId
    );
    course.teachers[teacherIndex].salary = event.target.value;
    this.setState({ course });
  };

  generateScheduleFields = () => {
    const { course } = this.state;
    let schedules = [];
    for (let i = 0; i < this.state.course.lessonsPerWeek; i++) {
      schedules.push(
        <div className="form-group row mt-2 fadeIn" key={i}>
          <label htmlFor="level" className="col-sm-2 col-md-2 col-form-label">
            Day of the week Lesson #{i + 1}
          </label>
          <div className="col-sm-10 col-md-2">
            <select
              className="form-control"
              value={
                course.scheudles[i] ? course.scheudles[i].dayOfTheWeek : ""
              }
              onChange={(event) => this.handleScheduleDayChange(event, i)}
              disabled={i === 0}
            >
              <option
                defaultValue
                disabled={
                  course.scheudles[i] && course.scheudles[i].dayOfTheWeek
                    ? true
                    : false
                }
              >
                ==Select==
              </option>
              <option value="1">Monday</option>
              <option value="2">Tuesday</option>
              <option value="3">Wednesday</option>
              <option value="4">Thursday</option>
              <option value="5">Friday</option>
              <option value="6">Saturday</option>
              <option value="0">Sunday</option>
            </select>
          </div>
          <label
            htmlFor="duration"
            className="col-sm-2 col-md-2 col-form-label"
          >
            Start time Lesson #{i + 1}
          </label>
          <div className="col-sm-10 col-md-2 timePicker">
            <TimePicker
              widgetPositioning={{ vertical: "top", horizontal: "auto" }}
              date={course.scheudles[i] ? course.scheudles[i].startTime : ""}
              onChange={(e) => this.handleTimeChange(e.date, "startTime", i)}
            />
          </div>
          <label
            htmlFor="duration"
            className="col-sm-2 col-md-2 col-form-label"
          >
            End time Lesson #{i + 1}
          </label>
          <div className="col-sm-10 col-md-2 timePicker">
            <TimePicker
              widgetPositioning={{ vertical: "top", horizontal: "auto" }}
              date={course.scheudles[i] ? course.scheudles[i].endTime : ""}
              onChange={(e) => this.handleTimeChange(e.date, "endTime", i)}
            />
          </div>
        </div>
      );
    }
    return schedules;
  };

  handleSubstituteUpdate = (selectedOption, teacherId) => {
    let { course } = this.state;
    let teacherIndex = course.teachers.findIndex(
      (el) => el.value === teacherId
    );
    if (selectedOption === null) {
      course.teachers[teacherIndex].substitute = null;
      course.teachers[teacherIndex].substituteId = null;
    } else {
      course.teachers[teacherIndex].substitute = selectedOption.label;
      course.teachers[teacherIndex].substituteId = selectedOption.value;
    }
    this.setState({ course });
  };

  generateTeacherSalary = () => {
    const { course, allTeachers } = this.state;
    let salaries = [];

    course.teachers.forEach((teacher) => {
      salaries.push(
        <div className="form-group row mt-2 fadeIn" key={teacher.value}>
          <label className="col-sm-12 col-md-2 col-form-label">
            {teacher.label}
          </label>
          <label htmlFor="level" className="col-sm-2 col-md-1 col-form-label">
            Salary
          </label>
          <div className="col-sm-10 col-md-4">
            <input
              type="number"
              className="form-control editedInformaton"
              min={0}
              value={teacher.salary || ""}
              onChange={(event) =>
                this.handleTeacherSalaryUpdate(event, teacher.value)
              }
            />
          </div>
          <label htmlFor="level" className="col-sm-2 col-md-1 col-form-label">
            SubStitute (optional)
          </label>
          <div className="col-sm-10 col-md-4">
            <Select
              value={{ label: teacher.substitute }}
              onChange={(selected) =>
                this.handleSubstituteUpdate(selected, teacher.value)
              }
              isSearchable
              noOptionsMessage={"There are no other teachers!"}
              isClearable={true}
              options={allTeachers.filter((el) => el.value !== teacher.value)}
              maxMenuHeight={"9rem"}
            />
          </div>
        </div>
      );
    });

    return salaries;
  };

  render() {
    const {
      course,
      showCharLimitWarning,
      allCourseDetails,
      currentNewDetail,
      newCourseDetails,
      allTeachers,
      mode,
    } = this.state;

    const levelOptions = [
      { value: "A1", label: "A1" },
      { value: "A2", label: "A2" },
      { value: "B1", label: "B1" },
      { value: "B2", label: "B2" },
      { value: "C1", label: "C1" },
      { value: "C2", label: "C2" },
    ];

    const statusOptions = [
      { value: "UPCOMMING", label: "Upcomming" },
      { value: "STARTED", label: "Started" },
      { value: "FINISHED", label: "Finished" },
      { value: "CANCELED", label: "Canceled" },
    ];

    const charWarning = (
      <div className="alert alert-danger" role="alert">
        The description has a limit of 2048 characters. Currect characters:
        {this.state.course.description
          ? this.state.course.description.length
          : 0}
      </div>
    );

    return (
      <React.Fragment>
        <div className="adminAllCoursesContainer container">
          {mode === "save" ? (
            <h1>Create a course</h1>
          ) : (
            <h1>Edit course #{course.id}</h1>
          )}
          <hr className="mb-5"></hr>
          <form className="fullHeight">
            <div className="form-group row">
              <label htmlFor="title" className="col-sm-2 col-form-label">
                Course Title
              </label>
              <div className="col-sm-10">
                <input
                  type="text"
                  className="form-control editedInformaton"
                  value={course.title || ""}
                  onChange={(event) => this.handleInputChange(event, "title")}
                />
              </div>
            </div>
            <div className="form-group row">
              <label htmlFor="description" className="col-sm-2 col-form-label">
                Description
              </label>
              <div className="col-sm-10">
                <textarea
                  className="form-control editedInformaton"
                  rows="5"
                  value={course.description || ""}
                  onChange={(event) =>
                    this.handleInputChange(event, "description")
                  }
                />
              </div>
            </div>
            <div className="form-group row">
              <label
                htmlFor="pricePerStudent"
                className="col-sm-2 col-md-2 col-form-label"
              >
                Price Per Student
              </label>
              <div className="col-sm-10 col-md-4">
                <input
                  type="number"
                  className="form-control editedInformaton"
                  value={course.pricePerStudent || ""}
                  onChange={(event) =>
                    this.handleInputChange(event, "pricePerStudent")
                  }
                />
              </div>
              <label
                htmlFor="descriptopm"
                className="col-sm-2 col-md-2 col-form-label"
              >
                Maximum number of students
              </label>
              <div className="col-sm-10 col-md-4">
                <input
                  type="number"
                  className="form-control editedInformaton"
                  value={course.maxStudents || ""}
                  onChange={(event) =>
                    this.handleInputChange(event, "maxStudents")
                  }
                />
              </div>
            </div>
            <hr />
            <div className="form-group row">
              <label
                htmlFor="pricePerStudent"
                className="col-sm-2 col-md-2 col-form-label"
              >
                Level
              </label>
              <div className="col-sm-10 col-md-4">
                <Select
                  options={levelOptions}
                  value={{ label: course.level }}
                  onChange={this.handleLevelChange}
                ></Select>
              </div>
              {mode === "edit" ? (
                <React.Fragment>
                  <label
                    htmlFor="pricePerStudent"
                    className="col-sm-2 col-md-2 col-form-label"
                  >
                    Course Status
                  </label>
                  <div className="col-sm-10 col-md-4">
                    <Select
                      options={statusOptions}
                      value={{ label: course.status }}
                      onChange={this.handleStatusChange}
                    ></Select>
                  </div>
                </React.Fragment>
              ) : null}
            </div>
            <div className="form-group row">
              <label
                htmlFor="courseDetails"
                className="col-sm-2 col-form-label mb-3"
              >
                Course details:
              </label>
              <div className="col-sm-10 text-left">
                {course.details
                  ? course.details.map((detail) => {
                      return (
                        <div
                          className="detailsBadgeDiv"
                          key={detail.id ? detail.id : detail}
                          onClick={() => this.removeCourseDetail(detail)}
                        >
                          <CourseDetailsBadge
                            text={detail.description}
                            hide={true}
                          ></CourseDetailsBadge>
                        </div>
                      );
                    })
                  : null}
                {newCourseDetails.length > 0
                  ? newCourseDetails.map((detail) => {
                      return (
                        <div
                          className="detailsBadgeDiv"
                          key={detail}
                          onClick={() => this.removeNewCourseDetail(detail)}
                        >
                          <CourseDetailsBadge
                            text={detail}
                            hide={true}
                          ></CourseDetailsBadge>
                        </div>
                      );
                    })
                  : null}
                {newCourseDetails.length === 0 &&
                (!course.details || course.details.length === 0) ? (
                  <label
                    htmlFor="pleaseAddDetails"
                    className="col-sm-10 col-md-10 col-form-label"
                  >
                    Please add details
                  </label>
                ) : null}
              </div>

              <label
                htmlFor="courseDetails"
                className="col-sm-2 col-md-2 col-form-label mt-2"
              >
                All details:
              </label>
              <div className="col-sm-10 text-left">
                {allCourseDetails && course.details ? (
                  this.filterCourseDetails().map((detail) => {
                    return (
                      <div
                        className="detailsBadgeDiv"
                        key={detail.id}
                        onClick={() => this.addCourseDetail(detail)}
                      >
                        <CourseDetailsBadge
                          text={detail.description}
                          hide={true}
                        ></CourseDetailsBadge>
                      </div>
                    );
                  })
                ) : (
                  <label
                    htmlFor="courseDetails"
                    className="col-sm-2 col-form-label mb-3"
                  >
                    None
                  </label>
                )}
              </div>
            </div>
            <div className="form-group row mt-2">
              <label
                htmlFor="courseDetails"
                className="col-sm-2 col-md-2 col-form-label"
              >
                Add custom details:
              </label>
              <div className="col-sm-10 col-md-8">
                <input
                  type="text"
                  className="form-control editedInformaton"
                  value={currentNewDetail}
                  onChange={(event) => this.handleNewCourseDetail(event)}
                />
              </div>
              <div className="col-sm-12 col-md-2">
                <button
                  type="button"
                  className="btn addDetailsButton"
                  disabled={currentNewDetail.length > 0 ? false : true}
                  onClick={this.addNewCourseDetail}
                >
                  Add
                </button>
              </div>
            </div>
            <hr />
            <div className="form-group row mt-4">
              <label
                htmlFor="duration"
                className="col-sm-2 col-md-1 col-form-label"
              >
                Start Date
              </label>
              <div className="col-sm-10 col-md-3">
                <input
                  type="date"
                  className="form-control"
                  id="date"
                  value={course.startDate}
                  onChange={(event) =>
                    this.handleInputChange(event, "startDate")
                  }
                />
              </div>
              <label
                htmlFor="duration"
                className="col-sm-2 col-md-2 col-form-label"
              >
                Duration in weeks
              </label>
              <div className="col-sm-10 col-md-2">
                <input
                  type="number"
                  className="form-control editedInformaton"
                  value={course.duration || ""}
                  onChange={(event) =>
                    this.handleInputChange(event, "duration")
                  }
                />
              </div>
              <label
                htmlFor="lessonsPerWeek"
                className="col-sm-2 col-md-2 col-form-label"
              >
                Lessons per week
              </label>
              <div className="col-sm-10 col-md-2">
                <input
                  type="number"
                  className="form-control editedInformaton"
                  value={course.lessonsPerWeek}
                  min={1}
                  onChange={(event) =>
                    this.handleInputChange(event, "lessonsPerWeek")
                  }
                />
              </div>
            </div>
            {course.lessonsPerWeek > 0
              ? this.generateScheduleFields().map((schedule) => {
                  return schedule;
                })
              : null}
            <hr />
            <div className="form-group row mt-4">
              <label
                htmlFor="duration"
                className="col-sm-2 col-md-2 col-form-label"
              >
                Teachers
              </label>
              <div className="col-sm-10 col-md-10">
                <Select
                  value={course.teachers}
                  isMulti
                  onChange={this.handleTeacherChange}
                  options={allTeachers}
                  maxMenuHeight={"9rem"}
                />
              </div>
            </div>
            {course.teachers.length > 0
              ? this.generateTeacherSalary().map((salary) => {
                  return salary;
                })
              : null}
          </form>
          {showCharLimitWarning ? charWarning : null}
          <div className="editButtonContainer">
            <button
              disabled={this.checkIfAllIsFilled() ? true : false}
              className="btn saveCourseButton"
              onClick={this.saveCourse}
            >
              <FontAwesomeIcon icon={faSave} className="editIcon" />
              Save
            </button>
          </div>
          <BackButton></BackButton>
        </div>
      </React.Fragment>
    );
  }
}

export default AdminAddCourse;
