import React, { useState, useEffect } from "react";

import { getFinishedCoursesOfTeacher } from "../../../services/courseService";
import { dynamicSort } from "../../../common/helper";
import FinisedCoursesTable from "./FinisedCoursesTable";

export default function Grading(props) {
  let teacherId;
  if (props.location) {
    teacherId = props.location.gradingProps.teacherId;
  } else {
    teacherId = props.teacherId;
  }

  const [courses, setCourses] = useState(null);

  useEffect(() => {
    async function getCourses() {
      const { data } = await getFinishedCoursesOfTeacher(teacherId);
      setCourses(data.sort(dynamicSort("id")));
    }
    getCourses();
  }, [teacherId]);

  return courses ? (
    <div className="lessonsOfCourseContainer mt-2">
      <FinisedCoursesTable
        courses={courses}
        message={"Finished Courses"}
        teacherId={teacherId}
      ></FinisedCoursesTable>
    </div>
  ) : (
    <div className="lessonsOfCourseContainer">
      <h1>No courses have been graded!</h1>
    </div>
  );
}
