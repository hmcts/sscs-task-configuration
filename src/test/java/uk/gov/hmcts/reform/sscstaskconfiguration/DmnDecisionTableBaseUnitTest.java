package uk.gov.hmcts.reform.sscstaskconfiguration;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.junit.jupiter.api.BeforeEach;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public abstract class DmnDecisionTableBaseUnitTest {

    protected static DmnDecisionTable CURRENT_DMN_DECISION_TABLE;
    protected DmnEngine dmnEngine;
    protected DmnDecision decision;

    @BeforeEach
    void setUp() {
        dmnEngine = DmnEngineConfiguration
            .createDefaultDmnEngineConfiguration()
            .buildEngine();

        // Parse decision
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = contextClassLoader.getResourceAsStream(CURRENT_DMN_DECISION_TABLE.getFileName());
        decision = dmnEngine.parseDecision(CURRENT_DMN_DECISION_TABLE.getKey(), inputStream);

    }

    public DmnDecisionTableResult evaluateDmnTable(Map<String, Object> variables) {
        return dmnEngine.evaluateDecisionTable(decision, variables);
    }

    public DmnDecisionTableResult evaluateRequiredDecision(String decisionTableId, Map<String, Object> variables) {
        Optional<DmnDecision> requiredDecision = decision.getRequiredDecisions().stream()
            .filter(d -> d.getKey().equals(decisionTableId))
            .findFirst();
        if (requiredDecision.isPresent()) {
            return dmnEngine.evaluateDecisionTable(requiredDecision.get(), variables);
        }
        return null;
    }
}
