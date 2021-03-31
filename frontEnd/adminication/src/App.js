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
import { getUser } from "./services/userService";

class App extends Component {
  state = {
    user: null,
  };

  async loadUser(username) {
    const { data } = await getUser(username);
    this.setState({ user: data });
  }

  loginRequest = async (username) => {
    await this.loadUser(username);
    this.props.history.push("/home");
  };

  render() {
    const { user } = this.state;

    let routing = null;

    if (user) {
      switch (user.roleName) {
        case "ROLE_TEACHER":
          routing = <TeacherSwitch user={user}></TeacherSwitch>;
          break;
        case "ROLE_STUDENT":
          routing = <StudentSwitch user={user}></StudentSwitch>;
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
            <Navbar user={user}></Navbar>
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
