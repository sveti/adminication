import React, { Component } from "react";
import logo from "../../assets/images/adminication.svg";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEye, faEyeSlash } from "@fortawesome/free-solid-svg-icons";
import Select from "react-select";
import { toast } from "react-toastify";
import {
  checkIfEmailExists,
  checkIfUsernameExists,
} from "../../services/userService";
import { registerParent } from "../../services/parentService";
import "./login.css";

class Register extends Component {
  state = {
    parent: {
      numberOfStudents: 1,
      students: [],
    },
    showPassword: false,
    firstPage: true,
  };

  handleInputChange = (event, property) => {
    let { parent } = this.state;
    parent[property] = event.target.value;
    this.setState({ parent });
  };

  handleStudentInputChange = (event, property, index) => {
    let { parent } = this.state;
    parent.students[index][property] = event.target.value;
    this.setState({ parent });
  };

  handleGenderChange = (selectedOption) => {
    let { parent } = this.state;
    parent.gender = selectedOption.value;
    this.setState({ parent });
  };

  handleStudentGenderChange = (selectedOption, index) => {
    let { parent } = this.state;
    parent.students[index].gender = selectedOption.value;
    this.setState({ parent });
  };

  changePasswordVisibility = () => {
    const { showPassword } = this.state;
    this.setState({ showPassword: !showPassword });
  };

  goBack = () => {
    this.setState({ firstPage: true });
  };

  handleFormSubmission = async () => {
    const { parent } = this.state;
    if (this.state.firstPage) {
      const unique = await this.validateInputForDuplicates(parent);
      if (unique) {
        if (parent.students.length !== parent.numberOfStudents) {
          parent.students = [];
          for (let i = 0; i < parent.numberOfStudents; i++) {
            parent.students.push({});
          }
        }

        this.setState({ parent, firstPage: false });
      }
    } else {
      parent.gender = parent.gender.toUpperCase();
      parent.role = "ROLE_PARENT";
      parent.students.forEach((st) => (st.gender = st.gender.toUpperCase()));
      parent.students.forEach((st) => (st.role = "ROLE_STUDENT"));

      let uniqueChildren = true;
      for (const child of parent.students) {
        let childUniqie = await this.validateInputForDuplicates(child);
        if (!childUniqie) {
          uniqueChildren = false;
          break;
        }
      }

      if (uniqueChildren) {
        let that = this;
        registerParent(parent)
          .then(function (response) {
            toast.success("You have been succesfully registered!", {
              position: "top-center",
              autoClose: 5000,
              hideProgressBar: false,
              closeOnClick: true,
              pauseOnHover: true,
              draggable: true,
              progress: undefined,
            });
            that.props.history.push("/");
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
    }
  };

  checkIfUsernameIsTaken = async (username) => {
    const { data } = await checkIfUsernameExists(username);
    return data;
  };

  checkIfEmailIsTaken = async (email) => {
    const { data } = await checkIfEmailExists(email);
    return data;
  };

  validateInputForDuplicates = async (parent) => {
    const userNameTaken = await this.checkIfUsernameIsTaken(parent.username);
    if (userNameTaken) {
      toast.error("Username " + parent.username + " is taken!", {
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
    const emailTaken = await this.checkIfEmailIsTaken(parent.email);
    if (emailTaken) {
      toast.error("Email " + parent.email + " is taken!", {
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

  generateStudentInformation = () => {
    const { parent } = this.state;
    const genderOptions = [
      { value: "Female", label: "Female" },
      { value: "Male", label: "Male" },
    ];
    let studentsFields = [];

    parent.students.forEach((student, index) => {
      studentsFields.push(
        <form key={index}>
          <div className="form-group row">
            <div className="col-12">
              <h5>Student #{index + 1}</h5>
            </div>
          </div>
          <div className="form-group row">
            <label htmlFor="name" className="col-sm-2 col-md-2 col-form-label">
              Name
            </label>
            <div className="col-sm-10 col-md-4">
              <input
                type="text"
                className=" form-control editedInformaton"
                value={student.name || ""}
                onChange={(event) =>
                  this.handleStudentInputChange(event, "name", index)
                }
              />
            </div>
            <label
              htmlFor="lastName"
              className="col-sm-2 col-md-2 col-form-label"
            >
              Family Name
            </label>
            <div className="col-sm-10 col-md-4">
              <input
                type="text"
                className=" form-control editedInformaton"
                value={student.lastName || ""}
                onChange={(event) =>
                  this.handleStudentInputChange(event, "lastName", index)
                }
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
            <div className="col-sm-10 col-md-10">
              <input
                type="text"
                className=" form-control editedInformaton"
                value={student.username || ""}
                onChange={(event) =>
                  this.handleStudentInputChange(event, "username", index)
                }
              />
            </div>
          </div>
          <div className="form-group row">
            <label
              htmlFor="password"
              className="col-sm-2 col-md-2 col-form-label"
            >
              Password
            </label>
            <div className="col-sm-10 col-md-10 input-group">
              <input
                value={student.password || ""}
                type="password"
                className="form-control editedInformaton"
                onChange={(event) =>
                  this.handleStudentInputChange(event, "password", index)
                }
              />
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
                value={student.email || ""}
                onChange={(event) =>
                  this.handleStudentInputChange(event, "email", index)
                }
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
                value={{ label: student.gender }}
                onChange={(selectedOption) =>
                  this.handleStudentGenderChange(selectedOption, index)
                }
              ></Select>
            </div>
          </div>
          <hr></hr>
        </form>
      );
    });

    return studentsFields;
  };

  unlockNextPage = (user) => {
    if (!user.username || user.username.length < 5) {
      return true;
    } else if (!user.password || user.password.length < 5) {
      return true;
    } else if (!user.name) {
      return true;
    } else if (!user.lastName) {
      return true;
    } else if (!user.email) {
      return true;
    } else if (!user.gender) {
      return true;
    }

    return false;
  };

  unlockSave = () => {
    const { parent } = this.state;
    for (let student of parent.students) {
      if (this.unlockNextPage(student)) {
        return true;
      }
    }
    return false;
  };

  render() {
    const { parent, showPassword, firstPage } = this.state;
    const genderOptions = [
      { value: "Female", label: "Female" },
      { value: "Male", label: "Male" },
    ];

    const fistPageForm = (
      <div className="col-md-8 py-5 border filled">
        <h4 className="pb-4 pr-3">Please fill the details of the parent</h4>
        <form>
          <div className="form-group row">
            <label htmlFor="name" className="col-sm-2 col-md-2 col-form-label">
              Name
            </label>
            <div className="col-sm-10 col-md-4">
              <input
                type="text"
                className=" form-control editedInformaton"
                value={parent.name || ""}
                onChange={(event) => this.handleInputChange(event, "name")}
              />
            </div>
            <label
              htmlFor="lastName"
              className="col-sm-2 col-md-2 col-form-label"
            >
              Family Name
            </label>
            <div className="col-sm-10 col-md-4">
              <input
                type="text"
                className=" form-control editedInformaton"
                value={parent.lastName || ""}
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
            <div className="col-sm-10 col-md-10">
              <input
                type="text"
                className=" form-control editedInformaton"
                value={parent.username || ""}
                onChange={(event) => this.handleInputChange(event, "username")}
              />
            </div>
          </div>
          <div className="form-group row">
            <label
              htmlFor="password"
              className="col-sm-2 col-md-2 col-form-label"
            >
              Password
            </label>
            <div className="col-sm-10 col-md-10 input-group">
              <input
                value={parent.password || ""}
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
                value={parent.email || ""}
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
                value={{ label: parent.gender }}
                onChange={this.handleGenderChange}
              ></Select>
            </div>
            <label
              htmlFor="numberOfStudents"
              className="col-sm-2 col-md-2 col-form-label"
            >
              Number of students
            </label>
            <div className="col-sm-10 col-md-4">
              <input
                type="number"
                min={1}
                className=" form-control editedInformaton"
                value={parent.numberOfStudents || ""}
                onChange={(event) =>
                  this.handleInputChange(event, "numberOfStudents")
                }
              />
            </div>
          </div>
          <hr></hr>
          <div className="form-row">
            <button
              type="button"
              className="btn register"
              onClick={this.handleFormSubmission}
              disabled={this.unlockNextPage(parent)}
            >
              Next
            </button>
          </div>
        </form>
      </div>
    );

    const secondPageForm = (
      <div className="col-md-8 py-5 border filled">
        <h4 className="pb-4 pr-3">Please fill the details of each student</h4>
        {this.generateStudentInformation().map((studentField) => {
          return studentField;
        })}

        <div className="form-row">
          <button
            type="button"
            className="btn login mr-4"
            onClick={this.goBack}
          >
            Back
          </button>
          <button
            type="button"
            className="btn register"
            onClick={this.handleFormSubmission}
            disabled={this.unlockSave()}
          >
            Register
          </button>
        </div>
      </div>
    );

    return (
      <div className="container-fluid outer">
        <section className="testimonial py-5 middle" id="testimonial">
          <div className="container">
            <div className="row ">
              <div className="col-md-4 py-5 bg-primary text-white text-center ">
                <div>
                  <div className="card-body">
                    <img
                      src={logo}
                      alt="logo"
                      className="logoRegisterPicture"
                    />
                    <h2 className="pt-3">Adminication</h2>
                    <h2 className="pb-3">Registration</h2>
                    <p>
                      Adminication is an online system for administration of the
                      education process. Please fill in the registry form in
                      order to create the accound and be able to use our
                      services. Any sufficiently advanced magic is
                      indistinguishable from technology .
                    </p>
                  </div>
                </div>
              </div>
              {firstPage ? fistPageForm : secondPageForm}
            </div>
          </div>
        </section>
      </div>
    );
  }
}

export default Register;
