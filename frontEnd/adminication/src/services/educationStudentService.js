import http from "./httpService";
import { gateway, educationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + educationServiceName + "/students/";

export function getGradesOfStudent(studentId) {
  return http.get(apiEndpoint + studentId + "/grades");
}
