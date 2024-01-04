package uk.gov.hmcts.reform.sscstaskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_COMPLETION_SSCS_BENEFIT;

class CamundaTaskCompletionTest extends DmnDecisionTableBaseUnitTest {

    private static final String BLANK = null;

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_COMPLETION_SSCS_BENEFIT;
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            eventAutoCompletesTasks("requestForInformation","reviewIncompleteAppeal", BLANK),
            eventAutoCompletesTasks("interlocInformationReceived",
                                    "reviewInformationRequested", "reviewAdminAction", BLANK),
            eventAutoCompletesTasks("validSendToInterloc",
                                    "reviewInformationRequested", "reviewAdminAction",
                                    "reviewConfidentialityRequest", "reviewReinstatementRequestJudge",
                                    "referredToInterlocJudge", "referredToInterlocTCW", "reviewBfDate", BLANK),
            eventAutoCompletesTasks("interlocSendToTcw",
                                    "reviewInformationRequested", "reviewAdminAction",
                                    "reviewUrgentHearingRequest", "reviewReinstatementRequestJudge",
                                    "referredByTcwPreHearing", "ftaNotProvidedAppointeeDetailsJudge",
                                    "referredByAdminJudgePreHearing", "referredToInterlocJudge", "reviewBfDate",
                                    "referredByAdminJudgePostHearing", "referredByTcwPostHearing", BLANK),
            eventAutoCompletesTasks("hmctsResponseReviewed","reviewFtaResponse", "reviewFtaDueDate", BLANK),
            eventAutoCompletesTasks("requestTranslationFromWLU","reviewBilingualDocument", BLANK),
            eventAutoCompletesTasks("actionFurtherEvidence",
                                    "issueOutstandingTranslation","actionUnprocessedCorrespondence", BLANK),
            eventAutoCompletesTasks("reviewConfidentialityRequest","reviewConfidentialityRequest", BLANK),
            eventAutoCompletesTasks("reviewPhmeRequest","reviewPheRequestJudge", BLANK),
            eventAutoCompletesTasks("decisionIssued",
                                    "referredByTcwPreHearing", "ftaNotProvidedAppointeeDetailsJudge",
                                    "referredByAdminJudgePreHearing", "referredToInterlocJudge",
                                    "reviewFtaValidityChallenge", "referredToInterlocTCW", "reviewNonCompliantAppeal",
                                    "ftaNotProvidedAppointeeDetailsTcw", "referredByAdminJudgePostHearing",
                                    "referredByTcwPostHearing", BLANK),
            eventAutoCompletesTasks("struckOut",
                                   "ftaNotProvidedAppointeeDetailsJudge", "referredByAdminJudgePreHearing",
                                    "referredByAdminJudgePostHearing", BLANK),
            eventAutoCompletesTasks("abateCase","ftaNotProvidedAppointeeDetailsJudge", BLANK),
            eventAutoCompletesTasks("writeFinalDecision",
                                    "referredByTcwPreHearing", "prepareForHearingJudge",
                                    "ftaNotProvidedAppointeeDetailsJudge", "referredByAdminJudgePreHearing",
                                    "referredByAdminJudgePostHearing", "referredByTcwPostHearing", BLANK),
            eventAutoCompletesTasks("actionPostponementRequest","reviewPostponementRequestJudge",
                                    "reviewPostponementRequestTCW", BLANK),
            eventAutoCompletesTasks("adjournCase", "prepareForHearingJudge", BLANK),
            eventAutoCompletesTasks("issueAdjournmentNotice", "writeDecisionJudge", BLANK),
            eventAutoCompletesTasks("sendToAdmin",
                                    "reviewConfidentialityRequest", "reviewUrgentHearingRequest",
                                    "reviewReinstatementRequestJudge", "referredByTcwPreHearing",
                                    "ftaNotProvidedAppointeeDetailsJudge", "referredByAdminJudgePreHearing",
                                    "referredToInterlocJudge", "reviewFtaValidityChallenge",
                                    "referredToInterlocTCW", "referredByJudge", "reviewNonCompliantAppeal",
                                    "ftaNotProvidedAppointeeDetailsTcw", "referredByAdminTcw",
                                    "reviewRemittedDecisionandProvideListingDirections",
                                    "referredByAdminJudgePostHearing", "referredByTcwPostHearing",
                                    "reviewLibertytoApplyApplication", "reviewLateStatementofReasonsApplication",
                                    "reviewSetAsideApplication", "reviewPermissiontoAppealApplication",
                                    "reviewCorrectionApplicationJudge", BLANK),
            eventAutoCompletesTasks("directionIssued",
                                    "reviewConfidentialityRequest", "reviewUrgentHearingRequest",
                                    "reviewReinstatementRequestJudge", "referredByTcwPreHearing",
                                    "ftaNotProvidedAppointeeDetailsJudge", "referredByAdminJudgePreHearing",
                                    "referredToInterlocJudge", "reviewFtaValidityChallenge",
                                    "ftaRequestTimeExtension", "referredToInterlocTCW",
                                    "ftaResponseOverdue", "referredByJudge", "reviewNonCompliantAppeal",
                                    "ftaNotProvidedAppointeeDetailsTcw", "referredByAdminTcw",
                                    "reviewLibertytoApplyApplication", "reviewLateStatementofReasonsApplication",
                                    "reviewPermissiontoAppealApplication", "reviewSetAsideApplication",
                                    "reviewRemittedDecisionandProvideListingDirections",
                                    "reviewCorrectionApplicationJudge", "referredByAdminJudgePostHearing",
                                    "referredByTcwPostHearing", BLANK),
            eventAutoCompletesTasks("issueFinalDecision",
                                    "reviewConfidentialityRequest", "writeDecisionJudge",
                                    "reviewCorrectionApplicationJudge", BLANK),
            eventAutoCompletesTasks("interlocReviewStateAmend","reviewConfidentialityRequest",
                                    "reviewUrgentHearingRequest", "reviewReinstatementRequestJudge",
                                    "reviewPheRequestJudge", "ftaNotProvidedAppointeeDetailsJudge",
                                    "referredByTcwPreHearing", "referredByAdminJudgePreHearing",
                                    "referredToInterlocJudge", "reviewFtaValidityChallenge",
                                    "ftaRequestTimeExtension", "referredToInterlocTCW",
                                    "ftaResponseOverdue", "referredByJudge", "processAudioVideoEvidence",
                                    "reviewNonCompliantAppeal", "ftaNotProvidedAppointeeDetailsTcw",
                                    "referredByAdminTcw", "referredByAdminJudgePostHearing",
                                    "referredByTcwPostHearing", BLANK),
            eventAutoCompletesTasks("uploadWelshDocument","reviewValidAppeal", BLANK),
            eventAutoCompletesTasks("amendDueDate", "reviewBfDate", BLANK),
            eventAutoCompletesTasks("updateListingRequirement","reviewListingError", BLANK),
            eventAutoCompletesTasks("createBundle","allocateCaseRolesAndCreateBundle", BLANK),
            eventAutoCompletesTasks("confirmPanelComposition", "confirmPanelComposition", BLANK),
            eventAutoCompletesTasks("adminActionCorrection", "reviewCorrectionApplicationAdmin", BLANK),
            eventAutoCompletesTasks("tcwReferToJudge", "reviewFtaValidityChallenge", "referredToInterlocTCW",
                                    "referredByJudge", "reviewNonCompliantAppeal", "ftaNotProvidedAppointeeDetailsTcw",
                                    "referredByAdminTcw", BLANK),
            eventAutoCompletesTasks("processAudioVideo", "processAudioVideoEvidence", BLANK),
            eventAutoCompletesTasks("sORWrite",
                                    "writeStatementofReason", "reviewPermissiontoAppealApplication",
                                    BLANK),
            eventAutoCompletesTasks("postHearingReview",
                                    "reviewLibertytoApplyApplication", "reviewCorrectionApplicationJudge",
                                    "reviewLateStatementofReasonsApplication", "reviewPermissiontoAppealApplication",
                                    "reviewSetAsideApplication", BLANK)
        );
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource("scenarioProvider")
    void given_event_ids_should_evaluate_dmn(String eventId, Map<String, Map<String, Object>> map, Set<Map<String, String>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("additionalData", map);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(new HashSet<Map<String,Object>>(dmnDecisionTableResult.getResultList()), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(45));
    }

    public static Arguments eventAutoCompletesTasks(String event, String... tasks) {
        return Arguments.of(event, null, Arrays.stream(tasks).map(t -> outputMap(t)).collect(Collectors.toSet())
        );
    }

    public static Arguments eventAutoCompletesTasks(String event, Map<String, Object> caseData, String... tasks) {
        return Arguments.of(event,
                            Map.of("Data", caseData),
                            Arrays.stream(tasks).map(t -> outputMap(t)).collect(Collectors.toSet())
        );
    }

    private static Map outputMap(String taskId) {
        if (taskId != null) {
            return Map.of(
                "taskType", taskId,
                "completionMode", "Auto"
            );
        }
        return Map.of(
            "completionMode", "Auto"
        );
    }
}
