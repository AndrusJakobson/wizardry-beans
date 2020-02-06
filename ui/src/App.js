import React, {Component} from 'react';
import {
  BrowserRouter as Router,
  Route,
  Link
} from 'react-router-dom';

import Client from "./Client";

import './App.css';
import Home from "./components/Home";

const Tech = ({match}) => {
  return <div>Current Route: {match.params.tech}</div>
};


class App extends Component {
  render() {
    return (
      <Router>
        <switch>
          <Route path="/" component={Home}/>
        </switch>
      </Router>
    );
  }
}

export default App;
