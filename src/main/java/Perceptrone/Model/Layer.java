package Perceptrone.Model;

import java.util.List;
import java.util.function.Function;

public final class Layer {
    private List<Neuron> neurons;

    public Layer(List<Neuron> neurons) {
        this.neurons = neurons;
    }

    public Neuron findNeuronByID(Integer id) {
        return this.neurons.stream()
                .filter(n -> n.id.equals(id))
                .findFirst()
                .get();
    }

    public void applyFunctionToEachNeuron(Function<Double, Double> activationFunction) {
        this.neurons
                .forEach(n -> n.value = activationFunction.apply(n.value));
    }

    public List<Neuron> getLayerAsList() {
        return this.neurons;
    }
}
