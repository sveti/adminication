import React, { Component } from "react";

import EventCard from "./EventCard";
import EventsSearchBar from "./EventsSearchBar";

import { getEvents, getEventsOfStudent } from "../../services/eventsService";
import { getScheduleOfStudent } from "../../services/scheduleService";
import {
  dynamicSort,
  getMinDateAsDate,
  textToDate,
  isAfterDate,
  textToDayOfTheWeekNumber,
  getMaxDate,
} from "../../common/helper";

import {
  getWaitingListEventsOfStudent,
  addEventWaitingListSignUp,
  deleteEventWaitingList,
} from "../../services/waitingListService";
import { addEventSignUp } from "../../services/eventSignUpService";
import { toast } from "react-toastify";

import "./events.css";

class AllEventsList extends Component {
  constructor(props) {
    super(props);
    this.state = {
      user: this.props.user,
      allEvents: [],
      events: [],
      studentSchedule: [],
      scheduleConflict: [],
      parentView: false,
      waitingList: [],
    };
    if (!this.props.user) {
      this.state.user = this.props.location.state.user;
      this.state.parentView = this.props.location.state.parentView;
    }
  }

  isOutsideOfTimeRange = (start1, end1, start2, end2) => {
    ////https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap

    return (start1 > start2 ? start1 : start2) > (end1 < end2 ? end1 : end2);
  };

  scheduleOverlap = (dates, startTimes, endTimes) => {
    const { studentSchedule } = this.state;
    let courseStartDate = getMaxDate(dates);
    // courseStartDate = courseStartDate.replace(/\./g, "-");

    //https://stackoverflow.com/questions/11343939/how-to-add-weeks-to-date-using-javascript#:~:text=You%20can%20do%20this%20%3A,7)%3B%20alert(now)%3B

    let conflictingStudentShed = [];

    studentSchedule.forEach((sched) => {
      let endDateAsDate = new Date(sched.startDate);
      endDateAsDate.setDate(endDateAsDate.getDate() + sched.duration * 7);
      if (courseStartDate > endDateAsDate) {
        //console.log(courseStartDate + " is after " + endDateAsDate);
      } else {
        //console.log(courseStartDate + " is before " + endDateAsDate);
        conflictingStudentShed.push(sched);
      }
    });

    if (conflictingStudentShed.length === 0) return false;

    let dayOfTheWeek = [];

    dates.forEach((element) => {
      dayOfTheWeek.push(textToDayOfTheWeekNumber(element));
    });

    let sameDayOfTheWeek = conflictingStudentShed.filter((schedule) =>
      dayOfTheWeek.includes(textToDayOfTheWeekNumber(schedule.startDate))
    );

    if (sameDayOfTheWeek.length === 0) return false;
    else {
      var indicator = false;
      sameDayOfTheWeek.some((schedule) => {
        startTimes.some((element, index) => {
          if (
            !this.isOutsideOfTimeRange(
              schedule.startTime,
              schedule.endTime,
              startTimes[index],
              endTimes[index]
            )
          ) {
            indicator = true;
            return true;
          }
          return false;
        });
        return indicator;
      });
      return indicator;
    }
  };
  getEvents = async () => {
    let { data } = await getEvents();

    const { data: studentEvents } = await getEventsOfStudent(
      this.state.user.id
    );

    data = data.filter(
      (event) => !studentEvents.some((e) => e.id === event.id)
    );

    data.sort(dynamicSort("id"));

    let scheduleConflict = [];
    data.forEach((event) => {
      let sheduleOverLap = this.scheduleOverlap(
        event.startDate,
        event.startTime,
        event.endTime
      );
      scheduleConflict.push({
        eventId: event.id,
        scheduleOverlap: sheduleOverLap,
      });
    });

    this.setState({
      allEvents: data,
      events: data,
      scheduleConflict: scheduleConflict,
    });
  };

  getScheduleOfStudent = async () => {
    const { data } = await getScheduleOfStudent(this.state.user.id);
    this.setState({ studentSchedule: data });
    this.getEvents();
  };

  getStudentWaitingList = async () => {
    const { data } = await getWaitingListEventsOfStudent(this.state.user.id);
    this.setState({ waitingList: data });
  };

  componentDidMount() {
    this.getScheduleOfStudent();
    this.getStudentWaitingList();
  }

  handleSubmit = (
    title,
    minAge,
    maxAge,
    avialable,
    startDate,
    endDate,
    scheduleConflict
  ) => {
    const { allEvents } = this.state;
    if (
      title.length === 0 &&
      minAge === 0 &&
      maxAge === 100 &&
      avialable === false &&
      startDate === "" &&
      endDate === "" &&
      scheduleConflict === false
    ) {
      this.setState({ events: allEvents });
    } else {
      let titleFiltered = [...allEvents],
        ageLimitFiltered = [...allEvents],
        avialableFiltered = [...allEvents],
        dateFiltered = [...allEvents],
        sheduleFiltered = [...allEvents];

      if (title.length !== 0) {
        titleFiltered = allEvents.filter((event) =>
          event.title.toLowerCase().includes(title.toLowerCase())
        );
      }

      if (minAge > 0) {
        ageLimitFiltered = ageLimitFiltered.filter(
          (event) => event.minAge <= minAge && event.maxAge >= minAge
        );
      }
      if (maxAge < 100) {
        ageLimitFiltered = ageLimitFiltered.filter(
          (event) => event.maxAge <= maxAge
        );
      }

      if (avialable) {
        avialableFiltered = allEvents.filter(
          (event) => event.maxNumberOfPeople - event.signedUp > 0
        );
      }
      if (startDate !== "") {
        dateFiltered = allEvents.filter((event) =>
          isAfterDate(getMinDateAsDate(event.startDate), textToDate(startDate))
        );
      }
      if (endDate !== "") {
        dateFiltered = dateFiltered.filter((event) =>
          isAfterDate(textToDate(endDate), getMinDateAsDate(event.startDate))
        );
      }
      if (scheduleConflict !== false) {
        sheduleFiltered = allEvents.filter(
          (event) =>
            !this.state.scheduleConflict.find(
              (conflicts) => event.id === conflicts.eventId
            ).scheduleOverlap
        );
      }

      let merged = titleFiltered
        .filter((n) => avialableFiltered.includes(n))
        .filter((n) => ageLimitFiltered.includes(n))
        .filter((n) => dateFiltered.includes(n))
        .filter((n) => sheduleFiltered.includes(n));
      this.setState({ events: merged });
    }
  };

  onEventSignUp = async (event) => {
    const response = await addEventSignUp({
      studentId: this.state.user.id,
      eventId: event.id,
    }).catch(function (error) {
      if (error.response) {
        // Request made and server responded
        toast.error(error.response.data.error, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else if (error.request) {
        // The request was made but no response was received
        toast.error("An error occured! Try again later", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else {
        // Something happened in setting up the request that triggered an Error
        toast.error(error.message, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      }
    });

    if (response && response.status === 200) {
      toast.success(response.data, {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
      this.props.increase();
    }
  };

  onWaitingListSignUp = async (event) => {
    const currentDate = new Date();
    const response = await addEventWaitingListSignUp({
      studentId: this.state.user.id,
      eventId: event.id,
      signed: currentDate.toISOString().slice(0, -5),
    }).catch(function (error) {
      if (error.response) {
        // Request made and server responded
        toast.error(error.response.data.error, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else if (error.request) {
        // The request was made but no response was received
        toast.error("An error occured saving your lesson! Try again later", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else {
        // Something happened in setting up the request that triggered an Error
        toast.error(error.message, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      }
    });

    if (response && response.status === 200) {
      toast.success(response.data, {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
      this.props.increase();
    }
  };

  onRemoveFromWaitingList = async (waitingList) => {
    const response = await deleteEventWaitingList(
      waitingList.eventWaitingListId
    ).catch(function (error) {
      if (error.response) {
        // Request made and server responded
        toast.error(error.response.data.error, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else if (error.request) {
        // The request was made but no response was received
        toast.error("An error occured saving your lesson! Try again later", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else {
        // Something happened in setting up the request that triggered an Error
        toast.error(error.message, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      }
    });

    if (response && response.status === 200) {
      toast.success(response.data, {
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
    const { events, allEvents, scheduleConflict, parentView, waitingList } =
      this.state;
    return (
      <div className="container">
        <div className="lessonsOfCourseContainer row">
          <div className="col-sm-12 col-md-4">
            <div className="p-3 border rounded m-2 mb-5">
              <EventsSearchBar
                handleSubmit={(
                  title,
                  minAge,
                  maxAge,
                  avialable,
                  startDate,
                  endDate,
                  scheduleConflict
                ) =>
                  this.handleSubmit(
                    title,
                    minAge,
                    maxAge,
                    avialable,
                    startDate,
                    endDate,
                    scheduleConflict
                  )
                }
              ></EventsSearchBar>
            </div>
          </div>
          <div className="col-sm-12 col-md-8">
            {allEvents.length > 0 ? (
              events.length > 0 ? (
                <React.Fragment>
                  <h3 className="mb-5">
                    Showing {events.length} out of {allEvents.length} events
                  </h3>
                  {events.map((event) => (
                    <EventCard
                      event={event}
                      key={event.id}
                      scheduleConflict={scheduleConflict.find(
                        (conflicts) => event.id === conflicts.eventId
                      )}
                      parentView={parentView}
                      waitingList={waitingList.filter(
                        (e) => e.eventId === event.id
                      )}
                      onEventSignUp={(event) => this.onEventSignUp(event)}
                      onWaitingListSignUp={(event) =>
                        this.onWaitingListSignUp(event)
                      }
                      onRemoveFromWaitingList={(waitingList) =>
                        this.onRemoveFromWaitingList(waitingList)
                      }
                    ></EventCard>
                  ))}
                </React.Fragment>
              ) : (
                <h3>No events match your search criteria!</h3>
              )
            ) : (
              <h3>No active events, try again later!</h3>
            )}
          </div>
        </div>
      </div>
    );
  }
}

export default AllEventsList;
