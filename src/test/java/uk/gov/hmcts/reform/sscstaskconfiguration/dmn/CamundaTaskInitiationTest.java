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
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_INITIATION_SSCS_BENEFIT;
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
            Arguments.of(
                "nonCompliant",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "nonCompliantCase",
                        "name", "Review non-compliant appeal",
                        "workingDaysAllowed", 2,
                        "processCategories", "Non-compliant appeal"
                    )
                )
            ),
            Arguments.of(
                "draftToIncompleteApplication",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewIncompleteAppeal",
                        "name", "Review Incomplete Appeal",
                        "workingDaysAllowed", 5,
                        "processCategories", "reviewIncompleteAppeal"
                    )
                )
            ),
            Arguments.of(
                "incompleteApplicationReceived",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewIncompleteAppeal",
                        "name", "Review Incomplete Appeal",
                        "workingDaysAllowed", 5,
                        "processCategories", "reviewIncompleteAppeal"
                    )
                )
            ),
            Arguments.of(
                "requestInfoIncompleteApplication",
                "withDwp",
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewInformationRequested",
                        "name", "Review Information Requested",
                        "delayDuration", 2,
                        "workingDaysAllowed", 3,
                        "processCategories", "reviewInformationRequested"
                    )
                )
            ),
            Arguments.of(
                "dwpSupplementaryResponse",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "actionUnprocessedCorrespondence",
                        "name", "Action Unprocessed Correspondence",
                        "workingDaysAllowed", 10,
                        "processCategories", "actionUnprocessedCorrespondence"
                    )
                )
            ),
            Arguments.of(
                "uploadDocument",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "actionUnprocessedCorrespondence",
                        "name", "Action Unprocessed Correspondence",
                        "workingDaysAllowed", 10,
                        "processCategories", "actionUnprocessedCorrespondence"
                    )
                )
            ),
            Arguments.of(
                "attachScannedDocs",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "actionUnprocessedCorrespondence",
                        "name", "Action Unprocessed Correspondence",
                        "workingDaysAllowed", 10,
                        "processCategories", "actionUnprocessedCorrespondence"
                    )
                )
            ),
            Arguments.of(
                "uploadDocumentFurtherEvidence",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "actionUnprocessedCorrespondence",
                        "name", "Action Unprocessed Correspondence",
                        "workingDaysAllowed", 10,
                        "processCategories", "actionUnprocessedCorrespondence"
                    )
                )
            ),
            Arguments.of(
                "dwpUploadResponse",
                "withDwp",
                Map.of("Data", Map.of("dwpFurtherInfo", "Yes")),
                singletonList(
                    Map.of(
                        "taskId", "reviewFtaResponse",
                        "name", "Review FTA Response",
                        "workingDaysAllowed", 2,
                        "processCategories", "reviewFtaResponse"
                    )
                )
            ),
            Arguments.of(
                "dwpUploadResponse",
                "withDwp",
                Map.of("Data", Map.of("dwpFurtherInfo", "No")),
                List.of()
            ),
            Arguments.of(
                "uploadDocument",
                null,
                Map.of("Data", Map.of("languagePreferenceWelsh", true)),
                List.of(
                    Map.of(
                        "taskId", "reviewBilingualDocument",
                        "name", "Review Bi-Lingual Document",
                        "workingDaysAllowed", 10,
                        "processCategories", "Translation Tasks"
                    ),
                    Map.of(
                        "taskId", "actionUnprocessedCorrespondence",
                        "name", "Action Unprocessed Correspondence",
                        "workingDaysAllowed", 10,
                        "processCategories", "actionUnprocessedCorrespondence"
                    )
                )
            ),
            Arguments.of(
                "dwpUploadResponse",
                null,
                Map.of("Data", Map.of("languagePreferenceWelsh", false)),
                List.of()
            ),
            Arguments.of(
                "attachScannedDocs",
                null,
                Map.of("Data", Map.of("languagePreferenceWelsh", true)),
                List.of(
                    Map.of(
                        "taskId", "reviewBilingualDocument",
                        "name", "Review Bi-Lingual Document",
                        "workingDaysAllowed", 10,
                        "processCategories", "Translation Tasks"
                    ),
                    Map.of(
                        "taskId", "actionUnprocessedCorrespondence",
                        "name", "Action Unprocessed Correspondence",
                        "workingDaysAllowed", 10,
                        "processCategories", "actionUnprocessedCorrespondence"
                    )
                )
            ),
            event("uploadDocumentFurtherEvidence")
                .withCaseData("languagePreferenceWelsh", true)
                .initiativesTask("reviewBilingualDocument", "Review Bi-Lingual Document",
                                 10, "Translation Tasks")
                .initiativesTask("actionUnprocessedCorrespondence", "Action Unprocessed Correspondence",
                                 10)
                .build(),
            event("actionFurtherEvidence")
                .withCaseData("scannedDocumentTypes", List.of("reinstatementRequest"))
                .initiativesTask("reviewReinstatementRequestJudge", "Review Reinstatement Request", 2)
                .build(),
            event("uploadWelshDocument")
                .withCaseData("scannedDocumentTypes", List.of("reinstatementRequest"))
                .initiativesTask("issueOutstandingTranslation", "Issue Outstanding Translation",
                                 10, "Translation Tasks")
                .initiativesTask("reviewReinstatementRequestJudge", "Review Reinstatement Request", 2)
                .build(),
            event("manageWelshDocuments")
                .withCaseData("scannedDocumentTypes", List.of("reinstatementRequest"))
                .initiativesTask("reviewReinstatementRequestJudge", "Review Reinstatement Request", 2)
                .build(),
            Arguments.of(
                "validAppealCreated",
                "validAppeal",
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewValidAppeal",
                        "name", "Review Valid Appeal",
                        "delayDuration", 3,
                        "workingDaysAllowed", 5,
                        "processCategories", "reviewValidAppeal"
                    )
                )
            ),
            Arguments.of(
                "readyToList",
                "listingError",
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewListingError",
                        "name", "Review Listing Error",
                        "workingDaysAllowed", 3,
                        "processCategories", "reviewListingError"
                    )
                )
            ),
            Arguments.of(
                "sendToRoboticsError",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewRoboticFail",
                        "name", "Review Robotic Fail",
                        "workingDaysAllowed", 3,
                        "processCategories", "reviewRoboticFail"
                    )
                )
            ),
            Arguments.of(
                "directionDueToday",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewBfDate",
                        "name", "Review BF Date",
                        "workingDaysAllowed", 5,
                        "processCategories", "reviewBfDate"
                    )
                )
            ),
            Arguments.of(
                "prepareForHearing",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "allocateCaseRolesAndCreateBundle",
                        "name", "Allocate Case Roles and Create Bundle",
                        "workingDaysAllowed", 3,
                        "processCategories", "allocateCaseRolesAndCreateBundle"
                    )
                )
            ),
            Arguments.of(
                "hearingToday",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewOutstandingDraftDecision",
                        "name", "Review Outstanding Draft Decision",
                        "workingDaysAllowed", 5,
                        "processCategories", "reviewOutstandingDraftDecision"
                    )
                )
            ),
            Arguments.of(
                "dwpUploadResponse",
                "withDwp",
                Map.of("Data", Map.of("dwpFurtherInfo", "Yes")),
                singletonList(
                    Map.of(
                        "taskId", "reviewFtaResponse",
                        "name", "Review FTA Response",
                        "workingDaysAllowed", 2,
                        "processCategories", "reviewFtaResponse"
                    )
                )
            ),
            event("dwpSupplementaryResponse")
                .withCaseData("languagePreferenceWelsh", true)
                .initiativesTask("reviewBilingualDocument", "Review Bi-Lingual Document",
                             10, "Translation Tasks")
                .initiativesTask("actionUnprocessedCorrespondence", "Action Unprocessed Correspondence", 10)
                .build(),
            event("uploadDocument")
                .withCaseData("languagePreferenceWelsh", true)
                .initiativesTask("reviewBilingualDocument", "Review Bi-Lingual Document",
                                 10, "Translation Tasks")
                .initiativesTask("actionUnprocessedCorrespondence", "Action Unprocessed Correspondence", 10)
                .build(),
            event("attachScannedDocs")
                .withCaseData("languagePreferenceWelsh", true)
                .initiativesTask("reviewBilingualDocument", "Review Bi-Lingual Document",
                                 10, "Translation Tasks")
                .initiativesTask("actionUnprocessedCorrespondence", "Action Unprocessed Correspondence", 10)
                .build(),
            event("uploadWelshDocument")
                .initiativesTask("issueOutstandingTranslation", "Issue Outstanding Translation",
                                 10, "Translation Tasks")
                .build(),
            event("sendToAdmin")
                .initiativesTask("reviewAdminAction", "Review Admin Action", 10)
                .build(),
            eventWithState("appealCreated", "withFta")
                .withCaseData("dwpDueDate", LocalDate.now().plusDays(7).toString())
                .initiativesTaskWithDelay("reviewFtaDueDate", "Review FTA Due Date", 7, 2)
                .build(),
            event("actionFurtherEvidence")
                .withCaseData("scannedDocumentTypes", List.of("confidentialityRequest"))
                .initiativesTask("reviewConfidentialityRequest", "Review Confidentiality Request", 2)
                .build(),
            event("uploadWelshDocument")
                .withCaseData("scannedDocumentTypes", List.of("confidentialityRequest"))
                .initiativesTask("issueOutstandingTranslation", "Issue Outstanding Translation",
                                 10, "Translation Tasks")
                .initiativesTask("reviewConfidentialityRequest", "Review Confidentiality Request", 2)
                .build(),
            event("manageWelshDocuments")
                .withCaseData("scannedDocumentTypes", List.of("confidentialityRequest"))
                .initiativesTask("reviewConfidentialityRequest", "Review Confidentiality Request", 2)
                .build(),
            event("actionFurtherEvidence")
                .withCaseData("scannedDocumentTypes", List.of("reinstatementRequest"))
                .initiativesTask("reviewReinstatementRequestJudge", "Review Reinstatement Request", 2)
                .build(),
            event("uploadWelshDocument")
                .withCaseData("scannedDocumentTypes", List.of("reinstatementRequest"))
                .initiativesTask("issueOutstandingTranslation", "Issue Outstanding Translation",
                                 10, "Translation Tasks")
                .initiativesTask("reviewReinstatementRequestJudge", "Review Reinstatement Request", 2)
                .build(),
            event("manageWelshDocuments")
                .withCaseData("scannedDocumentTypes", List.of("reinstatementRequest"))
                .initiativesTask("reviewReinstatementRequestJudge", "Review Reinstatement Request", 2)
                .build(),
            event("dwpUploadResponse")
                .withCaseData("workType", "phme")
                .initiativesTask("reviewPheRequestJudge", "Review PHE Request", 2)
                .build(),
            event("updateNotListable")
                .withCaseData("action", "reviewByJudge")
                .initiativesTask("ftaNotProvidedAppointeeDetailsJudge", "FTA not Provided Appointee Details", 2)
                .build(),
            event("actionPostponementRequest")
                .withCaseData("action", "sendToJudge")
                .initiativesTask("reviewPostponementRequestJudge", "Review Postponement Request", 2)
                .build(),
            event("actionFurtherEvidence")
                .withCaseData("scannedDocumentTypes", List.of("urgentHearingRequest"))
                .initiativesTask("reviewUrgentHearingRequest", "Review Urgent Hearing Request", 2)
                .build(),
            event("uploadWelshDocument")
                .withCaseData("scannedDocumentTypes", List.of("urgentHearingRequest"))
                .initiativesTask("issueOutstandingTranslation", "Issue Outstanding Translation",
                                 10, "Translation Tasks")
                .initiativesTask("reviewUrgentHearingRequest", "Review Urgent Hearing Request", 2)
                .build(),
            event("manageWelshDocuments")
                .withCaseData("scannedDocumentTypes", List.of("urgentHearingRequest"))
                .initiativesTask("reviewUrgentHearingRequest", "Review Urgent Hearing Request", 2)
                .build(),
            event("makeCaseUrgent")
                .initiativesTask("reviewUrgentHearingRequest", "Review Urgent Hearing Request", 2)
                .build(),
            event("tcwReferToJudge")
                .withCaseData("workType", "preHearingWork")
                .initiativesTask("referredByTcwPreHearing", "Referred By TCW", 2)
                .build(),
            event("createBundle")
                .withCaseData("panel", Map.of("assignedTo", "panel member 1"))
                .withCaseData("nextHearingDate", LocalDate.now().plusDays(7).toString())
                .initiativesTask("prepareForHearingJudge", "Prepare For Hearing", 2)
                .initiativesTaskWithDelay("writeDecisionJudge", "Write Decision", 7,2)
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
        assertThat(logic.getRules().size(), is(26));
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
