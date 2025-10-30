package uk.gov.hmcts.reform.sscstaskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTableBaseUnitTest;
import uk.gov.hmcts.reform.sscstaskconfiguration.utils.CourtSpecificCalendars;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_INITIATION_SSCS_BENEFIT;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.dynamicListValue;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.InitiationScenarioBuilder.event;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.InitiationScenarioBuilder.eventWithState;

class CamundaTaskInitiationTest extends DmnDecisionTableBaseUnitTest {

    private static final LocalDate TODAY = LocalDate.now();

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_INITIATION_SSCS_BENEFIT;
    }

    static Stream<Arguments> scenarioProvider() {
        return Stream.of(
            event("draftToIncompleteApplication")
                .initiativesTask("reviewIncompleteAppeal",
                                 "Review Incomplete Appeal - CTSC", 5)
                .build(),
            event("incompleteApplicationReceived")
                .initiativesTask("reviewIncompleteAppeal",
                                 "Review Incomplete Appeal - CTSC", 5)
                .build(),
            event("requestForInformation")
                .initiativesTask("reviewInformationRequested",
                                 "Review Information Requested - CTSC", 3)
                .build(),
            event("dwpSupplementaryResponse")
                .initiativesTask("actionUnprocessedCorrespondence",
                                 "Action Unprocessed Correspondence - CTSC", 10)
                .build(),
            event("uploadDocument")
                .initiativesTask("actionUnprocessedCorrespondence",
                                 "Action Unprocessed Correspondence - CTSC", 10)
                .build(),
            event("attachScannedDocs")
                .initiativesTask("actionUnprocessedCorrespondence",
                                 "Action Unprocessed Correspondence - CTSC", 10)
                .build(),
            event("uploadDocumentFurtherEvidence")
                .initiativesTask("actionUnprocessedCorrespondence",
                                 "Action Unprocessed Correspondence - CTSC", 10)
                .build(),
            eventWithState("dwpUploadResponse", "withDwp")
                .withCaseData("dwpFurtherInfo", true)
                .initiativesTask("reviewFtaResponse", "Review FTA Response - CTSC", 2)
                .build(),
            event("dwpUploadResponse")
                .withCaseData("dwpFurtherInfo", false)
                .build(),
            event("sendToAdmin")
                .initiativesTask("reviewAdminAction", "Review Admin Action - CTSC", 10)
                .build(),
            event("dwpUploadResponse")
                .withCaseData("languagePreferenceWelsh", false)
                .build(),
            event("attachScannedDocs")
                .withCaseData("languagePreferenceWelsh", true)
                .initiativesTask("reviewBilingualDocument",
                                 "Review Bi-Lingual Document - CTSC", 10, "Translation Tasks")
                .initiativesTask("actionUnprocessedCorrespondence",
                                 "Action Unprocessed Correspondence - CTSC", 10)
                .build(),
            event("uploadDocumentFurtherEvidence")
                .withCaseData("languagePreferenceWelsh", true)
                .initiativesTask("reviewBilingualDocument",
                                 "Review Bi-Lingual Document - CTSC", 10, "Translation Tasks")
                .initiativesTask("actionUnprocessedCorrespondence",
                                 "Action Unprocessed Correspondence - CTSC",10)
                .build(),
            eventWithState("dwpUploadResponse", "withDwp")
                .withCaseData("dwpFurtherInfo", false)
                .build(),
            event("uploadDocument")
                .withCaseData("languagePreferenceWelsh", true)
                .initiativesTask("reviewBilingualDocument",
                                 "Review Bi-Lingual Document - CTSC", 10, "Translation Tasks")
                .initiativesTask("actionUnprocessedCorrespondence",
                                 "Action Unprocessed Correspondence - CTSC",
                                 10)
                .build(),
            event("uploadWelshDocument")
                .withCaseData("scannedDocumentTypes", List.of("reinstatementRequest"))
                .initiativesTask("issueOutstandingTranslation", "Issue Outstanding Translation - CTSC",
                                 10, "Translation Tasks")
                .build(),
            eventWithState("validAppealCreated", "validAppeal")
                .initiativesTaskWithDelay("reviewValidAppeal",
                                 "Review Valid Appeal - CTSC", 1, 5)
                .build(),
            eventWithState("readyToList", "listingError")
                .initiativesTask("reviewListingError",
                                 "Review Listing Error - CTSC", 3)
                .build(),
            event("sendToRoboticsError")
                .initiativesTask("reviewRoboticFail",
                                 "Review Robotic Fail - CTSC", 3)
                .build(),
            event("directionDueToday")
                .initiativesTask("reviewBfDate",
                                 "Review BF Date - CTSC", 5)
                .build(),
            event("prepareForHearing")
                .initiativesTask("allocateCaseRolesAndCreateBundle",
                                 "Allocate Case Roles and Create Bundle - RPC", 3)
                .build(),
            event("dwpSupplementaryResponse")
                .withCaseData("languagePreferenceWelsh", true)
                .initiativesTask("reviewBilingualDocument", "Review Bi-Lingual Document - CTSC",
                                 10, "Translation Tasks")
                .initiativesTask("actionUnprocessedCorrespondence", "Action Unprocessed Correspondence - CTSC", 10)
                .build(),
            event("uploadWelshDocument")
                .initiativesTask("issueOutstandingTranslation", "Issue Outstanding Translation - CTSC",
                                 10, "Translation Tasks")
                .build(),
            event("uploadWelshDocument")
                .withCaseData("scannedDocumentTypes", List.of("confidentialityRequest"))
                .initiativesTask("issueOutstandingTranslation", "Issue Outstanding Translation - CTSC",
                                 10, "Translation Tasks")
                .build(),
            event("uploadWelshDocument")
                .withCaseData("scannedDocumentTypes", List.of("urgentHearingRequest"))
                .initiativesTask("issueOutstandingTranslation", "Issue Outstanding Translation - CTSC",
                                 10, "Translation Tasks")
                .build(),
            event("actionPostponementRequest")
                .withCaseData("daysToHearing", 14)
                .build(),
            event("actionPostponementRequest")
                .withCaseData("daysToHearing", 6)
                .initiativesTask("contactParties", "Contact Parties - RPC", 1)
                .build(),
            eventWithState("correctionRequest","postHearing")
                .build(),
            eventWithState("actionFurtherEvidence","dormantAppealState")
                .withCaseData("scannedDocumentTypes", List.of("correctionApplication"))
                .withCaseData("furtherEvidenceAction", dynamicListValue("adminActionCorrection"))
                .initiativesTask("reviewCorrectionApplicationAdmin", "Review Correction Application - CTSC", 3)
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", Arrays.asList("tribunal-member-1", "tribunal-member-2", "tribunal-member-3", "appraiser-1", "appraiser-2"))
                .initiativesTask("prepareForHearingTribunalMember1", "Prepare for hearing - Tribunal Member 1", 2, "prepareForHearingTribunalMember")
                .initiativesTask("prepareForHearingTribunalMember2", "Prepare for hearing - Tribunal Member 2", 2, "prepareForHearingTribunalMember")
                .initiativesTask("prepareForHearingTribunalMember3", "Prepare for hearing - Tribunal Member 3", 2, "prepareForHearingTribunalMember")
                .initiativesTask("prepareHearingAppraiser1", "Prepare for hearing - Appraiser 1", 2, "prepareHearingAppraiser")
                .initiativesTask("prepareHearingAppraiser2", "Prepare for hearing - Appraiser 2", 2, "prepareHearingAppraiser")
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", List.of("tribunal-member-1"))
                .initiativesTask("prepareForHearingTribunalMember1", "Prepare for hearing - Tribunal Member 1", 2, "prepareForHearingTribunalMember")
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", List.of("tribunal-member-2"))
                .initiativesTask("prepareForHearingTribunalMember2", "Prepare for hearing - Tribunal Member 2", 2, "prepareForHearingTribunalMember")
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", List.of("tribunal-member-3"))
                .initiativesTask("prepareForHearingTribunalMember3", "Prepare for hearing - Tribunal Member 3", 2, "prepareForHearingTribunalMember")
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", List.of("appraiser-1"))
                .initiativesTask("prepareHearingAppraiser1", "Prepare for hearing - Appraiser 1", 2, "prepareHearingAppraiser")
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", List.of("appraiser-2"))
                .initiativesTask("prepareHearingAppraiser2", "Prepare for hearing - Appraiser 2", 2, "prepareHearingAppraiser")
                .build(),
            event("newCaseRolesAssigned")
                .withCaseData("assignedCaseRoles", Arrays.asList("hearing-judge",
                                                                 "tribunal-member-1", "tribunal-member-2", "tribunal-member-3",
                                                                 "appraiser-1", "appraiser-2"))
                .initiativesTask("prepareForHearingTribunalMember1", "Prepare for hearing - Tribunal Member 1", 2, "prepareForHearingTribunalMember")
                .initiativesTask("prepareForHearingTribunalMember2", "Prepare for hearing - Tribunal Member 2", 2, "prepareForHearingTribunalMember")
                .initiativesTask("prepareForHearingTribunalMember3", "Prepare for hearing - Tribunal Member 3", 2, "prepareForHearingTribunalMember")
                .initiativesTask("prepareHearingAppraiser1", "Prepare for hearing - Appraiser 1", 2, "prepareHearingAppraiser")
                .initiativesTask("prepareHearingAppraiser2", "Prepare for hearing - Appraiser 2", 2, "prepareHearingAppraiser")
                .build(),
            event("createBundle")
                .build(),
            event("uploadWelshDocument")
                .withCaseData("scannedDocumentTypes", List.of("postponementRequest"))
                .initiativesTask("issueOutstandingTranslation", "Issue Outstanding Translation - CTSC",
                                 10, "Translation Tasks")
                .build(),
            eventWithState("sentToDwp", "withDwp")
                .withCaseData("dwpDueDate", LocalDate.now().plusDays(7).toString())
                .withCaseData("caseManagementCategory", Map.of("value", Map.of("code", "childSupport")))
                .initiativesTaskWithDelay("reviewFtaDueDate", "Review FTA Due Date - CTSC", 7, 2)
                .build(),
            eventWithState("sentToDwp", "withDwp")
                .withCaseData("dwpDueDate", LocalDate.now().plusDays(7).toString())
                .withCaseData("caseManagementCategory", Map.of("value", Map.of("code", "PIP")))
                .initiativesTaskWithDelay("reviewFtaDueDate", "Review FTA Due Date - CTSC", 7, 2)
                .build(),
            event("directionDueToday")
                .withCaseData("directionTypeDl", Map.of("value", Map.of("code", "grantExtension")))
                .initiativesTask("reviewBfDate", "Review BF Date - CTSC", 5)
                .build(),
            eventWithState("sORRequest", "postHearing")
                .withCaseData("sscsHearingRecordings", emptyList())
                .initiativesTask("uploadHearingRecordingSORCTSC", "Upload Hearing Recording: SOR - CTSC", 2)
                .build(),
            event("libertyToApplyGranted")
                .initiativesTask("reviewPostHearingNoticeforListingRequirements", "Review Post Hearing Notice for Listing Requirements - CTSC", 10)
                .build(),
            event("reviewAndSetAside")
                .initiativesTask("reviewPostHearingNoticeforListingRequirements", "Review Post Hearing Notice for Listing Requirements - CTSC", 10)
                .build(),
            eventWithState("actionFurtherEvidence", "dormantAppealState")
                .withCaseData("scannedDocumentTypes", List.of("correctionApplication"))
                .withCaseData("furtherEvidenceAction", dynamicListValue("sendToInterlocReviewByJudge"))
                .initiativesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge - CTSC",3)
                .build(),
            eventWithState("postHearingRequest", "dormantAppealState")
                .withCaseData("action", "correction")
                .initiativesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge - CTSC",3)
                .build(),
            event("sendToFirstTier")
                .withCaseData("sendToFirstTier", Map.of("action", "remitted"))
                .initiativesTask("shareRemittedDecision", "Allocate Judge and Share Remitted Decision - CTSC", 20)
                .build(),
            eventWithState("sendToFirstTier", "dormantAppealState")
                .withCaseData("sendToFirstTier", Map.of("action", "remade"))
                .initiativesTask("shareRemadeDecision", "Share Remade Decision - CTSC", 20)
                .build(),
            eventWithState("sendToFirstTier", "dormantAppealState")
                .withCaseData("sendToFirstTier", Map.of("action", "refused"))
                .initiativesTask("shareRefusedDecision", "Share Refused Decision - CTSC", 20)
                .build(),
            eventWithState("libertyToApplyRequest", "dormantAppealState")
                .initiativesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge - CTSC", 3)
                .build(),
            eventWithState("permissionToAppealRequest", "dormantAppealState")
                .initiativesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge - CTSC", 3)
                .build(),
            eventWithState("setAsideRequest", "dormantAppealState")
                .initiativesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge - CTSC", 3)
                .build(),
            eventWithState("sORRequest", "dormantAppealState")
                .withCaseData("issueFinalDecisionDate", TODAY.minusDays(21))
                .initiativesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge - CTSC", 3)
                .build(),
            eventWithState("sORRequest", "dormantAppealState")
                .withCaseData("issueFinalDecisionDate", TODAY.minusDays(45))
                .initiativesTask("reviewLateStatementofReasonsApplicationAndAllocateJudge",
                                 "Review Late SOR Application and Allocate Judge - CTSC", 2,
                                 "reviewStatementofReasonsApplication")
                .build(),
            event("nonCompliantSendToInterloc")
                .withCaseData("interlocReferralReason", "reviewPostponementRequest")
                .build(),
            event("actionFurtherEvidence")
                .withCaseData("scannedDocumentTypes", List.of("postponementRequest"))
                .withCaseData("furtherEvidenceAction", dynamicListValue("sendToInterlocReviewByTcw"))
                .build(),
            eventWithState("validSendToInterloc", "dormantAppealState")
                .withCaseData("interlocReferralReason", "reviewPostponementRequest")
                .withCaseData("action", "reviewByTcw")
                .build()
        );
    }

    @ParameterizedTest(name = "event id: {0} post event state: {1} appeal type: {2}")
    @MethodSource("scenarioProvider")
    void given_multiple_event_ids_should_evaluate_dmn(String eventId,
                                                      String postEventState,
                                                      Map<String, Object> map,
                                                      List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", map);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(32));
    }

    static Stream<Arguments> scenarioProviderDateDefaults() {
        return Stream.of(
            Arguments.of(
                Map.of("isScottishCase", "No"),
                singletonList(
                    Map.of(
                        "date_defaults_1", Map.of(
                            "delayUntilOrigin", TODAY,
                            "delayUtilNonWorkingCalendar", CourtSpecificCalendars.ENGLAND_AND_WALES_CALENDAR
                        )
                    )
                )
            ),
            Arguments.of(
                Map.of(),
                singletonList(
                    Map.of(
                        "date_defaults_1", Map.of(
                            "delayUntilOrigin", TODAY,
                            "delayUtilNonWorkingCalendar", CourtSpecificCalendars.ENGLAND_AND_WALES_CALENDAR
                        )
                    )
                )
            ),
            Arguments.of(
                Map.of("isScottishCase", "Yes",
                       "processingVenue", "Dundee"),
                singletonList(
                    Map.of(
                        "date_defaults_1", Map.of(
                            "delayUntilOrigin", TODAY,
                            "delayUtilNonWorkingCalendar", CourtSpecificCalendars.SCOTLAND_CALENDAR_DUNDEE
                        )
                    )
                )
            ),
            Arguments.of(
                Map.of("isScottishCase", "Yes",
                       "processingVenue", "Edinburgh"),
                singletonList(
                    Map.of(
                        "date_defaults_1", Map.of(
                            "delayUntilOrigin", TODAY,
                            "delayUtilNonWorkingCalendar", CourtSpecificCalendars.SCOTLAND_CALENDAR_EDINBURGH
                        )
                    )
                )
            )
        );
    }

    @ParameterizedTest(name = "caseData: {1}")
    @MethodSource("scenarioProviderDateDefaults")
    void date_calculation_defaults_by_venue(Map<String, Object> caseData,
                                            List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("additionalData", Map.of("Data", caseData));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateRequiredDecision(
            "sscs-task-initiation-date-calculation-defaults", inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }
}
