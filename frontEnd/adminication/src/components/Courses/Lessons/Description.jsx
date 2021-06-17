import React, { Component } from "react";
import EditSaveButton from "../../../common/EditSaveButton";
import { updateLessonDescription } from "../../../services/lessonService";
import { toast } from "react-toastify";

class Description extends Component {
  state = {
    description: this.props.description,
    lessonId: this.props.lessonId,
    editable: false,
  };

  validation = () => {
    if (this.state.description.length > 0) return true;
    else {
      toast.error("Description should be longer than 0 characters!", {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
      return false;
    }
  };

  onEditClick = () => {
    this.setState({ editable: true });
  };

  onChange = (event) => {
    this.setState({ description: event.target.value });
  };

  onSaveClick = () => {
    let lesson = {
      id: this.state.lessonId,
      description: this.state.description,
    };
    updateLessonDescription(lesson).then(
      (response) => {
        if (
          response &&
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
            value={description}
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
          validation={this.validation}
        ></EditSaveButton>
      </div>
    );
  }
}

export default Description;
