import React from "react";
import "./userProfile.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit } from "@fortawesome/free-solid-svg-icons";
const UserProfile = ({ givenId, user, avatar }) => {
  return (
    <div id={givenId} className="row wrapper mx-2">
      <div className="col-sm-12 col-lg-6">
        <img src={avatar} className="img-fluid userImage" alt="User Avatar" />
      </div>

      <div className="col-sm-12 col-lg-6 textSection">
        <div className="info-group">
          <p className="identity">Username:</p>
          <p className="userData">{user.username}</p>
        </div>
        <div className="info-group">
          <p className="identity">Name:</p>
          <p className="userData">{user.name + " " + user.lastName}</p>
        </div>
        <div className="info-group">
          <p className="identity">Email:</p>
          <p className="userData">{user.email}</p>
        </div>

        <button className="editButton">
          <FontAwesomeIcon icon={faEdit} className="editIcon" />
          Edit
        </button>
      </div>
    </div>
  );
};
export default UserProfile;
