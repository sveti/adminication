import React, { Component } from "react";

import WelcomeScreen from "../Header/WelcomeScreen";

import LogoTeacherF from "../../assets/images/teacherF.svg";
import LogoTeacherM from "../../assets/images/teacherM.svg";
import LogoStudentF from "../../assets/images/studentF.svg";
import LogoStudentM from "../../assets/images/studentM.svg";
import LogoParentM from "../../assets/images/parentM.svg";
import LogoParentF from "../../assets/images/parentF.svg";

import "./indexPage.css";

import Content from "./Content";

class TeacherIndex extends Component {
  state = {
    user: this.props.user,
  };

  editedUser = (user) => {
    ///update the user here aswell so that a name change can be visible
    this.setState({ user });
    this.props.editedUser(user);
  };

  async componentDidMount() {
    //change body color from the gradient of login screen
    document.body.style = "background: #fff;";
  }

  render() {
    const { user } = this.state;

    let avatar;
    if (user.roleName === "ROLE_TEACHER") {
      avatar = user.gender === "MALE" ? LogoTeacherM : LogoTeacherF;
    } else if (user.roleName === "ROLE_STUDENT") {
      avatar = user.gender === "MALE" ? LogoStudentM : LogoStudentF;
    } else if (user.roleName === "ROLE_PARENT") {
      avatar = user.gender === "MALE" ? LogoParentM : LogoParentF;
    }

    return (
      <React.Fragment>
        <WelcomeScreen
          name={user.name}
          lastName={user.lastName}
          roleName={user.roleName}
          avatar={avatar}
        ></WelcomeScreen>
        <Content
          givenId="mainContent"
          user={user}
          avatar={avatar}
          editedUser={(user) => this.editedUser(user)}
        ></Content>
      </React.Fragment>
    );
  }
}

export default TeacherIndex;
