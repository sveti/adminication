import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Select from "react-select";
import { getAllStudentsAdmin } from "../../services/studentService";
import { dynamicSort } from "../../common/helper";
import "./reports.css";

const StudentSelection = (props) => {
  const [students, setStudents] = useState([]);
  const [selectedStudentId, setselectedStudentId] = useState(null);
  const [selectedStudentName, setselectedStudentName] = useState("");

  useEffect(() => {
    let isMounted = true;
    async function getStudents() {
      const { data } = await getAllStudentsAdmin();
      return data;
    }
    getStudents().then((data) => {
      if (isMounted)
        setStudents({
          students: data.sort(dynamicSort("value")),
        });
    });
    return () => {
      isMounted = false;
    };
  }, []);

  const handleChange = (selectedOption) => {
    setselectedStudentId(selectedOption.value);
    setselectedStudentName(selectedOption.label);
  };

  return (
    <div className="container salaryDiv">
      <div className="row">
        <div className="col-12 mb-5">
          <h3>Select a student</h3>
        </div>
        <div className="col-10">
          <Select options={students.students} onChange={handleChange} />
        </div>
        <div className="col-2">
          <Link
            className=""
            to={{
              pathname: "/reports/students/" + selectedStudentId,
              statisticsProps: {
                studentId: selectedStudentId,
                studentName: selectedStudentName,
              },
            }}
          >
            <button
              className="btn selectBtn"
              disabled={selectedStudentId ? false : true}
            >
              Select
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default StudentSelection;
