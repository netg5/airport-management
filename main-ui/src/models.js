export function flightModel() {
    return {
        distance: null,
        departureTime: null,
        arrivalTime: null,
        price: null,
        place: null,
        aircraft: aircraft()
    }
}

export function aircraft() {
    return {
        registrationNumber: null,
        modelNumber: null,
        aircraftName: null,
        capacity: null,
        weight: null,
        exploitationPeriod: null,
        available: null,
        hangar: hangar(),
        manufacturer: manufacturer(),
    }
}

export function hangar() {
    return {
        hangarNumber: null,
        capacity: null,
        hangarLocation: null
    }
}

export function manufacturer() {
    return {
        manufacturerCode: null,
        manufacturerName: null,
        location: null
    }
}