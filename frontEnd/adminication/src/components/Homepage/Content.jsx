import React from "react";
import "./content.css";
import TeacherLessonsPage from "../Courses/Lessons/TeacherLessonsPage";
import UserProfile from "./UserProfile";
import StudentLessonsPage from "../Courses/Lessons/StudentLessonsPage";

const Content = ({ givenId, user, avatar, editedUser }) => {
  let startedCourses = null;

  switch (user.roleName) {
    case "ROLE_TEACHER":
      startedCourses = (
        <TeacherLessonsPage teacherId={user.id}></TeacherLessonsPage>
      );
      break;
    case "ROLE_STUDENT":
      startedCourses = (
        <StudentLessonsPage
          studentId={user.id}
          lessMargin={true}
        ></StudentLessonsPage>
      );
      break;
    default:
      startedCourses = null;
  }

  return (
    <div className="content">
      <div className="container-fluid">
        <UserProfile
          givenId={givenId}
          user={user}
          avatar={avatar}
          editedUser={(user) => editedUser(user)}
        ></UserProfile>
      </div>
      {startedCourses}
    </div>
  );
};
export default Content;
