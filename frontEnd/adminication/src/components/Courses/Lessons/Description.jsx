import React, { Component } from "react";
import EditSaveButton from "../../../common/EditSaveButton";
import { updateLessonDescription } from "../../../services/lessonService";

class Description extends Component {
  state = {
    description: this.props.description,
    lessonId: this.props.lessonId,
    editable: false,
    changedDescription: "",
    successMessage: "",
    errorMessage: "",
  };

  onEditClick = () => {
    this.setState({ editable: true });
  };

  onChange = (event) => {
    this.setState({ changedDescription: event.target.value });
  };

  onSaveClick = () => {
    const { changedDescription } = this.state;
    let lesson = {
      id: this.state.lessonId,
      description: this.state.changedDescription,
    };
    updateLessonDescription(lesson).then(
      (response) => {
        if (
          response.status === 200 &&
          response.data !== "An error has occured!"
        ) {
          this.setState({
            successMessage: response.data,
            errorMessage: "",
            description: changedDescription,
            editable: false,
          });
        } else {
          this.setState({
            successMessage: "",
            errorMessage: response.data,
          });
        }
      },
      (error) => {
        this.setState({
          successMessage: "",
          errorMessage: error.response.data.error,
        });
      }
    );
  };

  render() {
    const { description, editable, successMessage, errorMessage } = this.state;

    // console.log(this.props.active);

    let descriptionBox = <p>{description}</p>;
    let updateDiv,
      errorDiv = null;

    if (successMessage.length > 0) {
      updateDiv = (
        <div className="alert alert-success mt-3" role="alert">
          {successMessage}
        </div>
      );
    }
    if (errorMessage.length > 0) {
      errorDiv = (
        <div className="alert alert-danger mt-3" role="alert">
          {errorMessage}
        </div>
      );
    }

    if (editable) {
      descriptionBox = (
        <div className="form-group">
          <textarea
            className="form-control"
            id="exampleFormControlTextarea1"
            rows="3"
            defaultValue={description}
            onChange={this.onChange}
          ></textarea>
        </div>
      );
    }

    return (
      <div>
        <div className="lessonDescription">{descriptionBox}</div>
        <EditSaveButton
          onEdit={this.onEditClick}
          onSave={this.onSaveClick}
        ></EditSaveButton>
        {updateDiv}
        {errorDiv}
      </div>
    );
  }
}

export default Description;
