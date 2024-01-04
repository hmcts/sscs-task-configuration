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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_CANCELLATION_SSCS_BENEFIT;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.CancellationScenarioBuilder.event;

class CamundaTaskCancellationTest extends DmnDecisionTableBaseUnitTest {
    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_CANCELLATION_SSCS_BENEFIT;
    }

    static Stream<Arguments> scenarioProvider() {
        return Stream.of(
            event("addHearing").reconfigureAll().build(),
            event("caseUpdated").reconfigureAll().build(),
            event("voidCase")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewInformationRequested")
                .cancel("reviewFtaResponse")
                .cancel("reviewFtaDueDate")
                .cancel("reviewConfidentialityRequest")
                .cancel("reviewReinstatementRequestJudge")
                .cancel("reviewPheRequestJudge")
                .cancel("ftaNotProvidedAppointeeDetailsJudge")
                .cancel("reviewPostponementRequestJudge")
                .cancel("reviewUrgentHearingRequest")
                .cancel("referredByTcwPreHearing")
                .cancel("prepareForHearingJudge")
                .cancel("writeDecisionJudge")
                .cancel("reviewValidAppeal")
                .cancel("reviewListingError")
                .cancel("reviewRoboticFail")
                .cancel("allocateCaseRolesAndCreateBundle")
                .cancel("reviewOutstandingDraftDecision")
                .cancel("referredByAdminJudgePreHearing")
                .cancel("confirmPanelComposition")
                .cancel("referredToInterlocJudge")
                .cancel("contactParties")
                .cancel("reviewFtaValidityChallenge")
                .cancel("ftaRequestTimeExtension")
                .cancel("prepareForHearingTribunalMember")
                .cancel("reviewPostponementRequestTCW")
                .cancel("referredToInterlocTCW")
                .cancel("ftaResponseOverdue")
                .cancel("referredByJudge")
                .cancel("processAudioVideoEvidence")
                .cancel("reviewNonCompliantAppeal")
                .cancel("ftaNotProvidedAppointeeDetailsTcw")
                .cancel("referredByAdminTcw")
                .cancel("referredByAdminJudgePostHearing")
                .cancel("referredByTcwPostHearing")
                .cancel("prepareHearingAppraiser")
                .build(),
            event("appealWithdrawn")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewInformationRequested")
                .cancel("reviewFtaResponse")
                .cancel("reviewFtaDueDate")
                .cancel("reviewConfidentialityRequest")
                .cancel("reviewReinstatementRequestJudge")
                .cancel("reviewPheRequestJudge")
                .cancel("ftaNotProvidedAppointeeDetailsJudge")
                .cancel("reviewPostponementRequestJudge")
                .cancel("reviewUrgentHearingRequest")
                .cancel("referredByTcwPreHearing")
                .cancel("prepareForHearingJudge")
                .cancel("writeDecisionJudge")
                .cancel("reviewReinstatementRequestJudge")
                .cancel("reviewValidAppeal")
                .cancel("reviewListingError")
                .cancel("reviewRoboticFail")
                .cancel("allocateCaseRolesAndCreateBundle")
                .cancel("reviewOutstandingDraftDecision")
                .cancel("referredByAdminJudgePreHearing")
                .cancel("confirmPanelComposition")
                .cancel("referredToInterlocJudge")
                .cancel("contactParties")
                .cancel("reviewFtaValidityChallenge")
                .cancel("ftaRequestTimeExtension")
                .cancel("prepareForHearingTribunalMember")
                .cancel("reviewPostponementRequestTCW")
                .cancel("referredToInterlocTCW")
                .cancel("ftaResponseOverdue")
                .cancel("referredByJudge")
                .cancel("processAudioVideoEvidence")
                .cancel("reviewNonCompliantAppeal")
                .cancel("ftaNotProvidedAppointeeDetailsTcw")
                .cancel("referredByAdminTcw")
                .cancel("referredByAdminJudgePostHearing")
                .cancel("referredByTcwPostHearing")
                .cancel("prepareHearingAppraiser")
                .build(),
            event("appealDormant")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewInformationRequested")
                .cancel("reviewFtaResponse")
                .cancel("reviewFtaDueDate")
                .cancel("reviewConfidentialityRequest")
                .cancel("reviewReinstatementRequestJudge")
                .cancel("reviewPheRequestJudge")
                .cancel("ftaNotProvidedAppointeeDetailsJudge")
                .cancel("reviewPostponementRequestJudge")
                .cancel("reviewUrgentHearingRequest")
                .cancel("referredByTcwPreHearing")
                .cancel("prepareForHearingJudge")
                .cancel("writeDecisionJudge")
                .cancel("reviewReinstatementRequestJudge")
                .cancel("reviewValidAppeal")
                .cancel("reviewListingError")
                .cancel("reviewRoboticFail")
                .cancel("allocateCaseRolesAndCreateBundle")
                .cancel("reviewOutstandingDraftDecision")
                .cancel("referredByAdminJudgePreHearing")
                .cancel("confirmPanelComposition")
                .cancel("referredToInterlocJudge")
                .cancel("contactParties")
                .cancel("reviewFtaValidityChallenge")
                .cancel("ftaRequestTimeExtension")
                .cancel("prepareForHearingTribunalMember")
                .cancel("reviewPostponementRequestTCW")
                .cancel("referredToInterlocTCW")
                .cancel("ftaResponseOverdue")
                .cancel("referredByJudge")
                .cancel("processAudioVideoEvidence")
                .cancel("reviewNonCompliantAppeal")
                .cancel("ftaNotProvidedAppointeeDetailsTcw")
                .cancel("referredByAdminTcw")
                .cancel("referredByAdminJudgePostHearing")
                .cancel("referredByTcwPostHearing")
                .cancel("prepareHearingAppraiser")
                .build(),
            event("confirmLapsed")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewInformationRequested")
                .cancel("reviewFtaResponse")
                .cancel("reviewConfidentialityRequest")
                .cancel("reviewReinstatementRequestJudge")
                .cancel("reviewPheRequestJudge")
                .cancel("ftaNotProvidedAppointeeDetailsJudge")
                .cancel("reviewPostponementRequestJudge")
                .cancel("reviewUrgentHearingRequest")
                .cancel("referredByTcwPreHearing")
                .cancel("prepareForHearingJudge")
                .cancel("writeDecisionJudge")
                .cancel("reviewReinstatementRequestJudge")
                .cancel("allocateCaseRolesAndCreateBundle")
                .cancel("reviewOutstandingDraftDecision")
                .cancel("referredByAdminJudgePreHearing")
                .cancel("confirmPanelComposition")
                .cancel("referredToInterlocJudge")
                .cancel("contactParties")
                .cancel("reviewFtaValidityChallenge")
                .cancel("ftaRequestTimeExtension")
                .cancel("prepareForHearingTribunalMember")
                .cancel("reviewPostponementRequestTCW")
                .cancel("referredToInterlocTCW")
                .cancel("ftaResponseOverdue")
                .cancel("referredByJudge")
                .cancel("processAudioVideoEvidence")
                .cancel("reviewNonCompliantAppeal")
                .cancel("ftaNotProvidedAppointeeDetailsTcw")
                .cancel("referredByAdminTcw")
                .cancel("referredByAdminJudgePostHearing")
                .cancel("referredByTcwPostHearing")
                .cancel("prepareHearingAppraiser")
                .build(),
            event("struckOut")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewInformationRequested")
                .cancel("reviewFtaResponse")
                .cancel("reviewFtaDueDate")
                .cancel("reviewConfidentialityRequest")
                .cancel("reviewReinstatementRequestJudge")
                .cancel("reviewPheRequestJudge")
                .cancel("reviewPostponementRequestJudge")
                .cancel("reviewUrgentHearingRequest")
                .cancel("prepareForHearingJudge")
                .cancel("writeDecisionJudge")
                .cancel("reviewReinstatementRequestJudge")
                .cancel("reviewValidAppeal")
                .cancel("reviewListingError")
                .cancel("reviewRoboticFail")
                .cancel("allocateCaseRolesAndCreateBundle")
                .cancel("reviewOutstandingDraftDecision")
                .cancel("referredByAdminJudgePreHearing")
                .cancel("confirmPanelComposition")
                .cancel("referredToInterlocJudge")
                .cancel("contactParties")
                .cancel("reviewFtaValidityChallenge")
                .cancel("ftaRequestTimeExtension")
                .cancel("prepareForHearingTribunalMember")
                .cancel("reviewPostponementRequestTCW")
                .cancel("referredToInterlocTCW")
                .cancel("ftaResponseOverdue")
                .cancel("referredByJudge")
                .cancel("processAudioVideoEvidence")
                .cancel("ftaNotProvidedAppointeeDetailsTcw")
                .cancel("referredByAdminTcw")
                .cancel("reviewNonCompliantAppeal")
                .cancel("referredByAdminJudgePostHearing")
                .cancel("prepareHearingAppraiser")
                .build(),
            event("validSendToInterloc")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewBfDate")
                .cancel("contactParties")
                .build(),
            event("makeCaseUrgent")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewInformationRequested")
                .cancel("reviewBfDate")
                .cancel("contactParties")
                .build(),
            event("readyToList")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewInformationRequested")
                .cancel("reviewFtaResponse")
                .cancel("reviewFtaDueDate")
                .cancel("contactParties")
                .build(),
            event("decisionIssued")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewInformationRequested")
                .cancel("reviewConfidentialityRequest")
                .cancel("reviewUrgentHearingRequest")
                .cancel("referredByTcwPreHearing")
                .cancel("prepareForHearingJudge")
                .cancel("writeDecisionJudge")
                .cancel("reviewReinstatementRequestJudge")
                .cancel("reviewPheRequestJudge")
                .cancel("ftaNotProvidedAppointeeDetailsJudge")
                .cancel("reviewPostponementRequestJudge")
                .cancel("contactParties")
                .cancel("ftaRequestTimeExtension")
                .cancel("prepareForHearingTribunalMember")
                .cancel("reviewPostponementRequestTCW")
                .cancel("ftaResponseOverdue")
                .cancel("referredByJudge")
                .cancel("processAudioVideoEvidence")
                .cancel("reviewNonCompliantAppeal")
                .cancel("ftaNotProvidedAppointeeDetailsTcw")
                .cancel("referredByAdminTcw")
                .cancel("referredByTcwPostHearing")
                .cancel("prepareHearingAppraiser")
                .build(),
            event("issueFinalDecision")
                .cancel("reviewUrgentHearingRequest")
                .cancel("referredByTcwPreHearing")
                .cancel("prepareForHearingJudge")
                .cancel("writeDecisionJudge")
                .cancel("reviewReinstatementRequestJudge")
                .cancel("reviewOutstandingDraftDecision")
                .cancel("reviewPheRequestJudge")
                .cancel("ftaNotProvidedAppointeeDetailsJudge")
                .cancel("reviewPostponementRequestJudge")
                .cancel("referredByAdminJudgePreHearing")
                .cancel("confirmPanelComposition")
                .cancel("referredToInterlocJudge")
                .cancel("reviewFtaValidityChallenge")
                .cancel("ftaRequestTimeExtension")
                .cancel("prepareForHearingTribunalMember")
                .cancel("reviewPostponementRequestTCW")
                .cancel("referredToInterlocTCW")
                .cancel("ftaResponseOverdue")
                .cancel("referredByJudge")
                .cancel("processAudioVideoEvidence")
                .cancel("reviewNonCompliantAppeal")
                .cancel("ftaNotProvidedAppointeeDetailsTcw")
                .cancel("referredByAdminTcw")
                .cancel("referredByAdminJudgePostHearing")
                .cancel("referredByTcwPostHearing")
                .cancel("prepareHearingAppraiser")
                .build(),
            event("cancelTranslations")
                .cancel("Translation Tasks")
                .build(),
            event("interlocReviewStateAmend")
                .cancel("prepareForHearingJudge")
                .cancel("writeDecisionJudge")
                .cancel("prepareForHearingTribunalMember")
                .cancel("prepareHearingAppraiser")
                .build(),
            event("actionPostponementRequest")
                .cancel("reviewPostponementRequestJudge")
                .build(),
            event("cancelTranslations")
                .cancel("Translation Tasks")
                .build(),
            event("interlocSendToTcw")
                .cancel("reviewBfDate")
                .build(),
            event("issueAdjournmentNotice")
                .cancel("reviewOutstandingDraftDecision")
                .cancel("prepareForHearingTribunalMember")
                .build(),
            event("sentToDwp")
                .cancel("reviewValidAppeal")
                .build(),
            event("responseReceived")
                .cancel("ftaResponseOverdue")
                .build(),
            event("adminSendTorRsponseReceived")
                .cancel("ftaResponseOverdue")
                .build(),
            event("postponementGranted")
                .cancel("prepareForHearingJudge")
                .cancel("prepareForHearingTribunalMember")
                .cancel("prepareHearingAppraiser")
                .cancel("allocateCaseRolesAndCreateBundle")
                .build()
        );
    }

    @ParameterizedTest(name = "from state: {0}, event id: {1}, state: {2}")
    @MethodSource("scenarioProvider")
    void given_multiple_event_ids_should_evaluate_dmn(String fromState,
                                                      String eventId,
                                                      String state,
                                                      Map<String, Object> map,
                                                      Set<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("fromState", fromState);
        inputVariables.putValue("event", eventId);
        inputVariables.putValue("state", state);
        inputVariables.putValue("additionalData", map);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(new HashSet<Map<String,Object>>(dmnDecisionTableResult.getResultList()), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(3));
        assertThat(logic.getOutputs().size(), is(4));
        assertThat(logic.getRules().size(), is(42));
    }
}
