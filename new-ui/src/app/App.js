import React, {Component} from "react";
import {Route, Router} from "react-router-dom";
import "./App.css";
import Header from "../header/Header";

class App extends Component {
    render() {
        return (
            <Router>
                <Header/>
                <Route path="/" exact component={Header}/>
            </Router>
        );
    }
}

export default App;
