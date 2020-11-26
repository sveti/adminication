import React, { Component } from 'react';
import './App.css';

import {getUsers,getUser} from './services/userService'

class App extends Component {
  state = {
    isLoading: true,
    users: []
  };

  async componentDidMount() {

    const { data } = await getUsers();
    this.setState({ users: data, isLoading: false });
  }

  render() {
    const {users, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    return (
      <div className="App">
        <header className="App-header">
          <div className="App-intro">
            <h2>users</h2>
              {users.map(users =>
              <div key={users.id}>
                {users.username}
              </div>
            )}
                
              </div>
        </header>
      </div>
    );
  }
}

export default App;