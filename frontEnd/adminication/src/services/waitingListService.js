import http from "./httpService";
import { gateway, educationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + educationServiceName + "/waitingLists/";

export function addCourseWaitingListSignUp(waitingListSignUp) {
  return http.post(apiEndpoint + "add/course", waitingListSignUp);
}

export function getWaitingListCoursesOfStudent(studentId) {
  return http.get(apiEndpoint + "student/" + studentId + "/courses");
}

export function deleteCourseWaitingList(waitingListId) {
  return http.delete(apiEndpoint + "remove/" + waitingListId + "/course");
}

export function getWaitingListEventsOfStudent(studentId) {
  return http.get(apiEndpoint + "student/" + studentId + "/events");
}

export function addEventWaitingListSignUp(waitingListSignUp) {
  return http.post(apiEndpoint + "add/event", waitingListSignUp);
}

export function deleteEventWaitingList(waitingListId) {
  return http.delete(apiEndpoint + "remove/" + waitingListId + "/event");
}
