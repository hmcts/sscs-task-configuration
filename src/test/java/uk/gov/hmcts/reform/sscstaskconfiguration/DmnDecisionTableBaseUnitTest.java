package uk.gov.hmcts.reform.sscstaskconfiguration;

import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_INITIATION_SSCS_BENEFIT;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.junit.jupiter.api.BeforeEach;

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
        return requiredDecision.map(dmnDecision -> dmnEngine.evaluateDecisionTable(
            dmnDecision,
            variables
        )).orElse(null);
    }

    public Set<String> getAllTaskIds() {
        Set<String> taskIds = new HashSet<>();
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = contextClassLoader.getResourceAsStream(WA_TASK_INITIATION_SSCS_BENEFIT.getFileName());
        DmnDecision decision = dmnEngine.parseDecision(WA_TASK_INITIATION_SSCS_BENEFIT.getKey(), inputStream);

        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        logic.getRules().forEach(r -> taskIds.add(r.getConclusions().get(0).getExpression()));
        return taskIds;
    }

}
