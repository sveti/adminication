import React from "react";
import { Link } from "react-router-dom";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import "react-super-responsive-table/dist/SuperResponsiveTableStyle.css";

import { daysTillStart } from "../../../common/helper";

const EventsTable = (props) => {
  return (
    <div className="table">
      <div className="coursesTitle">
        <h2>{props.message}</h2>
      </div>
      <Table>
        <Thead>
          <Tr>
            <Th className="noBorder">Event title</Th>
            <Th className="noBorder">Start Date</Th>
            <Th className="noBorder">Timeframe</Th>
            <Th className="noBorder">Participants</Th>
            <Th className="noBorder">Days till Start</Th>
            <Th className="noBorder">Details</Th>
          </Tr>
        </Thead>
        <Tbody>
          {props.events.map((event) => {
            return (
              <Tr key={event.id}>
                <Td>{event.title}</Td>
                <Td>
                  {event.startDate.map((date) => {
                    return <div key={date}>{date}</div>;
                  })}
                </Td>
                <Td>
                  {event.startTime.map((time, index) => {
                    return (
                      <div key={time + index}>
                        {time.slice(0, -3)} -{" "}
                        {event.endTime[index].slice(0, -3)}
                      </div>
                    );
                  })}
                </Td>
                <Td>
                  {event.signedUp}/{event.maxNumberOfPeople}
                </Td>
                <Td>{daysTillStart(event.startDate)}</Td>
                <Td>
                  <Link
                    to={{
                      pathname: "/events/" + event.id,
                      state: {
                        event: event,
                        student: props.student,
                      },
                    }}
                  >
                    <button className="editButton details">Details</button>
                  </Link>
                </Td>
              </Tr>
            );
          })}
        </Tbody>
      </Table>
    </div>
  );
};

export default EventsTable;
