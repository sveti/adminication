import React from "react";

import "./welcomeScreen.css";
import LogoFemale from "./../../assets/images/teacherFemale.svg";
import LogoMale from "./../../assets/images/teacherMale.svg";
import Content from "./Content";
import { Link } from "react-scroll";

const WelcomeScreen = ({ name, lastName, gender }) => {
  return (
    <main>
      <section id="WelcomeScreen" className="welcome">
        <div>
          <img
            src={gender === "MALE" ? LogoMale : LogoFemale}
            alt="logo"
            className="welcome--logo"
          />
          <p className="welcomeMessage">
            Welcome,{name} {lastName}!
          </p>

          <Link
            activeClass="active"
            to="mainContent"
            smooth={true}
            offset={-200}
            duration={800}
          >
            <button className="welcome__cta-primary">Click</button>
          </Link>
        </div>
      </section>
      <Content givenId="mainContent"></Content>
      <Content givenId="otherContent1"></Content>
      <Content givenId="otherContent2"></Content>
    </main>
  );
};

export default WelcomeScreen;
