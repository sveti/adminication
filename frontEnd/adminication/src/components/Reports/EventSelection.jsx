import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Select from "react-select";
import { getAllEventsTitles } from "../../services/eventsService";
import { dynamicSort } from "../../common/helper";
import "./reports.css";

const convertEventToSelectCourse = (eventssArr) => {
  const newArrayOfObj = eventssArr.map(
    ({ id: value, title: label, ...rest }) => ({
      value,
      label,
      ...rest,
    })
  );

  return newArrayOfObj;
};

const EventSelection = (props) => {
  const [events, setEvents] = useState([]);
  const [selectedEventId, setSelectedEventId] = useState(null);
  const [selectedEventTitle, setSelectedEventTitle] = useState("");

  useEffect(() => {
    let isMounted = true;
    async function getEvents() {
      const { data } = await getAllEventsTitles();
      return data;
    }
    getEvents().then((data) => {
      if (isMounted)
        setEvents({
          events: convertEventToSelectCourse(data.sort(dynamicSort("id"))),
        });
    });
    return () => {
      isMounted = false;
    };
  }, []);

  const handleChange = (selectedOption) => {
    setSelectedEventId(selectedOption.value);
    setSelectedEventTitle(selectedOption.label);
  };

  return (
    <div className="container salaryDiv">
      <div className="row">
        <div className="col-12 mb-5">
          <h3>Select an event</h3>
        </div>
        <div className="col-10">
          <Select options={events.events} onChange={handleChange} />
        </div>
        <div className="col-2">
          <Link
            className=""
            to={{
              pathname: "/reports/events/" + selectedEventId,
              statisticsProps: {
                eventId: selectedEventId,
                eventTitle: selectedEventTitle,
              },
            }}
          >
            <button
              className="btn selectBtn"
              disabled={selectedEventId ? false : true}
            >
              Select
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default EventSelection;
