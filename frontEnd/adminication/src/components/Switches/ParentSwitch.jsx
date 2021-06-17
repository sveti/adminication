import React from "react";
import { Redirect, Switch, withRouter } from "react-router-dom";
import ProtectedRoute from "../../common/ProtectedRoute";
import Logout from "../Login/Logout";
import IndexPage from "../Homepage/IndexPage";
import NotFound from "../notFound";
import StudentSelection from "../Courses/StudentSelection";
import AllCoursesList from "../Courses/AllCoursesList";
import AllEventsList from "../Courses/Events/AllEventsList";

const ParentSwitch = ({ user }) => {
  function editedUser(passedUser) {
    user = passedUser;
  }

  return (
    <Switch>
      <ProtectedRoute path="/logout" component={Logout} />
      <ProtectedRoute
        path="/home"
        render={(params) => (
          <IndexPage
            {...params}
            user={user}
            editedUser={(user) => editedUser(user)}
          ></IndexPage>
        )}
      />

      <ProtectedRoute
        path="/courses/all"
        render={(params) => <AllCoursesList {...params}></AllCoursesList>}
      />

      <ProtectedRoute
        path="/events/all"
        render={(params) => <AllEventsList {...params}></AllEventsList>}
      />

      <ProtectedRoute
        path="/selectStudent"
        render={(params) => (
          <StudentSelection {...params} user={user}></StudentSelection>
        )}
      />

      <Redirect from="/" exact to="/login" />
      <ProtectedRoute path="/not-found" component={NotFound} />
      <Redirect to="/not-found" />
    </Switch>
  );
};

export default withRouter(ParentSwitch);
