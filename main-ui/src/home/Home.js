import React, {Component} from "react";

class Home extends Component {
    flights = {
        responseList: []
    };

    componentDidMount() {
        const hardcodedToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE" +
            "1NjgzODQwMTYsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU" +
            "0VSIiwiUk9MRV9BRE1JTiJdLCJqdGkiOiJkNzA2OTBmMS01M2ZjLTRhNDktOTQ1ZC01YjQ5YWI2" +
            "ZWZkNjYiLCJjbGllbnRfaWQiOiJ0cnVzdGVkLWNsaWVudCIsInNjb3BlIjpbInJlYWQiLCJ0cnVzdCI" +
            "sIndyaXRlIl19.G7_A9E2xS3nS1UMD1RK2DKsRLC7YNN81puunHML-0QlGa1c5M4Aqr8joOjryp0g2" +
            "mTcS-t7lSJnlCnDbsNwQQXOoEEI9R9YROxPGB-GTc5yeQEmwzGQ1_QG4hOVYT17u7k2dTCOFDhAUfy7TMT0A" +
            "27YDMhQIg3MAsBRkuDCxvyCeADJ_CL1BVCOXwmJKbLfTVRcugVzhg3pPX6Mq70Gc7reekw0UQ6LZI3kzCLCKl" +
            "1NBuietdg19C4wuaEF7ZEKU8D4ECygTcHfMMq-caBIvmjPG84AynkBxXh3MwpxPBhJxmRGf_8j79zv" +
            "lubMvfNTm66sk-Ssfb3j7fhvgnrXdaw";
        fetch("http://localhost:8080/manager/getAllFlights?access_token=" + hardcodedToken)
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