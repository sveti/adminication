import http from "./httpService";

import { gateway, authenticationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + authenticationServiceName;

export function checkCredentials(user) {
  return http.post(apiEndpoint + "/checkCredentials", user);
}
