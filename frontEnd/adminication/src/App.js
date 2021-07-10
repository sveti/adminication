import { faArrowAltCircleUp } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import ScrollToTop from "react-scroll-up";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./App.css";
import Navbar from "./components/Header/Navbar";
import Login from "./components/Login/Login";
import StudentSwitch from "./components/Switches/StudentSwitch";
import TeacherSwitch from "./components/Switches/TeacherSwitch";
import ParentSwitch from "./components/Switches/ParentSwitch";
import { getUser } from "./services/userService";

class App extends Component {
  state = {
    user: null,
    notificationsCount: 0,
  };

  increaseNotificationsCount = () => {
    let { notificationsCount } = this.state;
    notificationsCount++;
    this.setState({ notificationsCount });
  };
  decreaseNotificationsCount = () => {
    let { notificationsCount } = this.state;
    notificationsCount--;
    this.setState({ notificationsCount });
  };

  async loadUser(username) {
    const { data } = await getUser(username);
    this.setState({
      user: data,
      notificationsCount: data.notifications.length,
    });
  }

  loginRequest = async (username) => {
    await this.loadUser(username);
    this.props.history.push("/home");
  };

  render() {
    const { user, notificationsCount } = this.state;

    let routing = null;

    if (user) {
      switch (user.roleName) {
        case "ROLE_TEACHER":
          routing = (
            <TeacherSwitch
              user={user}
              increase={this.increaseNotificationsCount}
              decrease={this.decreaseNotificationsCount}
            ></TeacherSwitch>
          );
          break;
        case "ROLE_STUDENT":
          routing = (
            <StudentSwitch
              user={user}
              increase={this.increaseNotificationsCount}
              decrease={this.decreaseNotificationsCount}
            ></StudentSwitch>
          );
          break;
        case "ROLE_PARENT":
          routing = (
            <ParentSwitch
              user={user}
              increase={this.increaseNotificationsCount}
              decrease={this.decreaseNotificationsCount}
            ></ParentSwitch>
          );
          break;
        default:
          routing = null;
      }
    }

    if (!user) {
      if (window.location.pathname !== "/login") {
        window.location = "/login";
      }
      return (
        <div className="App">
          <main>
            <Login loginRequest={this.loginRequest}></Login>
          </main>
        </div>
      );
    } else {
      return (
        <div className="App">
          <div className="navContainer">
            <Navbar
              user={user}
              notificationsCount={notificationsCount}
            ></Navbar>
          </div>

          <main>
            {routing}
            <ScrollToTop id="scrollToTop" showUnder={700} duration={2000}>
              <FontAwesomeIcon
                className="styledButtonUp"
                icon={faArrowAltCircleUp}
              />
            </ScrollToTop>
            <ToastContainer
              position="top-center"
              autoClose={5000}
              hideProgressBar={false}
              newestOnTop
              closeOnClick
              rtl={false}
              pauseOnFocusLoss
              draggable
              pauseOnHover
            />
          </main>
        </div>
      );
    }
  }
}

export default withRouter(App);
