import React from "react";
import "./navbar.css";
import Logo from "./../../assets/images/adminication.svg";
import { Link } from "react-router-dom";

export default function Navbar(props) {
  const user = props.user;
  let navBarClasses = ["navbar fixed-top topNavbar"];
  navBarClasses.push(user.roleName.toLowerCase().split("_")[1]);

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
          <span className="brandName">
            Adminication {user.roleName.toLowerCase().split("_")[1]}
          </span>
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
                    teacherId: 11,
                  },
                }}
              >
                Lessons
              </Link>
            </li>
            <li className="nav-item">
              <Link className="nav-link" to={"/statistics/"}>
                Statistics
              </Link>
            </li>
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
