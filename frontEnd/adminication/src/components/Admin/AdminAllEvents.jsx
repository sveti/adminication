import React, { Component } from "react";
import { getAllEventsAdmin } from "../../services/eventsService";
import AdminEventsTable from "./AdminEventsTable";
import "./admin.css";

class AdminAllCourses extends Component {
  state = {
    events: [],
  };

  componentDidMount = async () => {
    const { data } = await getAllEventsAdmin();
    this.setState({ events: data });
  };

  render() {
    return (
      <div className="adminAllCoursesContainer">
        <AdminEventsTable data={this.state.events}></AdminEventsTable>
      </div>
    );
  }
}

export default AdminAllCourses;
