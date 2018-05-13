package Perceptrone.ThirdAttempt;

public class Neuron {
    public Double value;
    public ActivationFunction function;

    public Neuron(Double value, ActivationFunction function) {
        this.value = value;
        this.function = function;
    }
}
