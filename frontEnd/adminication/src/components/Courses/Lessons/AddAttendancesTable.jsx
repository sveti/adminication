import React, { Component } from "react";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCheckCircle,
  faTimesCircle,
  faEdit,
  faSave,
} from "@fortawesome/free-solid-svg-icons";

import {
  addAttendances,
  updateAttendances,
} from "../../../services/attendanceService";

class AddAttendesTable extends Component {
  state = {
    courseId: this.props.courseId,
    students: this.props.students,
    lessonId: this.props.lessonId,
    attendances: [],
    successMessage: "",
    errorMessage: "",
    visualAttended: [],
    buttonText: "Save",
    buttonIcon: faSave,
    buttonClasses: "saveButton",
    mode: "save",
    addAttendance: false,
    notSaved: true,
  };

  handleAddAttendance = () => {
    this.setState({ addAttendance: true });
  };

  upsert(array, item) {
    const i = array.findIndex(
      (attendance) => attendance.studentId === item.studentId
    );
    if (i > -1) array[i] = item;
    else array.push(item);
  }

  handleClick = (attended, studentId, index) => {
    const { lessonId } = this.state;

    let attendances = [...this.state.attendances];
    let visualAttended = [...this.state.visualAttended];

    let attendance = {};
    attendance["lessonId"] = lessonId;
    attendance["studentId"] = studentId;
    attendance["attended"] = attended;

    this.upsert(attendances, attendance);
    visualAttended[index] = attended;

    this.setState({ visualAttended: visualAttended, attendances: attendances });
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

  saveAttendance = () => {
    const { notSaved, attendances, students, mode } = this.state;

    if (students.length === attendances.length) {
      this.setState({
        errorMessage: "",
      });

      if (mode === "save") {
        if (notSaved) {
          addAttendances(this.state.attendances).then(
            (response) => {
              if (
                response.status === 200 &&
                response.data !== "An error has occured!"
              ) {
                this.setState({
                  successMessage: response.data,
                  errorMessage: "",
                  notSaved: false,
                });
                this.updateButton();
              } else {
                this.setState({
                  successMessage: "",
                  errorMessage: response.data,
                });
              }
            },
            (error) => {
              this.setState({
                successMessage: "",
                errorMessage: error.response.data.error,
              });
            }
          );
        } else {
          updateAttendances(this.state.attendances).then(
            (response) => {
              this.setState({
                successMessage: response.data,
                errorMessage: "",
                notSaved: false,
              });
              this.updateButton();
            },
            (error) => {
              this.setState({
                successMessage: "",
                errorMessage: error.response.data.error,
              });
            }
          );
        }
      } else {
        this.updateButton();
      }
    } else {
      this.setState({
        errorMessage: "Please fill in the attendances of all of the students!",
      });
    }
  };

  render() {
    const {
      buttonText,
      buttonIcon,
      buttonClasses,
      successMessage,
      errorMessage,
      visualAttended,
      addAttendance,
      mode,
    } = this.state;

    let updateDiv,
      errorDiv = null;

    if (successMessage.length > 0) {
      updateDiv = (
        <div className="alert alert-success mt-3" role="alert">
          {successMessage}
        </div>
      );
    }
    if (errorMessage.length > 0) {
      errorDiv = (
        <div className="alert alert-danger mt-3" role="alert">
          {errorMessage}
        </div>
      );
    }

    return (
      <div>
        <div>
          <h4 className={addAttendance === false ? "" : "d-none"}>
            No attendances for this lecture
          </h4>
          <button
            className={addAttendance === false ? "welcomeButton" : "d-none"}
            onClick={() => this.handleAddAttendance()}
          >
            Add attendances
          </button>
        </div>
        {addAttendance ? (
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
                {this.state.students.map((s, index) => {
                  return (
                    <Tr key={s.id} className="alternate">
                      <Td>{s.username}</Td>
                      <Td>{s.name + " " + s.lastName}</Td>
                      <Td>
                        <button
                          className="btn"
                          onClick={() => this.handleClick(true, s.id, index)}
                          disabled={mode === "save" ? false : true}
                        >
                          <FontAwesomeIcon
                            className={
                              visualAttended[index] === undefined
                                ? "notMarked"
                                : visualAttended[index]
                                ? "present"
                                : "notMarked"
                            }
                            icon={faCheckCircle}
                          />
                        </button>
                        <button
                          className="btn"
                          onClick={() => this.handleClick(false, s.id, index)}
                          disabled={mode === "save" ? false : true}
                        >
                          <FontAwesomeIcon
                            className={
                              visualAttended[index] === undefined
                                ? "notMarked"
                                : visualAttended[index]
                                ? "notMarked"
                                : "absent"
                            }
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
              <button className={buttonClasses} onClick={this.saveAttendance}>
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
        ) : null}
      </div>
    );
  }
}

export default AddAttendesTable;
