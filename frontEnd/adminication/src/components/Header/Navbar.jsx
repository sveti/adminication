import React from "react";
import "./navbar.css";
import Logo from "./../../assets/images/adminication.svg";

class Navbar extends React.Component {
  listener = null;
  state = {
    nav: false,
    removeNav: false,
    height: 100,
    oldScroll: 0,
    url: "",
  };
  componentDidMount() {
    window.addEventListener("scroll", this.handleScroll);
    this.setState({
      height:
        document.getElementById("WelcomeScreen").clientHeight -
        document.getElementById("mainMenu").clientHeight,
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
    const { nav, removeNav, url } = this.state;

    let navBarClasses = ["navbar"];
    if (nav) {
      navBarClasses.push("scrolled");
    } else {
      navBarClasses.push("topNavbar");
    }
    if (removeNav) {
      navBarClasses.push("removeNavbar");
    }

    return (
      <header className={navBarClasses.join(" ")} id="mainMenu">
        <nav className="navbar navbar-expand-lg">
          <a className="navbar-brand" href={url}>
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
          </a>
          <button
            className="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>

          <div className="collapse navbar-collapse" id="navbarSupportedContent">
            <ul className="navbar-nav mr-auto">
              <li className="nav-item active">
                <a className="nav-link" href={url}>
                  Home <span className="sr-only">(current)</span>
                </a>
              </li>
              <li className="nav-item">
                <a className="nav-link" href={url}>
                  Link
                </a>
              </li>
            </ul>
          </div>
        </nav>
      </header>
    );
  }
}

export default Navbar;
