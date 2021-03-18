import React, { Component } from "react";
import { withRouter } from "react-router-dom";

class BackButton extends Component {
  render() {
    return (
      <button
        className="editButton backButton"
        onClick={this.props.history.goBack}
      >
        Back
      </button>
    );
  }
}
export default withRouter(BackButton);
