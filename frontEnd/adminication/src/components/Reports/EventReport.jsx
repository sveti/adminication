import React, { Component } from "react";
import { dynamicSort } from "../../common/helper";
import { getEventReport } from "../../services/eventsService";
import BackButton from "../../common/BackButton";

import StatusBadge from "../../common/StausBadge";

class CourseReport extends Component {
  constructor(props) {
    super(props);
    this.state = {
      eventId: null,
      stats: [],
      showStats: false,
      title: null,
      showEnrollments: false,
      showWaitingList: false,
    };
    if (props.location && props.location.statisticsProps) {
      this.state.eventId = props.location.statisticsProps.eventId;
    } else {
      this.state.eventId = props.eventId;
    }
    if (
      props.location &&
      props.location.statisticsProps &&
      props.location.statisticsProps.eventTitle
    ) {
      this.state.title = props.location.statisticsProps.eventTitle;
    }
  }

  loadEventStats = async () => {
    let { data } = await getEventReport(this.state.eventId);
    data.studentsSignedUp = data.studentsSignedUp.sort(
      dynamicSort("studentName")
    );
    return data;
  };

  componentDidMount = async () => {
    const stats = await this.loadEventStats();
    this.setState({ stats, showStats: true });
  };

  createStudentEnrollment = (enrollment) => {
    return (
      <div className="row" key={enrollment.studentId}>
        <div className="col-12">
          <div className="row">
            <p className="col-2">#{enrollment.studentId}</p>
            <p className="col-8">{enrollment.studentName}</p>
          </div>
          <hr></hr>
        </div>
      </div>
    );
  };

  render() {
    const {
      eventId,
      title,
      showStats,
      stats,
      showEnrollments,
      showWaitingList,
    } = this.state;

    return (
      <div className="salaryDiv container">
        {title ? (
          <h3 className=" col-12 mb-5">
            Report for #{eventId} {title}
          </h3>
        ) : null}
        <div className="statsResult">
          {showStats ? (
            <div>
              {
                <React.Fragment>
                  <div className="row">
                    <h3 className="col-8">{stats.title}</h3>
                    <h3 className="col-4 mb-3">
                      <StatusBadge status={stats.status} />
                    </h3>
                    <p className="col-12 my-5 left">{stats.description}</p>
                  </div>
                  <div className="row mb-5">
                    <div className="col-3">
                      <h5>Capacity</h5>
                    </div>
                    <div className="col-3">
                      {stats.maxNumberOfPeople}{" "}
                      {stats.maxNumberOfPeople > 1 ? "students" : "student"}
                    </div>
                    <div className="col-3">
                      <h5>Age limit:</h5>
                    </div>
                    <div className="col-3">
                      {stats.minAge} - {stats.maxAge}
                    </div>
                  </div>
                  <div className="row mb-5">
                    <h4 className="col-10 mb-5">
                      Students signed up: {stats.studentsSignedUp.length}
                    </h4>
                    <p className="col-2">
                      {stats.studentsSignedUp.length > 0 ? (
                        <button
                          className="btn selectBtn"
                          onClick={() =>
                            this.setState({ showEnrollments: !showEnrollments })
                          }
                        >
                          {showEnrollments
                            ? "Hide enrollments"
                            : "Show enrollments"}
                        </button>
                      ) : null}
                    </p>
                    {showEnrollments ? (
                      <div className="col-12 text-left fadeIn">
                        {stats.studentsSignedUp.map((enrollment) => {
                          return this.createStudentEnrollment(enrollment);
                        })}
                      </div>
                    ) : null}
                  </div>
                  <div className="row mb-5">
                    <h4 className="col-10 mb-5">
                      Students waiting : {stats.studentsWaitingList.length}
                    </h4>
                    <p className="col-2">
                      {stats.studentsWaitingList.length > 0 ? (
                        <button
                          className="btn selectBtn"
                          onClick={() =>
                            this.setState({ showWaitingList: !showWaitingList })
                          }
                        >
                          {showWaitingList
                            ? "Hide waiting list"
                            : "Show waiting list"}
                        </button>
                      ) : null}
                    </p>
                    {showWaitingList ? (
                      <div className="col-12 text-left fadeIn">
                        {stats.studentsWaitingList.map((enrollment) => {
                          return this.createStudentEnrollment(enrollment);
                        })}
                      </div>
                    ) : null}
                  </div>
                </React.Fragment>
              }
            </div>
          ) : null}
        </div>
        <BackButton></BackButton>
      </div>
    );
  }
}

export default CourseReport;
