import React from "react";
import { Switch, withRouter, Route } from "react-router-dom";
import Register from "../Login/Register";
import LandingPage from "../Login/LandingPage";

const GeneralSwitch = () => {
  return (
    <Switch>
      <Route path="/register" component={Register} />
      <Route path="/" component={LandingPage} />
    </Switch>
  );
};

export default withRouter(GeneralSwitch);
