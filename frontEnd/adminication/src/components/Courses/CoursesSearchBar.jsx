import React, { Component } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBan, faFilter, faSearch } from "@fortawesome/free-solid-svg-icons";
import "./course.css";

class CoursesSearchBar extends Component {
  state = {
    activeLevels: [],
    activeCourseDetails: [],
    title: "",
    levels: ["A1", "A2", "B1", "B2", "C1", "C2"],
    avialable: false,
    startDate: "",
    endDate: "",
  };

  removeFilters = () => {
    this.setState({
      activeLevels: [],
      activeCourseDetails: [],
      title: "",
      avialable: false,
      startDate: "",
      endDate: "",
    });
    this.props.handleSubmit([], [], "", false, "", "");
  };

  onSubmit = (event) => {
    event.preventDefault();
    this.props.handleSubmit(
      this.state.activeLevels,
      this.state.activeCourseDetails,
      this.state.title,
      this.state.avialable,
      this.state.startDate,
      this.state.endDate
    );
  };

  onEndDateSelect = (event) => {
    this.setState({ endDate: event.target.value });
  };

  onStartDateSelect = (event) => {
    this.setState({ startDate: event.target.value });
  };

  onTitleChange = (event) => {
    this.setState({ title: event.target.value });
  };

  onLevelSelect = (level) => {
    let { activeLevels } = this.state;
    let index = activeLevels.indexOf(level);
    if (index === -1) {
      activeLevels.push(level);
    } else {
      activeLevels.splice(index, 1);
    }
    this.setState(activeLevels);
  };

  onCourseDetailSelect = (courseDetail) => {
    let { activeCourseDetails } = this.state;
    let index = activeCourseDetails.indexOf(courseDetail.description);
    if (index === -1) {
      activeCourseDetails.push(courseDetail.description);
    } else {
      activeCourseDetails.splice(index, 1);
    }
    this.setState(activeCourseDetails);
  };

  render() {
    const {
      levels,
      activeLevels,
      activeCourseDetails,
      title,
      avialable,
      startDate,
      endDate,
    } = this.state;
    return (
      <div className="row text-left">
        <div className="col-12">
          <FontAwesomeIcon icon={faFilter}></FontAwesomeIcon>
          <span className="h3 p-3">Filters</span>
          <hr />
        </div>
        <div className="col-12">
          <h4 className="my-2">Title:</h4>
          <input
            className="form-control mb-2"
            type="text"
            value={title}
            onChange={(event) => this.onTitleChange(event)}
          />
        </div>
        <div className="col-12">
          <form onSubmit={(event) => this.onSubmit(event)}>
            <h4 className="my-2">Level:</h4>
            {levels.map((level) => (
              <span className="checkbox mr-3" key={level}>
                <label>
                  <input
                    type="checkbox"
                    className="mr-2"
                    checked={activeLevels.indexOf(level) !== -1}
                    onChange={() => this.onLevelSelect(level)}
                  ></input>
                  {level}
                </label>
              </span>
            ))}
            <h4 className="my-2">Type of course:</h4>
            {this.props.allCourseDetails.map((courseDetail) => (
              <span className="checkbox mr-3" key={courseDetail.id}>
                <label>
                  <input
                    type="checkbox"
                    className="mr-2"
                    checked={
                      activeCourseDetails.indexOf(courseDetail.description) !==
                      -1
                    }
                    onChange={() => this.onCourseDetailSelect(courseDetail)}
                  ></input>
                  {courseDetail.description}
                </label>
              </span>
            ))}

            <h4 className="my-2">From:</h4>
            <input
              type="date"
              className="form-control"
              value={startDate}
              onChange={(event) => this.onStartDateSelect(event)}
            />

            <h4 className="my-2">To:</h4>
            <input
              type="date"
              className="form-control"
              value={endDate}
              onChange={(event) => this.onEndDateSelect(event)}
            />

            <label className="mt-3">
              <input
                type="checkbox"
                className="mr-2"
                checked={avialable}
                onChange={() => this.setState({ avialable: !avialable })}
              ></input>
              Show only avialable courses
            </label>
            <div>
              <button className="btn filterButton mt-3">
                <FontAwesomeIcon
                  icon={faSearch}
                  className="mr-2"
                ></FontAwesomeIcon>
                Search
              </button>
            </div>
          </form>
          <div className="mt-3">
            <button
              className="btn removeFiltersButton"
              onClick={() => this.removeFilters()}
            >
              <FontAwesomeIcon className="mr-2" icon={faBan}></FontAwesomeIcon>
              Clear Filters
            </button>
          </div>
        </div>
      </div>
    );
  }
}

export default CoursesSearchBar;
