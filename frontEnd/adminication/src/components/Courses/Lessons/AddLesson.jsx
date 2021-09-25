import React, { Component } from "react";
import { saveLesson } from "../../../services/lessonService";
import { getTodaysDate } from "../../../common/helper";
import "./lessons.css";
import { toast } from "react-toastify";

class AddLesson extends Component {
  state = {
    teacherId: this.props.teacherId,
    courseId: this.props.courseId,
    lesson: {
      date: getTodaysDate(),
      description: "",
    },
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

    let { lesson } = this.state;

    if (lesson.description.length < 1) {
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
      lesson.teacherId = this.state.teacherId;
      lesson.courseId = this.state.courseId;
      this.addLesson(lesson);
    }
  };

  isEmpty = (obj) => {
    return Object.keys(obj).length === 0 && obj.constructor === Object;
  };

  handleInputChange = (event, property) => {
    let lesson = { ...this.state.lesson };
    lesson[property] = event.target.value;
    this.setState({ lesson });
  };

  render() {
    const { lesson } = this.state;

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
              value={lesson.date || ""}
              onChange={(event) => this.handleInputChange(event, "date")}
            />
          </div>
          <div className="form-group">
            <label htmlFor="description">Description</label>
            <textarea
              className="form-control"
              id="description"
              rows="3"
              value={lesson.description || ""}
              onChange={(event) => this.handleInputChange(event, "description")}
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
