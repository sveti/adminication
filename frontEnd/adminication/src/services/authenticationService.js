import http from "./httpService";
import { gateway, authenticationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + authenticationServiceName;

export function authenticateUser() {
  return http.get(apiEndpoint + "/basicauth");
}
