package Perceptrone;

import Perceptrone.Model.ActivationFunction;
import Perceptrone.Model.Connection;
import Perceptrone.Model.Layer;

import java.util.List;

public final class Config {
    public int threadPoolSize;
    public List<Layer> layers;
    public List<Connection> connections;
}
