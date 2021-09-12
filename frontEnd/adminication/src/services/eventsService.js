import http from "./httpService";
import {
  gateway,
  educationServiceName,
  administrationServiceName,
} from "../config.json";

const apiEndpoint = gateway + "/" + educationServiceName + "/events/";
const adminEndpoint = gateway + "/" + administrationServiceName + "/events/";

export function getEvents() {
  return http.get(apiEndpoint + "events");
}

export function getEventsOfStudent(studentId) {
  return http.get(apiEndpoint + "student/" + studentId);
}

export function getAllEventsAdmin() {
  return http.get(apiEndpoint + "admin/events");
}

export function addNewEvent(event) {
  return http.post(adminEndpoint + "add", event);
}

export function editEvent(event) {
  return http.put(adminEndpoint + "edit", event);
}

export function getEditEvent(idEvent) {
  return http.get(adminEndpoint + "edit/" + idEvent);
}
