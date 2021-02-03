import http from "./httpService";
import { gateway, educationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + educationServiceName + "/lessons/";

export function getLessonById(idLesson) {
  return http.get(apiEndpoint + idLesson);
}

export function getLessonsByTeacherIdAndCourseId(idTeacher, idCourse) {
  return http.get(apiEndpoint + idTeacher + "/" + idCourse);
}
export function saveLesson(lesson) {
  return http.post(apiEndpoint + "add", lesson);
}
