import http from "./httpService";
import { gateway, educationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + educationServiceName + "/courses/";

export function getFinishedCoursesOfTeacher(idTeacher) {
  return http.get(apiEndpoint + idTeacher + "/finished");
}

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
export function getStudentsWithGradesByCourseId(idCourse) {
  return http.get(apiEndpoint + idCourse + "/studentsGrades");
}

export function getAttendanceByCourseId(idCourse) {
  return http.get(apiEndpoint + idCourse + "/attendance");
}

export function updateGrades(grades) {
  return http.put(apiEndpoint + "updateGrades", grades);
}
