import React from "react";
import { useState, useEffect } from "react";
import {
  getStartedCoursesOfTeacher,
  getSubStartedCoursesOfTeacher,
} from "../../../services/courseService";
import StartedCoursesTable from "../StartedCoursesTable";
import { dynamicSort } from "../../../common/helper";

function LessonsPage(props) {
  let teacherId;
  if (props.location && props.location.lessonProps) {
    teacherId = props.location.lessonProps.teacherId;
  } else {
    teacherId = props.teacherId;
  }

  const [courses, setCourses] = useState({
    startedCourses: [],
    substituteCourses: [],
  });

  useEffect(() => {
    async function getCourses() {
      const { data } = await getStartedCoursesOfTeacher(teacherId);
      let response = await getSubStartedCoursesOfTeacher(teacherId);
      setCourses({
        startedCourses: data.sort(dynamicSort("id")),
        substituteCourses: response.data.sort(dynamicSort("id")),
      });
    }
    getCourses();
  }, [teacherId]);

  return courses.startedCourses.length > 0 ||
    courses.substituteCourses.length > 0 ? (
    <div className="lessonsOfCourseContainer">
      {courses.startedCourses.length > 0 ? (
        <StartedCoursesTable
          message={"Started courses"}
          courses={courses.startedCourses}
          teacherId={teacherId}
        ></StartedCoursesTable>
      ) : null}
      {courses.substituteCourses.length > 0 ? (
        <StartedCoursesTable
          message={"Substitute courses"}
          courses={courses.substituteCourses}
          teacherId={teacherId}
        ></StartedCoursesTable>
      ) : null}
    </div>
  ) : (
    <h1>There are no started courses yet</h1>
  );
}
export default LessonsPage;
