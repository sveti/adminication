import React from "react";
import { useState, useEffect } from "react";

import BackButton from "../../../common/BackButton";

import { getGradesOfStudent } from "../../../services/educationStudentService";
import { dynamicSort } from "../../../common/helper";

import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
const StudentGrades = (props) => {
  const { user } = props;

  const [grades, setGrades] = useState([]);

  useEffect(() => {
    async function getGrades() {
      const { data } = await getGradesOfStudent(user.id);
      setGrades(data.sort(dynamicSort("id")));
    }
    getGrades();
  }, [user]);

  const notGradedBadge = (
    <div className="alert alert-danger notGraded" role="alert">
      Not graded
    </div>
  );

  return (
    <div className="gradingTable">
      {grades.length > 0 ? (
        <div>
          <h1 className="title">Grades</h1>
          <div className="gradesTableContainer">
            <Table>
              <Thead>
                <Tr>
                  <Th className="noBorder">Course</Th>
                  <Th className="noBorder">Final grade</Th>
                </Tr>
              </Thead>

              <Tbody>
                {grades.map((g) => {
                  return (
                    <Tr key={g.courseId} className="alternate">
                      <Td>{g.courseTitle}</Td>
                      <Td>{g.grade > 2 ? g.grade : notGradedBadge}</Td>
                    </Tr>
                  );
                })}
              </Tbody>
            </Table>
          </div>
        </div>
      ) : (
        <h3 className="mt-5">No courses have been graded yet</h3>
      )}
      <BackButton></BackButton>
    </div>
  );
};

export default StudentGrades;
