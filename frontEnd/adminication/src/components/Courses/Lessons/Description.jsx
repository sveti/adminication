import React, { Component } from "react";
import EditSaveButton from "../../../common/EditSaveButton";
import { updateLessonDescription } from "../../../services/lessonService";
import { toast } from "react-toastify";

class Description extends Component {
  state = {
    description: this.props.description,
    lessonId: this.props.lessonId,
    editable: false,
    changedDescription: "",
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
          toast.success(response.data, {
            position: "top-center",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
          this.setState({
            description: changedDescription,
            editable: false,
          });
        } else {
          toast.error(response.data, {
            position: "top-center",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
        }
      },
      (error) => {
        toast.error(error.response.data.error, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      }
    );
  };

  render() {
    const { description, editable } = this.state;

    let descriptionBox = <p>{description}</p>;

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
      </div>
    );
  }
}

export default Description;
