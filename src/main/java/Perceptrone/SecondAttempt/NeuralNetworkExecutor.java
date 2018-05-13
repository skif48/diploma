package Perceptrone.SecondAttempt;

import java.util.List;
import java.util.function.Function;

public class NeuralNetworkExecutor {
    public TransferVector execute(Function<TransferVector, TransferVector> activationFunction, List<Synapse> synapses, TransferVector transferVector) {
        TransferVector resultVector = new TransferVector(transferVector.data);
        for (Synapse synapse : synapses) {
            resultVector = activationFunction.apply(synapse.multiply(resultVector));
        }

        return transferVector;
    }
}
