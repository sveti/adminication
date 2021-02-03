import http from "./httpService";
import { gateway, educationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + educationServiceName + "/courses/";

export function getUpcommingCoursesOfTeacher(idTeacher) {
  return http.get(apiEndpoint + idTeacher + "/upcomming");
}

export function getStartedCoursesOfTeacher(idTeacher) {
  return http.get(apiEndpoint + idTeacher + "/started");
}
export function getSubStartedCoursesOfTeacher(idTeacher) {
  return http.get(apiEndpoint + idTeacher + "/sub/started");
}

export function getUpcommingCourse(idCourse) {
  return http.get(apiEndpoint + "upcomming/" + idCourse);
}

export function getCourseWithDetails(idCourse) {
  return http.get(apiEndpoint + "details/" + idCourse);
}

export function getStudentsByCourseId(idCourse) {
  return http.get(apiEndpoint + idCourse + "/students");
}
