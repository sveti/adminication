import React from "react";

import "./welcomeScreen.css";

import { Link } from "react-scroll";

const WelcomeScreen = ({ name, lastName, roleName, avatar }) => {
  return (
    <div>
      <section
        id="WelcomeScreen"
        className={"welcome " + roleName.toLowerCase().split("_")[1]}
      >
        <div>
          <img alt="logo" className="welcomeLogo" src={avatar} />
          <p className="welcomeMessage">
            Welcome,{name} {lastName}!
          </p>
          <Link
            activeClass="active"
            to="mainContent"
            smooth={true}
            offset={-150}
            duration={800}
          >
            <button className="welcomeButton">Click</button>
          </Link>
        </div>
      </section>
    </div>
  );
};

export default WelcomeScreen;
