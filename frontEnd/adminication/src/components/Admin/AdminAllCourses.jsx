import React, { Component } from "react";
import { getAllCoursesAdmin } from "../../services/courseService";
import AdminCoursesTable from "./AdminCoursesTable";
import "./admin.css";

class AdminAllCourses extends Component {
  state = {
    courses: [],
  };

  componentDidMount = async () => {
    const { data } = await getAllCoursesAdmin();
    this.setState({ courses: data });
  };

  render() {
    return (
      <div className="adminAllCoursesContainer">
        <AdminCoursesTable data={this.state.courses}></AdminCoursesTable>
      </div>
    );
  }
}

export default AdminAllCourses;
