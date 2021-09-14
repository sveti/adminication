import React, { Component } from "react";
import "./navbar.css";
import Logo from "./../../assets/images/adminication.svg";
import keycloakService from "../../services/keycloakService";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEnvelope } from "@fortawesome/free-solid-svg-icons";

class Navbar extends Component {
  constructor(props) {
    super(props);
    this.state = {
      user: this.props.user,
      navBarClasses: ["navbar fixed-top topNavbar"],
      role: this.props.user.roleName.toLowerCase().split("_")[1],
    };
    this.state.navBarClasses.push(this.state.role);
  }
  render() {
    const { user, navBarClasses, role } = this.state;
    const teacherNav = (
      <React.Fragment>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/courses",
              state: {
                user: user,
              },
            }}
          >
            My courses
          </Link>
        </li>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/lessons",
              lessonProps: {
                teacherId: user.id,
              },
            }}
          >
            Lessons
          </Link>
        </li>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/grading",
              gradingProps: {
                teacherId: user.id,
              },
            }}
          >
            Grading
          </Link>
        </li>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/reports",
              statisticsProps: {
                teacherId: user.id,
              },
            }}
          >
            Reports
          </Link>
        </li>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/notifications",
              data: { decrease: this.updateNotificationCount },
            }}
          >
            <FontAwesomeIcon icon={faEnvelope} className="mailIcon" />
            {this.props.notificationsCount > 0 ? (
              <span className="badge">{this.props.notificationsCount}</span>
            ) : null}
          </Link>
        </li>
      </React.Fragment>
    );

    const studentNav = (
      <React.Fragment>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/courses",
              state: {
                user: user,
              },
            }}
          >
            My courses
          </Link>
        </li>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/grades",
              state: {
                user: user,
              },
            }}
          >
            Grades
          </Link>
        </li>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/events",
              state: {
                user: user,
              },
            }}
          >
            My events
          </Link>
        </li>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/courses/all",
              state: {
                user: user,
              },
            }}
          >
            All courses
          </Link>
        </li>

        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/events/all",
              state: {
                user: user,
              },
            }}
          >
            All Events
          </Link>
        </li>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/notifications",
              data: { decrease: this.updateNotificationCount },
            }}
          >
            <FontAwesomeIcon icon={faEnvelope} className="mailIcon" />
            {this.props.notificationsCount > 0 ? (
              <span className="badge">{this.props.notificationsCount}</span>
            ) : null}
          </Link>
        </li>
      </React.Fragment>
    );

    const parentNav = (
      <React.Fragment>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/selectStudent",
              state: {
                user: user,
                message: "See current courses",
                path: "courses",
              },
            }}
          >
            Courses
          </Link>
        </li>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/selectStudent",
              state: {
                user: user,
                message: "See current events",
                path: "events",
              },
            }}
          >
            Events
          </Link>
        </li>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/selectStudent",
              state: {
                user: user,
                message: "See grades",
                path: "grades",
              },
            }}
          >
            Grading
          </Link>
        </li>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/selectStudent",
              state: {
                user: user,
                message: "Sign up for courses",
                path: "courses/all",
              },
            }}
          >
            All courses
          </Link>
        </li>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/selectStudent",
              state: {
                user: user,
                message: "Sign up for events",
                path: "events/all",
              },
            }}
          >
            All events
          </Link>
        </li>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/reports",
            }}
          >
            Reports
          </Link>
        </li>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/notifications",
            }}
          >
            <FontAwesomeIcon icon={faEnvelope} className="mailIcon" />
            {this.props.notificationsCount > 0 ? (
              <span className="badge">{this.props.notificationsCount}</span>
            ) : null}
          </Link>
        </li>
      </React.Fragment>
    );

    const adminNav = (
      <React.Fragment>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/courses/all",
            }}
          >
            Courses
          </Link>
        </li>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/events/all",
            }}
          >
            Events
          </Link>
        </li>
        <li className="nav-item">
          <Link
            className="nav-link"
            to={{
              pathname: "/teachers/all",
            }}
          >
            Teachers
          </Link>
        </li>
        <li className="nav-item dropdown">
          <div className="dropdown">
            <button
              className="btn dropdown-toggle nav-link"
              type="button"
              id="dropdownMenuButton"
              data-toggle="dropdown"
              aria-haspopup="true"
            >
              Reports
            </button>
            <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
              <Link
                className="nav-link nested"
                to={{
                  pathname: "/reports/teachers",
                }}
              >
                Teachers
              </Link>
              <Link
                className="nav-link nested"
                to={{
                  pathname: "/reports/students",
                }}
              >
                Students
              </Link>
              <Link
                className="nav-link nested"
                to={{
                  pathname: "/reports/parents",
                }}
              >
                Parents
              </Link>
              <Link
                className="nav-link nested"
                to={{
                  pathname: "/reports/courses",
                }}
              >
                Courses
              </Link>
              <Link
                className="nav-link nested"
                to={{
                  pathname: "/reports/events",
                }}
              >
                Events
              </Link>
            </div>
          </div>
        </li>
      </React.Fragment>
    );

    return (
      <header className={navBarClasses.join(" ")} id="mainMenu">
        <nav className="navbar navbar-expand-lg">
          <Link className="navbar-brand" to={"/home"}>
            <img
              src={Logo}
              width="30"
              height="30"
              className="d-inline-block align-top logo rotate"
              alt=""
              loading="lazy"
            />
            <span className="brandName">Adminication {role}</span>
          </Link>
          <button
            className="navbar-toggler collapsed position-relative"
            type="button"
            data-toggle="collapse"
            data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span> </span>
            <span> </span>
            <span> </span>
          </button>

          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav mr-auto">
              <li className="nav-item active">
                <Link className="nav-link" to={"/home"}>
                  Home<span className="sr-only">(current)</span>
                </Link>
              </li>
              {role === "teacher" ? teacherNav : null}
              {role === "student" ? studentNav : null}
              {role === "parent" ? parentNav : null}
              {role === "admin" ? adminNav : null}
              <li className="nav-item">
                <button
                  className="btn nav-link logoutMobile"
                  onClick={keycloakService.doLogout}
                >
                  Log out
                </button>
              </li>
            </ul>
          </div>

          <button
            className="btn nav-link logoutDesktop"
            onClick={keycloakService.doLogout}
          >
            Log out
          </button>
        </nav>
      </header>
    );
  }
}

export default Navbar;
