import React, { useState } from "react";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";

let formatDate = (d) => {
  return d[8] + d[9] + "." + d[5] + d[6] + "." + d[0] + d[1] + d[2] + d[3];
};

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
                  <Td className="py-2">{formatDate(lesson.date)}</Td>
                  <Td className="py-2">
                    {lesson.attended}/{courseSignedUp}
                  </Td>
                  <Td className="py-2">
                    {parseInt(lesson.attended) * parseFloat(salaryPerStudent)}$
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
