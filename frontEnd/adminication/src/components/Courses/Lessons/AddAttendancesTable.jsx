import React, { Component } from "react";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCheckCircle,
  faTimesCircle,
} from "@fortawesome/free-solid-svg-icons";

import {
  addAttendances,
  updateAttendances,
} from "../../../services/attendanceService";

import { upsert } from "../../../common/helper";

import { toast } from "react-toastify";
import EditSaveButton from "../../../common/EditSaveButton";

class AddAttendesTable extends Component {
  state = {
    courseId: this.props.courseId,
    students: this.props.students,
    lessonId: this.props.lessonId,
    attendances: [],
    visualAttended: [],
    mode: "save",
    addAttendance: false,
    notSaved: true,
  };

  handleAddAttendance = () => {
    this.setState({ addAttendance: true, mode: "save" });
  };

  handleClick = (attended, studentId, index) => {
    const { lessonId } = this.state;

    let attendances = [...this.state.attendances];
    let visualAttended = [...this.state.visualAttended];

    let attendance = {};
    attendance["lessonId"] = lessonId;
    attendance["studentId"] = studentId;
    attendance["attended"] = attended;

    upsert(attendances, attendance, "studentId");
    visualAttended[index] = attended;

    this.setState({ visualAttended: visualAttended, attendances: attendances });
  };

  editAttendances = () => {
    this.setState({ mode: "save" });
  };

  saveAttendance = () => {
    const { notSaved, attendances, students, mode } = this.state;

    if (students.length === attendances.length) {
      if (mode === "save") {
        if (notSaved) {
          addAttendances(this.state.attendances).then(
            (response) => {
              if (
                response.status === 200 &&
                response.data !== "An error has occured!"
              ) {
                this.setState({
                  notSaved: false,
                  mode: "edit",
                });
                toast.success(response.data, {
                  position: "top-center",
                  autoClose: 5000,
                  hideProgressBar: false,
                  closeOnClick: true,
                  pauseOnHover: true,
                  draggable: true,
                  progress: undefined,
                });
              } else {
                this.setState({
                  successMessage: "",
                  errorMessage: response.data,
                });
              }
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
          updateAttendances(this.state.attendances).then(
            (response) => {
              this.setState({
                notSaved: false,
                mode: "edit",
              });
              toast.success(response.data, {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
              });
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
        }
      } else {
      }
    } else {
      toast.error("Please fill in the attendances of all of the students!", {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
    }
  };

  render() {
    const { visualAttended, addAttendance, mode } = this.state;

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
              <EditSaveButton
                onEdit={this.editAttendances}
                onSave={this.saveAttendance}
                initialState={"save"}
              ></EditSaveButton>
            </div>
          </div>
        ) : null}
      </div>
    );
  }
}

export default AddAttendesTable;
