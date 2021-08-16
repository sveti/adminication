import keycloakService from "../../services/keycloakService";

const AuthenticatedUser = ({ children }) =>
  keycloakService.isLoggedIn() ? children : null;

export default AuthenticatedUser;
