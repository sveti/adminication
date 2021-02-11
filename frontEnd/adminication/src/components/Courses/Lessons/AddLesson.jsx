import React, { Component } from "react";
import { saveLesson } from "../../../services/lessonService";
import "./lessons.css";

class AddLesson extends Component {
  state = {
    teacherId: this.props.teacherId,
    courseId: this.props.courseId,
    error: {},
    success: null,
  };

  async addLesson(lesson) {
    let that = this;

    const response = await saveLesson(lesson).catch(function (error) {
      if (error.response) {
        // Request made and server responded
        // console.log(error.response.data);
        // console.log(error.response.status);
        // console.log(error.response.headers);
        that.setState({ error: error.response.data.error, success: "" });
      } else if (error.request) {
        // The request was made but no response was received
        // console.log(error.request);
        that.setState({
          error: "An error occured saving your lesson! Try again later",
          success: "",
        });
      } else {
        // Something happened in setting up the request that triggered an Error
        // console.log("Error", error.message);
        that.setState({ success: "", error: error.message });
      }
    });

    if (response.status === 200) {
      this.setState({ error: "", success: response.data });
      this.props.onAddedLesson();
    }
  }

  onSubmit = (e) => {
    e.preventDefault();
    const date = this.date.value;
    const description = this.description.value;

    if (description.length < 1) {
      this.setState({ error: "Please enter a lesson description!" });
    } else {
      let lesson = {};
      lesson.teacherId = this.state.teacherId;
      lesson.courseId = this.state.courseId;
      lesson.date = date;
      lesson.description = description;
      this.addLesson(lesson);
    }
  };

  getTodaysDate = () => {
    let curr = new Date();
    curr.setDate(curr.getDate());
    return curr.toISOString().substr(0, 10);
  };

  isEmpty = (obj) => {
    return Object.keys(obj).length === 0 && obj.constructor === Object;
  };

  render() {
    const { error, success } = this.state;

    let errorMessage = null;
    let successMessage = null;
    if (!this.isEmpty(error)) {
      errorMessage = (
        <div className="alert alert-danger mt-3 errorMessage" role="alert">
          {error}
        </div>
      );
    }
    if (success) {
      successMessage = (
        <div className="alert alert-success mt-3 successMessage" role="alert">
          {success}
        </div>
      );
    }

    return (
      <div className="addLessonForm">
        {errorMessage}
        {successMessage}
        <h2>Add Lesson</h2>
        <form onSubmit={this.onSubmit}>
          <div className="form-group">
            <label htmlFor="date">Date</label>
            <input
              type="date"
              className="form-control"
              id="date"
              defaultValue={this.getTodaysDate()}
              ref={(c) => (this.date = c)}
            />
          </div>
          <div className="form-group">
            <label htmlFor="description">Description</label>
            <textarea
              className="form-control"
              id="description"
              rows="3"
              ref={(c) => (this.description = c)}
            ></textarea>
          </div>

          <button type="submit" className="btn btn-primary">
            Submit
          </button>
        </form>
      </div>
    );
  }
}

export default AddLesson;
