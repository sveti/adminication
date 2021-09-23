import http from "./httpService";
import {
  gateway,
  educationServiceName,
  administrationServiceName,
} from "../config.json";

const apiEndpoint = gateway + "/" + educationServiceName + "/courses/";
const adminEndpoint = gateway + "/" + administrationServiceName + "/courses/";

export function getFinishedCoursesOfTeacher(idTeacher) {
  return http.get(apiEndpoint + "teacher/" + idTeacher + "/finished");
}

export function getUpcommingCoursesOfTeacher(idTeacher) {
  return http.get(apiEndpoint + "teacher/" + idTeacher + "/upcomming");
}

export function getStartedCoursesOfTeacher(idTeacher) {
  return http.get(apiEndpoint + "teacher/" + idTeacher + "/started");
}

export function getStartedCoursesOfStudent(idStudent) {
  return http.get(apiEndpoint + "student/" + idStudent + "/started");
}
export function getUpcommingCoursesOfStudent(idStudent) {
  return http.get(apiEndpoint + "student/" + idStudent + "/upcomming");
}

export function getSubStartedCoursesOfTeacher(idTeacher) {
  return http.get(apiEndpoint + "teacher/" + idTeacher + "/sub/started");
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

export function getAllCourses() {
  return http.get(apiEndpoint + "allCourses");
}

export function getAllCourseDetails() {
  return http.get(apiEndpoint + "courseDetails");
}

export function getAllCoursesAdmin() {
  return http.get(apiEndpoint + "/admin/courses");
}

export function addNewCourse(course) {
  return http.post(adminEndpoint + "add", course);
}

export function editCourse(course) {
  return http.put(adminEndpoint + "edit", course);
}

export function getEditCourse(idCourse) {
  return http.get(adminEndpoint + "edit/" + idCourse);
}

export function getCourseTitles() {
  return http.get(adminEndpoint + "titles");
}

export function getCourseTitlesAll() {
  return http.get(adminEndpoint + "titles/all");
}

export function getCourseReport(idCourse) {
  return http.get(adminEndpoint + "report/" + idCourse);
}
