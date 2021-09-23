import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Select from "react-select";
import { getCourseTitlesAll } from "../../services/courseService";
import { dynamicSort } from "../../common/helper";
import "./reports.css";

const convertCourseToSelectCourse = (coursesArr) => {
  const newArrayOfObj = coursesArr.map(
    ({ id: value, title: label, ...rest }) => ({
      value,
      label,
      ...rest,
    })
  );

  return newArrayOfObj;
};

const CourseSelection = (props) => {
  const [courses, setCourses] = useState([]);
  const [selectedCourseId, setSelectedCourseId] = useState(null);
  const [selectedCourseTitle, setSelectedCourseTitle] = useState("");

  useEffect(() => {
    let isMounted = true;
    async function getCourses() {
      const { data } = await getCourseTitlesAll();
      return data;
    }
    getCourses().then((data) => {
      if (isMounted)
        setCourses({
          courses: convertCourseToSelectCourse(data.sort(dynamicSort("id"))),
        });
    });
    return () => {
      isMounted = false;
    };
  }, []);

  const handleChange = (selectedOption) => {
    setSelectedCourseId(selectedOption.value);
    setSelectedCourseTitle(selectedOption.label);
  };

  return (
    <div className="container salaryDiv">
      <div className="row">
        <div className="col-12 mb-5">
          <h3>Select a course</h3>
        </div>
        <div className="col-10">
          <Select options={courses.courses} onChange={handleChange} />
        </div>
        <div className="col-2">
          <Link
            className=""
            to={{
              pathname: "/reports/courses/" + selectedCourseId,
              statisticsProps: {
                courseId: selectedCourseId,
                courseTitle: selectedCourseTitle,
              },
            }}
          >
            <button
              className="btn selectBtn"
              disabled={selectedCourseId ? false : true}
            >
              Select
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default CourseSelection;
