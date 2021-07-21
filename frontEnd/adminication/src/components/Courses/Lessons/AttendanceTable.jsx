import React, { Component } from "react";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCheckCircle,
  faTimesCircle,
} from "@fortawesome/free-solid-svg-icons";

import { updateAttendances } from "../../../services/attendanceService";
import { toast } from "react-toastify";
import EditSaveButton from "../../../common/EditSaveButton";

class AttendanceTable extends Component {
  state = {
    courseId: this.props.courseId,
    students: this.props.students,
    attendances: this.props.attendances,
    mode: "edit",
  };

  validation = () => {
    return true;
  };

  handleMarked = (attendance) => {
    let attendances = [...this.state.attendances];

    let index = attendances.findIndex((member) => member.id === attendance.id);
    attendances[index].attended = !attendance.attended;

    this.setState({ attendances });
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

  editAttendances = () => {
    this.setState({ mode: "save" });
  };

  saveAttendance = () => {
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
        this.setState({ mode: "edit" });
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
  };

  render() {
    const { attendances } = this.state;

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
                    <Td className="p-5">{s.username}</Td>
                    <Td className="p-5">{s.name + " " + s.lastName}</Td>
                    <Td className="p-5">
                      {this.getAttendance(
                        attendances.find((x) => x.studentId === s.id)
                      )}
                    </Td>
                  </Tr>
                );
              })}
            </Tbody>
          </Table>
          <EditSaveButton
            onEdit={this.editAttendances}
            onSave={this.saveAttendance}
            validation={this.validation}
          ></EditSaveButton>
        </div>
      </div>
    );
  }
}

export default AttendanceTable;
