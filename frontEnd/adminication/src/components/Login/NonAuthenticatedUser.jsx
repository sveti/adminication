import keycloakService from "../../services/keycloakService";

const NonAuthenticatedUser = ({ children }) =>
  !keycloakService.isLoggedIn() ? children : null;

export default NonAuthenticatedUser;
