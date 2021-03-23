import http from "./httpService";
import { gateway, administrationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + administrationServiceName + "/users/";

function usersUrl(username) {
  return `${apiEndpoint}${username}`;
}

export function getUsers() {
  return http.get(apiEndpoint);
}

export function getUser(username) {
  return http.get(usersUrl(username));
}

export function checkIfEmailExists(email) {
  return http.get(apiEndpoint + "validateEmail/" + email);
}

export function checkIfUsernameExists(username) {
  return http.get(apiEndpoint + "validateUsername/" + username);
}
