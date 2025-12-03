package uk.gov.hmcts.reform.sscstaskconfiguration.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.params.provider.Arguments;

public class InitiationScenarioBuilder {

    private String event;
    private String state;
    private Map<String,Object> caseData = new HashMap<>();
    private List<Map<String,Object>> results = new ArrayList<>();

    private InitiationScenarioBuilder(String event, String state) {
        this.event = event;
        this.state = state;
    }

    public static InitiationScenarioBuilder event(String event) {
        return new InitiationScenarioBuilder(event, null);
    }

    public static InitiationScenarioBuilder eventWithState(String event, String state) {
        return new InitiationScenarioBuilder(event, state);
    }

    public InitiationScenarioBuilder withCaseData(String key, Object value) {
        caseData.put(key, value);
        return this;
    }

    public InitiationScenarioBuilder initiatesTask(String taskId, String name, int workingDaysAllowed) {
        return initiatesTask(taskId, name, workingDaysAllowed, taskId);
    }

    public InitiationScenarioBuilder initiatesTask(String taskId,
                                                   String name,
                                                   int workingDaysAllowed,
                                                   String processCategories) {
        results.add(Map.of(
            "taskId", taskId,
            "name", name,
            "workingDaysAllowed", workingDaysAllowed,
            "processCategories", processCategories
        ));
        return this;
    }

    public InitiationScenarioBuilder initiatesTaskWithDelay(String taskId,
                                                            String name,
                                                            int delayDuration,
                                                            int workingDaysAllowed) {
        results.add(Map.of(
            "taskId", taskId,
            "name", name,
            "delayDuration", delayDuration,
            "workingDaysAllowed", workingDaysAllowed,
            "processCategories", taskId
        ));
        return this;
    }

    public Arguments build() {
        return Arguments.of(event, state, Map.of("Data", caseData), results);
    }

}
