import axios from "axios";
import logger from "./logService";
import keycloakService from "./keycloakService";

const _axios = axios.create();

axios.interceptors.response.use(null, (error) => {
  const expectedError =
    error.response &&
    error.response.status >= 400 &&
    error.response.status < 500;

  if (!expectedError) {
    logger.log(error);
  }

  return Promise.reject(error);
});

axios.interceptors.request.use(
  function (successfulReq) {
    if (keycloakService.isLoggedIn()) {
      // successfulReq.headers[
      //   "Authentication"
      // ] = `Bearer ${keycloakService.getToken()}`;

      const cb = () => {
        successfulReq.headers.Authorization = `Bearer ${keycloakService.getToken()}`;
        return Promise.resolve(successfulReq);
      };
      return keycloakService.updateToken(cb);
    }
    return successfulReq;
  },
  function (error) {
    return Promise.reject(error);
  }
);

// const configure = () => {
//   _axios.interceptors.request.use((config) => {
//     console.log("intersect!");
//     if (keycloakService.isLoggedIn()) {
//       const cb = () => {
//         config.headers.Authorization = `Bearer ${keycloakService.getToken()}`;
//         return Promise.resolve(config);
//       };
//       return keycloakService.updateToken(cb);
//     }
//   });
// };

const configure = () => {
  _axios.interceptors.request.use(
    (config) => {
      return config;
    },
    (error) => {
      Promise.reject(error);
    }
  );
};

const http = {
  get: axios.get,
  post: axios.post,
  put: axios.put,
  delete: axios.delete,
  configure,
};
export default http;
