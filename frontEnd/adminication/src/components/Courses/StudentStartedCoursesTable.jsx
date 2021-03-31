import React from "react";
import { Link } from "react-router-dom";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import { textToDayOfTheWeek, dynamicSort } from "../../common/helper";
import "react-super-responsive-table/dist/SuperResponsiveTableStyle.css";
import "./upcommingCoursesTable.css";

export default function StudentStartedCoursesTable(props) {
  function countAttendance(lessons) {
    let attendance = 0;

    lessons.forEach((element) => {
      if (element.attended === true) attendance++;
    });
    return attendance;
  }

  return (
    <div className="table">
      <div className="coursesTitle">
        <h2>{props.message}</h2>
      </div>
      <Table>
        <Thead>
          <Tr>
            <Th className="noBorder">Title</Th>
            <Th className="noBorder">Day</Th>
            <Th className="noBorder">Timeframe</Th>
            <Th className="noBorder">Lessons</Th>
            <Th className="noBorder">Attendance</Th>
            <Th className="noBorder"></Th>
          </Tr>
        </Thead>
        <Tbody>
          {props.courses.map((course) => {
            return (
              <Tr key={course.id}>
                <Td>{course.title}</Td>
                <Td>
                  {course.days.map((date) => {
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
                <Td>{course.lessons.length}</Td>
                <Td>
                  {countAttendance(course.lessons)}/{course.lessons.length}
                </Td>
                <Td>
                  <Link
                    to={{
                      pathname: "/courses/" + course.id + "/lessons",
                      lessonProps: {
                        course: course,
                        lessons: course.lessons.sort(dynamicSort("id")),
                      },
                    }}
                  >
                    <button className="editButton lessons">To course</button>
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
