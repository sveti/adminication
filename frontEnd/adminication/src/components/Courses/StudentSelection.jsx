import React from "react";
import { useState, useEffect } from "react";
import { getStudentsOfParent } from "../../services/parentService";
import LogoStudentF from "../../assets/images/studentF.svg";
import LogoStudentM from "../../assets/images/studentM.svg";
import { Link } from "react-router-dom";
import "./studentSelection.css";

const StudentSelection = (props) => {
  const [students, setStudents] = useState([]);

  const destination = "/" + props.location.state.path;

  useEffect(() => {
    async function getStudents() {
      const { data } = await getStudentsOfParent(props.user.id);
      setStudents(data);
    }
    getStudents();
  }, [props.user]);

  return (
    <div className="students card-deck">
      {students.map((s) => (
        <div className="card no-border" key={s.id}>
          <img
            className="card-img-top studentSelectionImage"
            src={s.gender === "FEMALE" ? LogoStudentF : LogoStudentM}
            alt="student logo"
          />
          <div className="card-body">
            <h5 className="card-title">{s.name + " " + s.lastName} </h5>
            <p className="card-text">
              <Link
                to={{
                  pathname: destination,
                  state: {
                    user: s,
                    parentView: true,
                  },
                }}
              >
                <button className="editButton details">
                  {props.location.state.message}
                </button>
              </Link>
            </p>
          </div>
        </div>
      ))}
    </div>
  );
};

export default StudentSelection;
