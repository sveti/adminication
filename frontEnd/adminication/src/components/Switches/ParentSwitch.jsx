import React from "react";
import { Redirect, Switch, withRouter } from "react-router-dom";
import ProtectedRoute from "../../common/ProtectedRoute";
import Logout from "../Login/Logout";
import IndexPage from "../Homepage/IndexPage";
import NotFound from "../notFound";
import StudentSelection from "../Courses/StudentSelection";
import AllCoursesList from "../Courses/AllCoursesList";
import AllEventsList from "../Courses/Events/AllEventsList";
import StudentCoursesPage from "../Courses/StudentCoursesPage";
import StudentLessonsOfCourse from "../Courses/Lessons/StudentLessonsOfCourse";
import Course from "../Courses/Course";
import StudentGrades from "../Courses/Grading/StudentGrades";
import ParentInvoice from "../Reports/ParentInvoice";

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
        path="/courses/:courseId/lessons"
        render={(params) => (
          <StudentLessonsOfCourse {...params}></StudentLessonsOfCourse>
        )}
      />
      <ProtectedRoute
        path="/courses/all"
        render={(params) => <AllCoursesList {...params}></AllCoursesList>}
      />
      <ProtectedRoute
        path="/courses/:courseId"
        render={(params) => <Course {...params} parentView={true}></Course>}
      />

      <ProtectedRoute
        path="/courses"
        render={(params) => (
          <StudentCoursesPage {...params}></StudentCoursesPage>
        )}
      />

      <ProtectedRoute
        path="/grades"
        render={(params) => <StudentGrades {...params}></StudentGrades>}
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

      <ProtectedRoute
        path="/reports"
        render={(params) => (
          <ParentInvoice {...params} user={user}></ParentInvoice>
        )}
      />

      <Redirect from="/" exact to="/login" />
      <ProtectedRoute path="/not-found" component={NotFound} />
      <Redirect to="/not-found" />
    </Switch>
  );
};

export default withRouter(ParentSwitch);
