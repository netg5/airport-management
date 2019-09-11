import React, {Component} from "react";

class Home extends Component {
    flights = {
        responseList: []
    };

    componentDidMount() {
        fetch("http://localhost:8080/manager/getAllFlights")
            .then(res => res.json())
            .then((data) => {
                this.setState({
                    responseList: {
                        price: data.price
                    }
                })
            })
            .catch();
    }

    render() {
        return (
            <div className="container">
                <div className="card-deck mb-3 text-center">
                    {this.flights.responseList.map(
                        (flight) => (
                            <div style={"padding-top: 350px"}>{flight.price}</div>
                            /*<div className="card mb-4 shadow-sm">
                                <div className="card-header">
                                    <h4 className="my-0 font-weight-normal">Free</h4>
                                </div>
                                <div className="card-body">
                                    <h1 className="card-title pricing-card-title">{flight.price}</h1>
                                    <ul className="list-unstyled mt-3 mb-4">
                                        <li>10 users included</li>
                                        <li>2 GB of storage</li>
                                        <li>Email support</li>
                                        <li>Help center access</li>
                                    </ul>
                                    <button type="button" className="btn btn-lg btn-block btn-outline-primary">Sign up
                                        for
                                        free
                                    </button>
                                </div>
                            </div>*/
                        ))
                    }
                </div>
            </div>
        )
    }
}

export default Home;