import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import Select from "react-select";
import { getAllParentsAdmin } from "../../services/parentService";
import { dynamicSort } from "../../common/helper";
import "./reports.css";

const ParentSelection = (props) => {
  const [parents, setParents] = useState([]);
  const [selectedParentId, setselectedParentId] = useState(null);
  const [selectedParentName, setselectedParentName] = useState("");

  useEffect(() => {
    let isMounted = true;
    async function getParents() {
      const { data } = await getAllParentsAdmin();
      return data;
    }
    getParents().then((data) => {
      if (isMounted)
        setParents({
          parents: data.sort(dynamicSort("value")),
        });
    });
    return () => {
      isMounted = false;
    };
  }, []);

  const handleChange = (selectedOption) => {
    setselectedParentId(selectedOption.value);
    setselectedParentName(selectedOption.label);
  };

  return (
    <div className="container salaryDiv">
      <div className="row">
        <div className="col-12 mb-5">
          <h3>Select a teacher</h3>
        </div>
        <div className="col-10">
          <Select options={parents.parents} onChange={handleChange} />
        </div>
        <div className="col-2">
          <Link
            className="nav-link"
            to={{
              pathname: "/reports/parents/" + selectedParentId,
              statisticsProps: {
                parentId: selectedParentId,
                parentName: selectedParentName,
              },
            }}
          >
            <button
              className="btn selectBtn"
              disabled={selectedParentId ? false : true}
            >
              Select
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
};

export default ParentSelection;
