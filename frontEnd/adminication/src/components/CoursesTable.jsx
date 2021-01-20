import React from "react";
import { Table, Thead, Tbody, Tr, Th, Td } from "react-super-responsive-table";
import "react-super-responsive-table/dist/SuperResponsiveTableStyle.css";
import "./coursesTable.css";

export default function CourseTable(props) {
  return (
    <div className="table">
      <div className="title">
        <h2>{props.message}</h2>
      </div>
      <Table>
        <Thead>
          <Tr>
            <Th className="noBorder">Course</Th>
            <Th className="noBorder">Date</Th>
            <Th className="noBorder">Time</Th>
          </Tr>
        </Thead>
        <Tbody>
          <Tr>
            <Td>Tablescon</Td>
            <Td>9 April 2019</Td>
            <Td>East Annex</Td>
          </Tr>
          <Tr>
            <Td>Capstone Data</Td>
            <Td>19 May 2019</Td>
            <Td>205 Gorgas</Td>
          </Tr>
          <Tr>
            <Td>Tuscaloosa D3</Td>
            <Td>29 June 2019</Td>
            <Td>Github</Td>
          </Tr>
        </Tbody>
      </Table>
    </div>
  );
}
