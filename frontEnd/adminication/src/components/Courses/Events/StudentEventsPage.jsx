import React from "react";
import { useState, useEffect } from "react";

import { getEventsOfStudent } from "../../../services/eventsService";

import { dynamicSort } from "../../../common/helper";
import EventsTable from "./EventsTable";

const StudentsEventsPage = (props) => {
  const { user } = props;

  const [events, setEvents] = useState({ upcomming: [], passed: [] });

  useEffect(() => {
    async function getEvents() {
      const { data } = await getEventsOfStudent(user.id);

      let upcomming = data.filter(
        (event) => event.status === "UPCOMMING" || event.status === "STARTED"
      );

      let passed = data.filter(
        (event) => event.status !== "UPCOMMING" && event.status !== "STARTED"
      );

      setEvents({
        upcomming: upcomming.sort(dynamicSort("id")),
        passed: passed.sort(dynamicSort("id")),
      });
    }
    getEvents();
  }, [user]);

  const upcomming = (
    <EventsTable
      message="Upcomming events"
      events={events.upcomming}
    ></EventsTable>
  );

  const passed = (
    <EventsTable message="Past events" events={events.passed}></EventsTable>
  );

  return events.upcomming.length > 0 || events.passed.length > 0 ? (
    <div className="lessonsOfCourseContainer lessMargin">
      {events.upcomming.length > 0 ? upcomming : null}
      {events.passed.length > 0 ? passed : null}
    </div>
  ) : (
    <div className="lessonsOfCourseContainer">
      <h1>There are no started events yet</h1>
    </div>
  );
};

export default StudentsEventsPage;
