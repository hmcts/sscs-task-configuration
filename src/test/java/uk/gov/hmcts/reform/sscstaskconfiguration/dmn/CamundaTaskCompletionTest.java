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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_COMPLETION_SSCS_BENEFIT;

class CamundaTaskCompletionTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_COMPLETION_SSCS_BENEFIT;
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            eventAutoCompletesTasks("nonCompliant","reviewTheAppeal"),
            eventAutoCompletesTasks("requestInfoIncompleteApplication","reviewIncompleteAppeal"),
            eventAutoCompletesTasks("interlocInformationReceived", "reviewInformationRequested", "reviewAdminAction"),
            eventAutoCompletesTasks("validSendToInterloc", "reviewInformationRequested", "reviewAdminAction",
                                    "reviewConfidentialityRequest", "referredToInterlocTCW"),
            eventAutoCompletesTasks("interlocSendToTcw","reviewInformationRequested", "reviewAdminAction",
                                    "reviewFtaDueDate", "reviewUrgentHearingRequest", "referredByTcwPreHearing",
                                    "referredByTcwPostHearing", "referredByAdminJudgePreHearing",
                                    "referredByAdminJudgePostHearing", "ftaNotProvidedAppointeeDetailsTcw"),
            eventAutoCompletesTasks("hmctsResponseReviewed","reviewFtaResponse"),
            eventAutoCompletesTasks("requestTranslationFromWLU","reviewBilingualDocument"),
            eventAutoCompletesTasks("actionFurtherEvidence","issueOutstandingTranslation"),
            eventAutoCompletesTasks("reviewConfidentialityRequest","reviewConfidentialityRequest"),
            eventAutoCompletesTasks("sendToAdmin","reviewConfidentialityRequest", "reviewUrgentHearingRequest",
                                    "referredByTcwPreHearing", "referredByTcwPostHearing",
                                    "referredByAdminJudgePreHearing", "referredByAdminJudgePostHearing",
                                    "referredByJudge", "referredToInterlocTCW", "reviewNonCompliantAppeal",
                                    "ftaNotProvidedAppointeeDetailsTcw"),
            eventAutoCompletesTasks("directionIssued","reviewConfidentialityRequest", "reviewUrgentHearingRequest",
                                    "referredByTcwPreHearing", "referredByTcwPostHearing",
                                    "referredByAdminJudgePreHearing", "referredByAdminJudgePostHearing",
                                    "ftaRequestTimeExtension", "referredByJudge", "referredToInterlocTCW",
                                    "reviewNonCompliantAppeal", "ftaNotProvidedAppointeeDetailsTcw"),
            eventAutoCompletesTasks("issueFinalDecision","reviewConfidentialityRequest", "writeDecisionJudge"),
            eventAutoCompletesTasks("interlocReviewStateAmend","reviewConfidentialityRequest",
                                    "reviewUrgentHearingRequest", "referredByTcwPreHearing",
                                    "referredByTcwPostHearing", "referredByAdminJudgePreHearing",
                                    "referredByAdminJudgePostHearing", "ftaRequestTimeExtension", "referredByJudge",
                                    "referredToInterlocTCW", "reviewNonCompliantAppeal",
                                    "ftaNotProvidedAppointeeDetailsTcw"),
            eventAutoCompletesTasks("decisionIssued", "referredByTcwPreHearing", "referredByTcwPostHearing",
                                    "referredByAdminJudgePreHearing", "referredByAdminJudgePostHearing",
                                    "referredToInterlocTCW", "reviewNonCompliantAppeal",
                                    "ftaNotProvidedAppointeeDetailsTcw"),
            eventAutoCompletesTasks("struckOut", "referredByTcwPreHearing", "referredByTcwPostHearing",
                                    "referredByAdminJudgePreHearing", "referredByAdminJudgePostHearing",
                                    "reviewNonCompliantAppeal"),
            eventAutoCompletesTasks("writeFinalDecision", "referredByTcwPreHearing", "referredByTcwPostHearing",
                                    "prepareForHearingJudge", "referredByAdminJudgePreHearing",
                                    "referredByAdminJudgePostHearing"),
            eventAutoCompletesTasks("adjournCase", "prepareForHearingJudge"),
            eventAutoCompletesTasks("issueAdjournmentNotice", "writeDecisionJudge"),
            eventAutoCompletesTasks("confirmPanelComposition", "confirmPanelComposition"),
            eventAutoCompletesTasks("tcwReferToJudge", "referredByJudge", "referredToInterlocTCW",
                                    "reviewNonCompliantAppeal", "ftaNotProvidedAppointeeDetailsTcw"),
            eventAutoCompletesTasks("actionPostponementRequest", "reviewPostponementRequestTCW")
        );
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource("scenarioProvider")
    void given_event_ids_should_evaluate_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {

        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(23));

    }

    public static Arguments eventAutoCompletesTasks(String event, String... tasks) {
        return Arguments.of(event, Arrays.stream(tasks).map(t -> Map.of(
                "taskType", t,
                "completionMode", "Auto"
            )).collect(Collectors.toList())
        );
    }
}
