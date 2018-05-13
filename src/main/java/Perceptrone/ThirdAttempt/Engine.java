package Perceptrone.ThirdAttempt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Engine {
    private ArrayList<Layer> layers;
    private int threadPoolSize;

    public Engine(ArrayList<Layer> layers) {
        this.layers = layers;
    }

    public ArrayList<Double> syncExecute() {
        int counter = 0;
        for(Layer layer : layers) {
            if(counter == layers.size() - 1) {
                return layer.getLayerValues();
            }

            Layer nextLayer = layers.get(counter + 1);
            for(int i = 0; i < layer.getTransferMatrix().data.size(); i++) {
                ArrayList<Double> currentRow = layer.getTransferMatrix().data.get(i);

                Double summator = 0.0;
                for(int j = 0; j < currentRow.size(); j++) {
                    summator += layer.getLayerValues().get(j) * currentRow.get(j);
                }

                nextLayer.getNeurons().get(i).value = (Double) nextLayer.getNeurons().get(i).function.apply(summator);
            }

            counter++;
        }
        return null;
    }

    public ArrayList<Double> asyncExecute(int threadsAmount) {
        int counter = 0;
        final long startTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(threadsAmount);

        final long stopTime = System.currentTimeMillis();

        System.out.println("threads allocation took " + (stopTime - startTime));

        for(Layer currentLayer: layers) {
            if(counter == layers.size() - 1) {
                return currentLayer.getLayerValues();
            }

            Layer nextLayer = layers.get(counter + 1);
            ArrayList<Callable<LambdaTransferObject>> lambdas = new ArrayList<>(currentLayer.getTransferMatrix().data.size());
            for(int i = 0; i < currentLayer.getTransferMatrix().data.size(); i++) {
                ArrayList<Double> currentRow = currentLayer.getTransferMatrix().data.get(i);
                final int currentRowIndex = i;
                final int currentCounter = counter;

                lambdas.add(() -> {
                    System.out.println(Thread.currentThread().getName() + " started at " + System.currentTimeMillis());
                    Double summator = 0.0;
                    for(int j = 0; j < currentRow.size(); j++) {
                        summator += currentLayer.getLayerValues().get(j) * currentRow.get(j);
                    }
                    LambdaTransferObject lambdaTransferObject = new LambdaTransferObject();
                    lambdaTransferObject.row = currentRowIndex;
                    lambdaTransferObject.value = (Double) nextLayer.getNeurons().get(currentRowIndex).function.apply(summator);

                    System.out.println(Thread.currentThread().getName() + " finished at " + System.currentTimeMillis());

                    return lambdaTransferObject;
                });
            }

            try {
                final List<Future<LambdaTransferObject>> futures = executorService.invokeAll(lambdas);
                futures.stream()
                        .forEach((future) -> {
                            try {
                                final int currentRowIndex = future.get().row;
                                final double value = future.get().value;
                                nextLayer.getNeurons().get(currentRowIndex).value = value;
                            } catch (Exception exc) {
                                exc.printStackTrace();
                            }
                        });
            } catch (Exception exc) {
                exc.printStackTrace();
            }

            counter++;
        }

        return null;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }
}
