import React, { useState } from "react";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";

function DetailedCourseSalary(props) {
  const { lessonsOfCourse, courseSignedUp, salaryPerStudent } = props;
  const [showMore, setShowMore] = useState(false);

  return (
    <div>
      <div className="col-12">
        <button
          className="editButton showMore"
          onClick={() => setShowMore(!showMore)}
        >
          {showMore ? "Show less" : "Show more"}
        </button>
      </div>
      {showMore ? (
        <div className="col-12 detailedCourseSalaryTable">
          <Table>
            <Thead>
              <Tr>
                <Th className="noBorder">Date</Th>
                <Th className="noBorder">Attended</Th>
                <Th className="noBorder">Salary</Th>
              </Tr>
            </Thead>
            <Tbody>
              {lessonsOfCourse.map((lesson) => (
                <Tr key={lesson.id} className="alternate">
                  <Td>{lesson.date}</Td>
                  <Td>
                    {lesson.attended}/{courseSignedUp}
                  </Td>
                  <Td>
                    {parseInt(lesson.attended) * parseFloat(salaryPerStudent)}
                  </Td>
                </Tr>
              ))}
            </Tbody>
          </Table>{" "}
        </div>
      ) : null}
    </div>
  );
}

export default DetailedCourseSalary;
