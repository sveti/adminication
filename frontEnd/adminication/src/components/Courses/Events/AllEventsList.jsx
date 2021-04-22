import React, { Component } from "react";

import EventCard from "./EventCard";
import EventsSearchBar from "./EventsSearchBar";

import { getEvents } from "../../../services/eventsService";
import { getScheduleOfStudent } from "../../../services/scheduleService";
import {
  dynamicSort,
  getMinDateAsDate,
  textToDate,
  isAfterDate,
  textToDayOfTheWeekNumber,
} from "../../../common/helper";

import "./events.css";

class AllEventsList extends Component {
  state = {
    user: this.props.user,
    allEvents: [],
    events: [],
    studentSchedule: [],
    scheduleConflict: [],
  };

  isOutsideOfTimeRange = (start1, end1, start2, end2) => {
    ////https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap

    return (start1 > start2 ? start1 : start2) > (end1 < end2 ? end1 : end2);
  };

  scheduleOverlap = (dates, startTimes, endTimes) => {
    const { studentSchedule } = this.state;

    let dayOfTheWeek = [];

    dates.forEach((element) => {
      dayOfTheWeek.push(textToDayOfTheWeekNumber(element));
    });
    let sameDayOfTheWeek = studentSchedule.filter((schedule) =>
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
    const { data } = await getEvents();
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

  componentDidMount() {
    this.getScheduleOfStudent();
  }

  handleSubmit = (title, avialable, startDate, endDate, scheduleConflict) => {
    const { allEvents } = this.state;
    if (
      title.length === 0 &&
      avialable === false &&
      startDate === "" &&
      endDate === "" &&
      scheduleConflict === false
    ) {
      this.setState({ events: allEvents });
    } else {
      let titleFiltered = [...allEvents],
        avialableFiltered = [...allEvents],
        dateFiltered = [...allEvents],
        sheduleFiltered = [...allEvents];

      if (title.length !== 0) {
        titleFiltered = allEvents.filter((event) =>
          event.title.toLowerCase().includes(title.toLowerCase())
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
        .filter((n) => dateFiltered.includes(n))
        .filter((n) => sheduleFiltered.includes(n));
      this.setState({ events: merged });
    }
  };

  render() {
    const { events, allEvents, scheduleConflict } = this.state;
    return (
      <div className="container">
        <div className="lessonsOfCourseContainer row">
          <div className="col-sm-12 col-md-4">
            <div className="p-3 border rounded m-2 mb-5">
              <EventsSearchBar
                handleSubmit={(
                  title,
                  avialable,
                  startDate,
                  endDate,
                  scheduleConflict
                ) =>
                  this.handleSubmit(
                    title,
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
