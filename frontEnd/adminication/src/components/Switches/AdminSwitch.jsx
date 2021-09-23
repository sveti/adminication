import React from "react";
import { Redirect, Switch, withRouter } from "react-router-dom";
import ProtectedRoute from "../../common/ProtectedRoute";
import NotFound from "../notFound";
import IndexPage from "../Homepage/IndexPage";
import AdminAllCourses from "../Admin/AdminAllCourses";
import AdminAllEvents from "../Admin/AdminAllEvents";
import AdminAddCourse from "../Admin/AdminAddCourse";
import AdminAddEvent from "../Admin/AdminAddEvent";
import AdminAllTeachers from "../Admin/AdminAllTeachers";
import AdminAddTeacher from "../Admin/AdminAddTeacher";
import TeacherSelection from "../Reports/TeacherSelection";
import ParentSelection from "../Reports/ParentSelection";
import TeacherSalary from "../Reports/TeacherSalary";
import ParentInvoice from "../Reports/ParentInvoice";
import StudentSelection from "../Reports/StudentSelection";
import StudentReport from "../Reports/StudentReport";
import CourseSelection from "../Reports/CourseSelection";
import CourseReport from "../Reports/CourseReport";
import EventSelection from "../Reports/EventSelection";
import EventReport from "../Reports/EventReport";

const AdminSwitch = ({ user, increase, decrease }) => {
  function editedUser(passedUser) {
    user = passedUser;
  }
  return (
    <Switch>
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
        path="/events/all"
        render={(params) => (
          <AdminAllEvents {...params} user={user}></AdminAllEvents>
        )}
      />
      <ProtectedRoute
        path="/events/add"
        render={(params) => (
          <AdminAddEvent {...params} mode={"save"}></AdminAddEvent>
        )}
      />

      <ProtectedRoute
        path="/events/:eventId"
        render={(params) => (
          <AdminAddEvent {...params} mode={"edit"}></AdminAddEvent>
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

      <ProtectedRoute
        path="/reports/teachers/:teacherId"
        render={(params) => <TeacherSalary {...params}></TeacherSalary>}
      />
      <ProtectedRoute
        path="/reports/teachers"
        render={(params) => <TeacherSelection {...params}></TeacherSelection>}
      />

      <ProtectedRoute
        path="/reports/parents/:parentId"
        render={(params) => <ParentInvoice {...params}></ParentInvoice>}
      />
      <ProtectedRoute
        path="/reports/parents"
        render={(params) => <ParentSelection {...params}></ParentSelection>}
      />

      <ProtectedRoute
        path="/reports/students/:studentId"
        render={(params) => <StudentReport {...params}></StudentReport>}
      />
      <ProtectedRoute
        path="/reports/students"
        render={(params) => <StudentSelection {...params}></StudentSelection>}
      />

      <ProtectedRoute
        path="/reports/courses/:courseId"
        render={(params) => <CourseReport {...params}></CourseReport>}
      />
      <ProtectedRoute
        path="/reports/courses"
        render={(params) => <CourseSelection {...params}></CourseSelection>}
      />

      <ProtectedRoute
        path="/reports/events/:eventId"
        render={(params) => <EventReport {...params}></EventReport>}
      />
      <ProtectedRoute
        path="/reports/events"
        render={(params) => <EventSelection {...params}></EventSelection>}
      />

      <ProtectedRoute path="/not-found" component={NotFound} />
      <Redirect to="/not-found" />
    </Switch>
  );
};

export default withRouter(AdminSwitch);
