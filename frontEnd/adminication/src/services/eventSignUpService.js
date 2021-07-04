import http from "./httpService";
import { gateway, educationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + educationServiceName + "/eventSignUps/";

export function addEventSignUp(signUp) {
  return http.post(apiEndpoint + "/add", signUp);
}

export function deleteEventSignUp(studentId, eventId) {
  return http.delete(apiEndpoint + "/delete/" + studentId + "/" + eventId);
}
