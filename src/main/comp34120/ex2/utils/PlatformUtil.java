package comp34120.ex2.utils;

import comp34120.ex2.Platform;
import comp34120.ex2.PlayerType;
import comp34120.ex2.Record;

import java.rmi.RemoteException;

public class PlatformUtil {
    public static Record[] getAllRecordsUntilDay(Platform platform, int day) throws RemoteException {
        Record[] records = new Record[day];
        for (int i = 1; i <= day; i++) {
            records[i - 1] = platform.query(PlayerType.LEADER, i);
        }
        return records;
    }

    public static float computeProfit(Record record) {
        float demandModel = 2f - record.m_leaderPrice + (0.3f * record.m_followerPrice);
        return (record.m_leaderPrice - record.m_cost) * demandModel;
    }

}
