import React, { Component } from "react";

import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";

import { toast } from "react-toastify";

import { dynamicSort, updateObject } from "../../../common/helper";

import {
  getStudentsWithGradesByCourseId,
  updateGrades,
} from "../../../services/courseService";

import "./grading.css";
import EditSaveButton from "../../../common/EditSaveButton";

class GradingTable extends Component {
  state = {
    courseId: this.props.location.gradingProps.courseId,
    teacherId: this.props.location.gradingProps.teacherId,
    studentsGrades: null,
    editMode: true,
    errorsInGrades: new Set(),
  };

  async loadStudents() {
    const { data } = await getStudentsWithGradesByCourseId(this.state.courseId);
    this.setState({ studentsGrades: data.sort(dynamicSort("name")) });
  }

  componentDidMount = () => {
    this.loadStudents();
  };

  validation = () => {
    if (this.errorsInGrades) {
      return this.errorsInGrades.size === 0 ? true : false;
    } else {
      return true;
    }
  };

  validateGrade = (grade) => {
    return grade >= 2 && grade <= 6;
  };

  updateGrade = (event, student) => {
    const grade = event.target.value;
    const isValid = this.validateGrade(grade);
    const errorsInGradesS = new Set(this.state.errorsInGrades);

    if (!isValid) {
      errorsInGradesS.add(student.studentId);
      this.setState({ errorsInGrades: errorsInGradesS });
    } else {
      let studentsGrades = [...this.state.studentsGrades];
      let studentInArray = studentsGrades.find((s) => {
        return s.studentId === student.studentId;
      });
      updateObject(studentsGrades, studentInArray, "studentId", "grade", grade);
      errorsInGradesS.delete(student.studentId);
      this.setState({
        studentsGrades: studentsGrades,
        errorsInGrades: errorsInGradesS,
      });
    }
  };

  saveGrades = () => {
    updateGrades(this.state.studentsGrades).then(
      (response) => {
        if (
          response &&
          response.status === 200 &&
          response.data !== "An error has occured!"
        ) {
          toast.success(response.data, {
            position: "top-center",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
          this.setState({ editMode: true });
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
  };

  editGrades = () => {
    this.setState({ editMode: false });
  };

  render() {
    const { studentsGrades, editMode, errorsInGrades } = this.state;

    const notGradedBadge = (
      <div className="alert alert-danger notGraded" role="alert">
        Not graded
      </div>
    );

    return studentsGrades ? (
      <div className="gradingTable">
        <div>
          <h1 className="title">Grades</h1>
          <div className="gradesTableContainer">
            <Table>
              <Thead>
                <Tr>
                  <Th className="noBorder">Username</Th>
                  <Th className="noBorder">Name</Th>
                  <Th className="noBorder">Final grade</Th>
                </Tr>
              </Thead>

              <Tbody>
                {this.state.studentsGrades.map((s) => {
                  return (
                    <Tr key={s.studentId} className="alternate">
                      <Td>#{s.studentId}</Td>
                      <Td>{s.username}</Td>
                      <Td>{s.name}</Td>
                      <Td>
                        {editMode ? (
                          s.grade === 0 ? (
                            notGradedBadge
                          ) : (
                            s.grade
                          )
                        ) : (
                          <input
                            type="number"
                            className="form-control gradeInput"
                            min="2.00"
                            max="6.00"
                            step="0.5"
                            onChange={(event) => this.updateGrade(event, s)}
                            defaultValue={s.grade > 0 ? s.grade : 2}
                          ></input>
                        )}
                      </Td>
                    </Tr>
                  );
                })}
              </Tbody>
            </Table>
            <EditSaveButton
              onEdit={this.editGrades}
              onSave={this.saveGrades}
              disabled={errorsInGrades.size !== 0}
              validation={this.validation}
            ></EditSaveButton>
          </div>
        </div>
      </div>
    ) : (
      <h1>No studentsGrades</h1>
    );
  }
}

export default GradingTable;
