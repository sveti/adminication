import React from "react";
import { Redirect, Switch, withRouter } from "react-router-dom";
import ProtectedRoute from "../../common/ProtectedRoute";
import Logout from "../Login/Logout";
import IndexPage from "../Homepage/IndexPage";
import NotFound from "../NotFound";
import StudentSelection from "../Courses/StudentSelection";
import AllCoursesList from "../Courses/AllCoursesList";
import StudentCoursesPage from "../Courses/StudentCoursesPage";
import StudentLessonsOfCourse from "../Courses/Lessons/StudentLessonsOfCourse";
import Course from "../Courses/Course";
import AllEventsList from "../Events/AllEventsList";
import StudentEventsPage from "../Events/StudentEventsPage";
import Event from "../Events/Event";
import StudentGrades from "../Courses/Grading/StudentGrades";
import ParentInvoice from "../Reports/ParentInvoice";
import AllNotifications from "../Notifications/AllNotifications";

const ParentSwitch = ({ user, increase, decrease }) => {
  function editedUser(passedUser) {
    user = passedUser;
  }

  return (
    <Switch>
      <ProtectedRoute path="/logout" component={Logout} />
      <ProtectedRoute
        path="/home"
        roles={["PARENT"]}
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
        roles={["PARENT"]}
        render={(params) => (
          <StudentLessonsOfCourse {...params}></StudentLessonsOfCourse>
        )}
      />
      <ProtectedRoute
        path="/courses/all"
        roles={["PARENT"]}
        render={(params) => (
          <AllCoursesList {...params} increase={increase}></AllCoursesList>
        )}
      />
      <ProtectedRoute
        path="/courses/:courseId"
        roles={["PARENT"]}
        render={(params) => <Course {...params} parentView={true}></Course>}
      />

      <ProtectedRoute
        path="/courses"
        roles={["PARENT"]}
        render={(params) => (
          <StudentCoursesPage {...params}></StudentCoursesPage>
        )}
      />

      <ProtectedRoute
        path="/grades"
        roles={["PARENT"]}
        render={(params) => <StudentGrades {...params}></StudentGrades>}
      />

      <ProtectedRoute
        path="/events/all"
        roles={["PARENT"]}
        render={(params) => (
          <AllEventsList {...params} increase={increase}></AllEventsList>
        )}
      />

      <ProtectedRoute
        path="/events/:eventId"
        roles={["PARENT"]}
        render={(params) => <Event {...params} parentView={true}></Event>}
      />

      <ProtectedRoute
        path="/events"
        roles={["PARENT"]}
        render={(params) => <StudentEventsPage {...params}></StudentEventsPage>}
      />

      <ProtectedRoute
        path="/selectStudent"
        roles={["PARENT"]}
        render={(params) => (
          <StudentSelection {...params} user={user}></StudentSelection>
        )}
      />

      <ProtectedRoute
        path="/reports"
        roles={["PARENT"]}
        render={(params) => (
          <ParentInvoice {...params} user={user}></ParentInvoice>
        )}
      />

      <ProtectedRoute
        path="/notifications"
        roles={["PARENT"]}
        render={(params) => (
          <AllNotifications
            {...params}
            user={user}
            decrease={decrease}
          ></AllNotifications>
        )}
      />

      <Redirect from="/" exact to="/login" />
      <ProtectedRoute path="/not-found" component={NotFound} />
      <Redirect to="/not-found" />
    </Switch>
  );
};

export default withRouter(ParentSwitch);
