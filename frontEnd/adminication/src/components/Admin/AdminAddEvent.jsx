import React, { Component } from "react";
import Select from "react-select";
import BackButton from "../../common/BackButton";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSave } from "@fortawesome/free-solid-svg-icons";
import { TimePicker } from "react-tempusdominus-bootstrap";
import {
  getEditEvent,
  editEvent,
  addNewEvent,
} from "../../services/eventsService";
import { getTodaysDate, textToDayOfTheWeekNumber } from "../../common/helper";
import { toast } from "react-toastify";
import moment from "moment";

import "./admin.css";

class AdminAddEvent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      mode: this.props.mode ? this.props.mode : "save",
      event: {
        startDate: getTodaysDate(),
        schedules: [
          { dayOfTheWeek: textToDayOfTheWeekNumber(getTodaysDate()) },
        ],
        eventsPerweek: 1,
      },
      showCharLimitWarning: false,
      charLimit: 2048,
    };
  }

  getEvent = async () => {
    const { data } = await getEditEvent(this.props.location.state.eventId);
    data.eventsPerweek = data.schedules.length;
    return data;
  };

  componentDidMount = async () => {
    if (this.state.mode === "edit") {
      const event = await this.getEvent();
      this.setState({ event });
    }
  };

  handleInputChange = (event, property) => {
    let ev = { ...this.state.event };
    if (property === "eventsPerweek" && event.target.value > 0) {
      if (event.target.value > ev.schedules.length) {
        for (let i = ev.schedules.length; i < event.target.value; i++) {
          ev.schedules.push({
            startDate: "",
            startTime: "",
            endTime: "",
          });
        }
      }
    }
    ev[property] = event.target.value;
    this.setState({ event: ev });

    if (
      property === "description" &&
      ev.description.length > this.state.charLimit
    ) {
      this.setState({ showCharLimitWarning: true });
    } else {
      this.setState({ showCharLimitWarning: false });
    }
  };

  handleTimeChange = (e, property, i) => {
    let time = moment(e, ["h:mm A"]).format("HH:mm");
    time += ":00";
    let { event } = this.state;
    // eslint-disable-next-line no-extend-native
    Array.prototype.insert = function (index) {
      this.splice.apply(
        this,
        [index, 0].concat(Array.prototype.slice.call(arguments, 1))
      );
      return this;
    };

    if (event.schedules[i] === undefined) {
      let obj = {};
      obj[property] = time;
      event.schedules.insert(i, obj);
    } else {
      let obj = { ...event.schedules[i] };
      obj[property] = time;
      event.schedules[i] = obj;
    }
    this.setState({ event });
  };

  handleScheduleDayChange = (event, i) => {
    // eslint-disable-next-line no-extend-native
    Array.prototype.insert = function (index) {
      this.splice.apply(
        this,
        [index, 0].concat(Array.prototype.slice.call(arguments, 1))
      );
      return this;
    };

    let { event: ev } = this.state;

    ev.schedules[i].startDate = event.target.value;

    this.setState({ event: ev });
  };

  handleStatusChange = (selectedOption) => {
    let { event } = this.state;
    event.status = selectedOption.value;
    this.setState({ event });
  };

  clearEvent = () => {
    let { event } = this.state;
    event.title = "";
    event.description = "";
    event.minAge = 0;
    event.maxAge = 100;
    event.maxNumberOfPeople = 0;
    event.startDate = getTodaysDate();
    event.schedules = [
      {
        dayOfTheWeek: textToDayOfTheWeekNumber(getTodaysDate()),
        startTime: null,
        endTime: null,
      },
    ];
    event.eventsPerweek = 1;

    this.setState({ event });
  };

  saveEvent = async () => {
    let { event, mode } = this.state;

    let updatedEvent = { ...event };
    updatedEvent.schedules.length = updatedEvent.eventsPerweek;

    if (mode === "save") {
      console.log("=====Save====");
      console.log(updatedEvent);
      const { data } = await addNewEvent(updatedEvent);
      if (data) {
        toast.success("The event has been added!", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        this.clearEvent();
        window.scrollTo(0, 0);
      } else {
        toast.error("An error has occured", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      }
    } else {
      console.log(updatedEvent);
      const { data } = await editEvent(updatedEvent);
      if (data) {
        toast.success("The event has been updated!", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        const e = await this.getEvent();
        this.setState({ event: e });
      } else {
        toast.error("An error has occured", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      }
    }
  };

  checkIfAllIsFilled = () => {
    const { event } = this.state;

    if (!event.title || event.title.length < 5) {
      return true;
    }

    if (!event.description || event.description.length > this.state.charLimit) {
      return true;
    }

    if (!event.minAge || event.minAge < 0) {
      return true;
    }
    if (!event.maxNumberOfPeople || event.maxNumberOfPeople <= 0) {
      return true;
    }
    if (!event.maxAge) {
      return true;
    }
    if (
      !event.schedules[0].startDate ||
      !event.schedules[0].startTime ||
      !event.schedules[0].endTime
    ) {
      return true;
    }
    return false;
  };

  generateScheduleFields = () => {
    const { event: ev } = this.state;
    let schedules = [];
    for (let i = 0; i < this.state.event.eventsPerweek; i++) {
      schedules.push(
        <div className="form-group row mt-2 fadeIn" key={i}>
          <label htmlFor="level" className="col-sm-2 col-md-2 col-form-label">
            Date for Part #{i + 1}
          </label>
          <div className="col-sm-10 col-md-2">
            <input
              type="date"
              className="form-control"
              id="date"
              value={ev.schedules[i].startDate || ""}
              onChange={(event) => this.handleScheduleDayChange(event, i)}
            />
          </div>
          <label
            htmlFor="duration"
            className="col-sm-2 col-md-2 col-form-label"
          >
            Start time Part #{i + 1}
          </label>
          <div className="col-sm-10 col-md-2 timePicker">
            <TimePicker
              widgetPositioning={{ vertical: "top", horizontal: "auto" }}
              date={ev.schedules[i] ? ev.schedules[i].startTime : ""}
              onChange={(e) => this.handleTimeChange(e.date, "startTime", i)}
            />
          </div>
          <label
            htmlFor="duration"
            className="col-sm-2 col-md-2 col-form-label"
          >
            End time Part #{i + 1}
          </label>
          <div className="col-sm-10 col-md-2 timePicker">
            <TimePicker
              widgetPositioning={{ vertical: "top", horizontal: "auto" }}
              date={ev.schedules[i] ? ev.schedules[i].endTime : ""}
              onChange={(e) => this.handleTimeChange(e.date, "endTime", i)}
            />
          </div>
        </div>
      );
    }
    return schedules;
  };

  render() {
    const { event: ev, showCharLimitWarning, mode } = this.state;

    const statusOptions = [
      { value: "UPCOMMING", label: "Upcomming" },
      { value: "STARTED", label: "Started" },
      { value: "FINISHED", label: "Finished" },
      { value: "CANCELED", label: "Canceled" },
    ];

    const charWarning = (
      <div className="alert alert-danger" role="alert">
        The description has a limit of 2048 characters. Currect characters:
        {this.state.event.description ? this.state.event.description.length : 0}
      </div>
    );

    return (
      <React.Fragment>
        <div className="adminAllCoursesContainer container">
          {mode === "save" ? (
            <h1>Create an event</h1>
          ) : (
            <h1>Edit event #{ev.id}</h1>
          )}
          <hr className="mb-5"></hr>
          <form className="fullHeight">
            <div className="form-group row">
              <label htmlFor="title" className="col-sm-2 col-form-label">
                Event Title
              </label>
              <div className="col-sm-10">
                <input
                  type="text"
                  className="form-control editedInformaton"
                  value={ev.title || ""}
                  onChange={(event) => this.handleInputChange(event, "title")}
                />
              </div>
            </div>
            <div className="form-group row">
              <label htmlFor="description" className="col-sm-2 col-form-label">
                Description
              </label>
              <div className="col-sm-10">
                <textarea
                  className="form-control editedInformaton"
                  rows="5"
                  value={ev.description || ""}
                  onChange={(event) =>
                    this.handleInputChange(event, "description")
                  }
                />
              </div>
            </div>
            <div className="form-group row">
              <label
                htmlFor="pricePerStudent"
                className="col-sm-2 col-md-2 col-form-label"
              >
                Max number of students
              </label>
              <div className="col-sm-10 col-md-2">
                <input
                  type="number"
                  className="form-control editedInformaton"
                  value={ev.maxNumberOfPeople || ""}
                  onChange={(event) =>
                    this.handleInputChange(event, "maxNumberOfPeople")
                  }
                />
              </div>
              <label
                htmlFor="descriptopm"
                className="col-sm-2 col-md-2 col-form-label"
              >
                Minimum student age
              </label>
              <div className="col-sm-10 col-md-2">
                <input
                  type="number"
                  className="form-control editedInformaton"
                  value={ev.minAge || ""}
                  onChange={(event) => this.handleInputChange(event, "minAge")}
                />
              </div>
              <label
                htmlFor="descriptopm"
                className="col-sm-2 col-md-2 col-form-label"
              >
                Maximum student age
              </label>
              <div className="col-sm-10 col-md-2">
                <input
                  type="number"
                  className="form-control editedInformaton"
                  value={ev.maxAge || ""}
                  onChange={(event) => this.handleInputChange(event, "maxAge")}
                />
              </div>
            </div>
            <hr />
            <div className="form-group row">
              {mode === "edit" ? (
                <React.Fragment>
                  <label
                    htmlFor="pricePerStudent"
                    className="col-sm-2 col-md-2 col-form-label"
                  >
                    Event Status
                  </label>
                  <div className="col-sm-10 col-md-4">
                    <Select
                      options={statusOptions}
                      value={{ label: ev.status || "" }}
                      onChange={this.handleStatusChange}
                    ></Select>
                  </div>
                </React.Fragment>
              ) : null}
              <label
                htmlFor="eventParts"
                className="col-sm-2 col-md-2 col-form-label"
              >
                Event Parts
              </label>
              <div className="col-sm-10 col-md-2">
                <input
                  type="number"
                  className="form-control editedInformaton"
                  min={1}
                  value={ev.eventsPerweek || ""}
                  onChange={(event) =>
                    this.handleInputChange(event, "eventsPerweek")
                  }
                />
              </div>
            </div>
            <hr />
            {ev.eventsPerweek > 0
              ? this.generateScheduleFields().map((schedule) => {
                  return schedule;
                })
              : null}
          </form>
          {showCharLimitWarning ? charWarning : null}
          <div className="editButtonContainer">
            <button
              disabled={this.checkIfAllIsFilled() ? true : false}
              className="btn saveCourseButton"
              onClick={this.saveEvent}
            >
              <FontAwesomeIcon icon={faSave} className="editIcon" />
              Save
            </button>
          </div>
          <BackButton></BackButton>
        </div>
      </React.Fragment>
    );
  }
}

export default AdminAddEvent;
