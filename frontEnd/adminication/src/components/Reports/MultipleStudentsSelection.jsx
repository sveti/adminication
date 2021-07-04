import React from "react";
import LogoStudentF from "../../assets/images/studentF.svg";
import LogoStudentM from "../../assets/images/studentM.svg";
import "./reports.css";

function MultipleStudentSelection(props) {
  let { selectedIds, students, studentSelection } = props;

  function selectStudent(id) {
    if (selectedIds.includes(id)) {
      selectedIds = selectedIds.filter((e) => e !== id);
      studentSelection(selectedIds);
    } else {
      selectedIds.push(id);
      studentSelection(selectedIds);
    }
  }

  return (
    <div className="mb-5 students card-deck">
      {students.map((s) => (
        <div
          className={selectedIds.includes(s.id) ? "card selected" : "card"}
          key={s.id}
          onClick={() => selectStudent(s.id)}
        >
          <img
            className="card-img-top studentSelectionImage"
            src={s.gender === "FEMALE" ? LogoStudentF : LogoStudentM}
            alt="student logo"
          />
          <div className="card-body">
            <h5 className="card-title">{s.name + " " + s.lastName} </h5>
            <p className="card-text"></p>
          </div>
        </div>
      ))}
    </div>
  );
}

export default MultipleStudentSelection;
