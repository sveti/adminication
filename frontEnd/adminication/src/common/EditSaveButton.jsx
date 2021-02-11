import React, { Component } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit, faSave } from "@fortawesome/free-solid-svg-icons";

class EditSaveButton extends Component {
  state = {
    buttonText: "Edit",
    buttonIcon: faEdit,
    buttonClasses: "editButton",
    mode: "display",
  };
  updateButton = () => {
    const { buttonText } = this.state;
    if (buttonText === "Edit") {
      this.props.onEdit();
      this.setState({
        buttonText: "Save",
        buttonIcon: faSave,
        buttonClasses: "saveButton",
        mode: "save",
      });
    } else {
      this.props.onSave();
      this.setState({
        buttonText: "Edit",
        buttonIcon: faEdit,
        buttonClasses: "editButton",
        mode: "display",
      });
    }
  };
  render() {
    const { buttonText, buttonIcon, buttonClasses } = this.state;
    return (
      <div className="editButtonContainer">
        <button className={buttonClasses} onClick={this.updateButton}>
          <FontAwesomeIcon
            id="editButton"
            icon={buttonIcon}
            className="editIcon"
          />
          {buttonText}
        </button>
      </div>
    );
  }
}

export default EditSaveButton;
