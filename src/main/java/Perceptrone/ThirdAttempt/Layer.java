package Perceptrone.ThirdAttempt;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Layer {
    private ArrayList<Neuron> neurons;
    private TransferMatrix transferMatrix;

    public Layer(ArrayList<Neuron> neurons, TransferMatrix transferMatrix) {
        this.neurons = neurons;
        this.transferMatrix = transferMatrix;
    }

    public ArrayList<Neuron> getNeurons() {
        return neurons;
    }

    public void setNeurons(ArrayList<Neuron> neurons) {
        this.neurons = neurons;
    }

    public ArrayList<Double> getLayerValues() {
        return new ArrayList<>(neurons.stream().map(neuron -> neuron.value).collect(Collectors.toList()));
    }


    public TransferMatrix getTransferMatrix() {
        return transferMatrix;
    }
}
