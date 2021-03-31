import React from "react";
import { useState, useEffect } from "react";
import { getStartedCoursesOfStudent } from "../../../services/courseService";
import StudentStartedCoursesTable from "../StudentStartedCoursesTable";
import { dynamicSort } from "../../../common/helper";

const StudentLessonsPage = (props) => {
  let studentId;
  if (props.location && props.location.lessonProps) {
    studentId = props.location.lessonProps.studentId;
  } else {
    studentId = props.studentId;
  }

  let classes = ["lessonsOfCourseContainer"];
  if (props.lessMargin) {
    classes.push("lessMargin");
  }

  const [courses, setCourses] = useState([]);

  useEffect(() => {
    async function getCourses() {
      const { data } = await getStartedCoursesOfStudent(studentId);
      setCourses(data.sort(dynamicSort("id")));
    }
    getCourses();
  }, [studentId]);

  return courses.length > 0 ? (
    <div className={classes.join(" ")}>
      <StudentStartedCoursesTable
        message="Started courses"
        courses={courses}
        studentId={studentId}
      ></StudentStartedCoursesTable>
    </div>
  ) : (
    <h1>There are no started courses yet</h1>
  );
};

export default StudentLessonsPage;
