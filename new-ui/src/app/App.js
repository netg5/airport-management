import React, {Component} from 'react';
import {Router} from "react-router-dom";
import './App.css';
import Header from "../header/header";

class App extends Component {
    render() {
        return (
            <Router>
                <Header/>
            </Router>
        );
    }
}

export default App;
