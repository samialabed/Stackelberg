package comp34120.ex2.regression;

import comp34120.ex2.Record;
import comp34120.ex2.utils.NeuralNetUtil;
import org.deeplearning4j.datasets.iterator.impl.ListDataSetIterator;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.GravesLSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.RmsProp;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class LSTMRegression implements Regression {
    // Random number generator seed, for reproducibility
    private static final int seed = 12345;
    // Number of epochs (full passes of the data)
    private static final int nEpochs = 30;
    // Network learning rate
    private static final double learningRate = 0.001;
    // Create the network
    private static final int numInput = 2;
    private static final int numOutputs = 1;
    private static final int nHidden = 3;

    private static final int tbpttLength = 2;                       //Length for truncated backpropagation through

    private final MultiLayerNetwork neuralNetwork;
    private final NeuralNetUtil neuralNetUtil;

    public LSTMRegression(NeuralNetUtil neuralNetUtil) {
        this.neuralNetUtil = neuralNetUtil;

        //Set up network configuration:
        GravesLSTM gravesLSTM = new GravesLSTM.Builder().nIn(numInput)
                                                        .nOut(nHidden)
                                                        .activation(Activation.TANH)
                                                        .build();

        GravesLSTM gravesLSTM2 = new GravesLSTM.Builder().nIn(nHidden)
                                                         .nOut(nHidden)
                                                         .activation(Activation.SOFTSIGN)
                                                         .build();


        RnnOutputLayer rnnOutputLayer = new RnnOutputLayer.Builder().activation(Activation.IDENTITY)
                                                                    .lossFunction(LossFunctions.LossFunction.MSE)
                                                                    .nIn(nHidden)
                                                                    .nOut(numOutputs)
                                                                    .build();


        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .l2(0.001)
                .weightInit(WeightInit.XAVIER)
                .updater(new Adam(learningRate,
                                  Adam.DEFAULT_ADAM_BETA1_MEAN_DECAY,
                                  Adam.DEFAULT_ADAM_BETA2_VAR_DECAY,
                                  Adam.DEFAULT_ADAM_EPSILON))
                .list()
                .layer(0, gravesLSTM)
                .layer(1, gravesLSTM2)
                .layer(2, rnnOutputLayer)
                .backpropType(BackpropType.Standard)
                // (samialab): Commented out is truncated bbptt, right now I am testing on full backprop but this is
                // expensive
//                .backpropType(BackpropType.TruncatedBPTT)
//                .tBPTTForwardLength(tbpttLength)
//                .tBPTTBackwardLength(tbpttLength)
                .pretrain(false)
                .backprop(true)
                .build();

        this.neuralNetwork = new MultiLayerNetwork(conf);
        this.neuralNetwork.init();

        DataSet trainingDataSet = neuralNetUtil.getTrainingDataSet();
        DataSetIterator trainingDataIterator = new ListDataSetIterator<>(trainingDataSet.asList());
        // Train the network on the full data set, and evaluate in periodically
        // free operation to do at construction
        INDArray output;
        for (int i = 0; i < nEpochs * 10; i++) {
            trainingDataIterator.reset();
            neuralNetwork.fit(trainingDataIterator);
            output = neuralNetwork.rnnTimeStep(trainingDataSet.getFeatureMatrix());
            System.out.println(output);
            neuralNetwork.rnnClearPreviousState();
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
