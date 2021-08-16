import React from "react";
import ReactDOM from "react-dom";
import { BrowserRouter } from "react-router-dom";
import App from "./App";
import http from "./services/httpService";
import ScrollToTop from "./common/ScrollToTop";
import reportWebVitals from "./reportWebVitals";
import keycloakService from "./services/keycloakService";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min";
import "font-awesome/css/font-awesome.css";
import "tempusdominus-bootstrap/build/css/tempusdominus-bootstrap.css";
import "./index.css";

const renderApp = () =>
  ReactDOM.render(
    <BrowserRouter>
      <ScrollToTop>
        <App />
      </ScrollToTop>
    </BrowserRouter>,
    document.getElementById("root")
  );

reportWebVitals();

keycloakService.initKeycloak(renderApp);
http.configure();
