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
import uk.gov.hmcts.reform.sscstaskconfiguration.utils.Permissions;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_PERMISSIONS_SSCS_BENEFIT;

class CamundaTaskPermissionTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_PERMISSIONS_SSCS_BENEFIT;
    }

    static Stream<Arguments> scenarioProvider() {
        return Stream.of(
            Arguments.of(
                "someTaskType",
                "someCaseData",
                List.of(
                    Permissions.DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
                    Permissions.DEFAULT_TASK_SUPERVISOR_PERMISSIONS
                )
            ),
            Arguments.of(
                "reviewIncompleteAppeal",
                "someCaseData",
                Permissions.defaultCtscPermissions()
            ),
            Arguments.of(
                "reviewInformationRequested",
                "someCaseData",
                Permissions.defaultCtscPermissions()
            ),
            Arguments.of(
                "reviewBilingualDocument",
                "someCaseData",
                Permissions.defaultCtscPermissionsWithCompleteOwn()
            ),
            Arguments.of(
                "issueOutstandingTranslation",
                "someCaseData",
                Permissions.defaultCtscPermissions()
            ),
            Arguments.of(
                "reviewAdminAction",
                "someCaseData",
                Permissions.defaultCtscPermissions()
            ),
            Arguments.of(
                "actionUnprocessedCorrespondence",
                "someCaseData",
                List.of(
                    Permissions.DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
                    Permissions.DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
                    Map.of(
                        "name", "allocated-ctsc-caseworker",
                        "value", "Read,Own,Claim,Unclaim,Manage,Cancel,UnclaimAssign,CompleteOwn",
                        "assignmentPriority", 1,
                        "roleCategory", "CTSC",
                        "autoAssignable", true
                    ),
                    Map.of(
                        "name", "ctsc",
                        "value", "Read,Own,Claim,Unclaim,Manage,Cancel,UnclaimAssign,CompleteOwn",
                        "assignmentPriority", 2,
                        "roleCategory", "CTSC",
                        "autoAssignable", false
                    ),
                    Map.of(
                        "name", "ctsc-team-leader",
                        "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel,CompleteOwn",
                        "assignmentPriority", 3,
                        "roleCategory", "CTSC",
                        "autoAssignable", false
                    )
                )
            ),
            Arguments.of(
                "reviewFtaDueDate",
                "someCaseData",
                Permissions.defaultCtscPermissions()
            ),
            Arguments.of(
                "reviewBilingualDocument",
                "someCaseData",
                Permissions.defaultCtscPermissionsWithCompleteOwn()
            ),
            Arguments.of(
                "reviewValidAppeal",
                "someCaseData",
                Permissions.defaultCtscPermissions()
            ),
            Arguments.of(
                "reviewListingError",
                "someCaseData",
                Permissions.defaultCtscPermissions()
            ),
            Arguments.of(
                "reviewRoboticFail",
                "someCaseData",
                Permissions.defaultCtscPermissions()
            ),
            Arguments.of(
                "reviewBfDate",
                "someCaseData",
                Permissions.defaultCtscPermissions()
            ),
            Arguments.of(
                "allocateCaseRolesAndCreateBundle",
                "someCaseData",
                Permissions.defaultAdminCaseWorkerPermissions()
            ),
            Arguments.of(
                "reviewOutstandingDraftDecision",
                "someCaseData",
                Permissions.defaultCtscPermissions()
            ),
            Arguments.of(
                "contactParties",
                "someCaseData",
                Permissions.defaultAdminCaseWorkerPermissionsWithCompleteOwn()
            ),
            Arguments.of(
                "reviewStatementofReasonsApplication",
                "someCaseData",
                Permissions.defaultCtscPermissions()
            ),
            Arguments.of(
                "reviewLibertytoApplyApplication",
                "someCaseData",
                List.of(
                    Permissions.DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
                    Permissions.DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
                    Map.of(
                        "name", "post-hearing-judge",
                        "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
                        "assignmentPriority", 1,
                        "roleCategory", "JUDICIAL",
                        "autoAssignable", true
                    ),
                    Map.of(
                        "name", "judge",
                        "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
                        "assignmentPriority", 2,
                        "roleCategory", "JUDICIAL",
                        "autoAssignable", false
                    )
                )
            ),
            Arguments.of(
                "reviewCorrectionApplicationJudge",
                "someCaseData",
                List.of(
                    Permissions.DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
                    Permissions.DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
                    Map.of(
                        "name", "post-hearing-judge",
                        "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
                        "assignmentPriority", 1,
                        "roleCategory", "JUDICIAL",
                        "autoAssignable", true
                    ),
                    Map.of(
                        "name", "judge",
                        "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
                        "assignmentPriority", 2,
                        "roleCategory", "JUDICIAL",
                        "autoAssignable", false
                    )
                )
            ),
            Arguments.of(
                "writeStatementofReason",
                "someCaseData",
                List.of(
                    Permissions.DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
                    Permissions.DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
                    Map.of(
                        "name", "hearing-judge",
                        "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
                        "assignmentPriority", 1,
                        "roleCategory", "JUDICIAL",
                        "autoAssignable", true
                    ),
                    Map.of(
                        "name", "judge",
                        "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
                        "assignmentPriority", 2,
                        "roleCategory", "JUDICIAL",
                        "autoAssignable", false
                    ),
                    Map.of(
                        "name", "fee-paid-judge",
                        "value", "Read,Own,Claim,Unclaim",
                        "assignmentPriority", 3,
                        "authorisations", "368",
                        "roleCategory", "JUDICIAL",
                        "autoAssignable", false
                    )
                ),
                Arguments.of(
                    "reviewStatementofReasons",
                    "someCaseData",
                    List.of(
                        Permissions.DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
                        Permissions.DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
                        Map.of(
                            "name", "hearing-judge",
                            "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
                            "assignmentPriority", 1,
                            "roleCategory", "JUDICIAL",
                            "autoAssignable", true
                        ),
                        Map.of(
                            "name", "judge",
                            "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
                            "assignmentPriority", 2,
                            "roleCategory", "JUDICIAL",
                            "autoAssignable", false
                        ),
                        Map.of(
                            "name", "fee-paid-judge",
                            "value", "Read,Own,Claim,Unclaim",
                            "assignmentPriority", 3,
                            "authorisations", "368",
                            "roleCategory", "JUDICIAL",
                            "autoAssignable", false
                        )
                    )
                )
            )
        );
    }

    @ParameterizedTest(name = "task type: {0} case data: {1}")
    @MethodSource("scenarioProvider")
    void given_null_or_empty_inputs_when_evaluate_dmn_it_returns_expected_rules(String taskType,
                                                                                String caseData,
                                                                                List<Map<String, String>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));
        inputVariables.putValue("case", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {

        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();

        List<String> inputColumnIds = asList("taskType", "case");
        //Inputs
        assertThat(logic.getInputs().size(), is(2));
        assertThatInputContainInOrder(inputColumnIds, logic.getInputs());
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
        assertThatOutputContainInOrder(outputColumnIds, logic.getOutputs());
        //Rules
        assertThat(logic.getRules().size(), is(23));
    }

    private void assertThatInputContainInOrder(List<String> inputColumnIds, List<DmnDecisionTableInputImpl> inputs) {
        IntStream.range(0, inputs.size())
            .forEach(i -> assertThat(inputs.get(i).getInputVariable(), is(inputColumnIds.get(i))));
    }

    private void assertThatOutputContainInOrder(List<String> outputColumnIds, List<DmnDecisionTableOutputImpl> output) {
        IntStream.range(0, output.size())
            .forEach(i -> assertThat(output.get(i).getOutputName(), is(outputColumnIds.get(i))));
    }
}
