import React, { Component } from "react";
import "./userProfile.css";
import EditSaveButton from "../common/EditSaveButton";
import {
  checkIfEmailExists,
  checkIfUsernameExists,
} from "../services/userService";

import { toast } from "react-toastify";

class UserProfile extends Component {
  state = {
    givenId: this.props.givenId,
    user: this.props.user,
    avatar: this.props.avatar,
    originalUsername: this.props.user.username,
    originalEmail: this.props.user.email,
    editMode: false,
    diasbleButton: false,
  };

  validation = async () => {
    const { user, originalUsername, originalEmail } = this.state;
    //change of email
    if (user.email !== originalEmail) {
      const { data } = await checkIfEmailExists(user.email);
      if (data === true) {
        toast.error("Email aready taken!", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        return false;
      }
    }
    //change of username
    if (user.username !== originalUsername) {
      const { data } = await checkIfUsernameExists(user.username);
      if (data === true) {
        toast.error("Username aready taken!", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        return false;
      }
    }

    return true;
  };

  handleEmailChange = (event, property) => {
    let user = { ...this.state.user };
    user[property] = event.target.value;
    this.setState({ user, diasbleButton: false });
  };

  editProfile = () => {
    this.setState({ editMode: true, diasbleButton: true });
  };

  saveProfile = () => {
    this.setState({ editMode: false });
  };

  render() {
    const { givenId, user, avatar, editMode, diasbleButton } = this.state;

    const displayTable = (
      <div className="col-sm-12 col-lg-9 textSection">
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
        </div>{" "}
      </div>
    );

    const form = (
      <div className="col-sm-12 col-lg-9 textSection">
        <div className="info-group row">
          <div className="col-sm-12 col-md-3">
            <p className="identity">Username:</p>
          </div>
          <div className="col-sm-12 col-md-9">
            <input
              type="text"
              className="form-control editedInformaton"
              value={user.username}
              onChange={(event) => this.handleEmailChange(event, "username")}
            ></input>
          </div>
        </div>
        <div className="info-group row">
          <div className="col-sm-12 col-md-3">
            <p className="identity">Name:</p>
          </div>
          <div className="col-sm-12 col-md-3">
            <input
              type="text"
              className="form-control editedInformaton"
              value={user.name}
              onChange={(event) => this.handleEmailChange(event, "name")}
            ></input>
          </div>
          <div className="col-sm-12 col-md-3">
            <p className="identity">Last Name:</p>
          </div>
          <div className="col-sm-12 col-md-3">
            <input
              type="text"
              className="form-control editedInformaton"
              value={user.lastName}
              onChange={(event) => this.handleEmailChange(event, "lastName")}
            ></input>
          </div>
        </div>
        <div className="info-group row">
          <div className="col-sm-12 col-md-3">
            <p className="identity">Email:</p>
          </div>
          <div className="col-sm-12 col-md-9">
            <input
              type="email"
              className="form-control editedInformaton"
              value={user.email}
              onChange={(event) => this.handleEmailChange(event, "email")}
            ></input>
          </div>
        </div>
        <hr></hr>
        <div className="info-group row">
          <div className="col-sm-12 col-md-3">
            <p className="identity">Old Password:</p>
          </div>
          <div className="col-sm-12 col-md-9">
            <input
              type="password"
              className="form-control editedInformaton"
            ></input>
          </div>
        </div>
        <div className="info-group row">
          <div className="col-sm-12 col-md-3">
            <p className="identity">New Password:</p>
          </div>
          <div className="col-sm-12 col-md-9">
            <input
              type="password"
              className="form-control editedInformaton"
            ></input>
          </div>
        </div>
        <div className="info-group row">
          <div className="col-sm-12 col-md-3">
            <p className="identity">Repeat New Password:</p>
          </div>
          <div className="col-sm-12 col-md-9">
            <input
              type="password"
              className="form-control editedInformaton"
            ></input>
          </div>
        </div>
      </div>
    );

    return (
      <div id={givenId} className="row wrapper mx-2">
        <div className="col-sm-12 col-lg-3">
          <img src={avatar} className="img-fluid userImage" alt="User Avatar" />
        </div>
        {editMode ? form : displayTable}
        <div className="editButtonContainer">
          <EditSaveButton
            onEdit={this.editProfile}
            onSave={this.saveProfile}
            disabled={diasbleButton}
            validation={this.validation}
          ></EditSaveButton>
        </div>
      </div>
    );
  }
}

export default UserProfile;
