import React, { Component } from "react";
import { saveLesson } from "../../../services/lessonService";
import { getTodaysDate } from "../../../common/helper";
import "./lessons.css";
import { toast } from "react-toastify";

class AddLesson extends Component {
  state = {
    teacherId: this.props.teacherId,
    courseId: this.props.courseId,
  };

  async addLesson(lesson) {
    const response = await saveLesson(lesson).catch(function (error) {
      if (error.response) {
        // Request made and server responded
        toast.error(error.response.data.error, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else if (error.request) {
        // The request was made but no response was received
        toast.error("An error occured saving your lesson! Try again later", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else {
        // Something happened in setting up the request that triggered an Error
        toast.error(error.message, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      }
    });

    if (response && response.status === 200) {
      toast.success(response.data, {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
      this.props.onAddedLesson();
    }
  }

  onSubmit = (e) => {
    e.preventDefault();
    const date = this.date.value;
    const description = this.description.value;

    if (description.length < 1) {
      toast.error("Please enter a lesson description!", {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
    } else {
      let lesson = {};
      lesson.teacherId = this.state.teacherId;
      lesson.courseId = this.state.courseId;
      lesson.date = date;
      lesson.description = description;
      this.addLesson(lesson);
    }
  };

  isEmpty = (obj) => {
    return Object.keys(obj).length === 0 && obj.constructor === Object;
  };

  render() {
    return (
      <div className="addLessonForm">
        <h2>Add Lesson</h2>
        <form onSubmit={this.onSubmit}>
          <div className="form-group">
            <label htmlFor="date">Date</label>
            <input
              type="date"
              className="form-control"
              id="date"
              defaultValue={getTodaysDate()}
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

          <button type="submit" className="btn sumbitBtn">
            Submit
          </button>
        </form>
      </div>
    );
  }
}

export default AddLesson;
