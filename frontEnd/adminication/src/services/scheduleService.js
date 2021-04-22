import http from "./httpService";
import { gateway, educationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + educationServiceName + "/schedule/";

export function getScheduleOfStudent(studentId) {
  return http.get(apiEndpoint + "student/" + studentId);
}
