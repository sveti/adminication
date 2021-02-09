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

class AttendanceTable extends Component {
  state = {
    courseId: this.props.courseId,
    students: this.props.students,
    attendances: this.props.attendances,
    buttonText: "Edit",
    buttonIcon: faEdit,
    buttonClasses: "editButton",
    mode: "display",
    updateMessage: "",
    errorMessage: "",
  };

  handleMarked = (attendance) => {
    attendance.attended = !attendance.attended;
    let attendances = [...this.state.attendances];
    attendances = attendances.filter((at) => at.id !== attendance.id);
    attendances.push(attendance);

    this.setState({ attendances: attendances });
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

  editAttendance = () => {
    const { buttonText } = this.state;
    if (buttonText === "Edit") {
      this.setState({
        buttonText: "Save",
        buttonIcon: faSave,
        buttonClasses: "saveButton",
        mode: "save",
      });
    } else {
      this.setState({
        buttonText: "Edit",
        buttonIcon: faEdit,
        buttonClasses: "editButton",
        mode: "display",
      });

      updateAttendances(this.state.attendances).then(
        (response) => {
          console.log(response);
          this.setState({ updateMessage: response.data, errorMessage: "" });
        },
        (error) => {
          this.setState({
            updateMessage: "",
            errorMessage: error.response.data.error,
          });
        }
      );
    }
  };

  render() {
    const {
      attendances,
      buttonText,
      buttonIcon,
      buttonClasses,
      updateMessage,
      errorMessage,
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
                      {this.getAttendance(
                        attendances.find((x) => x.studentId === s.id)
                      )}
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

export default AttendanceTable;
