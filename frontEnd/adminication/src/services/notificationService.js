import http from "./httpService";
import { gateway, notificationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + notificationServiceName + "/notifications/";

export function getAllNotificationsByUserId(userId) {
  return http.get(apiEndpoint + "user/" + userId);
}

export function dismissNotification(notificationId) {
  return http.get(apiEndpoint + notificationId + "/dismiss");
}
