import React, { Component } from "react";
import { getLessonsByTeacherIdAndCourseId } from "../../../services/lessonService";
import { getStudentsByCourseId } from "../../../services/courseService";
import AddLesson from "./AddLesson";
import LessonsList from "./LessonsList";
import "./lessons.css";

class LessonsOfCoursePage extends Component {
  state = {
    teacherId: this.props.location.lessonProps.teacherId,
    courseId: this.props.location.lessonProps.courseId,
    lessons: [],
    students: [],
  };

  dynamicSort(property) {
    var sortOrder = 1;
    if (property[0] === "-") {
      sortOrder = -1;
      property = property.substr(1);
    }
    return function (a, b) {
      /* next line works with strings and numbers,
       * and you may want to customize it to your needs
       */
      var result =
        a[property] < b[property] ? -1 : a[property] > b[property] ? 1 : 0;
      return result * sortOrder;
    };
  }

  async loadStudents() {
    const { data } = await getStudentsByCourseId(this.state.courseId);
    this.setState({ students: data.sort(this.dynamicSort("username")) });
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
  };

  render() {
    const { teacherId, courseId, lessons, students } = this.state;
    let lessonsList = null;

    if (lessons) {
      lessonsList = (
        <LessonsList lessons={lessons} students={students}></LessonsList>
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
