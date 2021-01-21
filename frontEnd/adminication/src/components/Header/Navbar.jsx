import React from "react";
import "./navbar.css";
import Logo from "./../../assets/images/adminication.svg";
import { Link } from "react-router-dom";

class Navbar extends React.Component {
  state = {
    nav: false,
    removeNav: false,
    height: 100,
    oldScroll: 0,
    id: this.props.user.id,
    stickyNav: false,
  };

  componentDidMount() {
    let height;
    if (this.state.stickyNav === false) {
      height =
        document.getElementById("WelcomeScreen").clientHeight -
        document.getElementById("mainMenu").clientHeight;
      window.addEventListener("scroll", this.handleScroll);
    } else {
      height = 0;
    }

    this.setState({
      height,
    });
  }

  componentWillUnmount() {
    window.removeEventListener("scroll", this.handleScroll);
  }

  checkIfScrollUp() {
    const check = this.state.oldScroll > window.pageYOffset;
    this.setState({
      oldScroll: window.pageYOffset,
    });
    return check;
  }

  stickyMenu = () => {
    this.setState({ stickyNav: true });
  };

  dynamicMenu = () => {
    this.setState({ stickyNav: false });
  };

  handleScroll = () => {
    const { height } = this.state;
    //put the scolled nav
    //ако се намирам след границата (банер+меню) сложи навигацията
    if (window.pageYOffset > height) {
      if (!this.state.nav) {
        this.setState({
          nav: true,
          removeNav: false,
        });
      }
    } else {
      //махни навигация ако я има
      if (this.state.nav) {
        //dissappear nav
        if (
          window.pageYOffset <
          document.getElementById("mainMenu").clientHeight + 10
        ) {
          this.setState({
            nav: false,
            removeNav: false,
          });
        } else if (
          window.pageYOffset < height + 1 &&
          (this.checkIfScrollUp() || this.props.scrollClicked)
        ) {
          this.setState({
            nav: true,
            removeNav: true,
          });
        } else {
          this.setState({
            nav: false,
            removeNav: false,
          });
        }
      }
    }
  };
  render() {
    const { nav, removeNav, id, stickyNav } = this.state;
    let navBarClasses = ["navbar"];
    if (stickyNav) {
      navBarClasses.push("fixed-top");
    } else {
      if (nav) {
        navBarClasses.push("scrolled");
      } else {
        navBarClasses.push("topNavbar");
      }
      if (removeNav) {
        navBarClasses.push("removeNavbar");
      }
    }

    navBarClasses.push(this.props.user.role.toLowerCase().split("_")[1]);

    return (
      <header className={navBarClasses.join(" ")} id="mainMenu">
        <nav className="navbar navbar-expand-lg">
          <Link className="navbar-brand" to={"/home/" + id}>
            <img
              src={Logo}
              width="30"
              height="30"
              className={
                nav
                  ? "d-inline-block align-top logo rotate"
                  : "d-inline-block align-top logo"
              }
              alt=""
              loading="lazy"
            />
            Adminication teacher
          </Link>
          <button
            className="navbar-toggler collapsed position-relative"
            type="button"
            data-toggle="collapse"
            data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span> </span>
            <span> </span>
            <span> </span>
          </button>

          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav mr-auto">
              <li className="nav-item active">
                <Link
                  className="nav-link"
                  to={"/home/" + id}
                  onClick={this.dynamicMenu}
                >
                  Home<span className="sr-only">(current)</span>
                </Link>
              </li>
              <li className="nav-item">
                <Link
                  className="nav-link"
                  to={{
                    pathname: "/courses",
                    state: {
                      user: this.props.user,
                    },
                  }}
                  onClick={this.stickyMenu}
                >
                  My courses
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to={"/attendaces/"}>
                  Attendances
                </Link>
              </li>
              <li className="nav-item">
                <Link className="nav-link" to={"/statistics/"}>
                  Statistics
                </Link>
              </li>
            </ul>
          </div>
        </nav>
      </header>
    );
  }
}

export default Navbar;
