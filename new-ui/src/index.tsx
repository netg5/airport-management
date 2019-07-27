import React, {Component} from "react";
import {Header} from "./Header";
import {Footer} from "./Footer";

class App extends Component {

    render() {
        return (
            <div>
                <Header/>
                <main role="main" className="flex-shrink-0">
                    <div id="root"></div>
                </main>
                <Footer/>
            </div>
        );
    }
}