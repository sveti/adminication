import React, { Component } from "react";
import { getLessonsByCourseId } from "../../../services/lessonService";
import {
  getStudentsByCourseId,
  getAttendanceByCourseId,
  finishCourse,
} from "../../../services/courseService";
import { dynamicSort } from "../../../common/helper";
import AddLesson from "./AddLesson";
import LessonsList from "./LessonsList";

import { withRouter } from "react-router-dom";
import { toast } from "react-toastify";
import "./lessons.css";

class LessonsOfCoursePage extends Component {
  state = {
    teacherId: this.props.location.lessonProps.teacherId,
    courseId: this.props.location.lessonProps.courseId,
    courseTitle: this.props.location.lessonProps.courseTitle,
    isSubstitute: this.props.location.lessonProps.isSubstitute,
    lessons: null,
    students: null,
    attendances: null,
  };

  formatDate = (d) => {
    return d[8] + d[9] + "." + d[5] + d[6] + "." + d[0] + d[1] + d[2] + d[3];
  };

  async loadAttendaces() {
    const { data } = await getAttendanceByCourseId(this.state.courseId);
    this.setState({ attendances: data });
  }

  async loadStudents() {
    const { data } = await getStudentsByCourseId(this.state.courseId);
    this.setState({ students: data.sort(dynamicSort("username")) });
  }

  async loadLessons() {
    const { data } = await getLessonsByCourseId(this.state.courseId);
    data.forEach((element) => {
      element.date = this.formatDate(element.date);
    });
    this.setState({ lessons: data });
  }

  updateOnAddedLesson = async () => {
    await this.loadLessons();
  };

  componentDidMount = () => {
    this.loadLessons();
    this.loadStudents();
    this.loadAttendaces();
  };

  finishCourse = async () => {
    const response = await finishCourse(this.state.courseId).catch(function (
      error
    ) {
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

    if (response && response.status === 200 && response.data === "ok") {
      toast.success("The course has succesfully finish! Please grade it!", {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
      this.props.history.goBack();
    } else {
      toast.error(response.data, {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
      this.props.history.push("/courses");
    }
  };

  render() {
    const {
      teacherId,
      courseId,
      students,
      attendances,
      lessons,
      courseTitle,
      isSubstitute,
    } = this.state;

    return (
      <div className="lessonsOfCourseContainer">
        <h2 className="mb-5">
          Lessons of Coruse #{courseId} {courseTitle}
          {lessons &&
          lessons.length > 0 &&
          !isSubstitute &&
          isSubstitute !== undefined ? (
            <button
              className="btn beginCourseButton ml-4"
              onClick={this.finishCourse}
            >
              Finish Course
            </button>
          ) : null}
        </h2>
        {lessons && attendances ? (
          <LessonsList
            lessons={lessons}
            students={students}
            attendances={attendances}
          ></LessonsList>
        ) : (
          <h4>There are no lessons of this course! Add some</h4>
        )}
        <AddLesson
          teacherId={teacherId}
          courseId={courseId}
          onAddedLesson={this.updateOnAddedLesson}
        ></AddLesson>
      </div>
    );
  }
}
export default withRouter(LessonsOfCoursePage);
