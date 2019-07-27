import React, {Component} from "react";

export class Footer extends Component {

    render(): React.ReactElement<any, string | React.JSXElementConstructor<any>> | string | number | {} | React.ReactNodeArray | React.ReactPortal | boolean | null | undefined {
        return (
            <footer className="footer mt-auto py-3">
                <div className="container">
                    <span className="text-muted">Copyright &#169; 2018-2019 Sergei Visotsky</span>
                </div>
            </footer>
        );
    }
}