package comp34120.ex2.accessor;

import comp34120.ex2.FollowerType;
import comp34120.ex2.Record;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataAccessorImpl implements DataAccessor {
    private final Map<FollowerType, List<Record>> followerTypeToHistoricRecordsMap;
    private final Map<FollowerType, PlayerParameter> followerTypeToParameterMap;
    // TODO(samialab): Do we need to have this dynamic?
    private final PlayerParameter leaderParameter;
    private final Map<Integer, Double> disturbancesMap;
    private final HSSFWorkbook workbook;

    public DataAccessorImpl(String dataFilePath) throws IOException {
        followerTypeToHistoricRecordsMap = new HashMap<>();
        followerTypeToParameterMap = new HashMap<>();
        disturbancesMap = new HashMap<>();

        //Get the workbook instance for XLS file
        FileInputStream file = new FileInputStream(new File(dataFilePath));
        workbook = new HSSFWorkbook(file);

        // Begin parsing the data sheet
        initialiseFollowerHistoricData();
        initialiseFollowerParameters();
        initialiseDisturbances();
        leaderParameter = new PlayerParameter(3, parseLeaderParameter());
    }


    @Override
    public PlayerParameter getLeaderParameter() {
        return leaderParameter;
    }

    @Override
    public List<Record> getHistoricRecords(FollowerType followerType) {
        return followerTypeToHistoricRecordsMap.get(followerType);
    }

    @Override
    public PlayerParameter getFollowerParameter(FollowerType followerType) {
        return followerTypeToParameterMap.get(followerType);
    }

    @Override
    public Map<Integer, Double> getDisturbancesMap() {
        return disturbancesMap;
    }

    @Override
    public Double getDisturbance(int day) {
        if (day < 101 || day > 130) {
            throw new IllegalArgumentException("Disturbance data exist only for days 101 to 130 inclusive");
        }
        return disturbancesMap.get(day);
    }

    private void initialiseFollowerHistoricData() {
        // Initialise Followers historic data
        for (FollowerType followerType : FollowerType.values()) {
            HSSFSheet sheet = workbook.getSheet("Follower_" + followerType.name());
            List<Record> historicData = new LinkedList<>();
            //Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); //Skip first row which has headers
            // TODO(samialab): Add cross checking expected Data format vs what we got here/Make it dynamic
            int dateCellIndex = 0;
            int leaderPriceCellIndex = 1;
            int followerPriceCellIndex = 2;
            int costCellIndex = 3;

            // Parse the worksheet and extract information
            rowIterator.forEachRemaining(row -> {
                int date = (int) row.getCell(dateCellIndex).getNumericCellValue();
                float leaderPrice = (float) row.getCell(leaderPriceCellIndex).getNumericCellValue();
                float followerPrice = (float) row.getCell(followerPriceCellIndex).getNumericCellValue();
                float cost = (float) row.getCell(costCellIndex).getNumericCellValue();
                historicData.add(new Record(date, leaderPrice, followerPrice, cost));
            });
            // Assign it to the follower
            followerTypeToHistoricRecordsMap.put(followerType, historicData);
        }
    }


    private void initialiseFollowerParameters() {
        // TODO(samialab): Safety check to ensure data file is in correct format
        int parameterNameCellIndex = 0;
        int parameterQuantityCellIndex = 1;

        // Initialise Followers historic data
        for (FollowerType followerType : FollowerType.values()) {
            HSSFSheet sheet = workbook.getSheet("Follower_" + followerType.name() + "_Dummy");
            //Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = sheet.iterator();
            int parametersQuantity = (int) rowIterator.next().getCell(parameterQuantityCellIndex).getNumericCellValue();
            Map<String, List<Double>> parameterNameToValuesMap = new HashMap<>();
            parsePlayerParameterSheet(parameterNameToValuesMap,
                                      parameterNameCellIndex,
                                      parameterQuantityCellIndex,
                                      rowIterator);
            PlayerParameter playerParameter = new PlayerParameter(parametersQuantity, parameterNameToValuesMap);
            followerTypeToParameterMap.put(followerType, playerParameter);
        }
    }

    private void initialiseDisturbances() {
        HSSFSheet sheet = workbook.getSheet("Disturbance_Dummy");
        //Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); //Skip first row which has headers
        int dateCellIndex = 0;
        int disturbanceCellIndex = 1;

        // Parse the worksheet and extract information
        rowIterator.forEachRemaining(row -> {
            Integer date = (int) row.getCell(dateCellIndex).getNumericCellValue();
            Double disturbanceValue = row.getCell(disturbanceCellIndex).getNumericCellValue();
            disturbancesMap.put(date, disturbanceValue);
        });
    }

    private Map<String, List<Double>> parseLeaderParameter() {
        Map<String, List<Double>> parameterMap = new HashMap<>();
        int parameterNameCellIndex = 0;
        int parameterQuantityCellIndex = 1;
        HSSFSheet sheet = workbook.getSheet("Leader_Dummy");
        //Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();
        parsePlayerParameterSheet(parameterMap, parameterNameCellIndex, parameterQuantityCellIndex, rowIterator);
        return parameterMap;
    }

    private void parsePlayerParameterSheet(Map<String, List<Double>> parameterMap, int parameterNameCellIndex, int
            parameterQuantityCellIndex, Iterator<Row> rowIterator) {
        rowIterator.forEachRemaining(row -> {
            List<Double> parameterValues = new LinkedList<>();
            String parameterName = row.getCell(parameterNameCellIndex).getStringCellValue();
            int quantity = (int) row.getCell(parameterQuantityCellIndex).getNumericCellValue();
            for (int col = 2; col <= quantity + 1; col++) {
                Double value = row.getCell(col).getNumericCellValue();
                parameterValues.add(value);
            }
            parameterMap.put(parameterName, parameterValues);
        });
    }
}
