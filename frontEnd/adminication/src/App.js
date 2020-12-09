import React, { Component } from 'react';
import './App.css';

import {getUsers,getUser} from './services/userService'

import Navbar from './components/Header/Navbar.jsx'
import WelcomeScreen from './components/Header/WelcomeScreen';
import ScrollToTop from '../node_modules/react-scroll-up'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faArrowAltCircleUp } from '@fortawesome/free-solid-svg-icons'

class App extends Component {
  state = {
    isLoading: true,
    user: {},
    srollClicked:false,
  };

  async componentDidMount() {
    window.addEventListener('scroll', this.handleScroll);
    const { data } = await getUser(1);
    this.setState({ user: data, isLoading: false });
  }

  componentWillUnmount() {
    window.removeEventListener("scroll", this.handleScroll);
  }

  handleScroll = () =>{
    if(window.pageYOffset<100){
      this.setState({srollClicked:false});
    }
  }

  handleClick = () =>{

    this.setState({srollClicked:true});

  }

  render() {
    const {user, isLoading,srollClicked} = this.state;

    // if (isLoading) {
    //   return <p>Loading...</p>;
    // }


    return (
      <div className="App" >
        <div className="navContainer">
          <Navbar scrollClicked={srollClicked}></Navbar>
        </div>
        
        <WelcomeScreen name={user.name} lastName={user.lastName} gender={user.gender}></WelcomeScreen>
        <ScrollToTop id="scrollToTop" showUnder={700} duration={2000}>
        <FontAwesomeIcon className="styledButtonUp" icon={faArrowAltCircleUp} onClick={this.handleClick}/>
          </ScrollToTop>
      </div>
    );
  }
}

export default App;