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
                        "processCategories", "Routine work"
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
                        "processCategories", "Routine work"
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
                "dwpSupplementaryResponse",
                null,
                Map.of("Data", Map.of("languagePreferenceWelsh", true)),
                singletonList(
                    Map.of(
                        "taskId", "reviewBilingualDocument",
                        "name", "Review Bi-Lingual Document",
                        "workingDaysAllowed", 10,
                        "processCategories", "Translation Tasks"
                    )
                )
            ),
            Arguments.of(
                "uploadDocument",
                null,
                Map.of("Data", Map.of("languagePreferenceWelsh", true)),
                singletonList(
                    Map.of(
                        "taskId", "reviewBilingualDocument",
                        "name", "Review Bi-Lingual Document",
                        "workingDaysAllowed", 10,
                        "processCategories", "Translation Tasks"
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
                singletonList(
                    Map.of(
                        "taskId", "reviewBilingualDocument",
                        "name", "Review Bi-Lingual Document",
                        "workingDaysAllowed", 10,
                        "processCategories", "Translation Tasks"
                    )
                )
            ),
            event("uploadDocumentFurtherEvidence")
                .withCaseData("languagePreferenceWelsh", true)
                .initiativesTask("reviewBilingualDocument", "Review Bi-Lingual Document",
                                 10, "Translation Tasks")
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
            event("tcwReferToJudge")
                .withCaseData("workType", "postHearingWork")
                .initiativesTask("referredByTcwPostHearing", "Referred By TCW", 2)
                .build(),
            event("createBundle")
                .withCaseData("panel", Map.of("assignedTo", "panel member 1"))
                .withCaseData("nextHearingDate", LocalDate.now().plusDays(7).toString())
                .initiativesTask("prepareForHearingJudge", "Prepare For Hearing", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("workType", "preHearingWork")
                .withCaseData("selectWhoReviewsCase", "reviewByJudge")
                .initiativesTask("referredToInterlocJudge", "Referred to interloc", 2, "Routine work")
                .initiativesTask("referredByAdminJudgePreHearing", "Referred By Admin", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("workType", "postHearingWork")
                .withCaseData("selectWhoReviewsCase", "reviewByJudge")
                .initiativesTask("referredToInterlocJudge", "Referred to interloc", 2, "Routine work")
                .initiativesTask("referredByAdminJudgePostHearing", "Referred By Admin", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("workType", "postHearingWork")
                .withCaseData("selectWhoReviewsCase", "reviewByJudge")
                .initiativesTask("referredToInterlocJudge", "Referred to interloc", 2, "Routine work")
                .initiativesTask("referredByAdminJudgePostHearing", "Referred By Admin", 2)
                .build(),
            event("dwpUploadResponse")
                .withCaseData("benefitCode", "026")
                .initiativesTask("confirmPanelComposition", "Confirm Panel Composition", 2)
                .build(),
            event("hearingToday")
                .initiativesTask("writeDecisionJudge", "Write Decision", 2)
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
        assertThat(logic.getRules().size(), is(19));

    }

}
