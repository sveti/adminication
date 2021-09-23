import { faArrowAltCircleUp } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import React, { Component } from "react";
import { withRouter } from "react-router-dom";
import Loader from "react-loader-spinner";
import ScrollToTop from "react-scroll-up";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./App.css";
import Navbar from "./components/Header/Navbar";
import StudentSwitch from "./components/Switches/StudentSwitch";
import TeacherSwitch from "./components/Switches/TeacherSwitch";
import ParentSwitch from "./components/Switches/ParentSwitch";
import AdminSwitch from "./components/Switches/AdminSwitch";
import AuthenticatedUser from "./components/Login/AuthenticatedUser";
import NonAuthenticatedUser from "./components/Login/NonAuthenticatedUser";
import { getUser } from "./services/userService";
import keycloakService from "./services/keycloakService";
import GeneralSwich from "./components/Switches/GeneralSwich";

class App extends Component {
  state = {
    user: null,
    notificationsCount: 0,
  };

  componentDidMount = async () => {
    if (keycloakService.isLoggedIn()) {
      await this.loadUser(keycloakService.getUsername());
      this.props.history.push("/home");
    }
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

  render() {
    const { user, notificationsCount } = this.state;

    let routing = <GeneralSwich></GeneralSwich>;

    if (user) {
      if (keycloakService.hasRole(["TEACHER"])) {
        routing = (
          <TeacherSwitch
            user={user}
            increase={this.increaseNotificationsCount}
            decrease={this.decreaseNotificationsCount}
          ></TeacherSwitch>
        );
      } else if (keycloakService.hasRole(["STUDENT"])) {
        routing = (
          <StudentSwitch
            user={user}
            increase={this.increaseNotificationsCount}
            decrease={this.decreaseNotificationsCount}
          ></StudentSwitch>
        );
      } else if (keycloakService.hasRole(["PARENT"])) {
        routing = (
          <ParentSwitch
            user={user}
            increase={this.increaseNotificationsCount}
            decrease={this.decreaseNotificationsCount}
          ></ParentSwitch>
        );
      } else if (keycloakService.hasRole(["ADMIN"])) {
        routing = (
          <AdminSwitch
            user={user}
            increase={this.increaseNotificationsCount}
            decrease={this.decreaseNotificationsCount}
          ></AdminSwitch>
        );
      }
    }
    return (
      <React.Fragment>
        <AuthenticatedUser>
          {this.state.user ? (
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
          ) : (
            <div className="center">
              <Loader
                type="Oval"
                color="#00BFFF"
                height={80}
                width={80}
              ></Loader>
            </div>
          )}
        </AuthenticatedUser>

        <NonAuthenticatedUser>
          {routing}
          {/* <LandingPage></LandingPage> */}
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
        </NonAuthenticatedUser>
      </React.Fragment>
    );
  }
}

export default withRouter(App);
