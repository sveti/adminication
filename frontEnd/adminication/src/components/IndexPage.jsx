import React, { Component } from "react";

import WelcomeScreen from "./Header/WelcomeScreen";

import LogoTeacherF from "../assets/images/teacherF.svg";
import LogoTeacherM from "../assets/images/teacherM.svg";
import LogoStudentF from "../assets/images/studentF.svg";
import LogoStudentM from "../assets/images/studentM.svg";

import "./indexPage.css";

import Content from "./Content";

class TeacherIndex extends Component {
  state = {
    user: this.props.user,
    id: this.props.match.params.id,
  };

  async componentDidMount() {
    //change body color from the gradient of login screen
    document.body.style = "background: #fff;";
    window.addEventListener("scroll", this.handleScroll);
  }

  componentWillUnmount() {
    window.removeEventListener("scroll", this.handleScroll);
  }

  render() {
    const { user } = this.state;

    let avatar;
    if (user.roleName === "ROLE_TEACHER") {
      avatar = user.gender === "MALE" ? LogoTeacherM : LogoTeacherF;
    } else if (user.roleName === "ROLE_STUDENT") {
      avatar = user.gender === "MALE" ? LogoStudentM : LogoStudentF;
    }

    return (
      <React.Fragment>
        <WelcomeScreen
          name={user.name}
          lastName={user.lastName}
          roleName={user.roleName}
          avatar={avatar}
        ></WelcomeScreen>
        <Content givenId="mainContent" user={user} avatar={avatar}></Content>
      </React.Fragment>
    );
  }
}

export default TeacherIndex;
