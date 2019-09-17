import React, {Component} from "react";
import {BrowserRouter as Router, Route} from "react-router-dom";
import "./App.css";
import Header from "../header/Header";
import Footer from "../footer/Footer";
import Home from "../home/Home";

class App extends Component {
    render() {
        return (
            <Router>
                <Header/>
                <main>
                    <div>
                        <Route path="/" component={Home}/>
                    </div>
                </main>
                <Footer/>
            </Router>
        );
    }
}

export default App;
