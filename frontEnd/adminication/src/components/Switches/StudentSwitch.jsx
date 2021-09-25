import React from "react";
import { Redirect, Switch, withRouter } from "react-router-dom";
import ProtectedRoute from "../../common/ProtectedRoute";
import StudentLessonsOfCourse from "../Courses/Lessons/StudentLessonsOfCourse";
import StudentCoursesPage from "../Courses/StudentCoursesPage";
import Course from "../Courses/Course";
import AllCoursesList from "../Courses/AllCoursesList";
import IndexPage from "../Homepage/IndexPage";
import Logout from "../Login/Logout";
import NotFound from "../NotFound";
import AllEventsList from "../Events/AllEventsList";
import StudentsEventsPage from "../Events/StudentEventsPage";
import Event from "../Events/Event";
import StudentGrades from "../Courses/Grading/StudentGrades";
import AllNotifications from "../Notifications/AllNotifications";

const StudentSwitch = ({ user, increase, decrease }) => {
  function editedUser(passedUser) {
    user = passedUser;
  }
  return (
    <Switch>
      <ProtectedRoute path="/logout" component={Logout} />
      <ProtectedRoute
        path="/home"
        roles={["STUDENT"]}
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
        roles={["STUDENT"]}
        render={(params) => (
          <AllCoursesList
            {...params}
            user={user}
            decrease={decrease}
          ></AllCoursesList>
        )}
      />

      <ProtectedRoute
        path="/courses/:courseId/lessons"
        roles={["STUDENT"]}
        render={(params) => (
          <StudentLessonsOfCourse {...params}></StudentLessonsOfCourse>
        )}
      />

      <ProtectedRoute
        path="/courses/:courseId"
        roles={["STUDENT"]}
        render={(params) => <Course {...params} parentView={false}></Course>}
      />

      <ProtectedRoute
        path="/courses"
        roles={["STUDENT"]}
        render={(params) => (
          <StudentCoursesPage {...params} user={user}></StudentCoursesPage>
        )}
      />

      <ProtectedRoute
        path="/grades"
        roles={["STUDENT"]}
        render={(params) => (
          <StudentGrades {...params} user={user}></StudentGrades>
        )}
      />

      <ProtectedRoute
        path="/events/all"
        roles={["STUDENT"]}
        render={(params) => (
          <AllEventsList {...params} user={user}></AllEventsList>
        )}
      />
      <ProtectedRoute
        path="/events/:eventId"
        roles={["STUDENT"]}
        render={(params) => <Event {...params} parentView={false}></Event>}
      />

      <ProtectedRoute
        path="/events"
        roles={["STUDENT"]}
        render={(params) => (
          <StudentsEventsPage {...params} user={user}></StudentsEventsPage>
        )}
      />
      <ProtectedRoute
        path="/notifications"
        roles={["STUDENT"]}
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

export default withRouter(StudentSwitch);
