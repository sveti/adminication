import http from "./httpService";
import { gateway, educationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + educationServiceName + "/attendance/";

export function updateAttendances(attendances) {
  return http.put(apiEndpoint + "update", attendances);
}

export function addAttendances(attendances) {
  return http.post(apiEndpoint + "add", attendances);
}
