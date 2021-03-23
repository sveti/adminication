import http from "./httpService";
import { gateway, educationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + educationServiceName + "/lessons/";

export function getLessonById(idLesson) {
  return http.get(apiEndpoint + idLesson);
}

export function getLessonsByCourseId(idCourse) {
  return http.get(apiEndpoint + idCourse + "/lessons");
}
export function saveLesson(lesson) {
  return http.post(apiEndpoint + "add", lesson);
}

export function updateLessonDescription(lesson) {
  return http.put(apiEndpoint + "updateDescription", lesson);
}

export function getAttendancesByLessonId(idLesson) {
  return http.get(apiEndpoint + idLesson + "/attendance");
}
