import React, { Component } from "react";
import { getTodaysDate } from "./helper";
import { toast } from "react-toastify";

class MonthYearSelector extends Component {
  constructor(props) {
    super(props);
    this.state = {
      months: [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December",
      ],
      selectedPeriod: {
        month: "",
        year: getTodaysDate().split("-")[0],
      },
    };
  }
  periodChange = (event, property) => {
    let selectedPeriod = this.state.selectedPeriod;
    selectedPeriod[property] = event.target.value;
    this.setState({ selectedPeriod });
    if (this.props.onPeriodChange) {
      this.props.onPeriodChange();
    }
  };

  validate = () => {
    const { selectedPeriod } = this.state;
    if (selectedPeriod.month === "") {
      toast.error("Please select a month!", {
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

    if (selectedPeriod.year < 1000 || selectedPeriod.year > 9999) {
      toast.error("Please enter a valid year!", {
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
    return true;
  };

  showStatistics = () => {
    if (this.validate()) {
      let newPeriod = { ...this.state.selectedPeriod };

      //Jan is 0 as index but 1 as month
      const montAsANumber =
        this.state.months.indexOf(this.state.selectedPeriod.month) + 1;
      newPeriod.month = montAsANumber;
      this.props.handleSubmit(newPeriod);
    }
  };

  render() {
    const { selectedPeriod } = this.state;
    return (
      <React.Fragment>
        <div className="row form-group mx-auto">
          <div className="form-group col-sm-12 col-md-3">
            <p>Month</p>
          </div>
          <div className="form-group col-sm-12 col-md-3">
            <select
              className="form-control"
              value={selectedPeriod.month}
              onChange={(event) => this.periodChange(event, "month")}
            >
              <option disabled hidden value=""></option>
              {this.state.months.map((m) => (
                <option key={m}>{m}</option>
              ))}
            </select>
          </div>
          <div className="form-group col-sm-12 col-md-3">
            <p>Year</p>
          </div>
          <div className="form-group col-sm-12 col-md-3">
            <input
              type="number"
              className="form-control"
              id="date"
              min={1000}
              max={9999}
              value={selectedPeriod.year}
              onChange={(event) => this.periodChange(event, "year")}
            />
          </div>
        </div>
        <div className="mx-auto">
          <button className="editButton" onClick={this.showStatistics}>
            Show
          </button>
        </div>
      </React.Fragment>
    );
  }
}

export default MonthYearSelector;
