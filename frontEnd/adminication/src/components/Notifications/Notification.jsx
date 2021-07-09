import React, { Component } from "react";
import { getMonthByDate } from "../../common/helper";
import { dismissNotification } from "../../services/notificationService";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheckCircle } from "@fortawesome/free-solid-svg-icons";
import { toast } from "react-toastify";

import "./notifications.css";

class Notification extends Component {
  constructor(props) {
    super(props);
    this.state = {
      notification: this.props.notification,
      headerClasses: ["card-header"],
      footerClasses: ["card-footer"],
      showDismiss: false,
    };
    if (this.props.notification.status === "SEND") {
      this.state.headerClasses.push("unreadNotification");
      this.state.footerClasses.push("unreadNotification");
      this.state.showDismiss = true;
    }
  }

  dismissNotification = async () => {
    this.disableDismiss();
    this.props.dismissSingleNotification(this.state.notification);
    const response = await dismissNotification(
      this.state.notification.id
    ).catch(function (error) {
      if (error.response) {
        // Request made and server responded
        toast.error(error.response.data.error, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else if (error.request) {
        // The request was made but no response was received
        toast.error("An error occured! Try again later", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      } else {
        // Something happened in setting up the request that triggered an Error
        toast.error(error.message, {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
      }
    });

    if (response && response.status === 200) {
      toast.success("Notiification has been dismissed", {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
    }
  };

  disableDismiss = () => {
    let { headerClasses, footerClasses } = this.state;
    headerClasses.pop();
    footerClasses.pop();
    this.setState({
      headerClasses: headerClasses,
      footerClasses: footerClasses,
      showDismiss: false,
    });
  };

  formatDate = (date) => {
    const year = date.substr(0, 4);
    const month = date.substr(5, 2);
    const day = date.substr(8, 2);
    const time = date.substr(11, 5);

    return time + " " + day + " " + getMonthByDate(month) + " " + year;
  };

  render() {
    const { notification, headerClasses, footerClasses, showDismiss } =
      this.state;

    return (
      <div className="mx-auto">
        <div className="card-body">
          <div className={headerClasses.join(" ")}>
            <div className="text-left">
              <h6 className="my-2 ">From {notification.senderName}</h6>
            </div>
            {showDismiss ? (
              <div
                className="float-right dismissButton"
                onClick={this.dismissNotification}
              >
                <FontAwesomeIcon
                  className="float-right"
                  icon={faCheckCircle}
                ></FontAwesomeIcon>
              </div>
            ) : null}
          </div>

          <p className="card-text my-4 ">{notification.message}</p>
          <div className={footerClasses.join(" ")}>
            <h6 className="card-subtitle my-1 ">
              {this.formatDate(notification.send)}
            </h6>
          </div>
        </div>
      </div>
    );
  }
}

export default Notification;
