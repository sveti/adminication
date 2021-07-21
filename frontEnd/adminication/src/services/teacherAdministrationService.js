import http from "./httpService";
import { gateway, administrationServiceName } from "../config.json";

const apiEndpoint = gateway + "/" + administrationServiceName + "/teachers/";

export function getTeachersForCourse() {
  return http.get(apiEndpoint + "teachersForCourse");
}
