import React from "react";
import "./content.css";
import LessonsPage from "./Courses/Lessons/LessonsPage";
import UserProfile from "./UserProfile";

const Content = ({ givenId, user, avatar }) => {
  return (
    <div className="content">
      <div className="container-fluid">
        <UserProfile
          givenId={givenId}
          user={user}
          avatar={avatar}
        ></UserProfile>
      </div>
      <LessonsPage teacherId={user.id}></LessonsPage>
    </div>
  );
};
export default Content;
