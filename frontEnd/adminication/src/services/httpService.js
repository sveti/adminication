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

const configure = () => {
  _axios.interceptors.request.use((config) => {
    if (keycloakService.isLoggedIn()) {
      const cb = () => {
        config.headers.Authorization = `Bearer ${keycloakService.getToken()}`;
        return Promise.resolve(config);
      };
      return keycloakService.updateToken(cb);
    }
  });
};

export function setJWT(jwt) {
  axios.defaults.headers.common["Authorization"] = jwt;
}
const http = {
  get: axios.get,
  post: axios.post,
  put: axios.put,
  delete: axios.delete,
  setJWT,
  configure,
};
export default http;
