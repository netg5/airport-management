import React, {Component} from "react";
import {Navbar} from "react-bootstrap";

export class Header extends Component {

    render(): React.ReactElement<any, string | React.JSXElementConstructor<any>> | string | number | {} | React.ReactNodeArray | React.ReactPortal | boolean | null | undefined {
        return (
            <header>
                <Navbar className="navbar navbar-expand-md fixed-top navbar-dark bg-dark">
                    <a className="navbar-brand" href="/">Flight reservation</a>
                    <div className="collapse navbar-collapse" id="navbarCollapse">
                        <ul className="navbar-nav mr-auto">
                            <li className="nav-item">
                                <a className="nav-link" href="/customers/1">Customer</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="/aircrafts/1">Aircraft</a>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="/routes/31">Route</a>
                            </li>
                        </ul>
                    </div>
                </Navbar>
            </header>
        );
    }
}