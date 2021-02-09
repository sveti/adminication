import React from "react";
import { useState, useEffect } from "react";
import { getStartedCoursesOfTeacher } from "../../../services/courseService";
import StartedCoursesTable from "../StartedCoursesTable";
import { dynamicSort } from "../../../common/helper";

function LessonsPage(props) {
  const teacherId = props.location.lessonProps.teacherId;
  const [courses, setCourses] = useState(null);

  useEffect(() => {
    async function getCourses() {
      const { data } = await getStartedCoursesOfTeacher(teacherId);
      setCourses(data.sort(dynamicSort("id")));
    }
    getCourses();
  }, [teacherId]);

  return courses ? (
    <div className="lessonsOfCourseContainer">
      <StartedCoursesTable
        message={"Please select a course"}
        courses={courses}
      ></StartedCoursesTable>
    </div>
  ) : (
    <h1>There are no started courses yet</h1>
  );
}
export default LessonsPage;
