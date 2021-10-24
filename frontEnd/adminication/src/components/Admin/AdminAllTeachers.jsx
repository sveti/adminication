import React, { Component } from "react";
import { getAllTeachersAdmin } from "../../services/teacherAdministrationService";
import AdminTeachersTable from "./AdminTeachersTable";
import "./admin.css";

class AdminAllTeachers extends Component {
  state = {
    teachers: [],
  };
  toTitleCase = (str) => {
    return str.replace(/\w\S*/g, function (txt) {
      return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
    });
  };

  componentDidMount = async () => {
    const { data } = await getAllTeachersAdmin();
    data.forEach((element) => {
      element.gender = this.toTitleCase(element.gender);
    });
    this.setState({ teachers: data });
  };

  render() {
    const { teachers } = this.state;
    return (
      <div className="adminAllCoursesContainer">
        {teachers.length > 0 ? (
          <AdminTeachersTable data={teachers}></AdminTeachersTable>
        ) : (
          <h3>There are currently no teachers in this system, add some!</h3>
        )}
      </div>
    );
  }
}

export default AdminAllTeachers;
