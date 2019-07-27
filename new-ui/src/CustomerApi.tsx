import React, {Component} from "react";
import axios from "axios";

export class CustomerApi extends Component {
    static getCustomerById(cusomerId: any) {
        const url = 'http://localhost:8080/reservations/customers/' + cusomerId;
        return axios
            .get(url)
            .then(response => response.data);
    }

    render(): React.ReactElement<any, string | React.JSXElementConstructor<any>> | string | number | {} | React.ReactNodeArray | React.ReactPortal | boolean | null | undefined {
        const customer = CustomerApi.getCustomerById(1);
        return (
            <div className="container">

                <h1>Customer:</h1>
                {/*<div>Customer ID: {customer.customerId}</div>*/}
                {/*<div>Fist name: {customer.firstName}</div>*/}
                {/*<div>Last name: {customer.lastName}</div>*/}
                {/*<div>Age: {customer.age}</div>*/}

                <p>
                    <a href="/customers/1/reservations">Reservations for the first customer</a>
                </p>
                <p>
                    <a href="/customers/1/tickets">Tickets for this customer</a>
                </p>

                <h6>Add a new customer:</h6>
                <form method="post">
                    <p>
                        <label>
                            <input type="text" className="form-control" placeholder="First name"/>
                        </label>
                    </p>
                    <p>
                        <label>
                            <input type="text" className="form-control" placeholder="Last name"/>
                        </label>
                    </p>
                    <p>
                        <label>
                            <input type="number" className="form-control" placeholder="Age"/>
                        </label>
                    </p>
                    <button className="btn btn-primary">Submit</button>
                </form>
            </div>
        );
    }
}