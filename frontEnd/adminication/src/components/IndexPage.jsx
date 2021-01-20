import React, { Component } from "react";
import { getUser, getUserRole } from "../services/userService";

import Navbar from "./Header/Navbar";
import WelcomeScreen from "./Header/WelcomeScreen";
import ScrollToTop from "react-scroll-up";
import LogoTeacherF from "../assets/images/teacherF.svg";
import LogoTeacherM from "../assets/images/teacherM.svg";
import LogoStudentF from "../assets/images/studentF.svg";
import LogoStudentM from "../assets/images/studentM.svg";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowAltCircleUp } from "@fortawesome/free-solid-svg-icons";

import "./indexPage.css";

import Content from "./Content";

class TeacherIndex extends Component {
  state = {
    user: {},
    id: this.props.match.params.id,
    srollClicked: false,
    isLoading: true,
  };

  async loadUser() {
    const { id } = this.state;
    const { data } = await getUser(id);
    let role = await getUserRole(id);
    data.role = role.data;
    this.setState({ user: data, isLoading: false });
  }

  async componentDidMount() {
    //change body color from the gradient
    document.body.style = "background: #fff;";
    this.loadUser();
    window.addEventListener("scroll", this.handleScroll);
  }

  componentWillUnmount() {
    window.removeEventListener("scroll", this.handleScroll);
  }

  handleScroll = () => {
    if (window.pageYOffset < 100) {
      this.setState({ srollClicked: false });
    }
  };

  handleClick = () => {
    this.setState({ srollClicked: true });
  };

  render() {
    const { user, srollClicked, isLoading } = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    } else {
      let avatar;
      if (user.role === "ROLE_TEACHER") {
        avatar = user.gender === "MALE" ? LogoTeacherM : LogoTeacherF;
      } else if (user.role === "ROLE_STUDENT") {
        avatar = user.gender === "MALE" ? LogoStudentM : LogoStudentF;
      }

      return (
        <React.Fragment>
          <div className="navContainer">
            <Navbar scrollClicked={srollClicked} role={user.role}></Navbar>
          </div>

          <WelcomeScreen
            name={user.name}
            lastName={user.lastName}
            role={user.role}
            avatar={avatar}
          ></WelcomeScreen>
          <Content givenId="mainContent" user={user} avatar={avatar}></Content>
          <ScrollToTop id="scrollToTop" showUnder={700} duration={2000}>
            <FontAwesomeIcon
              className="styledButtonUp"
              icon={faArrowAltCircleUp}
              onClick={this.handleClick}
            />
          </ScrollToTop>
        </React.Fragment>
      );
    }
  }
}

export default TeacherIndex;
