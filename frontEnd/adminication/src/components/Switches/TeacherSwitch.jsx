import React from "react";
import { Redirect, Switch, withRouter } from "react-router-dom";
import ProtectedRoute from "../../common/ProtectedRoute";
import Logout from "../Login/Logout";
import IndexPage from "../Homepage/IndexPage";
import Course from "../Courses/Course";
import CoursesPage from "../Courses/CoursesPage";
import LessonsOfCoursePage from "../Courses/Lessons/LessonsOfCoursePage";
import TeacherLessonsPage from "../Courses/Lessons/TeacherLessonsPage";
import GradingTable from "../Courses/Grading/GradingTable";
import Grading from "../Courses/Grading/Grading";
import TeacherSalary from "../Reports/TeacherSalary";
import AllNotifications from "../Notifications/AllNotifications";
import NotFound from "../notFound";

const TeacherSwitch = ({ user, increase, decrease }) => {
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
      <ProtectedRoute path="/courses/:courseId" component={Course} />
      <ProtectedRoute
        path="/courses"
        render={() => <CoursesPage id={user.id}></CoursesPage>}
      />
      <ProtectedRoute
        path="/lessons/:courseId"
        render={(params) => (
          <LessonsOfCoursePage {...params}></LessonsOfCoursePage>
        )}
      />
      <ProtectedRoute
        path="/lessons"
        render={(params) => (
          <TeacherLessonsPage {...params}></TeacherLessonsPage>
        )}
      />

      <ProtectedRoute
        path="/grading/:courseId"
        render={(params) => <GradingTable {...params}></GradingTable>}
      />
      <ProtectedRoute
        path="/grading"
        render={(params) => <Grading teacherId={user.id} {...params}></Grading>}
      />
      <ProtectedRoute
        path="/reports"
        render={(params) => (
          <TeacherSalary teacherId={user.id} {...params}></TeacherSalary>
        )}
      />

      <ProtectedRoute
        path="/notifications"
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

export default withRouter(TeacherSwitch);
