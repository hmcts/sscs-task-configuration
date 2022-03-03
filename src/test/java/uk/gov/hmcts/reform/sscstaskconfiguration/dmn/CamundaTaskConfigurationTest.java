package uk.gov.hmcts.reform.sscstaskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTableBaseUnitTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_CONFIGURATION_SSCS_BENEFIT;

class CamundaTaskConfigurationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_CONFIGURATION_SSCS_BENEFIT;
    }

    static Stream<Arguments> scenarioProvider() {
        List<Map<String, Object>> expectationList = List.of(
            Map.of(
                "name", "location",
                "value", "1"
            ),
            Map.of(
                "name", "locationName",
                "value", "HMCTS"
            ),
            Map.of(
                "name", "workType",
                "value", "routine_work"
            ),
            Map.of(
                "name", "roleCategory",
                "value", "LEGAL_OPERATIONS"
            ),
            Map.of(
                "name", "description",
                "value", "Description of what needs to be done to complete the task"
            )
        );

        return Stream.of(
            Arguments.of(
                expectationList
            )
        );
    }


    @ParameterizedTest
    @MethodSource("scenarioProvider")
    void testTaskConfigurationDmn(List<Map<String, String>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", null);
        inputVariables.putValue("taskAttributes", null);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> dmnDecisionResultList = dmnDecisionTableResult.getResultList().stream()
            .collect(Collectors.toList());

        assertThat(dmnDecisionResultList, is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();

        assertThat(logic.getRules().size(), is(5));
    }

    private void getExpectedValue(List<Map<String, String>> rules, String name, String value) {
        Map<String, String> rule = new HashMap<>();
        rule.put("name", name);
        rule.put("value", value);
        rules.add(rule);
    }

}

