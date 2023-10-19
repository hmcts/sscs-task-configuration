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
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.Permissions.permission;

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
                "reviewFtaDueDate",
                "someCaseData",
                Permissions.defaultCtscPermissions()
            ),
            Arguments.of(
                "referredByTcwPreHearing",
                "someCaseData",
                List.of(
                    permission("case-allocator","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("task-supervisor","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("interloc-judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1),
                    permission("judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
                    permission("fee-paid-judge","Read,Own,Claim,Unclaim", "JUDICIAL","368")
                )
            ),
            Arguments.of(
                "prepareForHearingJudge",
                "someCaseData",
                List.of(
                    permission("case-allocator","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("task-supervisor","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("hearing-judge","Read,Own", "JUDICIAL", 1),
                    permission("judge","Read,Own", "JUDICIAL"),
                    permission("fee-paid-judge","Read,Own", "JUDICIAL")
                )
            ),
            Arguments.of(
                "writeDecisionJudge",
                "someCaseData",
                List.of(
                    permission("case-allocator","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("task-supervisor","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("hearing-judge","Read,Own", "JUDICIAL", 1),
                    permission("judge","Read,Own,Claim", "JUDICIAL"),
                    permission("fee-paid-judge","Read,Own,Claim", "JUDICIAL")
                )
            ),
            Arguments.of(
                "reviewUrgentHearingRequest",
                "someCaseData",
                Permissions.defaultPermissionsJudgesReviewTasks()
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
                "reviewValidAppeal",
                "someCaseData",
                Permissions.defaultCtscPermissionsWithCompleteOwn()
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
                "reviewConfidentialityRequest",
                "someCaseData",
                Permissions.defaultPermissionsJudgesReviewTasks()
            ),
            Arguments.of(
                "reviewReinstatementRequestJudge",
                "someCaseData",
            Permissions.defaultPermissionsJudgesReviewTasks()
            ),
            Arguments.of(
                "reviewPheRequestJudge",
                "someCaseData",
                Permissions.defaultPermissionsJudgesTasks()
            ),
            Arguments.of(
                "reviewPostponementRequestJudge",
                "someCaseData",
                List.of(
                    permission("case-allocator","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("task-supervisor","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("tribunal-caseworker","Read,Execute,Unclaim", "LEGAL_OPERATIONS"),
                    permission("hearing-judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1),
                    permission("judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL")
                )
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
                "referredByAdminJudgePreHearing",
                "someCaseData",
                List.of(
                    permission("case-allocator","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("task-supervisor","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("interloc-judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1),
                    permission("judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
                    permission("fee-paid-judge","Read,Own,Claim,Unclaim", "JUDICIAL", "368")
                )
            ),
            Arguments.of(
                "confirmPanelComposition",
                "someCaseData",
                List.of(
                    permission("case-allocator","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("task-supervisor","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("interloc-judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1),
                    permission("judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
                    permission("fee-paid-judge","Read,Own,Claim,Unclaim", "JUDICIAL", "368")
                )
            ),
            Arguments.of(
                "referredToInterlocJudge",
                "someCaseData",
                Permissions.defaultPermissionsJudgesTasks()
            ),
            Arguments.of(
                "contactParties",
                "someCaseData",
                Permissions.defaultAdminCaseWorkerPermissionsWithCompleteOwn()
            ),
            Arguments.of(
                "reviewCorrectionApplicationAdmin",
                "someCaseData",
                List.of(
                    permission("case-allocator","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("task-supervisor","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("allocated-ctsc-caseworker","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "CTSC", 1),
                    permission("ctsc","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "CTSC", 2, false),
                    permission("ctsc-team-leader","Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel,Complete", "CTSC", 3,false)
                )
            ),
            Arguments.of(
                "reviewFtaValidityChallenge",
                "someCaseData",
                Permissions.defaultPermissionsTcwTasks()
            ),
            Arguments.of(
                "ftaRequestTimeExtension",
                "someCaseData",
                Permissions.defaultPermissionsTcwTasks()
            ),
            Arguments.of(
                "prepareForHearingTribunalMember1",
                "someCaseData",
                Permissions.defaultJudicalMember1Permissions()
            ),
            Arguments.of(
                "prepareForHearingTribunalMember2",
                "someCaseData",
                Permissions.defaultJudicalMember2Permissions()
            ),
            Arguments.of(
                "prepareForHearingTribunalMember3",
                "someCaseData",
                Permissions.defaultJudicalMember3Permissions()
            ),
            Arguments.of(
                "reviewPostponementRequestTCW",
                "someCaseData",
                Permissions.defaultPermissionsTcwTasks()
            ),
            Arguments.of(
                "referredToInterlocTCW",
                "someCaseData",
                Permissions.defaultPermissionsTcwTasks()
            ),
            Arguments.of(
                "ftaResponseOverdue",
                "someCaseData",
                Permissions.defaultPermissionsTcwTasks()
            ),
            Arguments.of(
                "referredByJudge",
                "someCaseData",
                List.of(
                    permission("case-allocator","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("task-supervisor","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("allocated-tribunal-caseworker", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "LEGAL_OPERATIONS", 1),
                    permission("tribunal-caseworker","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "LEGAL_OPERATIONS")
                )
            ),
            Arguments.of(
                "processAudioVideoEvidence",
                "someCaseData",
                List.of(
                    permission("case-allocator","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("task-supervisor","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("allocated-tribunal-caseworker", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Complete", "LEGAL_OPERATIONS", 1),
                    permission("tribunal-caseworker","Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Complete", "LEGAL_OPERATIONS"),
                    permission("judge","Read,Execute,Unclaim,UnclaimAssign", "JUDICIAL")
                )
            ),
            Arguments.of(
                "reviewNonCompliantAppeal",
                "someCaseData",
                Permissions.defaultPermissionsTcwTasks()
            ),
            Arguments.of(
                "ftaNotProvidedAppointeeDetailsTcw",
                "someCaseData",
                Permissions.defaultPermissionsTcwTasks()
            ),
            Arguments.of(
                "referredByAdminTcw",
                "someCaseData",
                List.of(
                    permission("case-allocator","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("task-supervisor","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
                    permission("allocated-tribunal-caseworker", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "LEGAL_OPERATIONS", 1),
                    permission("tribunal-caseworker","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "LEGAL_OPERATIONS")
                )
            ),
            Arguments.of(
                "reviewStatementofReasonsApplication",
                "someCaseData",
                Permissions.defaultCtscPermissions()
            ),
            Arguments.of(
                "reviewLibertytoApplyApplication",
                "someCaseData",
                Permissions.defaultPermissionsPostHearingTasks()
            ),
            Arguments.of(
                "reviewCorrectionApplicationJudge",
                "someCaseData",
                Permissions.defaultPermissionsPostHearingTasks()
            ),
            Arguments.of(
                "writeStatementofReason",
                "someCaseData",
                List.of(
                    Permissions.DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
                    Permissions.DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
                    permission("hearing-judge", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1),
                    permission("judge", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
                    permission("fee-paid-judge", "Read,Own,Claim,Unclaim", "JUDICIAL", "368")
                )
            ),
            Arguments.of(
                "reviewStatementofReasons",
                "someCaseData",
                List.of(
                    Permissions.DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
                    Permissions.DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
                    permission("hearing-judge", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1),
                    permission("judge", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
                    permission("fee-paid-judge", "Read,Own,Claim,Unclaim", "JUDICIAL", "368")
                )
            ),
            Arguments.of(
                "reviewPermissiontoAppealApplication",
                "someCaseData",
                Permissions.defaultPermissionsPostHearingTasks()
            ),
            Arguments.of(
                "reviewRemittedDecisionandProvideListingDirections",
                "someCaseData",
                Permissions.defaultPermissionsPostHearingTasks()
            ),
            Arguments.of(
                "reviewPostHearingNoticeforListingRequirements",
                "someCaseData",
                List.of(
                    Permissions.DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
                    Permissions.DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
                    permission("allocated-ctsc-caseworker", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,CompleteOwn,CancelOwn", "CTSC", 1),
                    permission("ctsc", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,CompleteOwn,CancelOwn", "CTSC",2,false),
                    permission("ctsc-team-leader", "Read,Own,Claim,Unclaim,Manage,Assign,Unassign,Cancel,Complete", "CTSC", 3, false)
                )
            ),
            Arguments.of(
                "reviewSetAsideApplication",
                "someCaseData",
                Permissions.defaultPermissionsPostHearingTasks()
            ),
            Arguments.of(
                "shareRemittedDecision",
                "someCaseData",
                List.of(
                    Permissions.DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
                    Permissions.DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
                    permission("allocated-ctsc-caseworker","Read,Own,Claim,Unclaim,Manage,UnclaimAssign,CompleteOwn", "CTSC", 1),
                    permission("ctsc","Read,Own,Claim,Unclaim,Manage,UnclaimAssign,CompleteOwn", "CTSC", 2, false),
                    permission("ctsc-team-leader","Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel,Complete", "CTSC", 3,false)
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
        assertThat(logic.getRules().size(), is(44));
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
