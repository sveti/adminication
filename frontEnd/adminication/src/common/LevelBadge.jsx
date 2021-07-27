import React from "react";
import "./levelBadge.css";

const LevelBadge = (props) => {
  function zIndexModifier() {
    if (props.hide === true) {
      return " hide";
    } else return " normal";
  }
  let badgeClasses =
    "badge badge-pill py-2 normal " + props.level + " " + zIndexModifier();

  return <span className={badgeClasses}>Level {props.level}</span>;
};

export default LevelBadge;
