import React, {Component} from 'react';
import {BrowserRouter as Router, Route} from 'react-router-dom';


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
