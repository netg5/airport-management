import React, {Component} from "react";

class Header extends Component {
    render() {
        return (
            <header>
                <nav className="navbar navbar-expand-md fixed-top navbar-dark bg-dark">
                    <a className="navbar-brand" href="/">Flight reservation</a>
                    <div className="collapse navbar-collapse" id="navbarCollapse">
                        <ul className="navbar-nav mr-auto">
                            <li className="nav-item">
                                <a className="nav-link" href="/passengers/1">Customer</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="/aircrafts/1">Aircraft</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="/routes/31">Route</a>
                            </li>
                        </ul>
                    </div>
                </nav>
            </header>
        );
    }
}

export default Header;