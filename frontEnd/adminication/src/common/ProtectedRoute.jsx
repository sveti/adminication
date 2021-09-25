import React from "react";
import { Route } from "react-router";
import NotAllowed from "../components/NotAllowed";
import keycloakService from "../services/keycloakService";

const ProtectedRoute = ({ roles, children, ...rest }) => {
  return (
    <Route {...rest}>
      {keycloakService.hasRole(roles) ? children : <NotAllowed></NotAllowed>}
    </Route>
  );
};

export default ProtectedRoute;
