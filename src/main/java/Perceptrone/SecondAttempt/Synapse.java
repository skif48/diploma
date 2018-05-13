package Perceptrone.SecondAttempt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Synapse {
    public List<ArrayList<Double>> weights;

    private class WeightRowAndIndex {
        public ArrayList<Double> row;
        public int index;

        public WeightRowAndIndex(ArrayList<Double> row, int index) {
            this.row = row;
            this.index = index;
        }
    }

    public Synapse(List<ArrayList<Double>> weights) {
        this.weights = weights;
    }

    public TransferVector multiply(TransferVector transferVector) {
        int counter = 0;
        List<Double> resultList = new ArrayList<>();

        for (List<Double> row : weights) {
            double result = 0;
            for(Double synapseElement : row) {
                result += (synapseElement * transferVector.data.get(counter));
            }
            resultList.add(result);
            counter++;
        }

        return new TransferVector(resultList);
    }

    private List<WeightRowAndIndex> toWeightRowAndIndex(List<ArrayList<Double>> weights) {
        List<WeightRowAndIndex> result = new ArrayList<>();
        int counter = 0;
        for(ArrayList<Double> row : weights) {
            result.add(new WeightRowAndIndex(row, counter));
            counter++;
        }
        return result;
    }

    /*public TransferVector parallelMultiply(TransferVector transferVector) {

        Stream<TransferVector> stream = this.toWeightRowAndIndex(this.weights).parallelStream().forEach((WeightRowAndIndex weightRowAndIndex) -> {
            List<Double> resultList = new ArrayList<>();

            double result = 0;
            for(Double synapseElement : weightRowAndIndex.row) {
                result += (synapseElement * transferVector.data.get(weightRowAndIndex.index));
            }
            resultList.add(result);

            return new TransferVector(resultList);
        });
    }*/

}
