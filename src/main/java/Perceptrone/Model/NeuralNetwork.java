package Perceptrone.Model;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class NeuralNetwork {
    private List<Layer> layers;
    private Function activationFunction;
    private List<Connection> connections; //each inner list of connections represents a list of all connections between two layers

    public NeuralNetwork(
            List<Layer> layers,
            //Function<Double, Double> activationFunction,
            List<Connection> connections
    ) {
        this.layers = layers;
        this.connections = connections;
        this.activationFunction = activationFunction;
    }

    public Layer execute() {
        int currentLayerIndex = 0;
        for(Layer currentLayer : this.layers) {
            if(currentLayerIndex == this.layers.size() - 1) break;
            Layer nextLayer = this.layers.get(currentLayerIndex + 1);
            for(Neuron rightNeuron : nextLayer.getLayerAsList()) {
                List<Connection> relatedConnections = this.connections.stream().filter(con -> con.rightNeuronID.equals(rightNeuron.id)).collect(Collectors.toList());
                for(Connection currentConnection : relatedConnections) {
                    rightNeuron.value += currentLayer.findNeuronByID(currentConnection.leftNeuronID).value * currentConnection.weight;
                }
            }
            currentLayerIndex++;
        }

        return this.layers.get(this.layers.size() - 1);
    }
}