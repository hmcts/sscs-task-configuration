package uk.gov.hmcts.reform.sscstaskconfiguration.utils;

import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CancellationScenarioBuilder {

    private String event;

    private Map<String,String> additionalData = new HashMap<>();
    private List<Map<String,String>> results = new ArrayList<>();

    private CancellationScenarioBuilder(String event) {
        this.event = event;
    }

    public static CancellationScenarioBuilder event(String event) {
        return new CancellationScenarioBuilder(event);
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
            additionalData.isEmpty() ? null : Map.of("Data", additionalData),
            results
        );
    }

    public CancellationScenarioBuilder withAdditionalData(String name, String value) {
        additionalData.put(name, value);
        return this;
    }
}
