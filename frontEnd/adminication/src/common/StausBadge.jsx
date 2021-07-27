import React from "react";
import "./statusBadge.css";
const StatusBadge = (props) => {
  function zIndexModifier() {
    if (props.hide === true) {
      return " hide";
    } else return " normal";
  }
  let badgeClasses =
    "badge badge-pill py-2 " +
    props.status.toLowerCase() +
    " " +
    zIndexModifier();

  return <span className={badgeClasses}>{props.status}</span>;
};

export default StatusBadge;
