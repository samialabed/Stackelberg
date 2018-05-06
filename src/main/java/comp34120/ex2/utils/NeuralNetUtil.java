package comp34120.ex2.utils;

import comp34120.ex2.Record;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;

public class NeuralNetUtil {
    private static final double DATE_SCALE = 200.0;

    // Cache records
    private INDArray dateFeature;
    private INDArray leaderPriceFeature;
    private INDArray followerOutput;

    public NeuralNetUtil(Record[] records) {
        double[] days = new double[records.length];
        double[] leaderPrices = new double[records.length];
        double[] squaredLeaderPrice = new double[records.length];
        double[] followerOutputs = new double[records.length];

        for (int i = 0; i < records.length; i++) {
            days[i] = records[i].m_date / DATE_SCALE;
            followerOutputs[i] = records[i].m_followerPrice;
            leaderPrices[i] = records[i].m_leaderPrice;
            squaredLeaderPrice[i] = leaderPrices[i] * leaderPrices[i];
        }

        this.followerOutput = Nd4j.create(followerOutputs, new int[]{records.length, 1});
        this.dateFeature = Nd4j.create(days, new int[]{records.length, 1});
        this.leaderPriceFeature = Nd4j.create(leaderPrices, new int[]{records.length, 1});
    }

    public DataSet getTrainingDataSet() {
        return new DataSet(getInputFeatures(), getOutputFeatures());
    }

    public void updateFeaturesWithRecord(Record record) {
        INDArray newDateFeature = Nd4j.create(new double[]{record.m_date / DATE_SCALE}, new int[]{1, 1});
        INDArray newFollowerFeature = Nd4j.create(new double[]{record.m_followerPrice}, new int[]{1, 1});
        INDArray newLeaderFeature = Nd4j.create(new double[]{record.m_leaderPrice}, new int[]{1, 1});

        dateFeature = Nd4j.vstack(dateFeature, newDateFeature);
        followerOutput = Nd4j.vstack(followerOutput, newFollowerFeature);
        leaderPriceFeature = Nd4j.vstack(leaderPriceFeature, newLeaderFeature);
    }

    public INDArray createInputFeatureVector(double day, double leaderPrice) {
        INDArray newDateFeature = Nd4j.create(new double[]{day / DATE_SCALE}, new int[]{1, 1});
        INDArray newLeaderFeature = Nd4j.create(new double[]{leaderPrice}, new int[]{1, 1});
        return Nd4j.hstack(newDateFeature,
                           newLeaderFeature);
    }


    private INDArray getInputFeatures() {

        return Nd4j.hstack(dateFeature,
                           leaderPriceFeature);
    }

    private INDArray getOutputFeatures() {
        return followerOutput;
    }
}
