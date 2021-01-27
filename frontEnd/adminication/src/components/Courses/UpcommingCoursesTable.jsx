import React from "react";
import { Link } from "react-router-dom";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import "react-super-responsive-table/dist/SuperResponsiveTableStyle.css";
import "./upcommingCoursesTable.css";

export default function UpcommingCoursesTable(props) {
  function getMinDate(startDate) {
    const dates = startDate.map((dateStr) => {
      return new Date(dateStr);
    });
    var minDate = new Date(Math.min.apply(null, dates));
    return minDate;
  }

  function daysTillStart(startDate) {
    let Difference_In_Time = getMinDate(startDate).getTime() - Date.now();

    return Math.round(Difference_In_Time / (1000 * 3600 * 24));
  }

  return (
    <div className="table">
      <div className="title">
        <h2>{props.message}</h2>
      </div>
      <Table>
        <Thead>
          <Tr>
            <Th className="noBorder">Course</Th>
            <Th className="noBorder">Start Date</Th>
            <Th className="noBorder">Timeframe</Th>
            <Th className="noBorder">Duration</Th>
            <Th className="noBorder">Participants</Th>
            <Th className="noBorder">Days till Start</Th>
            <Th className="noBorder">Details</Th>
          </Tr>
        </Thead>
        <Tbody>
          {props.courses.map((course) => {
            return (
              <Tr key={course.id}>
                <Td>{course.title}</Td>
                <Td>
                  {course.startDate.map((date) => {
                    return <div key={date}>{date}</div>;
                  })}
                </Td>
                <Td>
                  {course.startTime.map((time, index) => {
                    return (
                      <div key={time + index}>
                        {time.slice(0, -3)} -{" "}
                        {course.endTime[index].slice(0, -3)}
                      </div>
                    );
                  })}
                </Td>
                <Td>{course.duration} Weeks</Td>
                <Td>
                  {course.signedUp}/{course.signUpLimit}
                </Td>
                <Td>{daysTillStart(course.startDate)}</Td>
                <Td>
                  <Link to={"/courses/" + course.id}>
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
}
