package uk.gov.hmcts.reform.sscstaskconfiguration.utils;

import org.junit.jupiter.params.provider.Arguments;

import java.util.*;

public class CancellationScenarioBuilder {

    private String event;
    private Map<String,Object> caseData = new HashMap<>();

    private Set<Map<String,String>> results = new HashSet<>();

    private CancellationScenarioBuilder(String event) {
        this.event = event;
    }

    public static CancellationScenarioBuilder event(String event) {
        return new CancellationScenarioBuilder(event);
    }

    public CancellationScenarioBuilder withCaseData(String key, Object value) {
        caseData.put(key, value);
        return this;
    }

    public CancellationScenarioBuilder cancel(String processCategories) {
        results.add(Map.of(
            "action", "Cancel",
            "processCategories", processCategories
        ));
        return this;
    }

    public CancellationScenarioBuilder cancelAll() {
        results.add(Map.of(
            "action", "Cancel"
        ));
        return this;
    }

    public CancellationScenarioBuilder reconfigure(String processCategories) {
        results.add(Map.of(
            "action", "Reconfigure",
            "processCategories", processCategories
        ));
        return this;
    }

    public CancellationScenarioBuilder reconfigureAll() {
        results.add(Map.of(
            "action", "Reconfigure"
        ));
        return this;
    }

    public Arguments build() {
        return Arguments.of(
            null,
            event,
            null,
            Map.of("Data", caseData),
            results
        );
    }
}
