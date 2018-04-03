package comp34120.ex2.accessor;

import comp34120.ex2.FollowerType;

import java.util.List;

public interface DataAccessor {

    PlayerParameter getLeaderParameter();

    List<Record> getHistoricRecords(FollowerType followerType);

    PlayerParameter getFollowerParameter(FollowerType followerType);
}
