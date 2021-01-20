import React, { Component } from 'react';
import { Route, Redirect, Switch,withRouter } from "react-router-dom";

import './App.css';

import IndexPage from './components/IndexPage';
import Login from './components/Login/Login';


class App extends Component {
  
  loginRequest = (event) => {

    event.preventDefault();
    this.props.history.push("/home/1");

  };

  render() {
    return (
    <div className="App">
        <main>
        <Switch>
            <Route path="/login" render ={() => <Login loginRequest={this.loginRequest} pesho={"pesho"}></Login>}/>
            <Route path="/home/:id" component={IndexPage} />
            <Redirect from="/" exact to="/login" />
          </Switch>
        </main>
    </div>
    );
  }
}

export default withRouter(App);;