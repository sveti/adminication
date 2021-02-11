import React, { Component } from "react";
import { getLessonsByTeacherIdAndCourseId } from "../../../services/lessonService";
import {
  getStudentsByCourseId,
  getAttendanceByCourseId,
} from "../../../services/courseService";
import AddLesson from "./AddLesson";
import LessonsList from "./LessonsList";
import "./lessons.css";

import { dynamicSort } from "../../../common/helper";

class LessonsOfCoursePage extends Component {
  state = {
    teacherId: this.props.location.lessonProps.teacherId,
    courseId: this.props.location.lessonProps.courseId,
    lessons: null,
    students: null,
    attendances: null,
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
    const { data } = await getLessonsByTeacherIdAndCourseId(
      this.state.teacherId,
      this.state.courseId
    );
    this.setState({ lessons: data });
  }

  updateOnAddedLesson = () => {
    this.loadLessons();
  };

  componentDidMount = () => {
    this.loadLessons();
    this.loadStudents();
    this.loadAttendaces();
  };

  render() {
    const { teacherId, courseId, students, attendances } = this.state;

    let lessonsList = null;

    if (this.state.lessons && attendances) {
      lessonsList = (
        <LessonsList
          lessons={this.state.lessons}
          students={students}
          attendances={attendances}
        ></LessonsList>
      );
    }

    return (
      <div className="lessonsOfCourseContainer">
        {lessonsList}
        <AddLesson
          teacherId={teacherId}
          courseId={courseId}
          onAddedLesson={this.updateOnAddedLesson}
        ></AddLesson>
      </div>
    );
  }
}
export default LessonsOfCoursePage;
