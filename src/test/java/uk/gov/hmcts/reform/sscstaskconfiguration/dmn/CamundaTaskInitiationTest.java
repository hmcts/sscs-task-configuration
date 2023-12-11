package uk.gov.hmcts.reform.sscstaskconfiguration.dmn;

import java.util.Arrays;
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
                "requestForInformation",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewInformationRequested",
                        "name", "Review Information Requested",
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
                "dwpUploadResponse",
                "withDwp",
                Map.of("Data", Map.of("dwpFurtherInfo", true)),
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
                "dwpChallengeValidity",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewFtaValidityChallenge",
                        "name", "Review FTA validity challenge",
                        "workingDaysAllowed", 2,
                        "processCategories", "reviewFtaValidityChallenge"
                    )
                )
            ),
            Arguments.of(
                "dwpUploadResponse",
                null,
                Map.of("Data", Map.of("dwpFurtherInfo", false)),
                List.of()
            ),
            event("sendToAdmin")
                .initiativesTask("reviewAdminAction", "Review Admin Action", 10)
                .build(),
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
            Arguments.of(
                "uploadDocumentFurtherEvidence",
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
                "dwpUploadResponse",
                "withDwp",
                Map.of("Data", Map.of("dwpFurtherInfo", true)),
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
                Map.of("Data", Map.of("dwpFurtherInfo", false)),
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
            Arguments.of(
                "validAppealCreated",
                "validAppeal",
                null,
                singletonList(
                    Map.of(
                        "taskId", "reviewValidAppeal",
                        "name", "Review Valid Appeal",
                        "delayDuration", 1,
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
                .withCaseData("dwpEditedEvidenceReason", "phme")
                .initiativesTask("reviewPheRequestJudge", "Review PHE Request", 2)
                .build(),
            event("updateNotListable")
                .withCaseData("action", "reviewByJudge")
                .initiativesTask("ftaNotProvidedAppointeeDetailsJudge", "FTA not Provided Appointee Details", 2)
                .build(),
            event("actionPostponementRequest")
                .withCaseData("action", "reviewByJudge")
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
                .withCaseData("workType", "pre")
                .initiativesTask("referredByTcwPreHearing", "Referred By TCW", 2)
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", Arrays.asList("hearing-judge"))
                .initiativesTask("prepareForHearingJudge", "Prepare For Hearing", 2)
                .build(),
            event("hearingToday")
                .initiativesTask("writeDecisionJudge", "Write Decision", 2)
                .initiativesTask("reviewOutstandingDraftDecision", "Review Outstanding Draft Decision", 5)
                .initiativesTask("updateHearingDetails", "Update Hearing Details", 5)
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "reviewByJudge")
                .withCaseData("interlocReferralReason", "N/A")
                .initiativesTask("referredToInterlocJudge", "Referred to interloc", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "reviewByJudge")
                .withCaseData("workType", "pre")
                .withCaseData("interlocReferralReason", "adviceOnHowToProceed")
                .initiativesTask("referredByAdminJudgePreHearing", "Referred By Admin", 2)
                .build(),
            event("dwpUploadResponse")
                .withCaseData("benefitCode", "026")
                .initiativesTask("confirmPanelComposition", "Confirm Panel Composition", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "reviewByJudge")
                .withCaseData("interlocReferralReason", "noResponseToDirection")
                .initiativesTask("referredToInterlocJudge", "Referred to interloc - No response to a direction", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "reviewByJudge")
                .withCaseData("interlocReferralReason", "Other")
                .initiativesTask("referredToInterlocJudge", "Referred to interloc", 2)
                .build(),
            event("actionPostponementRequest")
                .withCaseData("daysToHearing", 14)
                .build(),
            event("actionPostponementRequest")
                .withCaseData("daysToHearing", 6)
                .initiativesTask("contactParties", "Contact Parties", 1)
                .build(),
            eventWithState("correctionRequest","postHearing")
                .initiativesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge", 3)
                .build(),
            eventWithState("actionFurtherEvidence","dormantAppealState")
                .withCaseData("scannedDocumentTypes", List.of("correctionApplication"))
                .withCaseData("furtherEvidenceAction", dynamicListValue("adminActionCorrection"))
                .initiativesTask("reviewCorrectionApplicationAdmin", "Review Correction Application", 3)
                .build(),
            event("dwpRequestTimeExtension")
                .initiativesTask("ftaRequestTimeExtension", "Request FTA Time Extension", 2)
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", Arrays.asList("tribunal-member-1", "tribunal-member-2", "tribunal-member-3"))
                .initiativesTask("prepareForHearingTribunalMember1", "Prepare for hearing", 2, "prepareForHearingTribunalMember")
                .initiativesTask("prepareForHearingTribunalMember2", "Prepare for hearing", 2, "prepareForHearingTribunalMember")
                .initiativesTask("prepareForHearingTribunalMember3", "Prepare for hearing", 2, "prepareForHearingTribunalMember")
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", Arrays.asList("tribunal-member-1"))
                .initiativesTask("prepareForHearingTribunalMember1", "Prepare for hearing", 2, "prepareForHearingTribunalMember")
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", Arrays.asList("tribunal-member-2"))
                .initiativesTask("prepareForHearingTribunalMember2", "Prepare for hearing", 2, "prepareForHearingTribunalMember")
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", Arrays.asList("tribunal-member-3"))
                .initiativesTask("prepareForHearingTribunalMember3", "Prepare for hearing", 2, "prepareForHearingTribunalMember")
                .build(),
            event("createBundle")
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "reviewByTcw")
                .initiativesTask("referredToInterlocTCW", "Referred to interloc", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "postponementRequestInterlocSendToTcw")
                .initiativesTask("reviewPostponementRequestTCW", "Review postponement request", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "reviewByTcw")
                .withCaseData("interlocReferralReason", "adviceOnHowToProceed")
                .initiativesTask("referredByAdminTcw", "Referred by Admin", 2)
                .build(),
            event("actionFurtherEvidence")
                .withCaseData("scannedDocumentTypes", List.of("postponementRequest"))
                .initiativesTask("reviewPostponementRequestTCW", "Review postponement request", 2)
                .build(),
            event("uploadWelshDocument")
                .withCaseData("scannedDocumentTypes", List.of("postponementRequest"))
                .initiativesTask("issueOutstandingTranslation", "Issue Outstanding Translation",
                                 10, "Translation Tasks")
                .initiativesTask("reviewPostponementRequestTCW", "Review postponement request", 2)
                .build(),
            event("manageWelshDocuments")
                .withCaseData("scannedDocumentTypes", List.of("postponementRequest"))
                .initiativesTask("reviewPostponementRequestTCW", "Review postponement request", 2)
                .build(),
            event("postponementRequest")
                .initiativesTask("reviewPostponementRequestTCW", "Review postponement request", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "reviewByTcw")
                .withCaseData("interlocReferralReason", "complexCase")
                .initiativesTask("referredToInterlocTCW", "Referred to interloc - Complex Case", 2)
                .build(),
            event("nonCompliantSendToInterloc")
                .initiativesTask("referredToInterlocTCW", "Referred to interloc", 2)
                .build(),
            event("sentToDwp")
                .withCaseData("caseManagementCategory", Map.of("value", Map.of("code", "childSupport")))
                .initiativesTaskWithDelay("ftaResponseOverdue", "Referred to Interloc - FTA response overdue", 42, 2)
                .build(),
            eventWithState("sentToDwp", "withDwp")
                .withCaseData("dwpDueDate", LocalDate.now().plusDays(7).toString())
                .withCaseData("caseManagementCategory", Map.of("value", Map.of("code", "childSupport")))
                .initiativesTaskWithDelay("reviewFtaDueDate", "Review FTA Due Date", 7, 2)
                .initiativesTaskWithDelay("ftaResponseOverdue", "Referred to Interloc - FTA response overdue", 42, 2)
                .build(),
            eventWithState("sentToDwp", "withDwp")
                .withCaseData("dwpDueDate", LocalDate.now().plusDays(7).toString())
                .withCaseData("caseManagementCategory", Map.of("value", Map.of("code", "PIP")))
                .initiativesTaskWithDelay("reviewFtaDueDate", "Review FTA Due Date", 7, 2)
                .initiativesTaskWithDelay("ftaResponseOverdue", "Referred to Interloc - FTA response overdue", 28, 2)
                .build(),
            event("directionDueToday")
                .withCaseData("directionTypeDl", Map.of("value", Map.of("code", "grantExtension")))
                .initiativesTask("reviewBfDate", "Review BF Date", 5)
                .initiativesTask("ftaResponseOverdue", "Referred to Interloc - FTA response overdue", 2)
                .build(),
            event("interlocSendToTcw")
                .initiativesTask("referredByJudge", "Referred By Judge", 2)
                .build(),
            event("uploadDocument")
                .withCaseData("scannedDocumentTypes", List.of("audioDocument"))
                .initiativesTask("actionUnprocessedCorrespondence", "Action Unprocessed Correspondence", 10)
                .initiativesTask("processAudioVideoEvidence", "Process audio/video evidence", 2)
                .build(),
            event("dwpSupplementaryResponse")
                .withCaseData("scannedDocumentTypes", List.of("videoDocument", "audioDocument"))
                .initiativesTask("actionUnprocessedCorrespondence", "Action Unprocessed Correspondence", 10)
                .initiativesTask("processAudioVideoEvidence", "Process audio/video evidence", 2)
                .build(),
            event("dwpUploadResponse")
                .withCaseData("scannedDocumentTypes", List.of("audioDocument", "other"))
                .initiativesTask("processAudioVideoEvidence", "Process audio/video evidence", 2)
                .build(),
            event("uploadFurtherEvidence")
                .withCaseData("scannedDocumentTypes", List.of("videoDocument"))
                .initiativesTask("processAudioVideoEvidence", "Process audio/video evidence", 2)
                .build(),
            event("uploadDocumentFurtherEvidence")
                .withCaseData("scannedDocumentTypes", List.of("videoDocument"))
                .initiativesTask("actionUnprocessedCorrespondence", "Action Unprocessed Correspondence", 10)
                .initiativesTask("processAudioVideoEvidence", "Process audio/video evidence", 2)
                .build(),
            event("nonCompliant")
                .initiativesTask("reviewNonCompliantAppeal", "Review Non Compliant Appeal", 2)
                .build(),
            event("draftToNonCompliant")
                .initiativesTask("reviewNonCompliantAppeal", "Review Non Compliant Appeal", 2)
                .build(),
            event("updateNotListable")
                .withCaseData("action", "reviewByTcw")
                .initiativesTask("ftaNotProvidedAppointeeDetailsTcw", "FTA Not Provided Appointee Details", 2)
                .build(),
            eventWithState("sORRequest", "postHearing")
                .withCaseData("sscsHearingRecordings", emptyList())
                .initiativesTask("reviewStatementofReasonsApplication", "Review Statement of Reasons Application", 2)
                .build(),
            eventWithState("validSendToInterloc", "postHearing")
                .withCaseData("interlocReferralReason", "reviewLibertyToApplyApplication")
                .initiativesTask("reviewLibertytoApplyApplication", "Review Liberty to Apply Application", 2)
                .build(),
            eventWithState("actionFurtherEvidence", "dormantAppealState")
                .withCaseData("scannedDocumentTypes", List.of("libertyToApplyApplication"))
                .withCaseData("furtherEvidenceAction", dynamicListValue("sendToInterlocReviewByJudge"))
                .initiativesTask("reviewLibertytoApplyApplication", "Review Liberty to Apply Application", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("interlocReferralReason", "reviewCorrectionApplication")
                .initiativesTask("reviewCorrectionApplicationJudge", "Review Correction Application", 2)
                .build(),
            event("actionFurtherEvidence")
                .withCaseData("scannedDocumentTypes", List.of("correctionApplication"))
                .withCaseData("furtherEvidenceAction", dynamicListValue("sendToInterlocReviewByJudge"))
                .initiativesTask("reviewCorrectionApplicationJudge", "Review Correction Application", 2)
                .build(),
            event("adminActionCorrection")
                .initiativesTask("reviewCorrectionApplicationJudge", "Review Correction Application", 2)
                .build(),
            eventWithState("validSendToInterloc", "postHearing")
                .withCaseData("interlocReferralReason", "statementOfReasonsApplication")
                .withCaseData("issueFinalDecisionDate", TODAY.plusDays(-28L)) // 1 month or less ago
                .initiativesTask("writeStatementofReason", "Write Statement of Reason", 28)
                .build(),
            eventWithState("setAsideRefused", "postHearing")
                .withCaseData("setAside", Map.of("requestStatementOfReasons", true))
                .withCaseData("issueFinalDecisionDate", TODAY.plusDays(-28L)) // 1 month or less ago
                .initiativesTask("writeStatementofReason", "Write Statement of Reason", 28)
                .build(),
            eventWithState("actionFurtherEvidence", "postHearing")
                .withCaseData("scannedDocumentTypes", List.of("statementOfReasonsApplication"))
                .withCaseData("furtherEvidenceAction", dynamicListValue("sendToInterlocReviewByJudge"))
                .withCaseData("issueInterlocDecisionDate", TODAY.plusDays(-28L)) // 1 month or less ago
                .initiativesTask("writeStatementofReason", "Write Statement of Reason", 28)
                .build(),
            eventWithState("sORExtendTime", "postHearing")
                .initiativesTask("writeStatementofReason", "Write Statement of Reason", 28)
                .build(),
            eventWithState("validSendToInterloc", "postHearing")
                .withCaseData("interlocReferralReason", "lateStatementOfReasonsApplication")
                .withCaseData("issueFinalDecisionDate", TODAY.plusDays(-32)) // over 1 month ago
                .initiativesTask("reviewLateStatementofReasonsApplication", "Review Late SOR Application", 2)
                .build(),
            eventWithState("actionFurtherEvidence", "postHearing")
                .withCaseData("scannedDocumentTypes", List.of("statementOfReasonsApplication"))
                .withCaseData("furtherEvidenceAction", dynamicListValue("sendToInterlocReviewByJudge"))
                .withCaseData("issueInterlocDecisionDate", TODAY.plusDays(-32)) // over 1 month ago
                .initiativesTask("reviewLateStatementofReasonsApplication", "Review Late SOR Application", 2)
                .build(),
            eventWithState("validSendToInterloc", "postHearing")
                .withCaseData("interlocReferralReason", "reviewPermissionToAppealApplication")
                .initiativesTask("reviewPermissiontoAppealApplication", "Review Permission to Appeal Application", 2)
                .build(),
            eventWithState("validSendToInterloc", "postHearing")
                .withCaseData("interlocReferralReason", "reviewPermissionToAppealApplication")
                .withCaseData("otherParties", List.of("other party 1"))
                .initiativesTaskWithDelay("reviewPermissiontoAppealApplication", "Review Permission to Appeal Application", 21, 2)
                .build(),
            eventWithState("actionFurtherEvidence", "postHearing")
                .withCaseData("scannedDocumentTypes", List.of("permissionToAppealApplication"))
                .withCaseData("furtherEvidenceAction", dynamicListValue("sendToInterlocReviewByJudge"))
                .initiativesTask("reviewPermissiontoAppealApplication", "Review Permission to Appeal Application", 2)
                .build(),
            eventWithState("actionFurtherEvidence", "postHearing")
                .withCaseData("scannedDocumentTypes", List.of("permissionToAppealApplication"))
                .withCaseData("furtherEvidenceAction", dynamicListValue("sendToInterlocReviewByJudge"))
                .withCaseData("jointParty", "Yes")
                .initiativesTaskWithDelay("reviewPermissiontoAppealApplication", "Review Permission to Appeal Application", 21, 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("interlocReferralReason", "reviewUpperTribunalDecision")
                .initiativesTask("reviewRemittedDecisionandProvideListingDirections", "Review Remitted Decision and Provide Listing Directions", 2)
                .build(),
            event("libertyToApplyGranted")
                .initiativesTask("reviewPostHearingNoticeforListingRequirements", "Review Post Hearing Notice for Listing Requirements", 10)
                .build(),
            eventWithState("postHearingReview", "postHearing")
                .withCaseData("postHearingReviewType", "setAside")
                .initiativesTask("reviewPostHearingNoticeforListingRequirements", "Review Post Hearing Notice for Listing Requirements", 10)
                .build(),
            eventWithState("validSendToInterloc", "dormantAppealState")
                .withCaseData("interlocReferralReason", "reviewSetAsideApplication")
                .initiativesTask("reviewSetAsideApplication", "Review Set Aside Application", 2)
                .build(),
            eventWithState("validSendToInterloc", "postHearing")
                .withCaseData("interlocReferralReason", "reviewSetAsideApplication")
                .withCaseData("jointParty", "Yes")
                .initiativesTaskWithDelay("reviewSetAsideApplication", "Review Set Aside Application", 21, 2)
                .build(),
            eventWithState("actionFurtherEvidence", "postHearing")
                .withCaseData("scannedDocumentTypes", List.of("setAsideApplication"))
                .withCaseData("furtherEvidenceAction", dynamicListValue("sendToInterlocReviewByJudge"))
                .initiativesTask("reviewSetAsideApplication", "Review Set Aside Application", 2)
                .build(),
            eventWithState("actionFurtherEvidence", "dormantAppealState")
                .withCaseData("scannedDocumentTypes", List.of("setAsideApplication"))
                .withCaseData("furtherEvidenceAction", dynamicListValue("sendToInterlocReviewByJudge"))
                .withCaseData("otherParties", List.of("other party 1"))
                .initiativesTaskWithDelay("reviewSetAsideApplication", "Review Set Aside Application", 21,2)
                .build(),
            event("sendToFirstTier")
                .withCaseData("sendToFirstTier", Map.of("action", "remitted"))
                .initiativesTask("shareRemittedDecision", "Allocate Judge and Share Remitted Decision", 20)
                .build(),
            eventWithState("sendToFirstTier", "dormantAppealState")
                .withCaseData("sendToFirstTier", Map.of("action", "remade"))
                .initiativesTask("shareRemadeDecision", "Share Remade Decision", 20)
                .build(),
            eventWithState("sendToFirstTier", "dormantAppealState")
                .withCaseData("sendToFirstTier", Map.of("action", "refused"))
                .initiativesTask("shareRefusedDecision", "Share Refused Decision", 20)
                .build(),
            eventWithState("libertyToApplyRequest", "dormantAppealState")
                .initiativesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge", 3)
                .build(),
            eventWithState("permissionToAppealRequest", "dormantAppealState")
                .initiativesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge", 3)
                .build(),
            eventWithState("setAsideRequest", "dormantAppealState")
                .initiativesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge", 3)
                .build(),
            eventWithState("sORRequest", "dormantAppealState")
                .withCaseData("issueFinalDecisionDate", TODAY.minusDays(21))
                .initiativesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge", 3)
                .build(),
            eventWithState("sORRequest", "dormantAppealState")
                .withCaseData("issueFinalDecisionDate", TODAY.minusDays(45))
                .initiativesTask("reviewLateStatementofReasonsApplicationAndAllocateJudge",
                                 "Review Late SOR Application and Allocate Judge", 2,
                                 "reviewStatementofReasonsApplication")
                .build(),
            event("validSendToInterloc")
                .withCaseData("workType", "post")
                .withCaseData("action", "reviewByJudge")
                .withCaseData("interlocReferralReason", "adviceOnHowToProceed")
                .initiativesTask("referredByAdminJudgePostHearing", "Referred By Admin", 2)
                .build(),
            event("tcwReferToJudge")
                .withCaseData("workType", "post")
                .initiativesTask("referredByTcwPostHearing", "Referred By TCW", 2)
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
        assertThat(logic.getRules().size(), is(80));
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
