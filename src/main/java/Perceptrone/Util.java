package Perceptrone;

import Perceptrone.ThirdAttempt.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;

public final class Util {
    public static Engine loadConfig(String configFilePath) {
        final JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader(configFilePath));
            JSONObject jsonConfig = (JSONObject) object;
            JSONObject data = (JSONObject) jsonConfig.get("data");
            JSONArray layersJSON = (JSONArray) data.get("layers");

            ArrayList<Layer> layers = new ArrayList<>();

            for(Object layer : layersJSON) {
                JSONObject layerDataJSON = (JSONObject) layer;
                JSONArray neuronsJSONArray = (JSONArray) layerDataJSON.get("neurons");
                ArrayList<Neuron> neurons = new ArrayList<>();
                for(Object neuronJSON : neuronsJSONArray) {
                    JSONObject jsonNeuron = (JSONObject) neuronJSON;
                    Neuron neuron;
                    Double value = 0.0;
                    if(jsonNeuron.get("value") != null) {
                        Long longValue = (Long) jsonNeuron.get("value");
                        value = longValue.doubleValue();
                    }
                    if(jsonNeuron.get("activationFunction") != null) {
                        switch((String) jsonNeuron.get("activationFunction")) {
                            case "threshold": neuron = new Neuron(value, new ThresholdFunction()); break;
                            default: neuron = new Neuron(value, new ThresholdFunction()); break;
                        }

                        neurons.add(neuron);
                    } else {
                        neurons.add(new Neuron(value, null));
                    }
                }

                JSONArray weightsJSONArray = (JSONArray) layerDataJSON.get("weights");
                ArrayList<ArrayList<Double>> transferMatrixData = new ArrayList<>();
                if(weightsJSONArray != null) {
                    for(Object row : weightsJSONArray.toArray()) {
                        JSONArray rowArray = (JSONArray) row;
                        ArrayList<Double> rowList = new ArrayList<>();
                        for(Object weight : rowArray) {
                            Double doubleWeight;
                            try {
                                doubleWeight = (Double) weight;
                            } catch(ClassCastException castExc) {
                                doubleWeight = new Double((String)weight);
                            }
                            rowList.add(doubleWeight);
                        }
                        transferMatrixData.add(rowList);
                    }
                }

                TransferMatrix transferMatrix = new TransferMatrix(transferMatrixData);

                Layer layerData = new Layer(neurons, transferMatrix);
                layers.add(layerData);
            }

            Engine engine = new Engine(layers);

            if(jsonConfig.get("forceThreadPoolSize") != null) {
                Long longThreadPoolSize = (Long) jsonConfig.get("forceThreadPoolSize");
                engine.setThreadPoolSize(longThreadPoolSize.intValue());
            }

            return engine;
        } catch(Exception exception) {
            exception.printStackTrace();
            System.exit(1);
            return null;
        }
    }
}
