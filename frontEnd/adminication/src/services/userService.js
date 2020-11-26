import http from "./httpService";
import { gateway,administrationServiceName } from "../config.json";

const apiEndpoint = gateway+'/'+administrationServiceName + "/users/";

function usersUrl(id) {
  return `${apiEndpoint}${id}`;
}

export function getUsers() {
  return http.get(apiEndpoint);
}

export function getUser(userId) {
  return http.get(usersUrl(userId));
}

// export function saveMovie(movie) {
//   if (movie._id) {
//     const body = { ...movie };
//     delete body._id;
//     return http.put(movieUrl(movie._id), body);
//   }

//   return http.post(apiEndpoint, movie);
// }

// export function deleteMovie(movieId) {
//   return http.delete(movieUrl(movieId));
// }
