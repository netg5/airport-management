import React from "react";
import ReactDOM from "react-dom";
import "./bootstrap/bootstrap-grid.min.css"
import "./bootstrap/bootstrap-reboot.min.css"
import "./bootstrap/bootstrap.min.css"
import "./index.css";
import App from "./app/App";
import {Route, Router} from "react-router-dom";
import * as serviceWorker from "./serviceWorker";
import Header from "./header/Header";

ReactDOM.render(
    (
        <Router>
            <Header/>
            <Route path="/" exact component={Header}/>
        </Router>
    ),
    document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
