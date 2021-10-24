import React from "react";
import { Link } from "react-router-dom";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import "react-super-responsive-table/dist/SuperResponsiveTableStyle.css";

export default function FinisedCoursesTable(props) {
  const graded = (
    <div className="alert alert-success" role="alert">
      Graded
    </div>
  );
  const nonGraded = (
    <div className="alert alert-danger" role="alert">
      Awaits grading
    </div>
  );

  return (
    <div className="table">
      {props.courses.length > 0 ? (
        <React.Fragment>
          <div className="coursesTitle">
            <h2>{props.message}</h2>
          </div>
          <Table>
            <Thead>
              <Tr>
                <Th className="noBorder">Course</Th>
                <Th className="noBorder">Status</Th>
                <Th className="noBorder">Grades</Th>
              </Tr>
            </Thead>
            <Tbody>
              {props.courses.map((course) => {
                return (
                  <Tr key={course.id}>
                    <Td>{course.title}</Td>
                    <Td>
                      {course.signedUp - course.numberOfSetGrades === 0
                        ? graded
                        : nonGraded}
                    </Td>
                    <Td>
                      {" "}
                      <Link
                        to={{
                          pathname: "/grading/" + course.id,
                          gradingProps: {
                            courseId: course.id,
                            teacherId: props.teacherId,
                          },
                        }}
                      >
                        <button className="editButton lessons">Grade</button>
                      </Link>
                    </Td>
                  </Tr>
                );
              })}
            </Tbody>
          </Table>
        </React.Fragment>
      ) : (
        <h1>No courses awaiting grading!</h1>
      )}
    </div>
  );
}
