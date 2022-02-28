package uk.gov.hmcts.reform.sscstaskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableInputImpl;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableOutputImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTableBaseUnitTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_PERMISSIONS_SSCS_BENEFIT;

class CamundaTaskPermissionTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_PERMISSIONS_SSCS_BENEFIT;
    }

    @ParameterizedTest
    @CsvSource({"ftaChallengeValidity", "nonCompliantCase"})
    void given_multiple_task_type_when_evaluate_dmn(String taskType) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));
        inputVariables.putValue("caseData",  null);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> dmnDecisionResultList = dmnDecisionTableResult.getResultList().stream()
            .collect(Collectors.toList());

        assertEquals(Map.of(
            "name", "caseworker-sscs-registrar",
            "value", "Read,Refer,Own,Manage,Cancel",
            "roleCategory", "LEGAL_OPERATIONS",
            "assignmentPriority", 1,
            "autoAssignable", true
        ),dmnDecisionResultList.get(0));
    }

     @Test
     void if_this_test_fails_needs_updating_with_your_changes() {

         //The purpose of this test is to prevent adding new rows without being tested
         DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();

         List<String> inputColumnIds = asList("taskType", "case");
         //Inputs
         assertThat(logic.getInputs().size(), is(2));
         //assertThatInputContainInOrder(inputColumnIds, logic.getInputs());
         //Outputs
         List<String> outputColumnIds = asList(
             "name",
             "value",
             "roleCategory",
             "authorisations",
             "assignmentPriority",
             "autoAssignable"
         );
         assertThat(logic.getOutputs().size(), is(6));
         //assertThatOutputContainInOrder(outputColumnIds, logic.getOutputs());
         //Rules
         assertThat(logic.getRules().size(), is(1));

     }

     //We only have one rule of premission now so we don't need checking order. It may need in the future so I comment its.
    // private void assertThatInputContainInOrder(List<String> inputColumnIds, List<DmnDecisionTableInputImpl> inputs) {
    //     IntStream.range(0, inputs.size())
    //         .forEach(i -> assertThat(inputs.get(i).getInputVariable(), is(inputColumnIds.get(i))));
    // }

    // private void assertThatOutputContainInOrder(List<String> outputColumnIds, List<DmnDecisionTableOutputImpl> output) {
    //     IntStream.range(0, output.size())
    //         .forEach(i -> assertThat(output.get(i).getOutputName(), is(outputColumnIds.get(i))));
    // }
}
