import http from "./httpService";
import { gateway, administrationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + administrationServiceName;

export function getTeacherStatisticForMonth(teacherId, month, year) {
  return http.get(
    apiEndpoint + "/teachers/" + teacherId + "/" + month + "/" + year
  );
}

export function getStudentAttendaceForMonth(teacherId, month, year) {
  return http.get(
    apiEndpoint + "/students/" + teacherId + "/" + month + "/" + year
  );
}
