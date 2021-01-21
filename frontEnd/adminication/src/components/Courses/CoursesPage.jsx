import React, { Component } from "react";
import CourseTable from "./CoursesTable";

class CoursesPage extends Component {
  render() {
    return (
      <React.Fragment>
        <CourseTable message={"UpComming courses"}></CourseTable>
        <CourseTable message={"UpComming courses"}></CourseTable>
        <CourseTable message={"UpComming courses"}></CourseTable>
        <CourseTable message={"UpComming courses"}></CourseTable>
        <CourseTable message={"UpComming courses"}></CourseTable>
      </React.Fragment>
    );
  }
}

export default CoursesPage;
