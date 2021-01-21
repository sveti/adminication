import React from "react";
import "./content.css";
import UserProfile from "./UserProfile";
import CourseTable from "./Courses/CoursesTable";
const Content = ({ givenId, user, avatar }) => {
  return (
    <div className="content">
      <div className="container-fluid">
        <UserProfile
          givenId={givenId}
          user={user}
          avatar={avatar}
        ></UserProfile>
        <CourseTable message={"Upcomming courses"}></CourseTable>
      </div>
    </div>
  );
};
export default Content;
