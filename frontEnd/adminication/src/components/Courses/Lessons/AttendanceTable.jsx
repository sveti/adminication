import React, { Component } from "react";

class AttendanceTable extends Component {
  state = {
    courseId: this.props.courseId,
    students: this.props.students,
  };

  render() {
    const { students } = this.state;

    let studentsList = null;

    if (students) {
      studentsList = (
        <div>
          {this.state.students.map((s) => {
            return <p key={s.id}>{s.id}</p>;
          })}
        </div>
      );
    }

    return (
      <div>
        <h1>Attendance</h1>
        {studentsList}
      </div>
    );
  }
}

export default AttendanceTable;
