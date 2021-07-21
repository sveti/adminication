import React, { Component } from "react";
import BackButton from "../../common/BackButton";

class AdminEditCourse extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }
  render() {
    return (
      <div className="adminAllCoursesContainer">
        <h1>{this.props.location.state.courseId}</h1>
        <BackButton></BackButton>
      </div>
    );
  }
}

export default AdminEditCourse;
