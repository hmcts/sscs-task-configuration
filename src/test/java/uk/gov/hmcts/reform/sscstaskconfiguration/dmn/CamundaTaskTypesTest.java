package uk.gov.hmcts.reform.sscstaskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.junit.Assert.assertTrue;

class CamundaTaskTypesTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_TYPES_SSCS_BENEFIT;
    }

    @Test
    void task_type_entries_have_correct_format() {
        VariableMap inputVariables = new VariableMapImpl();
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        for (Map<String,Object> taskType : dmnDecisionTableResult.getResultList()) {
            assertThat((String) taskType.get("taskTypeId"), matchesPattern("^[a-z][A-Za-z0-9]*$"));
        }
    }

    @Test
    void includes_all_tasks() {
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();

        Set<String> taskTypeIds = new HashSet<>();
        logic.getRules().forEach(r -> taskTypeIds.add(r.getConclusions().get(0).getExpression()));
        Set<String> taskIds = getAllTaskIds();

        assertThat(taskTypeIds, is(taskIds));
        assertThat(taskTypeIds.size(), is(taskIds.size()));
    }

    @Test
    void check_dmn_changed() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(1));
        assertThat(logic.getOutputs().size(), is(2));
        assertThat(logic.getRules().size(), is(61));
    }
}
