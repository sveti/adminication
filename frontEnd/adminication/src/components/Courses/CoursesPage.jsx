import React, { Component } from "react";
import UpcommingCoursesTable from "./UpcommingCoursesTable";
import StartedCoursesTable from "./StartedCoursesTable";
import "./coursesPage.css";
import {
  getUpcommingCoursesOfTeacher,
  getStartedCoursesOfTeacher,
  getSubStartedCoursesOfTeacher,
} from "../../services/courseService";

class CoursesPage extends Component {
  state = {
    id: this.props.id,
    upCommingCourses: [],
    startedCourses: [],
    subStartedCourses: [],
  };

  async loadSubStartedCourses() {
    const { data } = await getSubStartedCoursesOfTeacher(this.state.id);
    this.setState({ subStartedCourses: data });
  }

  async loadStartedCourses() {
    const { data } = await getStartedCoursesOfTeacher(this.state.id);
    this.setState({ startedCourses: data });
  }

  async loadupCommingCourses() {
    const { data } = await getUpcommingCoursesOfTeacher(this.state.id);
    this.setState({ upCommingCourses: data });
  }

  componentDidMount = () => {
    this.loadupCommingCourses();
    this.loadStartedCourses();
    this.loadSubStartedCourses();
  };

  render() {
    const { startedCourses, upCommingCourses, subStartedCourses } = this.state;

    let started,
      upcomming,
      substitute = null;

    if (startedCourses.length > 0) {
      started = (
        <div className="coursesTableContainer">
          <StartedCoursesTable
            message={"Started courses"}
            courses={startedCourses}
          ></StartedCoursesTable>
        </div>
      );
    }

    if (upCommingCourses.length > 0) {
      upcomming = (
        <div className="coursesTableContainer">
          <UpcommingCoursesTable
            message={"Upcomming courses"}
            courses={upCommingCourses}
          ></UpcommingCoursesTable>
        </div>
      );
    }

    if (subStartedCourses.length > 0) {
      substitute = (
        <div className="coursesTableContainer">
          <StartedCoursesTable
            message={"Substituting courses"}
            courses={subStartedCourses}
          ></StartedCoursesTable>
        </div>
      );
    }

    return (
      <React.Fragment>
        {started}
        {substitute}
        {upcomming}
      </React.Fragment>
    );
  }
}

export default CoursesPage;
