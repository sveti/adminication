import React from "react";
import { Link } from "react-router-dom";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import { textToDayOfTheWeek } from "../../common/helper";
import "react-super-responsive-table/dist/SuperResponsiveTableStyle.css";
import "./upcommingCoursesTable.css";

export default function StartedCoursesTable(props) {
  return (
    <div className="table">
      <div className="coursesTitle">
        <h2>{props.message}</h2>
      </div>
      <Table>
        <Thead>
          <Tr>
            <Th className="noBorder">Course</Th>
            <Th className="noBorder">Start Date</Th>
            <Th className="noBorder">Day</Th>
            <Th className="noBorder">Timeframe</Th>
            <Th className="noBorder">Lessons</Th>
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
                  {course.startDate.map((date) => {
                    return <div key={date}>{textToDayOfTheWeek(date)}</div>;
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

                <Td>
                  <Link
                    to={{
                      pathname: "/lessons/" + course.id,
                      lessonProps: {
                        courseId: course.id,
                        teacherId: props.teacherId,
                        courseTitle: course.title,
                      },
                    }}
                  >
                    <button className="editButton lessons">Lessons</button>
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
