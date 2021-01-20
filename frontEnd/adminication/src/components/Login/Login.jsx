import React, { Component } from "react";
import "./login.css";
import Logo from "../../logo.svg";

export default class Login extends Component {
  handleSumbit = (event) => {
    event.preventDefault();
    this.props.loginRequest(event);
  };

  render() {
    return (
      <div className="container login">
        <div className="row">
          <div className="col-12">
            <div className="outer">
              <div className="inner">
                <form onSubmit={this.handleSumbit}>
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
                  <p className="forgot-password text-right">
                    Forgot <a href="#">password?</a>
                  </p>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
