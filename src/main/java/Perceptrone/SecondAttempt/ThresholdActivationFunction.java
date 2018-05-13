package Perceptrone.SecondAttempt;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ThresholdActivationFunction implements Function<TransferVector, TransferVector> {

    @Override
    public TransferVector apply(TransferVector transferVector) {
        List<Double> result = new ArrayList<>();
        for(Double value : transferVector.data) {
            if(value >= 0.5) {
                result.add(1.0);
            } else {
                result.add(0.0);
            }
        }

        return new TransferVector(result);
    }
}
