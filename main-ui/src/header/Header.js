import React, {Component} from "react";
import {NavLink} from "react-router-dom";

class Header extends Component {
    render() {
        return (
            <header>
                <div className="container">
                    <nav className="navbar navbar-expand-md fixed-top navbar-dark bg-dark flex-row">
                        <a className="navbar-brand" href="/">Flight reservation</a>
                        <div className="collapse navbar-collapse" id="navbarCollapse">
                            <ul className="navbar-nav mr-auto">
                                <li className="nav-item">
                                    <NavLink className="nav-link" to="/passenger/1">Passenger</NavLink>
                                </li>
                                <li className="nav-item">
                                    <NavLink className="nav-link" to="/aircrafts/1">Aircraft</NavLink>
                                </li>
                                <li className="nav-item">
                                    <NavLink className="nav-link" to="/routes/31">Route</NavLink>
                                </li>
                            </ul>
                        </div>
                    </nav>
                </div>
            </header>
        );
    }
}

export default Header;