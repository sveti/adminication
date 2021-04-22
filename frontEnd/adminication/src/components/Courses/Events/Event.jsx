import React from "react";
import BackButton from "../../../common/BackButton";

import { textToDayOfTheWeek } from "../../../common/helper";

const Event = (props) => {
  const event = props.location.state.event;
  return (
    <React.Fragment>
      <div className="card courseContainer rounded-lg">
        <div className="card-body">
          <div className="card-title">
            <h1>Event #{event.id}</h1>
            <h2 className="courseTitle">{event.title}</h2>
          </div>

          <div className="row info-group">
            <div className="col-sm-12 col-md-3 dataLabel">
              <h5>Description: </h5>
            </div>
            <div className="col-sm-12 col-md-9 courseData">
              {event.description}
            </div>
          </div>
          <div className="row info-group space">
            <div className="col-sm-12 col-md-3 dataLabel">
              <h5>Timeframe: </h5>
            </div>
            <div className="col-sm-12 col-md-9 courseData">
              {event.startDate.map((startD, index) => {
                return (
                  <p key={startD} className="mb-3">
                    {event.startDate} ( {textToDayOfTheWeek(event.startDate)} ){" "}
                    {event.startTime[index].slice(0, -3)} -
                    {event.endTime[index].slice(0, -3)}{" "}
                  </p>
                );
              })}
            </div>
          </div>
          <div className="row info-group">
            <div className="col-sm-12 col-md-3 dataLabel">
              <h5>Number of signed up: </h5>
            </div>
            <div className="col-sm-12 col-md-3 courseData">
              {event.signedUp}
            </div>
            <div className="col-sm-12 col-md-3 dataLabel">
              <h5>Maximum number of students: </h5>
            </div>
            <div className="col-sm-12 col-md-3 courseData">
              {event.maxNumberOfPeople}
            </div>
          </div>
        </div>
      </div>
      <div>
        <BackButton></BackButton>
      </div>
    </React.Fragment>
  );
};

export default Event;
