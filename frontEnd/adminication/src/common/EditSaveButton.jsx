import React, { Component } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit, faSave } from "@fortawesome/free-solid-svg-icons";

class EditSaveButton extends Component {
  constructor(props) {
    super(props);
    this.state = {
      buttonText: "Edit",
      buttonIcon: faEdit,
      buttonClasses: "editButton",
    };
    if (this.props.initialState) {
      if (this.props.initialState === "save") {
        this.state.buttonText = "Save";
        this.state.buttonIcon = faSave;
        this.state.buttonClasses = "saveButton";
      }
    }
  }

  setSaveButton = () => {
    this.setState({
      buttonText: "Save",
      buttonIcon: faSave,
      buttonClasses: "saveButton",
    });
  };

  setEditButton = () => {
    this.setState({
      buttonText: "Edit",
      buttonIcon: faEdit,
      buttonClasses: "editButton",
    });
  };

  updateButton = async () => {
    const { buttonText } = this.state;

    if (buttonText === "Edit") {
      if (this.props.onEdit) {
        this.props.onEdit();
      }
      this.setSaveButton();
    } else {
      if (await this.props.validation()) {
        if (this.props.onSave) {
          this.props.onSave();
        }
        this.setEditButton();
      }
    }
  };
  render() {
    const { buttonText, buttonIcon, buttonClasses } = this.state;
    return (
      <div className="editButtonContainer">
        <button
          className={buttonClasses}
          onClick={this.updateButton}
          disabled={this.props.disabled}
        >
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
