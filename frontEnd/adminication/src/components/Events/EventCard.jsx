import React, { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBan, faCheck } from "@fortawesome/free-solid-svg-icons";
import { getMinDate, textToDayOfTheWeek } from "../../common/helper";

const EventCard = ({
  event,
  scheduleConflict,
  parentView,
  onEventSignUp,
  onWaitingListSignUp,
  waitingList,
  onRemoveFromWaitingList,
}) => {
  let sheduleOverLap;
  if (scheduleConflict !== undefined) {
    sheduleOverLap = scheduleConflict.scheduleOverlap;
  } else {
    sheduleOverLap = false;
  }

  const [disable, setDisable] = useState(false);

  const scheduleOkBadge = (
    <span className="mx-3">
      <FontAwesomeIcon icon={faCheck}></FontAwesomeIcon>
    </span>
  );
  const scheduleAlertBadge = (
    <span className="mx-3">
      <FontAwesomeIcon icon={faBan}></FontAwesomeIcon> Shedule conflict
    </span>
  );

  let eventClasses = ["card-header", "cardHeaderBackground"];
  if (sheduleOverLap) {
    eventClasses.push("scheduleOverlap");
  } else {
    eventClasses.push("noScheduleOverlap");
  }
  function onSignUpButtonClick() {
    setDisable(true);
    onEventSignUp(event);
  }

  function onWaitingListButtonClick() {
    setDisable(true);
    onWaitingListSignUp(event);
  }

  function onRemoveFromWaitingListClick() {
    setDisable(true);
    onRemoveFromWaitingList(waitingList[0]);
  }

  const signUpButton = (
    <button
      className="btn float-right signUpButton"
      disabled={disable}
      onClick={onSignUpButtonClick}
    >
      Sign up
    </button>
  );

  const courseWaitingList = (
    <button
      className="btn float-right waitingListButton"
      disabled={disable}
      onClick={onWaitingListButtonClick}
    >
      Join waiting list
    </button>
  );
  const removeFromWaitingList = (
    <span>
      <button
        className="btn float-right removeWaitingListButton"
        disabled={disable}
        onClick={onRemoveFromWaitingListClick}
      >
        Remove from waiting list
      </button>
    </span>
  );

  return (
    <div className="card text-left mb-5">
      <div className={eventClasses.join(" ")}>
        <h5 className="m-0 d-inline-block">{event.title}</h5>
        {sheduleOverLap ? scheduleAlertBadge : scheduleOkBadge}
        {parentView
          ? waitingList.length > 0
            ? removeFromWaitingList
            : event.maxNumberOfPeople - event.signedUp > 0
            ? signUpButton
            : courseWaitingList
          : null}
        {waitingList.length > 0 && disable !== true ? (
          <h6 className="card-subtitle mb-2 numberInLine">
            Number in Line: {waitingList[0].numberInLine + 1}
          </h6>
        ) : null}
      </div>
      <div className="card-body">
        <h6 className="card-subtitle mb-2">
          Age group {event.minAge}-{event.maxAge} year old
        </h6>
        <p className="card-text mt-2">{event.description}</p>

        <p className="card-text">Start date : {getMinDate(event.startDate)}</p>

        <div className="miniTable">
          <div className="card-header px-0">
            <span className="mx-2">Timeframe</span>
          </div>
          <ul className="list-group list-group-flush">
            {event.startDate.map((d, index) => (
              <li className="list-group-item" key={d}>
                <span>
                  {d[8] +
                    d[9] +
                    "." +
                    d[5] +
                    d[6] +
                    "." +
                    d[0] +
                    d[1] +
                    d[2] +
                    d[3]}{" "}
                </span>
                <span>({textToDayOfTheWeek(d)})</span>
                <span> {event.startTime[index].slice(0, -3)} - </span>
                <span>{event.endTime[index].slice(0, -3)} </span>
              </li>
            ))}
          </ul>
        </div>
      </div>
      <div
        className={
          "card-footer " +
          (event.maxNumberOfPeople - event.signedUp > 0
            ? "enoughSpace"
            : "notEnoughSpace")
        }
      >
        Free places : {event.maxNumberOfPeople - event.signedUp}/
        {event.maxNumberOfPeople}
      </div>
    </div>
  );
};

export default EventCard;
