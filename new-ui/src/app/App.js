import React, {Component} from "react";
import {BrowserRouter as Router} from "react-router-dom";
import "./App.css";
import Header from "../header/Header";
import Footer from "../footer/Footer";

class App extends Component {
    render() {
        return (
            <Router>
                <Header/>
                <main>
                    <div>
                        {/*<Route path="/" exact component={Header}/>*/}
                    </div>
                </main>
                <Footer/>
            </Router>
        );
    }
}

export default App;
