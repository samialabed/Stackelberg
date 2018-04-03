package comp34120.ex2.accessor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PlayerParameter {
    private final int quantity;
    private final Map<String, List<Double>> parameterNameToValuesMap;
    private final List<String> parameters;

    public PlayerParameter(int quantity, Map<String, List<Double>> parameterNameToValuesMap) {
        this.quantity = quantity;
        this.parameterNameToValuesMap = parameterNameToValuesMap;
        this.parameters = new LinkedList<>();
        parameters.addAll(parameterNameToValuesMap.keySet());
    }

    public int getQuantity() {
        return quantity;
    }

    public Map<String, List<Double>> getParameterNameToValuesMap() {
        return parameterNameToValuesMap;
    }

    public List<String> getParameters() {
        return parameters;
    }
}
