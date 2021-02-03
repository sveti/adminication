import React from "react";
import { useState, useEffect } from "react";
import { getStartedCoursesOfTeacher } from "../../../services/courseService";
import StartedCoursesTable from "../StartedCoursesTable";

function LessonsPage(props) {
  const teacherId = props.location.lessonProps.teacherId;
  const [courses, setCourses] = useState(null);

  useEffect(() => {
    async function getCourses() {
      const { data } = await getStartedCoursesOfTeacher(teacherId);
      setCourses(data);
    }
    getCourses();
  }, [teacherId]);

  return courses ? (
    <StartedCoursesTable
      message={"Please select a course"}
      courses={courses}
    ></StartedCoursesTable>
  ) : (
    <h1>There are no started courses yet</h1>
  );
}
export default LessonsPage;
