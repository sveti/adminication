import React from "react";
import { Link } from "react-router-dom";
import keycloakService from "../../services/keycloakService";
import "./login.css";

const LandingPage = () => {
  return (
    <div className="container">
      <div className="outer">
        <div className="middle">
          <div className="inner text-center">
            <h1 className="mb-5">Adminication</h1>
            <h3>Bathelor project by </h3>
            <h3 className="mb-5">Svetozara Minkova F54148</h3>

            <button
              className="btn login mx-3"
              onClick={keycloakService.doLogin}
            >
              Log in
            </button>
            <Link to="/register">
              <button className="btn register"> Register</button>
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LandingPage;
