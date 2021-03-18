import React, { Component } from "react";
import { Route, Redirect, Switch, withRouter } from "react-router-dom";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowAltCircleUp } from "@fortawesome/free-solid-svg-icons";
import { ToastContainer } from "react-toastify";
import ScrollToTop from "react-scroll-up";

import CoursesPage from "./components/Courses/CoursesPage";
import Course from "./components/Courses/Course";
import NotFound from "./components/notFound";
import IndexPage from "./components/IndexPage";
import Login from "./components/Login/Login";
import Logout from "./components/Login/Logout";
import Navbar from "./components/Header/Navbar";
import LessonsOfCoursePage from "./components/Courses/Lessons/LessonsOfCoursePage";
import LessonsPage from "./components/Courses/Lessons/LessonsPage";

import { getUser } from "./services/userService";
import { getCurrentUser } from "./services/authenticationService";

import "react-toastify/dist/ReactToastify.css";
import "./App.css";
import Grading from "./components/Courses/Grading/Grading";
import GradingTable from "./components/Courses/Grading/GradingTable";

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

  checkIfAuthenticated = () => {
    const user = getCurrentUser();
    this.setState({ user });
  };

  render() {
    const { user } = this.state;

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
            <Switch>
              <Route
                path="/login"
                render={() => <Login loginRequest={this.loginRequest}></Login>}
              />
              <Route path="/logout" component={Logout} />
              <Route
                path="/home"
                render={(params) => (
                  <IndexPage {...params} user={user}></IndexPage>
                )}
              />
              <Route path="/courses/:courseId" component={Course} />
              <Route
                path="/courses"
                render={() => (
                  <CoursesPage id={this.state.user.id}></CoursesPage>
                )}
              />
              <Route
                path="/lessons/:courseId"
                render={(params) => (
                  <LessonsOfCoursePage {...params}></LessonsOfCoursePage>
                )}
              />
              <Route
                path="/lessons"
                render={(params) => <LessonsPage {...params}></LessonsPage>}
              />

              <Route
                path="/grading/:courseId"
                render={(params) => <GradingTable {...params}></GradingTable>}
              />
              <Route
                path="/grading"
                render={(params) => (
                  <Grading teacherId={user.id} {...params}></Grading>
                )}
              />

              <Redirect from="/" exact to="/login" />
              <Route path="/not-found" component={NotFound} />
              <Redirect to="/not-found" />
            </Switch>

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
