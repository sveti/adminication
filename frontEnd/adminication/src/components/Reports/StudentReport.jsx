import React, { Component } from "react";
import { getStudentAttendaceForMonth } from "../../services/reportsService";
import MonthYearSelector from "../../common/MonthYearSelector";
import GeneralStudentReport from "./GeneralStudentReport";

class StudentReport extends Component {
  constructor(props) {
    super(props);
    this.state = {
      studentId: null,
      stats: [],
      showStats: false,
      name: null,
    };
    if (props.location && props.location.statisticsProps) {
      this.state.studentId = props.location.statisticsProps.studentId;
    } else {
      this.state.studentId = props.studentId;
    }
    if (
      props.location &&
      props.location.statisticsProps &&
      props.location.statisticsProps.studentName
    ) {
      this.state.name = props.location.statisticsProps.studentName;
    }
  }

  loadDataForOneStudent = async (id, selectedPeriod) => {
    const { data } = await getStudentAttendaceForMonth(
      id,
      selectedPeriod.month,
      selectedPeriod.year
    );
    return data;
  };
  loadStats = async (selectedPeriod) => {
    const { studentId } = this.state;
    var collectedData = [];
    const data = await this.loadDataForOneStudent(studentId, selectedPeriod);
    collectedData.push({ lessons: data });
    this.setState({ stats: collectedData });
  };

  getTotal = () => {
    const { stats } = this.state;
    let totalAttendances = 0;
    let totalSum = 0;

    stats.forEach((stat) => {
      stat.lessons.forEach((element) => {
        totalAttendances += element.attendances.length;
        totalSum += element.attendances.length * element.pricePerAttendance;
      });
    });

    return { totalAttendances: totalAttendances, totalSum: totalSum };
  };
  handleSubmit = async (selectedPeriod) => {
    await this.loadStats(selectedPeriod);
    this.setState({ showStats: true });
    this.getTotal();
  };

  onPeriodChange = () => {
    if (this.state.showStats) {
      this.setState({ showStats: false });
    }
  };

  render() {
    const { name, showStats, stats } = this.state;

    return (
      <div className="salaryDiv container">
        {name ? <h3 className="mb-5">Report for {name}</h3> : null}
        <MonthYearSelector
          handleSubmit={(selectedPeriod) => this.handleSubmit(selectedPeriod)}
          onPeriodChange={() => this.onPeriodChange()}
        ></MonthYearSelector>
        <div className="statsResult">
          {showStats ? (
            <div>
              {
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

                  <div className="statsWrapper">
                    <GeneralStudentReport
                      data={stats[0]}
                    ></GeneralStudentReport>
                  </div>
                </div>
              }
            </div>
          ) : null}
        </div>
      </div>
    );
  }
}

export default StudentReport;
