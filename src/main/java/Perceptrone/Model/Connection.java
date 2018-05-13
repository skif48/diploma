package Perceptrone.Model;

public final class Connection {
    public Double weight;
    public Integer leftNeuronID;
    public Integer rightNeuronID;

    public Connection(Double weight, Integer leftNeuronID, Integer rightNeuronID) {
        this.weight = weight;
        this.leftNeuronID = leftNeuronID;
        this.rightNeuronID = rightNeuronID;
    }
}
