import React from "react";
import "./courseDetailsBadge.css";

const CourseDetailsBadge = (props) => {
  function badgeColor(text) {
    if (text.length < 7) return "short";
    else if (text.length < 10) return "shortish";
    else if (text.length < 15) return "medium";
    else if (text.length < 20) return "mediumish";
    else if (text.length < 25) return "long";
    else return "longer";
  }

  let badgeClasses = "badge badge-pill mr-2 my-2 p-2 " + badgeColor(props.text);

  return <span className={badgeClasses}>{props.text}</span>;
};

export default CourseDetailsBadge;
