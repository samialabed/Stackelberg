package comp34120.ex2.Utils;

import comp34120.ex2.Record;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;

public class NeuralNetUtil {
    // Cache records
    private INDArray dateFeature;
    private INDArray leaderPriceFeature;
    private INDArray followerOutput;
    private INDArray costsFeature;

    public NeuralNetUtil(Record[] records) {
        float[] days = new float[records.length];
        float[] leaderPrices = new float[records.length];
        float[] followerOutputs = new float[records.length];
        float[] costs = new float[records.length];

        for (int i = 0; i < records.length; i++) {
            days[i] = records[i].m_date;
            followerOutputs[i] = records[i].m_followerPrice;
            leaderPrices[i] = records[i].m_leaderPrice;
            costs[i] = records[i].m_cost;
        }

        this.followerOutput = Nd4j.create(followerOutputs, new int[]{records.length, 1});
        this.dateFeature = Nd4j.create(days, new int[]{records.length, 1});
        this.leaderPriceFeature = Nd4j.create(leaderPrices, new int[]{records.length, 1});
        this.costsFeature = Nd4j.create(costs, new int[]{records.length, 1});
    }

    public DataSet getTrainingDataSet() {
        return new DataSet(getInputFeatures(), getOutputFeatures());
    }

    public void updateFeaturesWithRecord(Record record) {
        INDArray newDateFeature = Nd4j.create(new float[]{record.m_date}, new int[]{1, 1});
        INDArray newFollowerFeature = Nd4j.create(new float[]{record.m_followerPrice}, new int[]{1, 1});
        INDArray newLeaderFeature = Nd4j.create(new float[]{record.m_leaderPrice}, new int[]{1, 1});
        INDArray newCostFeature = Nd4j.create(new float[]{record.m_cost}, new int[]{1, 1});

        dateFeature = Nd4j.vstack(dateFeature, newDateFeature);
        costsFeature = Nd4j.vstack(costsFeature, newCostFeature);
        followerOutput = Nd4j.vstack(followerOutput, newFollowerFeature);
        leaderPriceFeature = Nd4j.vstack(leaderPriceFeature, newLeaderFeature);
    }

    public INDArray createInputFeatureVector(int day, float leaderPrice, float unitCost) {
        INDArray newDateFeature = Nd4j.create(new float[]{day}, new int[]{1, 1});
        INDArray newLeaderFeature = Nd4j.create(new float[]{leaderPrice}, new int[]{1, 1});
        INDArray newCostFeature = Nd4j.create(new float[]{unitCost}, new int[]{1, 1});
        return Nd4j.hstack(newDateFeature, newLeaderFeature, newCostFeature);
    }

    private INDArray getInputFeatures() {
        return Nd4j.hstack(dateFeature, leaderPriceFeature, costsFeature);
    }

    private INDArray getOutputFeatures() {
        return followerOutput;
    }
}