import React, {Component} from "react";
import "./Footer.css";

class Footer extends Component {
    constructor(props) {
        super(props);

        this.year = new Date().getFullYear();
    }

    render() {
        return (
            <footer className="footer mt-auto py-3">
                <div className="container">
                    <div className="row">
                        <span className="text-muted">Copyright &#169; 2018-{this.year} Sergei Visotsky</span>
                    </div>
                </div>
            </footer>
        );
    }
}

export default Footer;