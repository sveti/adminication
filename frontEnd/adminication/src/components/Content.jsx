import React from "react";
import "./content.css";
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
    </div>
  );
};
export default Content;
