import React from "react";
import "./navbar.css";
import Logo from "./../../assets/images/adminication.svg";
import { Link } from "react-router-dom";

export default function Navbar(props) {
  const user = props.user;
  let navBarClasses = ["navbar fixed-top topNavbar"];
  const role = user.roleName.toLowerCase().split("_")[1];
  navBarClasses.push(role);

  const teacherNav = (
    <React.Fragment>
      <li className="nav-item">
        <Link
          className="nav-link"
          to={{
            pathname: "/courses",
            state: {
              user: props.user,
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
            pathname: "/statistics",
            statisticsProps: {
              teacherId: user.id,
            },
          }}
        >
          Reports
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
              user: props.user,
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
              user: props.user,
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
              user: props.user,
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
              user: props.user,
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
              user: props.user,
            },
          }}
        >
          All Events
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
              user: props.user,
              message: "See current courses",
              path: "courses",
            },
          }}
        >
          Current courses
        </Link>
      </li>
      <li className="nav-item">
        <Link
          className="nav-link"
          to={{
            pathname: "/selectStudent",
            state: {
              user: props.user,
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
              user: props.user,
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
              user: props.user,
              message: "Sign up for events",
              path: "events/all",
            },
          }}
        >
          All events
        </Link>
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
            <li className="nav-item">
              <Link className="nav-link logoutMobile" to={"/logout/"}>
                Log out
              </Link>
            </li>
          </ul>
        </div>

        <Link className="nav-link logoutDesktop" to={"/logout/"}>
          Log out
        </Link>
      </nav>
    </header>
  );
}
