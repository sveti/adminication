import { Component } from "react";
import keycloakService from "../../services/keycloakService";

class Logout extends Component {
  componentDidMount() {
    keycloakService.doLogout();
  }
  render() {
    return null;
  }
}

export default Logout;
