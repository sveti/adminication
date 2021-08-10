import React from "react";
import { Redirect, Switch, withRouter } from "react-router-dom";
import ProtectedRoute from "../../common/ProtectedRoute";
import Logout from "../Login/Logout";
import NotFound from "../notFound";
import IndexPage from "../Homepage/IndexPage";
import AdminAllCourses from "../Admin/AdminAllCourses";
import AdminAddCourse from "../Admin/AdminAddCourse";
import AdminAllTeachers from "../Admin/AdminAllTeachers";
import AdminAddTeacher from "../Admin/AdminAddTeacher";

const AdminSwitch = ({ user, increase, decrease }) => {
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
        render={(params) => (
          <AdminAllCourses {...params} user={user}></AdminAllCourses>
        )}
      />
      <ProtectedRoute
        path="/courses/add"
        render={(params) => (
          <AdminAddCourse {...params} mode={"save"}></AdminAddCourse>
        )}
      />

      <ProtectedRoute
        path="/courses/:courseId"
        render={(params) => (
          <AdminAddCourse {...params} mode={"edit"}></AdminAddCourse>
        )}
      />

      <ProtectedRoute
        path="/teachers/all"
        render={(params) => (
          <AdminAllTeachers {...params} user={user}></AdminAllTeachers>
        )}
      />
      <ProtectedRoute
        path="/teachers/add"
        render={(params) => (
          <AdminAddTeacher {...params} mode={"save"}></AdminAddTeacher>
        )}
      />

      <ProtectedRoute
        path="/teachers/:teacherId"
        render={(params) => (
          <AdminAddTeacher {...params} mode={"edit"}></AdminAddTeacher>
        )}
      />

      <Redirect from="/" exact to="/login" />
      <ProtectedRoute path="/not-found" component={NotFound} />
      <Redirect to="/not-found" />
    </Switch>
  );
};

export default withRouter(AdminSwitch);
