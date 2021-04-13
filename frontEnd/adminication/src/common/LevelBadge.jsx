import React from "react";
import "./levelBadge.css";
const LevelBadge = (props) => {
  let badgeClasses = "badge badge-pill py-2 " + props.level;

  return <span className={badgeClasses}>Level {props.level}</span>;
};

export default LevelBadge;
