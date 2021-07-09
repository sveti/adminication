import React, { Component } from "react";

import { getAllNotificationsByUserId } from "../../services/notificationService";
import Notification from "./Notification";
import "./notifications.css";

class AllNotifications extends Component {
  state = {
    user: this.props.user,
    allNotificaions: [],
  };

  dismissSingleNotification = (notification) => {
    let { allNotificaions } = this.state;
    let index = allNotificaions.indexOf(notification);
    notification.status = "OPENED";
    allNotificaions[index] = notification;
    this.setState({ allNotificaions });
    this.props.location.data.decrease();
  };

  getAllNotificationsOfUser = async () => {
    const { data } = await getAllNotificationsByUserId(this.state.user.id);
    this.setState({ allNotificaions: data.reverse() });
  };

  componentDidMount = () => {
    this.getAllNotificationsOfUser();
  };

  render() {
    const { allNotificaions } = this.state;
    return (
      <div className="notificationsContainer">
        {allNotificaions.length > 0 ? (
          <React.Fragment>
            <h1>
              Showing {allNotificaions.length} notification
              {allNotificaions.length > 1 ? "s" : null}
            </h1>
            {allNotificaions.map((not) => {
              return (
                <Notification
                  dismissSingleNotification={(notification) =>
                    this.dismissSingleNotification(notification)
                  }
                  key={not.id}
                  notification={not}
                ></Notification>
              );
            })}
          </React.Fragment>
        ) : (
          <h3>There are currently no notifications</h3>
        )}
      </div>
    );
  }
}

export default AllNotifications;
