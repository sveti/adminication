import React, { Component } from "react";

import MonthYearSelector from "../../common/MonthYearSelector";
import MultipleStudentSelection from "./MultipleStudentsSelection";
import GeneralStudentReport from "./GeneralStudentReport";
import { getStudentAttendaceForMonth } from "../../services/reportsService";
import { getStudentsOfParent } from "../../services/parentService";

import "./reports.css";

class ParentInvoice extends Component {
  constructor(props) {
    super(props);
    this.state = {
      parentId: null,
      students: [],
      selectedIds: [],
      stats: [],
      showStats: false,
      name: null,
    };

    if (props.location && props.location.statisticsProps) {
      this.state.parentId = props.location.statisticsProps.parentId;
    } else if (props.parentId) {
      this.state.parentId = props.parentId;
    } else {
      this.state.parentId = props.user.id;
    }
    if (
      props.location &&
      props.location.statisticsProps &&
      props.location.statisticsProps.parentName
    ) {
      this.state.name = props.location.statisticsProps.parentName;
    }
  }

  onPeriodChange = () => {
    if (this.state.showStats) {
      this.setState({ showStats: false });
    }
  };

  studentSelection = (selectedIds) => {
    this.setState({ selectedIds: selectedIds });
  };

  getStudents = async () => {
    const { data } = await getStudentsOfParent(this.state.parentId);
    this.setState({ students: data });
  };

  loadDataForOneStudent = async (id, selectedPeriod) => {
    const { data } = await getStudentAttendaceForMonth(
      id,
      selectedPeriod.month,
      selectedPeriod.year
    );
    return data;
  };

  loadStats = async (selectedPeriod) => {
    const { selectedIds } = this.state;

    var collectedData = [];

    for (const el of selectedIds) {
      const loadedData = await this.loadDataForOneStudent(el, selectedPeriod);
      collectedData.push({ lessons: loadedData, studentId: el });
    }
    this.setState({ stats: collectedData });
  };

  componentDidMount = async () => {
    await this.getStudents();
    let allId = [];
    this.state.students.forEach((s) => allId.push(s.id));
    this.setState({ selectedIds: allId });
  };
  handleSubmit = async (selectedPeriod) => {
    await this.loadStats(selectedPeriod);
    this.setState({ showStats: true });
    this.getTotal();
  };

  getTotal = () => {
    const { stats, selectedIds } = this.state;
    let totalAttendances = 0;
    let totalSum = 0;

    stats.forEach((stat) => {
      if (selectedIds.indexOf(stat.studentId) !== -1) {
        stat.lessons.forEach((element) => {
          totalAttendances += element.attendances.length;
          totalSum += element.attendances.length * element.pricePerAttendance;
        });
      }
    });

    return { totalAttendances: totalAttendances, totalSum: totalSum };
  };

  render() {
    const { showStats, selectedIds, stats, students, name } = this.state;

    return (
      <div className="salaryDiv container">
        {name ? <h3>Report for {name}</h3> : null}
        <MultipleStudentSelection
          students={this.state.students}
          selectedIds={this.state.selectedIds}
          parentId={this.state.parentId}
          studentSelection={(selectedIds) => this.studentSelection(selectedIds)}
        ></MultipleStudentSelection>
        <MonthYearSelector
          handleSubmit={(selectedPeriod) => this.handleSubmit(selectedPeriod)}
          onPeriodChange={() => this.onPeriodChange()}
        ></MonthYearSelector>
        <div className="statsResult">
          {/* have you pressed the button */}
          {showStats ? (
            <div>
              {
                ///have you selected a student
                selectedIds.length > 0 ? (
                  <div>
                    <div className="row totalStats">
                      <div className="col-sm-12 col-md-4">
                        <h5>Total Attendances for period</h5>
                      </div>
                      <div className="col-sm-12 col-md-2">
                        {this.getTotal().totalAttendances}
                      </div>
                      <div className="col-sm-12 col-md-4">
                        <h5>Due amount</h5>
                      </div>
                      <div className="col-sm-12 col-md-2">
                        {this.getTotal().totalSum}$
                      </div>
                    </div>
                    {selectedIds.map((selectedId) => {
                      return (
                        <div key={selectedId} className="statsWrapper">
                          <GeneralStudentReport
                            student={students.find((s) => s.id === selectedId)}
                            data={stats.find(
                              (stat) => stat.studentId === selectedId
                            )}
                          ></GeneralStudentReport>
                        </div>
                      );
                    })}
                  </div>
                ) : (
                  <h3 className="statsWrapper">
                    You haven't selected a student
                  </h3>
                )
              }
            </div>
          ) : null}
        </div>
      </div>
    );
  }
}

export default ParentInvoice;
