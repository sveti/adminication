import http from "./httpService";
import { gateway, administrationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + administrationServiceName + "/students/";

export function getAllStudentsAdmin() {
  return http.get(apiEndpoint + "admin/students");
}
