package uk.gov.hmcts.reform.sscstaskconfiguration;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.junit.jupiter.api.BeforeEach;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_CANCELLATION_SSCS_BENEFIT;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_INITIATION_SSCS_BENEFIT;

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

    public void getAllTaskIds() {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = contextClassLoader.getResourceAsStream(WA_TASK_INITIATION_SSCS_BENEFIT.getFileName());
        decision = dmnEngine.parseDecision(WA_TASK_INITIATION_SSCS_BENEFIT.getKey(), inputStream);

        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        logic.getRules().forEach(r -> System.out.println(r.getConclusions().get(0).getExpression()));
    }

}
