import React, { Component } from "react";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCheckCircle,
  faTimesCircle,
  faEdit,
  faSave,
} from "@fortawesome/free-solid-svg-icons";

import { updateAttendances } from "../../../services/attendanceService";

class AddAttendesTable extends Component {
  state = {
    courseId: this.props.courseId,
    students: this.props.students,
    attendances: [],
    buttonText: "Save",
    buttonIcon: faSave,
    buttonClasses: "saveButton",
    mode: "save",
    updateMessage: "",
    errorMessage: "",
  };

  handleClick = (attended, studentId) => {
    console.log(attended);
    console.log(studentId);
  };

  getAttendance = (attendance) => {
    const { mode } = this.state;

    if (attendance.attended) {
      return (
        <FontAwesomeIcon
          className="present"
          icon={faCheckCircle}
          onClick={
            mode === "save" ? () => this.handleMarked(attendance) : undefined
          }
        />
      );
    } else {
      return (
        <FontAwesomeIcon
          className="absent"
          icon={faTimesCircle}
          onClick={
            mode === "save" ? () => this.handleMarked(attendance) : undefined
          }
        />
      );
    }
  };

  getIconForAttendance = () => {};

  render() {
    const {
      attendances,
      buttonText,
      buttonIcon,
      buttonClasses,
      updateMessage,
      errorMessage,
      mode,
    } = this.state;

    let updateDiv,
      errorDiv = null;

    if (updateMessage) {
      updateDiv = (
        <div className="alert alert-success mt-3" role="alert">
          {updateMessage}
        </div>
      );
    }
    if (errorMessage) {
      errorDiv = (
        <div className="alert alert-danger mt-3" role="alert">
          {errorMessage}
        </div>
      );
    }

    return (
      <div>
        <div>
          <h1>Attendances</h1>
          <Table>
            <Thead>
              <Tr>
                <Th className="noBorder">Username</Th>
                <Th className="noBorder">Name</Th>
                <Th className="noBorder">Attended</Th>
              </Tr>
            </Thead>

            <Tbody>
              {this.state.students.map((s) => {
                return (
                  <Tr key={s.id} className="alternate">
                    <Td>{s.username}</Td>
                    <Td>{s.name + " " + s.lastName}</Td>
                    <Td>
                      <button
                        className="btn"
                        onClick={() => this.handleClick(true, s.id)}
                      >
                        <FontAwesomeIcon
                          className="notMarked"
                          icon={faCheckCircle}
                        />
                      </button>
                      <button
                        className="btn"
                        onClick={() => this.handleClick(false, s.id)}
                      >
                        <FontAwesomeIcon
                          className="notMarked"
                          icon={faTimesCircle}
                        />
                      </button>
                    </Td>
                  </Tr>
                );
              })}
            </Tbody>
          </Table>
          <div className="editButtonContainer">
            <button className={buttonClasses} onClick={this.editAttendance}>
              <FontAwesomeIcon
                id="editButton"
                icon={buttonIcon}
                className="editIcon"
              />
              {buttonText}
            </button>
          </div>
          {updateDiv}
          {errorDiv}
        </div>
      </div>
    );
  }
}

export default AddAttendesTable;
