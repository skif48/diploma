package Perceptrone.ThirdAttempt;

public class ThresholdFunction extends ActivationFunction<Double> {
    @Override
    public Double apply(Double value) {
        if(value >= 0.5) {
            return 1.0;
        } else {
            return 0.0;
        }
    }
}
