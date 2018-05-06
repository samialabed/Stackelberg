package comp34120.ex2.regression;

import comp34120.ex2.Record;
import comp34120.ex2.utils.NeuralNetUtil;
import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class LinearRegression implements Regression {
    // Random number generator seed, for reproducibility
    private static final int seed = 12345;
    // Number of epochs (full passes of the data)
    private static final int nEpochs = 50;
    // Network learning rate
    private static final double learningRate = 0.00025;
    // Create the network
    private static final int numInput = 2;
    private static final int numOutputs = 1;
    private static final int nHidden = 1;


    private final MultiLayerNetwork neuralNetwork;
    private final NeuralNetUtil neuralNetUtil;

    public LinearRegression(NeuralNetUtil neuralNetUtil) {
        this.neuralNetUtil = neuralNetUtil;
        OutputLayer outputLayer = new OutputLayer.Builder().nIn(nHidden)
                                                           .nOut(numOutputs)
                                                           .lossFunction(LossFunctions.LossFunction.MSE)
                                                           .activation(Activation.IDENTITY)
                                                           .build();

        DenseLayer linearLayer = new DenseLayer.Builder().nIn(numInput)
                                                         .nOut(nHidden)
                                                         .activation(Activation.IDENTITY)
                                                         .build();

        this.neuralNetwork = new MultiLayerNetwork(new NeuralNetConfiguration.Builder()
                                                           .seed(seed)
                                                           .weightInit(WeightInit.XAVIER)
                                                           .updater(new Adam(learningRate,
                                                                             Adam.DEFAULT_ADAM_BETA1_MEAN_DECAY,
                                                                             Adam.DEFAULT_ADAM_BETA2_VAR_DECAY,
                                                                             Adam.DEFAULT_ADAM_EPSILON))
                                                           .list()
                                                           .layer(0, linearLayer)
                                                           .layer(1, outputLayer)
                                                           .pretrain(false)
                                                           .backprop(true)
                                                           .build());
        neuralNetwork.init();

//        Debugging listerner, not needed in production
//        neuralNetwork.setListeners(new ScoreIterationListener(1));

        DataSetIterator trainingDataIterator = new ListDataSetIterator<>(neuralNetUtil.getTrainingDataSet().asList());
        // Train the network on the full data set, and evaluate in periodically
        // free operation to do at construction
        for (int i = 0; i < nEpochs * 10; i++) {
            trainingDataIterator.reset();
            neuralNetwork.fit(trainingDataIterator);
        }
    }

    @Override
    public float predictFollowerPrice(double day, double leaderPrice) {
        INDArray inputFeatureVector = neuralNetUtil.createInputFeatureVector(day, leaderPrice);
        INDArray predictedPrice = neuralNetwork.output(inputFeatureVector, false);
        return predictedPrice.getFloat(0);
    }

    @Override
    public void update(Record record) {
        neuralNetUtil.updateFeaturesWithRecord(record);

        DataSetIterator trainingDataIterator = new ListDataSetIterator<>(neuralNetUtil.getTrainingDataSet().asList());
        // Train the network on the full data set, and evaluate in periodically
        for (int i = 0; i < nEpochs; i++) {
            trainingDataIterator.reset();
            neuralNetwork.fit(trainingDataIterator);
        }
    }

}
