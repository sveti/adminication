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
import { toast } from "react-toastify";

class AttendanceTable extends Component {
  state = {
    courseId: this.props.courseId,
    students: this.props.students,
    attendances: this.props.attendances,
    buttonText: "Edit",
    buttonIcon: faEdit,
    buttonClasses: "editButton",
    mode: "display",
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
        <button
          className="btn"
          onClick={
            mode === "save" ? () => this.handleMarked(attendance) : undefined
          }
          disabled={mode === "save" ? false : true}
        >
          <FontAwesomeIcon className="present" icon={faCheckCircle} />
        </button>
      );
    } else {
      return (
        <button
          className="btn"
          onClick={
            mode === "save" ? () => this.handleMarked(attendance) : undefined
          }
          disabled={mode === "save" ? false : true}
        >
          <FontAwesomeIcon className="absent" icon={faTimesCircle} />
        </button>
      );
    }
  };

  updateButton = () => {
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
    }
  };

  editAttendance = () => {
    if (this.state.mode === "save") {
      updateAttendances(this.state.attendances).then(
        (response) => {
          toast.success(response.data, {
            position: "top-center",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
          this.updateButton();
        },
        (error) => {
          toast.error(error.response.data.error, {
            position: "top-center",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
        }
      );
    } else {
      this.updateButton();
    }
  };

  render() {
    const { attendances, buttonText, buttonIcon, buttonClasses } = this.state;

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
        </div>
      </div>
    );
  }
}

export default AttendanceTable;
