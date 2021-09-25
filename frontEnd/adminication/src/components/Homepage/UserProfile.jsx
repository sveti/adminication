import React, { Component } from "react";
import "./userProfile.css";
import EditSaveButton from "../../common/EditSaveButton";
import {
  checkIfEmailExists,
  checkIfUsernameExists,
  updateUser,
} from "../../services/userService";

import { checkCredentials } from "../../services/authenticationService";

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
    password: {
      old: "",
      newPassword: "",
      repeatPassword: "",
    },
  };

  validation = async () => {
    const { user, originalUsername, originalEmail, password } = this.state;
    //name or lastName are empty
    if (user.name.length === 0 || user.lastName.length === 0) {
      toast.error("Please fill in your name!", {
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
    ///password change
    if (password.old !== "") {
      let credentialsCheckUser = {
        username: originalUsername,
        password: password.old,
      };
      const { data } = await checkCredentials(credentialsCheckUser);
      if (data === false) {
        toast.error("Incorrect password!", {
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
      if (password.newPassword.length < 5) {
        toast.error("Password must be at least 5 characters long!", {
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
      if (password.newPassword !== password.repeatPassword) {
        toast.error("Password wasn't repeated correctly!", {
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

  handlePasswordChange = (event, property) => {
    let pass = { ...this.state.password };
    pass[property] = event.target.value;
    this.setState({ password: pass, diasbleButton: false });
  };

  handleInputChange = (event, property) => {
    let user = { ...this.state.user };
    user[property] = event.target.value;
    ///the username cannot be shorter than 5 characters
    if (property === "username" && event.target.value.length < 5) {
      this.setState({ user, diasbleButton: true });
    } else {
      this.setState({ user, diasbleButton: false });
    }
  };

  editProfile = () => {
    this.setState({ editMode: true, diasbleButton: true });
  };

  saveProfile = async () => {
    let { user, password } = this.state;

    ///if the user has a new pass mark it in the object
    if (password.newPassword.length > 0) {
      user.password = password.newPassword;
      user.changedPassword = true;
    } else {
      user.changedPassword = false;
    }

    user.originalUsername = this.state.originalUsername;
    console.log(user);
    const { data } = await updateUser(user);
    if (data) {
      toast.success("Update successfull!", {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
      this.props.editedUser(user);
      this.setState({ editMode: false });
    } else {
      toast.error("An error has occured", {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
    }
  };

  render() {
    const { givenId, user, avatar, editMode, diasbleButton, password } =
      this.state;

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
            <p className="identity">Username: (min 5 symbols)</p>
          </div>
          <div className="col-sm-12 col-md-9">
            <input
              type="text"
              className="form-control editedInformaton"
              value={user.username}
              onChange={(event) => this.handleInputChange(event, "username")}
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
              onChange={(event) => this.handleInputChange(event, "name")}
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
              onChange={(event) => this.handleInputChange(event, "lastName")}
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
              onChange={(event) => this.handleInputChange(event, "email")}
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
              value={password.old}
              onChange={(event) => this.handlePasswordChange(event, "old")}
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
              value={password.newPassword}
              onChange={(event) =>
                this.handlePasswordChange(event, "newPassword")
              }
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
              value={password.repeatPassword}
              onChange={(event) =>
                this.handlePasswordChange(event, "repeatPassword")
              }
            ></input>
          </div>
        </div>
      </div>
    );

    return (
      <div id={givenId} className="row wrapper mx-2 mb-5">
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
