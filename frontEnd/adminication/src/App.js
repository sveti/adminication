import React, { Component } from "react";
import { Route, Redirect, Switch, withRouter } from "react-router-dom";

import "./App.css";
import CoursesPage from "./components/Courses/CoursesPage";
import Course from "./components/Courses/Course";
import NotFound from "./components/notFound";
import IndexPage from "./components/IndexPage";
import Login from "./components/Login/Login";
import Navbar from "./components/Header/Navbar";

import ScrollToTop from "react-scroll-up";

import { getUser } from "./services/userService";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowAltCircleUp } from "@fortawesome/free-solid-svg-icons";
import { ToastContainer } from "react-toastify";
import LessonsOfCoursePage from "./components/Courses/Lessons/LessonsOfCoursePage";
import LessonsPage from "./components/Courses/Lessons/LessonsPage";

import "react-toastify/dist/ReactToastify.css";

class App extends Component {
  state = {
    user: {},
    scrollClicked: false,
    isAuthenticated: false,
  };

  async loadUser() {
    //const id = window.location.pathname.split("/")[2];
    const id = 11;
    const { data } = await getUser(id);
    this.setState({ user: data, isAuthenticated: true });
  }

  loginRequest = (event) => {
    event.preventDefault();
    this.loadUser();
    this.props.history.push("/home/11");
    this.forceUpdate();
  };

  handleScroll = () => {
    if (window.pageYOffset < 100) {
      this.setState({ scrollClicked: false });
    }
  };

  handleClick = () => {
    this.setState({ srollClicked: true });
  };

  render() {
    const { isAuthenticated, scrollClicked, user } = this.state;

    if (!isAuthenticated) {
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
            <Navbar scrollClicked={scrollClicked} user={user}></Navbar>
          </div>
          <main>
            <Switch>
              <Route
                path="/login"
                render={() => <Login loginRequest={this.loginRequest}></Login>}
              />
              <Route
                path="/home/:id"
                render={(params) => (
                  <IndexPage {...params} user={user}></IndexPage>
                )}
              />
              <Route path="/courses/:id" component={Course} />
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

              <Redirect from="/" exact to="/login" />
              <Route path="/not-found" component={NotFound} />
              <Redirect to="/not-found" />
            </Switch>

            <ScrollToTop id="scrollToTop" showUnder={700} duration={2000}>
              <FontAwesomeIcon
                className="styledButtonUp"
                icon={faArrowAltCircleUp}
                onClick={this.handleClick}
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
