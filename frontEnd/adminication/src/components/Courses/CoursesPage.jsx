import React, { Component } from "react";
import UpcommingCoursesTable from "./UpcommingCoursesTable";
import StartedCoursesTable from "./StartedCoursesTable";
import "./coursesPage.css";
import {
  getUpcommingCoursesOfTeacher,
  getStartedCoursesOfTeacher,
  getSubStartedCoursesOfTeacher,
} from "../../services/courseService";

import { dynamicSort } from "../../common/helper";

class CoursesPage extends Component {
  state = {
    id: this.props.id,
    upCommingCourses: [],
    startedCourses: [],
    subStartedCourses: [],
  };

  async loadSubStartedCourses() {
    const { data } = await getSubStartedCoursesOfTeacher(this.state.id);
    this.setState({ subStartedCourses: data.sort(dynamicSort("id")) });
  }

  async loadStartedCourses() {
    const { data } = await getStartedCoursesOfTeacher(this.state.id);
    this.setState({ startedCourses: data.sort(dynamicSort("id")) });
  }

  async loadupCommingCourses() {
    const { data } = await getUpcommingCoursesOfTeacher(this.state.id);
    this.setState({ upCommingCourses: data.sort(dynamicSort("id")) });
  }

  componentDidMount = () => {
    this.loadupCommingCourses();
    this.loadStartedCourses();
    this.loadSubStartedCourses();
  };

  render() {
    const { id, startedCourses, upCommingCourses, subStartedCourses } =
      this.state;

    let started,
      upcomming,
      substitute = null;

    if (startedCourses.length > 0) {
      started = (
        <div className="coursesTableContainer lessMargin">
          <StartedCoursesTable
            message={"Started courses"}
            courses={startedCourses}
            teacherId={id}
          ></StartedCoursesTable>
        </div>
      );
    }

    if (upCommingCourses.length > 0) {
      upcomming = (
        <div className="coursesTableContainer lessMargin">
          <UpcommingCoursesTable
            message={"Upcomming courses"}
            courses={upCommingCourses}
          ></UpcommingCoursesTable>
        </div>
      );
    }

    if (subStartedCourses.length > 0) {
      substitute = (
        <div className="coursesTableContainer lessMargin">
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
