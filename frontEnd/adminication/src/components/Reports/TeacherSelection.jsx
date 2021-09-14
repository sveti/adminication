import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Select from "react-select";
import { getAllTeachersAdmin } from "../../services/teacherAdministrationService";
import { dynamicSort } from "../../common/helper";
import "./reports.css";

const convertParentToSelectTeacher = (teacherArr) => {
  const newArrayOfObj = teacherArr.map(
    ({ id: value, name: label, ...rest }) => ({
      value,
      label,
      ...rest,
    })
  );

  return newArrayOfObj;
};

const TeacherSelection = (props) => {
  const [teachers, setTeachers] = useState([]);
  const [selectedTeacherId, setSelectedTeacherId] = useState(null);
  const [selectedTeacherName, setSelectedTeacherName] = useState("");

  useEffect(() => {
    let isMounted = true;
    async function getTeachers() {
      const { data } = await getAllTeachersAdmin();
      return data;
    }
    getTeachers().then((data) => {
      if (isMounted)
        setTeachers({
          teachers: convertParentToSelectTeacher(data.sort(dynamicSort("id"))),
        }); // add conditional check
    });
    return () => {
      isMounted = false;
    };
  }, []);

  const handleChange = (selectedOption) => {
    setSelectedTeacherId(selectedOption.value);
    setSelectedTeacherName(selectedOption.label);
  };

  return (
    <div className="container salaryDiv">
      <div className="row">
        <div className="col-12 mb-5">
          <h3>Select a teacher</h3>
        </div>
        <div className="col-10">
          <Select options={teachers.teachers} onChange={handleChange} />
        </div>
        <div className="col-2">
          <Link
            className="nav-link"
            to={{
              pathname: "/reports/teachers/" + selectedTeacherId,
              statisticsProps: {
                teacherId: selectedTeacherId,
                teacherName: selectedTeacherName,
              },
            }}
          >
            <button
              className="btn selectBtn"
              disabled={selectedTeacherId ? false : true}
            >
              Select
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default TeacherSelection;
