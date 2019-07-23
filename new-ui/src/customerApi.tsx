import React from 'react';
import axios from 'axios';
import './static/css/style.css';
import * as serviceWorker from './serviceWorker';

const jsonHeader = {
    'Content-Type': "application/json"
};

export function getCustomerById(cusomerId) {
    const url = 'http://localhost:8080/reservations/customers/' + cusomerId;
    return axios.get(url)
        .then(response => response.data);
}


serviceWorker.unregister();
