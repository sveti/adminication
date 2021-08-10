import React, { Component } from "react";
import Select from "react-select";
import BackButton from "../../common/BackButton";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash, faSave } from "@fortawesome/free-solid-svg-icons";
import { getCourseTitles } from "../../services/courseService";
import {
  addTeacher,
  getTeachersForCourse,
} from "../../services/teacherAdministrationService";
import {
  checkIfEmailExists,
  checkIfUsernameExists,
} from "../../services/userService";
import { toast } from "react-toastify";

class AdminAddTeacher extends Component {
  constructor(props) {
    super(props);
    this.state = {
      mode: this.props.mode ? this.props.mode : "save",
      teacher: {
        teachings: [],
      },
      showPassword: false,
      courses: [],
      substitutes: [],
    };
  }

  convertFromIdToValue = (courses) => {
    ///replace id from array with value for view
    const newArrayOfObj = courses.map(
      ({ id: value, title: label, ...rest }) => ({
        value,
        label,
        ...rest,
      })
    );

    return newArrayOfObj;
  };

  convertTeachingsToEntity = (teachings) => {
    const newArrayOfObj = teachings.map(
      ({ value: courseId, salary, substituteId }) => ({
        courseId,
        salary,
        substituteId,
      })
    );

    return newArrayOfObj;
  };

  loadCourses = async () => {
    let { data } = await getCourseTitles();
    data = this.convertFromIdToValue(data);
    this.setState({ courses: data });
  };

  loadTeachers = async () => {
    let { data } = await getTeachersForCourse();
    data = this.convertFromIdToValue(data);
    this.setState({ substitutes: data });
  };

  componentDidMount = () => {
    this.loadCourses();
    this.loadTeachers();
  };

  handleInputChange = (event, property) => {
    let teacher = { ...this.state.teacher };
    teacher[property] = event.target.value;
    this.setState({ teacher });
  };

  handleGenderChange = (selectedOption) => {
    let { teacher } = this.state;
    teacher.gender = selectedOption.value;
    this.setState({ teacher });
  };

  handleCourseSelection = (selectedOption) => {
    let { teacher } = this.state;
    teacher.teachings = selectedOption;
    this.setState({ teacher });
  };

  changePasswordVisibility = () => {
    const { showPassword } = this.state;
    this.setState({ showPassword: !showPassword });
  };

  checkIfAllIsFilled = () => {
    const { teacher } = this.state;

    if (
      !teacher.name ||
      teacher.name.length === 0 ||
      teacher.name.length > 50
    ) {
      return true;
    }

    if (
      !teacher.lastName ||
      teacher.lastName.length === 0 ||
      teacher.lastName.length > 50
    ) {
      return true;
    }

    if (
      !teacher.username ||
      teacher.username.length < 5 ||
      teacher.username.length > 50
    ) {
      return true;
    }
    if (!teacher.password || teacher.password.length < 5) {
      return true;
    }

    if (!teacher.email || teacher.email.length === 0) {
      return true;
    }
    if (!teacher.gender) {
      return true;
    }

    return false;
  };

  handleTeacherSalaryUpdate = (event, courseId) => {
    let { teacher } = this.state;
    let courseIndex = teacher.teachings.findIndex(
      (el) => el.value === courseId
    );
    teacher.teachings[courseIndex].salary = event.target.value;
    this.setState({ teacher });
  };

  handleSubstituteUpdate = (selectedOption, courseId) => {
    let { teacher } = this.state;
    let courseIndex = teacher.teachings.findIndex(
      (el) => el.value === courseId
    );
    if (selectedOption === null) {
      teacher.teachings[courseIndex].substitute = null;
      teacher.teachings[courseIndex].substituteId = null;
    } else {
      teacher.teachings[courseIndex].substitute = selectedOption.label;
      teacher.teachings[courseIndex].substituteId = selectedOption.value;
    }
    console.log(teacher);
    this.setState({ teacher });
  };

  generateTeacherSalary = () => {
    const { teacher, substitutes } = this.state;
    let salaries = [];

    teacher.teachings.forEach((course) => {
      salaries.push(
        <div className="form-group row mt-2 fadeIn" key={course.value}>
          <label className="col-sm-12 col-md-2 col-form-label">
            {course.label}
          </label>
          <label htmlFor="salary" className="col-sm-2 col-md-1 col-form-label">
            Salary
          </label>
          <div className="col-sm-10 col-md-4">
            <input
              type="number"
              className="form-control editedInformaton"
              min={0}
              value={course.salary || ""}
              onChange={(event) =>
                this.handleTeacherSalaryUpdate(event, course.value)
              }
            />
          </div>
          <label htmlFor="level" className="col-sm-2 col-md-1 col-form-label">
            SubStitute (optional)
          </label>
          <div className="col-sm-10 col-md-4">
            <Select
              value={{ label: course.substitute }}
              onChange={(selected) =>
                this.handleSubstituteUpdate(selected, course.value)
              }
              isSearchable
              noOptionsMessage={"There are no other teachers!"}
              isClearable={true}
              options={substitutes}
              maxMenuHeight={"9rem"}
            />
          </div>
        </div>
      );
    });

    return salaries;
  };

  clearTeacher = () => {
    let teacher = {};
    teacher.name = "";
    teacher.username = "";
    teacher.lastName = "";
    teacher.password = "";
    teacher.gender = null;
    teacher.teachings = [];
    let { courses } = this.state;

    courses.forEach((c) => delete c.salary);
    this.setState({ teacher, courses, showPassword: false });
  };

  checkIfUsernameIsTaken = async (username) => {
    const { data } = await checkIfUsernameExists(username);
    return data;
  };

  checkIfEmailIsTaken = async (email) => {
    const { data } = await checkIfEmailExists(email);
    return data;
  };

  validateInputForDuplicates = async (teacher) => {
    const userNameTaken = await this.checkIfUsernameIsTaken(teacher.username);
    if (userNameTaken) {
      toast.error("Username is taken!", {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
      return false;
    }
    const emailTaken = await this.checkIfEmailIsTaken(teacher.email);
    if (emailTaken) {
      toast.error("Email is taken!", {
        position: "top-center",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
      });
      return false;
    }
    return true;
  };

  saveTeacher = async (event) => {
    event.preventDefault();
    let { teacher } = this.state;

    const unique = await this.validateInputForDuplicates(teacher);

    if (unique) {
      teacher.teachings = this.convertTeachingsToEntity(teacher.teachings);
      teacher.gender = teacher.gender.toUpperCase();
      let that = this;
      addTeacher(teacher)
        .then(function (response) {
          toast.success(response.data, {
            position: "top-center",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
          that.clearTeacher();
          window.scrollTo(0, 0);
        })
        .catch(function (response) {
          toast.error(response.message, {
            position: "top-center",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
          });
        });
    }
  };

  render() {
    const { teacher, mode, showPassword, courses } = this.state;
    const genderOptions = [
      { value: "Female", label: "Female" },
      { value: "Male", label: "Male" },
    ];
    return (
      <div className=" adminAllCoursesContainer container">
        {mode === "save" ? (
          <h1>Add a teacher</h1>
        ) : (
          <h1>Edit teacher #{teacher.id}</h1>
        )}
        <hr className="mb-5"></hr>
        <form className="fullHeight">
          <div className="form-group row">
            <label htmlFor="name" className="col-sm-2 col-md-2 col-form-label">
              Teacher Name
            </label>
            <div className="col-sm-10 col-md-4">
              <input
                type="text"
                className=" form-control editedInformaton"
                value={teacher.name || ""}
                onChange={(event) => this.handleInputChange(event, "name")}
              />
            </div>
            <label
              htmlFor="lastName"
              className="col-sm-2 col-md-2 col-form-label"
            >
              Teacher Family Name
            </label>
            <div className="col-sm-10 col-md-4">
              <input
                type="text"
                className=" form-control editedInformaton"
                value={teacher.lastName || ""}
                onChange={(event) => this.handleInputChange(event, "lastName")}
              />
            </div>
          </div>
          <div className="form-group row">
            <label
              htmlFor="username"
              className="col-sm-2 col-md-2 col-form-label"
            >
              Username
            </label>
            <div className="col-sm-10 col-md-4">
              <input
                type="text"
                className=" form-control editedInformaton"
                value={teacher.username || ""}
                onChange={(event) => this.handleInputChange(event, "username")}
              />
            </div>
            <label
              htmlFor="password"
              className="col-sm-2 col-md-2 col-form-label"
            >
              Password
            </label>
            <div className="col-sm-10 col-md-4 input-group">
              <input
                value={teacher.password || ""}
                type={showPassword ? "text" : "password"}
                className="form-control editedInformaton"
                aria-describedby="basic-addon"
                onChange={(event) => this.handleInputChange(event, "password")}
              />
              <div className="input-group-append">
                <span className="input-group-text" id="basic-addon">
                  <FontAwesomeIcon
                    icon={showPassword ? faEyeSlash : faEye}
                    title={showPassword ? "Hide password" : "Show password"}
                    onClick={this.changePasswordVisibility}
                  ></FontAwesomeIcon>
                </span>
              </div>
            </div>
          </div>
          <div className="form-group row">
            <label htmlFor="email" className="col-sm-2 col-md-2 col-form-label">
              Email
            </label>
            <div className="col-sm-10 col-md-10">
              <input
                type="email"
                className=" form-control editedInformaton"
                value={teacher.email || ""}
                onChange={(event) => this.handleInputChange(event, "email")}
              />
            </div>
          </div>
          <div className="form-group row">
            <label htmlFor="title" className="col-sm-2 col-md-2 col-form-label">
              Gender
            </label>
            <div className="col-sm-10 col-md-4">
              <Select
                options={genderOptions}
                value={{ label: teacher.gender }}
                onChange={this.handleGenderChange}
              ></Select>
            </div>
          </div>
          <hr></hr>
          <div className="form-group row mt-4">
            <label
              htmlFor="duration"
              className="col-sm-2 col-md-2 col-form-label"
            >
              Courses
            </label>
            <div className="col-sm-10 col-md-10">
              <Select
                value={teacher.teachings}
                isMulti
                onChange={this.handleCourseSelection}
                options={courses}
                maxMenuHeight={"9rem"}
              />
            </div>
          </div>
          {teacher.teachings && teacher.teachings.length > 0
            ? this.generateTeacherSalary().map((salary) => {
                return salary;
              })
            : null}
        </form>
        <div className="editButtonContainer">
          <button
            disabled={this.checkIfAllIsFilled() ? true : false}
            className="btn saveCourseButton"
            onClick={this.saveTeacher}
          >
            <FontAwesomeIcon icon={faSave} className="editIcon" />
            Save
          </button>
        </div>
        <BackButton></BackButton>
      </div>
    );
  }
}

export default AdminAddTeacher;
