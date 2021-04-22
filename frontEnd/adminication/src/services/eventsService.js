import http from "./httpService";
import { gateway, educationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + educationServiceName + "/events/";

export function getEvents() {
  return http.get(apiEndpoint + "events");
}

export function getEventsOfStudent(studentId) {
  return http.get(apiEndpoint + "student/" + studentId);
}
