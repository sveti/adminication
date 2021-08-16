import http from "./httpService";
import { gateway, administrationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + administrationServiceName + "/parents/";

export function getStudentsOfParent(parentId) {
  return http.get(apiEndpoint + parentId + "/students");
}

export function registerParent(parent) {
  return http.post(apiEndpoint + "add", parent);
}
