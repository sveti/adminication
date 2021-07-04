import React, { Component } from "react";

import { getTeacherStatisticForMonth } from "../../services/reportsService";

import "./reports.css";
import GeneralCourseSalary from "./GeneralCourseSalary";
import MonthYearSelector from "../../common/MonthYearSelector";

class TeacherSalary extends Component {
  constructor(props) {
    super(props);
    this.state = {
      teacherId: null,
      stats: null,
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

  loadStats = async (selectedPeriod) => {
    const { data } = await getTeacherStatisticForMonth(
      this.state.teacherId,
      selectedPeriod.month,
      selectedPeriod.year
    );

    this.setState({
      stats: data,
      showStats: true,
      totalLessonsForPeriod: data.lessons.length,
    });
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
  handleSubmit = async (selectedPeriod) => {
    await this.loadStats(selectedPeriod);
    this.getTotalSalary();
  };

  render() {
    const { stats, showStats, totalSalaryForPeriod, totalLessonsForPeriod } =
      this.state;

    return (
      <div className="salaryDiv container">
        <MonthYearSelector
          handleSubmit={(selectedPeriod) => this.handleSubmit(selectedPeriod)}
        ></MonthYearSelector>
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
