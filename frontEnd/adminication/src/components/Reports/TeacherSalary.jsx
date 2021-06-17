import React, { Component } from "react";

import { getTeacherStatisticForMonth } from "../../services/reportsService";
import { getTodaysDate } from "../../common/helper";

import { toast } from "react-toastify";

import "./reports.css";
import GeneralCourseSalary from "./GeneralCourseSalary";

class TeacherSalary extends Component {
  constructor(props) {
    super(props);
    this.state = {
      teacherId: null,
      stats: null,
      months: [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December",
      ],
      selectedPeriod: {
        month: "",
        year: getTodaysDate().split("-")[0],
      },
      showStats: false,
      totalSalaryForPeriod: 0,
      totalLessonsForPeriod: 0,
    };
    if (props.location && props.location.statisticsProps) {
      this.state.teacherId = props.location.statisticsProps.teacherId;
    } else {
      this.state.teacherId = props.teacherId;
    }
  }

  periodChange = (event, property) => {
    let selectedPeriod = this.state.selectedPeriod;
    selectedPeriod[property] = event.target.value;
    this.setState({ selectedPeriod });
  };

  validate = () => {
    const { selectedPeriod } = this.state;
    if (selectedPeriod.month === "") {
      toast.error("Please select a month!", {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
      return false;
    }

    if (selectedPeriod.year < 1000 || selectedPeriod.year > 9999) {
      toast.error("Please enter a valid year!", {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
      return false;
    }
    return true;
  };

  loadStats = async () => {
    const { selectedPeriod } = this.state;

    //Jan is 0 as index but 1 as month
    const montAsANumber = this.state.months.indexOf(selectedPeriod.month) + 1;

    const { data } = await getTeacherStatisticForMonth(
      this.state.teacherId,
      montAsANumber,
      selectedPeriod.year
    );

    this.setState({
      stats: data,
      showStats: true,
      totalLessonsForPeriod: data.lessons.length,
    });
  };

  showStatistics = async () => {
    if (this.validate()) {
      await this.loadStats();
      this.getTotalSalary();
    }
  };

  getLessonsOfCourse = (courseId) => {
    let lessons = this.state.stats.lessons;
    ///keep only those lessons who have the same course id as my course id
    lessons = lessons.filter((item) => item.courseId === courseId);
    return lessons;
  };

  getTotalSalary = () => {
    const { stats } = this.state;

    let salary = 0;

    stats.teachings.forEach((t) => (salary += this.getSalaryForCourse(t)));
    stats.substitutings.forEach((t) => (salary += this.getSalaryForCourse(t)));

    this.setState({ totalSalaryForPeriod: salary });
  };

  getSalaryForCourse = (teaching) => {
    let salary = 0;
    const lessons = this.getLessonsOfCourse(teaching.courseId);
    lessons.forEach(
      (l) =>
        (salary += parseInt(l.attended) * parseFloat(teaching.salaryPerStudent))
    );

    return salary;
  };

  render() {
    const {
      stats,
      selectedPeriod,
      showStats,
      totalSalaryForPeriod,
      totalLessonsForPeriod,
    } = this.state;

    return (
      <div className="salaryDiv container">
        <div className="row form-group mx-auto">
          <div className="form-group col-sm-12 col-md-3">
            <p>Month</p>
          </div>
          <div className="form-group col-sm-12 col-md-3">
            <select
              className="form-control"
              value={selectedPeriod.month}
              onChange={(event) => this.periodChange(event, "month")}
            >
              <option disabled hidden value=""></option>
              {this.state.months.map((m) => (
                <option key={m}>{m}</option>
              ))}
            </select>
          </div>
          <div className="form-group col-sm-12 col-md-3">
            <p>Year</p>
          </div>
          <div className="form-group col-sm-12 col-md-3">
            <input
              type="number"
              className="form-control"
              id="date"
              min={1000}
              max={9999}
              value={selectedPeriod.year}
              onChange={(event) => this.periodChange(event, "year")}
            />
          </div>
        </div>
        <div className="mx-auto">
          <button className="editButton" onClick={this.showStatistics}>
            Show
          </button>
        </div>
        <div className="statsResult">
          {/* have you pressed the button */}

          {showStats ? (
            <div className="statsWrapper">
              <div className="row totalStats">
                <div className="col-sm-12 col-md-4">
                  <h5>Total Lessons for period</h5>
                </div>
                <div className="col-sm-12 col-md-2">
                  {totalLessonsForPeriod}
                </div>
                <div className="col-sm-12 col-md-4">
                  <h5>Total Salary</h5>
                </div>
                <div className="col-sm-12 col-md-2">{totalSalaryForPeriod}</div>
              </div>

              {/* //are there any lessons for this period */}
              {stats.lessons.length > 0 ? (
                //show the lessons by course
                stats.teachings.map((t) => {
                  return (
                    <GeneralCourseSalary
                      key={t.id}
                      lessonsOfCourse={this.getLessonsOfCourse(t.courseId)}
                      teaching={t}
                      salaryForCourse={this.getSalaryForCourse(t)}
                    ></GeneralCourseSalary>
                  );
                })
              ) : (
                <h1>No lessons for the selected time period!</h1>
              )}
              {stats.lessons.length > 0
                ? //show the lessons by course
                  stats.substitutings.map((t) => {
                    return (
                      <GeneralCourseSalary
                        key={t.id}
                        lessonsOfCourse={this.getLessonsOfCourse(t.courseId)}
                        teaching={t}
                        salaryForCourse={this.getSalaryForCourse(t)}
                      ></GeneralCourseSalary>
                    );
                  })
                : null}
            </div>
          ) : ///don't show stats
          null}
        </div>
      </div>
    );
  }
}

export default TeacherSalary;
