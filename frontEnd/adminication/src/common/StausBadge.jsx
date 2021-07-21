import React from "react";
import "./statusBadge.css";
const StatusBadge = (props) => {
  let badgeClasses = "badge badge-pill py-2 " + props.status.toLowerCase();

  return <span className={badgeClasses}>{props.status}</span>;
};

export default StatusBadge;
