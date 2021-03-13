import React from "react";
import { useState, useEffect } from "react";
import { getStartedCoursesOfTeacher } from "../../../services/courseService";
import StartedCoursesTable from "../StartedCoursesTable";
import { dynamicSort } from "../../../common/helper";

function LessonsPage(props) {
  let teacherId;
  if (props.location) {
    teacherId = props.location.lessonProps.teacherId;
  } else {
    teacherId = props.teacherId;
  }

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
        message={"Started courses"}
        courses={courses}
      ></StartedCoursesTable>
    </div>
  ) : (
    <h1>There are no started courses yet</h1>
  );
}
export default LessonsPage;
