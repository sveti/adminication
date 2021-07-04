import http from "./httpService";
import { gateway, educationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + educationServiceName + "/enrollments/";

export function addEnrollment(enrollment) {
  return http.post(apiEndpoint + "/add", enrollment);
}

export function deleteEnrollment(studentId, courseId) {
  return http.delete(apiEndpoint + "/delete/" + studentId + "/" + courseId);
}
