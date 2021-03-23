import React, { Component } from "react";
import { getLessonsByCourseId } from "../../../services/lessonService";
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
    const { data } = await getLessonsByCourseId(this.state.courseId);
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

  render() {
    const { teacherId, courseId, students, attendances, lessons } = this.state;
    return (
      <div className="lessonsOfCourseContainer">
        {lessons && attendances ? (
          <LessonsList
            lessons={lessons}
            students={students}
            attendances={attendances}
          ></LessonsList>
        ) : (
          <h1>There are no lessons of this course! Add some</h1>
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
export default LessonsOfCoursePage;
