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

    static Map<String, Object> permission(String role, String value) {
        return Map.of(
            "name", role,
            "value", value,
            "autoAssignable", false
        );
    }

    static Map<String, Object> permission(String role, String value, String roleCategory) {
        return Map.of(
            "name", role,
            "value", value,
            "roleCategory", roleCategory,
            "autoAssignable", false
        );
    }

    static Map<String, Object> permission(String role, String value, String roleCategory, int assignmentPriority) {
        return Map.of(
            "name", role,
            "value", value,
            "roleCategory", roleCategory,
            "assignmentPriority", assignmentPriority,
            "autoAssignable", true
        );
    }

    static Map<String, Object> permission(String role, String value, String roleCategory, String authorisations) {
        return Map.of(
            "name", role,
            "value", value,
            "roleCategory", roleCategory,
            "authorisations", authorisations,
            "autoAssignable", false
        );
    }

    static Stream<Arguments> scenarioProvider() {
        return Stream.of(
            Arguments.of(
                "someTaskType",
                "someCaseData",
                List.of(
                    Map.of(
                        "name", "case-allocator",
                        "value", "Read,Own,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim",
                        "autoAssignable", false
                    ),
                    Map.of(
                        "name", "task-supervisor",
                        "value", "Read,Own,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim",
                        "autoAssignable", false
                    )
                )
            ),
            Arguments.of(
                "reviewIncompleteAppeal",
                "someCaseData",
                defaultCtscPermissions()
            ),
            Arguments.of(
                "reviewInformationRequested",
                "someCaseData",
                defaultCtscPermissions()
            ),
            Arguments.of(
                "reviewBilingualDocument",
                "someCaseData",
                defaultCtscPermissionsWithCompleteOwn()
            ),
            Arguments.of(
                "issueOutstandingTranslation",
                "someCaseData",
                defaultCtscPermissions()
            ),
            Arguments.of(
                "reviewAdminAction",
                "someCaseData",
                defaultCtscPermissions()
            ),
            Arguments.of(
                "reviewFtaDueDate",
                "someCaseData",
                defaultCtscPermissions()
            ),
            Arguments.of(
                "reviewFtaDueDate",
                "someCaseData",
                defaultCtscPermissions()
            ),
            Arguments.of(
                "reviewConfidentialityRequest",
                "someCaseData",
                defaultPermissionsJudgesReviewTasks()
            ),
            Arguments.of(
                "reviewReinstatementRequestJudge",
                "someCaseData",
                defaultPermissionsJudgesReviewTasks()
            ),
            Arguments.of(
                "reviewPheRequestJudge",
                "someCaseData",
                defaultPermissionsJudgesTasks()
            ),
            Arguments.of(
                "reviewPheRequestJudge",
                "someCaseData",
                defaultPermissionsJudgesTasks()
            ),
            Arguments.of(
                "reviewPostponementRequestJudge",
                "someCaseData",
                defaultPermissionsHearingJudgesTasks()
            )
        );
    }

    private static List<Map<String, Object>> defaultCtscPermissions() {
        return List.of(
            Map.of(
                "name", "case-allocator",
                "value", "Read,Own,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim",
                "autoAssignable", false
            ),
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Own,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim",
                "autoAssignable", false
            ),
            Map.of(
                "name", "Allocated-CTSC-Caseworker",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
                "assignmentPriority", 1,
                "roleCategory", "CTSC",
                "autoAssignable", true
            ),
            Map.of(
                "name", "CTSC-Administrator",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
                "assignmentPriority", 2,
                "roleCategory", "CTSC",
                "autoAssignable", false
            ),
            Map.of(
                "name", "CTSC-Team-Leader",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel",
                "assignmentPriority", 3,
                "roleCategory", "CTSC",
                "autoAssignable", false
            )
        );
    }

    private static List<Map<String, Object>> defaultCtscPermissionsWithCompleteOwn() {
        return List.of(
            Map.of(
                "name", "case-allocator",
                "value", "Read,Own,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim",
                "autoAssignable", false
            ),
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Own,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim",
                "autoAssignable", false
            ),
            Map.of(
                "name", "Allocated-CTSC-Caseworker",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,CompleteOwn",
                "assignmentPriority", 1,
                "roleCategory", "CTSC",
                "autoAssignable", true
            ),
            Map.of(
                "name", "CTSC-Administrator",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,CompleteOwn",
                "assignmentPriority", 2,
                "roleCategory", "CTSC",
                "autoAssignable", false
            ),
            Map.of(
                "name", "CTSC-Team-Leader",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel,CompleteOwn",
                "assignmentPriority", 3,
                "roleCategory", "CTSC",
                "autoAssignable", false
            )
        );
    }

    private static List<Map<String, Object>> defaultPermissionsJudgesTasks() {
        return List.of(
            permission("case-allocator","Read,Own,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
            permission("task-supervisor","Read,Own,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
            permission("tribunal-caseworker","Read,Execute,Unclaim", "LEGAL_OPERATIONS"),
            permission("interloc-judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1),
            permission("judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
            permission("fee-paid-judge","Read,Own,Claim,Unclaim", "JUDICIAL","368")
        );
    }

    private static List<Map<String, Object>> defaultPermissionsHearingJudgesTasks() {
        return List.of(
            permission("case-allocator","Read,Own,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
            permission("task-supervisor","Read,Own,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
            permission("tribunal-caseworker","Read,Execute,Unclaim", "LEGAL_OPERATIONS"),
            permission("hearing-judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1),
            permission("judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
            permission("fee-paid-judge","Read,Own,Claim,Unclaim", "JUDICIAL","368")
        );
    }

    private static List<Map<String, Object>> defaultPermissionsJudgesReviewTasks() {
        return List.of(
            permission("case-allocator","Read,Own,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
            permission("task-supervisor","Read,Own,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
            permission("tribunal-caseworker","Read,Execute,Unclaim", "LEGAL_OPERATIONS"),
            permission("interloc-judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Complete", "JUDICIAL", 1),
            permission("judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Complete", "JUDICIAL"),
            permission("fee-paid-judge","Read,Own,Claim,Unclaim", "JUDICIAL","368")
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
        assertThat(logic.getRules().size(), is(18));

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
