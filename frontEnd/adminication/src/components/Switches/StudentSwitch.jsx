import React from "react";
import { Redirect, Switch, withRouter } from "react-router-dom";
import ProtectedRoute from "../../common/ProtectedRoute";
import StudentLessonsOfCourse from "../Courses/Lessons/StudentLessonsOfCourse";
import StudentCoursesPage from "../Courses/StudentCoursesPage";
import Course from "../Courses/Course";
import AllCoursesList from "../Courses/AllCoursesList";
import IndexPage from "../Homepage/IndexPage";
import Logout from "../Login/Logout";
import NotFound from "../notFound";
import AllEventsList from "../Events/AllEventsList";
import StudentsEventsPage from "../Events/StudentEventsPage";
import Event from "../Events/Event";
import StudentGrades from "../Courses/Grading/StudentGrades";
import AllNotifications from "../Notifications/AllNotifications";

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
        path="/courses/all"
        render={(params) => (
          <AllCoursesList {...params} user={user}></AllCoursesList>
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
        render={(params) => <Course {...params} parentView={false}></Course>}
      />

      <ProtectedRoute
        path="/courses"
        render={(params) => (
          <StudentCoursesPage {...params} user={user}></StudentCoursesPage>
        )}
      />

      <ProtectedRoute
        path="/grades"
        render={(params) => (
          <StudentGrades {...params} user={user}></StudentGrades>
        )}
      />

      <ProtectedRoute
        path="/events/all"
        render={(params) => (
          <AllEventsList {...params} user={user}></AllEventsList>
        )}
      />
      <ProtectedRoute
        path="/events/:eventId"
        render={(params) => <Event {...params} parentView={false}></Event>}
      />

      <ProtectedRoute
        path="/events"
        render={(params) => (
          <StudentsEventsPage {...params} user={user}></StudentsEventsPage>
        )}
      />
      <ProtectedRoute
        path="/notifications"
        render={(params) => (
          <AllNotifications {...params} user={user}></AllNotifications>
        )}
      />

      <Redirect from="/" exact to="/login" />
      <ProtectedRoute path="/not-found" component={NotFound} />
      <Redirect to="/not-found" />
    </Switch>
  );
};

export default withRouter(StudentSwitch);
