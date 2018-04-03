package comp34120.ex2.accessor;

import comp34120.ex2.FollowerType;
import comp34120.ex2.Record;

import java.util.List;
import java.util.Map;

public interface DataAccessor {

    PlayerParameter getLeaderParameter();

    List<Record> getHistoricRecords(FollowerType followerType);

    PlayerParameter getFollowerParameter(FollowerType followerType);

    Map<Integer, Double> getDisturbancesMap();

    Double getDisturbance(int day);
}
