import jwtDecode from "jwt-decode";

import http from "./httpService";
import { getUser } from "./userService";

import { gateway, authenticationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + authenticationServiceName;
const tokenKey = "token";

http.setJWT(getJWT());

export async function login(username, password) {
  const { data } = await http.post(apiEndpoint + "/authenticate", {
    username,
    password,
  });
  localStorage.setItem(tokenKey, data.jwt);
}

export function logout() {
  localStorage.removeItem(tokenKey);
}

export async function getCurrentUser() {
  try {
    const jwt = localStorage.getItem(tokenKey);
    const user = jwtDecode(jwt);
    const { data } = await getUser(user.sub);
    return data;
  } catch (ex) {
    return null;
  }
}

export function getJWT() {
  return localStorage.getItem(tokenKey);
}
