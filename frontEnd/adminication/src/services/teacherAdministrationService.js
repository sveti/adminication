import http from "./httpService";
import { gateway, administrationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + administrationServiceName + "/teachers/";

export function getTeachersForCourse() {
  return http.get(apiEndpoint + "teachersForCourse");
}

export function getAllTeachersAdmin() {
  return http.get(apiEndpoint + "all");
}

export function addTeacher(teacher) {
  return http.post(apiEndpoint + "add", teacher);
}
