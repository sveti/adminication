import React, { useState } from "react";
import "./login.css";
import Logo from "../../logo.svg";
import { login } from "../../services/authenticationService";

export default function Login(props) {
  const [errorMessage, setErrormessage] = useState("");

  function validate(event) {
    event.preventDefault();

    if (event.target.username.value.length === 0) {
      setErrormessage("Please enter your username!");
    } else if (event.target.password.value.length === 0) {
      setErrormessage("Please enter your password!");
    } else {
      handleSumbit(event);
    }
  }

  async function handleSumbit(event) {
    event.preventDefault();

    try {
      await login(event.target.username.value, event.target.password.value);
      props.loginRequest(event.target.username.value);
    } catch (ex) {
      if (ex.response && ex.response.data.status === 401) {
        setErrormessage("Invalid username or password!");
      } else if (ex.response) {
        setErrormessage(ex.response.data.status);
      } else {
        setErrormessage("An error has occured! Try again later");
      }
    }
  }

  let error = null;

  if (errorMessage !== "") {
    error = (
      <div className="alert alert-danger mt-3" role="alert">
        {errorMessage}
      </div>
    );
  }

  //change body color from the gradient
  document.body.style =
    "background: linear-gradient(to right, #05cdff, #d2c04e, #e74c3c)";

  return (
    <div className="container ">
      <div className="row">
        <div className="col-12">
          <div className="outer">
            <div className="inner">
              <form onSubmit={validate}>
                <div className="logoContainer">
                  <img src={Logo} alt="logo" className="img-fluid logo" />
                </div>
                <div className="form-group">
                  <label>Username</label>
                  <input
                    name="username"
                    type="text"
                    className="form-control"
                    placeholder="Enter username"
                  />
                </div>

                <div className="form-group">
                  <label>Password</label>
                  <input
                    name="password"
                    type="password"
                    className="form-control"
                    placeholder="Enter password"
                  />
                </div>

                <button
                  type="submit"
                  className="btn btn-lg btn-block loginButton"
                >
                  Sign in
                </button>
                {error}
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
