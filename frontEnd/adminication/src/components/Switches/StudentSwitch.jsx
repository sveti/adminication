import React from "react";
import { Redirect, Switch, withRouter } from "react-router-dom";
import ProtectedRoute from "../../common/ProtectedRoute";
import StudentLessonsOfCourse from "../Courses/Lessons/StudentLessonsOfCourse";
import StudentCoursesPage from "../Courses/StudentCoursesPage";
import Course from "../Courses/Course";
import IndexPage from "../Homepage/IndexPage";

import Logout from "../Login/Logout";
import NotFound from "../notFound";

const StudentSwitch = ({ user }) => {
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
        path="/courses/:courseId/lessons"
        render={(params) => (
          <StudentLessonsOfCourse {...params}></StudentLessonsOfCourse>
        )}
      />

      <ProtectedRoute
        path="/courses/:courseId"
        render={(params) => <Course {...params}></Course>}
      />

      <ProtectedRoute
        path="/courses"
        render={(params) => (
          <StudentCoursesPage {...params} user={user}></StudentCoursesPage>
        )}
      />
      <Redirect from="/" exact to="/login" />
      <ProtectedRoute path="/not-found" component={NotFound} />
      <Redirect to="/not-found" />
    </Switch>
  );
};

export default withRouter(StudentSwitch);
