import React from "react";

import { useState, useEffect } from "react";
import {
  getStartedCoursesOfStudent,
  getUpcommingCoursesOfStudent,
} from "../../services/courseService";
import { dynamicSort } from "../../common/helper";
import StudentStartedCoursesTable from "./StudentStartedCoursesTable";
import UpcommingCoursesTable from "./UpcommingCoursesTable";

const StudentCoursesPage = (props) => {
  let { user } = props;

  if (!user) {
    user = props.location.state.user;
  }

  const [courses, setCourses] = useState({ started: [], upcomming: [] });

  useEffect(() => {
    async function getCourses() {
      const { data } = await getStartedCoursesOfStudent(user.id);
      const response = await getUpcommingCoursesOfStudent(user.id);
      setCourses({
        started: data.sort(dynamicSort("id")),
        upcomming: response.data.sort(dynamicSort("id")),
      });
    }
    getCourses();
  }, [user]);

  const started = (
    <StudentStartedCoursesTable
      message="Started courses"
      courses={courses.started}
    ></StudentStartedCoursesTable>
  );
  const upcomming = (
    <UpcommingCoursesTable
      message="Upcomming courses"
      courses={courses.upcomming}
      student={user}
    ></UpcommingCoursesTable>
  );

  return courses.started.length > 0 || courses.upcomming.length > 0 ? (
    <div className="lessonsOfCourseContainer lessMargin">
      {courses.started.length > 0 ? started : null}
      {courses.upcomming.length > 0 ? upcomming : null}
    </div>
  ) : (
    <div className="lessonsOfCourseContainer">
      <h1 className="mt-1">There are no started courses yet</h1>
    </div>
  );
};

export default StudentCoursesPage;
