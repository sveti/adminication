import React, { Component } from "react";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCheckCircle,
  faTimesCircle,
} from "@fortawesome/free-solid-svg-icons";

import { addAttendances } from "../../../services/attendanceService";
import { getAttendancesByLessonId } from "../../../services/lessonService";

import { upsert } from "../../../common/helper";

import { toast } from "react-toastify";
import EditSaveButton from "../../../common/EditSaveButton";
import AttendanceTable from "./AttendanceTable";

class AddAttendesTable extends Component {
  state = {
    courseId: this.props.courseId,
    students: this.props.students,
    lessonId: this.props.lessonId,
    attendances: [],
    visualAttended: [],
    addAttendance: false,
    submittedAttendance: false,
    savedAttendances: [],
  };

  validation = () => {
    if (this.state.students.length === this.state.attendances.length) {
      return true;
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
    return false;
  };

  handleAddAttendance = () => {
    this.setState({ addAttendance: true });
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

  editAttendances = () => {};

  getSavedAttendances = async () => {
    const { data } = await getAttendancesByLessonId(this.state.lessonId);
    this.setState({ savedAttendances: data });
  };

  saveAttendance = async () => {
    const { attendances } = this.state;

    const { data } = await addAttendances(attendances);
    if (data !== "An error has occured!") {
      await this.getSavedAttendances();

      this.setState({
        submittedAttendance: true,
        addAttendance: false,
      });

      toast.success(data, {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
    } else {
      toast.error("An error has occured", {
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
    const { visualAttended, addAttendance, submittedAttendance } = this.state;

    return (
      <div>
        {!submittedAttendance ? (
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
        ) : null}

        {addAttendance ? (
          <div>
            <h1>Attendances</h1>
            <Table>
              <Thead>
                <Tr>
                  <Th className="noBorder">Name</Th>
                  <Th className="noBorder">Attended</Th>
                </Tr>
              </Thead>

              <Tbody>
                {this.state.students.map((s, index) => {
                  return (
                    <Tr key={s.id} className="alternate">
                      <Td>{s.name + " " + s.lastName}</Td>
                      <Td>
                        <button
                          className="btn"
                          onClick={() => this.handleClick(true, s.id, index)}
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
                validation={this.validation}
              ></EditSaveButton>
            </div>
          </div>
        ) : null}
        {submittedAttendance ? (
          <AttendanceTable
            courseId={this.state.courseId}
            students={this.state.students}
            lessonId={this.state.lessonId}
            attendances={this.state.savedAttendances}
          ></AttendanceTable>
        ) : null}
      </div>
    );
  }
}

export default AddAttendesTable;
