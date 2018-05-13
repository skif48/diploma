const fs = require('fs');
const jsonFile = {
    forceThreadPoolSize: 2,
    data: {
        layers: []
    }
};

let args = process.argv;
args.shift();
args.shift();
console.log(args);

function randomNumber() {
    let value = (Math.floor(Math.random() * 201) - 100) / 100;
    if(value == 0) {
        return "0.0";
    } else if(value == -1) {
        return "-1.0";
    } else if(value == 1) {
        return "1.0";
    } else {
        return value;
    }
}

function oneOrZero() {
    return Math.random() > 0.5 ? 1 : 0;
}

for (let i = 0; i < args.length; i++) {
    let sizeInt = Number.parseInt(args[i]);
    let sizeNextInt;
    if(i < args.length - 1) {
        sizeNextInt = Number.parseInt(args[i + 1]);
    }

    let layer = {
        neurons: [],
        weights: []
    };

    if(i === 0) {
        for(let j = 0; j < sizeInt; j++) {
            layer.neurons.push({value: oneOrZero()})
        }
    } else {
        for(let j = 0; j < sizeInt; j++) {
            layer.neurons.push({activationFunction: 'threshold'});
        }
    }

    if(sizeNextInt) {
        for(let j = 0; j < sizeNextInt; j++) {
            const weightsRow = [];
            for(let k = 0; k < sizeInt; k++) {
                weightsRow.push(randomNumber());
            }
            layer.weights.push(weightsRow);
        }
    }

    jsonFile.data.layers.push(layer);
}

fs.writeFileSync('randomNetwork.json', JSON.stringify(jsonFile));
