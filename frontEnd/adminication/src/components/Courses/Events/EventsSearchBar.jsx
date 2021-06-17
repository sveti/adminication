import React, { Component } from "react";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBan, faFilter, faSearch } from "@fortawesome/free-solid-svg-icons";

class EventsSearchBar extends Component {
  state = {
    title: "",
    minAge: 0,
    maxAge: 100,
    startDate: "",
    endDate: "",
    avialable: false,
    scheduleConflict: false,
  };

  removeFilters = () => {
    this.setState({
      title: "",
      minAge: 0,
      maxAge: 100,
      avialable: false,
      startDate: "",
      endDate: "",
      scheduleConflict: false,
    });
    this.props.handleSubmit("", 0, 100, false, "", "", false);
  };

  onTitleChange = (event) => {
    this.setState({ title: event.target.value });
  };

  onEndDateSelect = (event) => {
    this.setState({ endDate: event.target.value });
  };

  onStartDateSelect = (event) => {
    this.setState({ startDate: event.target.value });
  };

  onSubmit = (event) => {
    event.preventDefault();
    this.props.handleSubmit(
      this.state.title,
      this.state.minAge,
      this.state.maxAge,
      this.state.avialable,
      this.state.startDate,
      this.state.endDate,
      this.state.scheduleConflict
    );
  };

  onRangeSiderChange = (event) => {
    console.log(event);
  };

  render() {
    const { title, startDate, endDate, avialable, scheduleConflict } =
      this.state;

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
          <h4 className="my-2">Age:</h4>
        </div>
        <div className="col-6">
          <h6 className="my-2">from:</h6>
          <input
            className="form-control mb-2"
            type="text"
            pattern="[0-9]*"
            value={this.state.minAge}
            onChange={(event) =>
              this.setState({ minAge: event.target.value.replace(/\D/, "") })
            }
          />
        </div>
        <div className="col-6">
          <h6 className="my-2">to:</h6>
          <input
            className="form-control mb-2"
            type="text"
            pattern="[0-9]*"
            value={this.state.maxAge}
            onChange={(event) =>
              this.setState({ maxAge: event.target.value.replace(/\D/, "") })
            }
          />
        </div>
        <div className="col-12">
          <form onSubmit={(event) => this.onSubmit(event)}>
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
              Show only avialable events
            </label>
            <label className="mt-3">
              <input
                type="checkbox"
                className="mr-2"
                checked={scheduleConflict}
                onChange={() =>
                  this.setState({ scheduleConflict: !scheduleConflict })
                }
              ></input>
              Show only events without schedule conflict
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

export default EventsSearchBar;
