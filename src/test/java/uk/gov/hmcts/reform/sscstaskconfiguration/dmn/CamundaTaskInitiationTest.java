package uk.gov.hmcts.reform.sscstaskconfiguration.dmn;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_INITIATION_SSCS_BENEFIT;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.dynamicListValue;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.InitiationScenarioBuilder.event;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.InitiationScenarioBuilder.eventWithState;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
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

class CamundaTaskInitiationTest extends DmnDecisionTableBaseUnitTest {

    private static final LocalDate TODAY = LocalDate.now();

    @BeforeAll
    public static void initialisation() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_INITIATION_SSCS_BENEFIT;
    }

    static Stream<Arguments> scenarioProvider() {
        return Stream.of(
            event("draftToIncompleteApplication")
                .initiatesTask("reviewIncompleteAppeal",
                                 "CTSC - Review Incomplete Appeal", 5)
                .build(),
            event("incompleteApplicationReceived")
                .initiatesTask("reviewIncompleteAppeal",
                                 "CTSC - Review Incomplete Appeal", 5)
                .build(),
            event("requestForInformation")
                .initiatesTask("reviewInformationRequested",
                               "Review Information Requested - CTSC", 3)
                .build(),
            event("dwpSupplementaryResponse")
                .initiatesTask("actionUnprocessedCorrespondence",
                               "CTSC - Action Unprocessed Correspondence", 10)
                .build(),
            event("attachScannedDocs")
                .initiatesTask("actionUnprocessedCorrespondence",
                               "CTSC - Action Unprocessed Correspondence", 10)
                .build(),
            event("uploadDocumentFurtherEvidence")
                .initiatesTask("actionUnprocessedCorrespondence",
                               "CTSC - Action Unprocessed Correspondence", 10)
                .build(),
            eventWithState("dwpUploadResponse", "withDwp")
                .withCaseData("dwpFurtherInfo", true)
                .initiatesTask("reviewFtaResponse", "CTSC - Review FTA Response", 2)
                .build(),
            event("dwpChallengeValidity")
                .initiatesTask("reviewFtaValidityChallenge",
                               "Review FTA validity challenge - LO", 2)
                .build(),
            event("dwpUploadResponse")
                .withCaseData("dwpFurtherInfo", false)
                .build(),
            event("sendToAdmin")
                .initiatesTask("reviewAdminAction", "CTSC - Review Admin Action", 5)
                .build(),
            event("dwpUploadResponse")
                .withCaseData("languagePreferenceWelsh", false)
                .build(),
            event("attachScannedDocs")
                .withCaseData("languagePreferenceWelsh", true)
                .initiatesTask("reviewBilingualDocument",
                               "Review Bi-Lingual Document - CTSC", 10, "Translation Tasks")
                .initiatesTask("actionUnprocessedCorrespondence",
                               "CTSC - Action Unprocessed Correspondence", 10)
                .build(),
            event("uploadDocumentFurtherEvidence")
                .withCaseData("languagePreferenceWelsh", true)
                .initiatesTask("reviewBilingualDocument",
                               "Review Bi-Lingual Document - CTSC", 10, "Translation Tasks")
                .initiatesTask("actionUnprocessedCorrespondence",
                               "CTSC - Action Unprocessed Correspondence", 10)
                .build(),
            eventWithState("dwpUploadResponse", "withDwp")
                .withCaseData("dwpFurtherInfo", false)
                .build(),
            event("uploadDocument")
                .withCaseData("languagePreferenceWelsh", true)
                .initiatesTask("reviewBilingualDocument",
                               "Review Bi-Lingual Document - CTSC", 10, "Translation Tasks")
                .build(),
            event("actionFurtherEvidence")
                .withCaseData("scannedDocumentTypes", List.of("reinstatementRequest"))
                .initiatesTask("reviewReinstatementRequestJudge", "Review Reinstatement Request - Judge", 2)
                .build(),
            event("uploadWelshDocument")
                .withCaseData("scannedDocumentTypes", List.of("reinstatementRequest"))
                .initiatesTask("issueOutstandingTranslation", "Issue Outstanding Translation - CTSC",
                               10, "Translation Tasks")
                .build(),
            eventWithState("validAppealCreated", "validAppeal")
                .initiatesTaskWithDelay("reviewValidAppeal",
                                        "Review Valid Appeal - CTSC", 1, 5)
                .build(),
            eventWithState("readyToList", "listingError")
                .initiatesTask("reviewListingError",
                               "Review Listing Error - CTSC", 3)
                .build(),
            event("sendToRoboticsError")
                .initiatesTask("reviewRoboticFail",
                               "Review Robotic Fail - CTSC", 3)
                .build(),
            event("directionDueToday")
                .initiatesTask("reviewBfDate",
                               "Review BF Date - CTSC", 5)
                .build(),
            event("prepareForHearing")
                .initiatesTask("allocateCaseRolesAndCreateBundle",
                               "Allocate Case Roles and Create Bundle - RPC", 3)
                .build(),
            event("dwpSupplementaryResponse")
                .withCaseData("languagePreferenceWelsh", true)
                .initiatesTask("reviewBilingualDocument", "Review Bi-Lingual Document - CTSC",
                               10, "Translation Tasks")
                .initiatesTask("actionUnprocessedCorrespondence", "CTSC - Action Unprocessed Correspondence", 10)
                .build(),
            event("uploadWelshDocument")
                .initiatesTask("issueOutstandingTranslation", "Issue Outstanding Translation - CTSC",
                               10, "Translation Tasks")
                .build(),
            event("actionFurtherEvidence")
                .withCaseData("scannedDocumentTypes", List.of("confidentialityRequest"))
                .initiatesTask("reviewConfidentialityRequest", "Review Confidentiality Request - Judge", 2)
                .build(),
            event("uploadWelshDocument")
                .withCaseData("scannedDocumentTypes", List.of("confidentialityRequest"))
                .initiatesTask("issueOutstandingTranslation", "Issue Outstanding Translation - CTSC",
                               10, "Translation Tasks")
                .build(),
            event("manageWelshDocuments")
                .withCaseData("scannedDocumentTypes", List.of("confidentialityRequest"))
                .initiatesTask("reviewConfidentialityRequest", "Review Confidentiality Request - Judge", 2)
                .build(),
            event("manageWelshDocuments")
                .withCaseData("scannedDocumentTypes", List.of("reinstatementRequest"))
                .initiatesTask("reviewReinstatementRequestJudge", "Review Reinstatement Request - Judge", 2)
                .build(),
            event("dwpUploadResponse")
                .withCaseData("dwpEditedEvidenceReason", "phme")
                .initiatesTask("reviewPheRequestJudge", "Review PHE Request - Judge", 2)
                .build(),
            event("updateNotListable")
                .withCaseData("action", "reviewByJudge")
                .initiatesTask("ftaNotProvidedAppointeeDetailsJudge", "FTA not Provided Appointee Details - Judge", 2)
                .build(),
            event("actionPostponementRequest")
                .withCaseData("action", "reviewByJudge")
                .initiatesTask("reviewPostponementRequestJudge", "Review Postponement Request - Judge", 2)
                .build(),
            event("uploadWelshDocument")
                .withCaseData("scannedDocumentTypes", List.of("urgentHearingRequest"))
                .initiatesTask("issueOutstandingTranslation", "Issue Outstanding Translation - CTSC",
                               10, "Translation Tasks")
                .build(),
            event("manageWelshDocuments")
                .withCaseData("scannedDocumentTypes", List.of("urgentHearingRequest"))
                .initiatesTask("reviewUrgentHearingRequest", "Review Urgent Hearing Request - Judge", 2)
                .build(),
            event("makeCaseUrgent")
                .initiatesTask("reviewUrgentHearingRequest", "Review Urgent Hearing Request - Judge", 2)
                .build(),
            event("tcwReferToJudge")
                .withCaseData("workType", "pre")
                .initiatesTask("referredByTcwPreHearing", "Referred By TCW - Judge", 2)
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", List.of("hearing-judge"))
                .initiatesTask("prepareForHearingJudge", "Prepare For Hearing - Judge", 2)
                .build(),
            event("hearingToday")
                .initiatesTask("writeDecisionJudge", "Write Decision - Judge", 2)
                .initiatesTask("updateHearingDetails", "Update Hearing Details - RPC", 5)
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "reviewByJudge")
                .withCaseData("interlocReferralReason", "N/A")
                .initiatesTask("referredToInterlocJudge", "Referred to interloc - Judge", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "reviewByJudge")
                .withCaseData("workType", "pre")
                .withCaseData("interlocReferralReason", "adviceOnHowToProceed")
                .initiatesTask("referredByAdminJudgePreHearing", "Referred By Admin - Judge", 2)
                .build(),
            event("hmctsResponseReviewed")
                .withCaseData("action", dynamicListValue("reviewByJudge"))
                .withCaseData("interlocReferralReason", "confirmPanelCompositionAndListingDirections")
                .initiatesTask("confirmPanelComposition", "Confirm Panel Composition - Judge", 2)
                .initiatesTask("provideListingDirections", "Provide Listing Directions - Judge", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "reviewByJudge")
                .withCaseData("interlocReferralReason", "noResponseToDirection")
                .initiatesTask("referredToInterlocJudge", "Referred to interloc - No response to a direction - Judge", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "reviewByJudge")
                .withCaseData("interlocReferralReason", "Other")
                .initiatesTask("referredToInterlocJudge", "Referred to interloc - Judge", 2)
                .build(),
            event("actionPostponementRequest")
                .withCaseData("daysToHearing", 14)
                .build(),
            event("actionPostponementRequest")
                .withCaseData("daysToHearing", 6)
                .initiatesTask("contactParties", "Contact Parties - RPC", 1)
                .build(),
            eventWithState("correctionRequest","postHearing")
                .build(),
            eventWithState("actionFurtherEvidence","dormantAppealState")
                .withCaseData("scannedDocumentTypes", List.of("correctionApplication"))
                .withCaseData("furtherEvidenceAction", dynamicListValue("adminActionCorrection"))
                .initiatesTask("reviewCorrectionApplicationAdmin", "Review Correction Application - CTSC", 3)
                .build(),
            event("dwpRequestTimeExtension")
                .initiatesTask("ftaRequestTimeExtension", "Request FTA Time Extension - LO", 2)
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", Arrays.asList("tribunal-member-1", "tribunal-member-2", "tribunal-member-3", "appraiser-1", "appraiser-2"))
                .initiatesTask("prepareForHearingTribunalMember1", "Prepare for hearing - Tribunal Member 1", 2, "prepareForHearingTribunalMember")
                .initiatesTask("prepareForHearingTribunalMember2", "Prepare for hearing - Tribunal Member 2", 2, "prepareForHearingTribunalMember")
                .initiatesTask("prepareForHearingTribunalMember3", "Prepare for hearing - Tribunal Member 3", 2, "prepareForHearingTribunalMember")
                .initiatesTask("prepareHearingAppraiser1", "Prepare for hearing - Appraiser 1", 2, "prepareHearingAppraiser")
                .initiatesTask("prepareHearingAppraiser2", "Prepare for hearing - Appraiser 2", 2, "prepareHearingAppraiser")
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", List.of("tribunal-member-1"))
                .initiatesTask("prepareForHearingTribunalMember1", "Prepare for hearing - Tribunal Member 1", 2, "prepareForHearingTribunalMember")
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", List.of("tribunal-member-2"))
                .initiatesTask("prepareForHearingTribunalMember2", "Prepare for hearing - Tribunal Member 2", 2, "prepareForHearingTribunalMember")
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", List.of("tribunal-member-3"))
                .initiatesTask("prepareForHearingTribunalMember3", "Prepare for hearing - Tribunal Member 3", 2, "prepareForHearingTribunalMember")
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", List.of("appraiser-1"))
                .initiatesTask("prepareHearingAppraiser1", "Prepare for hearing - Appraiser 1", 2, "prepareHearingAppraiser")
                .build(),
            event("createBundle")
                .withCaseData("assignedCaseRoles", List.of("appraiser-2"))
                .initiatesTask("prepareHearingAppraiser2", "Prepare for hearing - Appraiser 2", 2, "prepareHearingAppraiser")
                .build(),
            event("newCaseRolesAssigned")
                .withCaseData("assignedCaseRoles", Arrays.asList("hearing-judge",
                                                                 "tribunal-member-1", "tribunal-member-2", "tribunal-member-3",
                                                                 "appraiser-1", "appraiser-2"))
                .initiatesTask("prepareForHearingJudge", "Prepare For Hearing - Judge", 2)
                .initiatesTask("prepareForHearingTribunalMember1", "Prepare for hearing - Tribunal Member 1", 2, "prepareForHearingTribunalMember")
                .initiatesTask("prepareForHearingTribunalMember2", "Prepare for hearing - Tribunal Member 2", 2, "prepareForHearingTribunalMember")
                .initiatesTask("prepareForHearingTribunalMember3", "Prepare for hearing - Tribunal Member 3", 2, "prepareForHearingTribunalMember")
                .initiatesTask("prepareHearingAppraiser1", "Prepare for hearing - Appraiser 1", 2, "prepareHearingAppraiser")
                .initiatesTask("prepareHearingAppraiser2", "Prepare for hearing - Appraiser 2", 2, "prepareHearingAppraiser")
                .build(),
            event("createBundle")
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "reviewByTcw")
                .initiatesTask("referredToInterlocTCW", "Referred to interloc - LO", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "reviewByTcw")
                .withCaseData("interlocReferralReason", "timeExtension")
                .initiatesTask("referredToInterlocTCW", "Referred to interloc - Time extension - LO", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "postponementRequestInterlocSendToTcw")
                .initiatesTask("reviewPostponementRequestTCW", "Review postponement request - LO", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "reviewByTcw")
                .withCaseData("interlocReferralReason", "adviceOnHowToProceed")
                .initiatesTask("referredByAdminTcw", "Referred by Admin - LO", 2)
                .build(),
            event("uploadWelshDocument")
                .withCaseData("scannedDocumentTypes", List.of("postponementRequest"))
                .initiatesTask("issueOutstandingTranslation", "Issue Outstanding Translation - CTSC",
                               10, "Translation Tasks")
                .build(),
            event("manageWelshDocuments")
                .withCaseData("scannedDocumentTypes", List.of("postponementRequest"))
                .initiatesTask("reviewPostponementRequestTCW", "Review postponement request - LO", 2)
                .build(),
            event("postponementRequest")
                .initiatesTask("reviewPostponementRequestTCW", "Review postponement request - LO", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "reviewByTcw")
                .withCaseData("interlocReferralReason", "complexCase")
                .initiatesTask("referredToInterlocTCW", "Referred to interloc - Complex Case - LO", 2)
                .build(),
            event("nonCompliantSendToInterloc")
                .initiatesTask("referredToInterlocTCW", "Referred to interloc - LO", 2)
                .build(),
            event("sentToDwp")
                .withCaseData("caseManagementCategory", Map.of("value", Map.of("code", "childSupport")))
                .initiatesTaskWithDelay("ftaResponseOverdue", "Referred to Interloc - FTA response overdue - LO", 42, 2)
                .build(),
            eventWithState("sentToDwp", "withDwp")
                .withCaseData("dwpDueDate", LocalDate.now().plusDays(7).toString())
                .withCaseData("caseManagementCategory", Map.of("value", Map.of("code", "childSupport")))
                .initiatesTaskWithDelay("reviewFtaDueDate", "Review FTA Due Date - CTSC", 7, 2)
                .initiatesTaskWithDelay("ftaResponseOverdue", "Referred to Interloc - FTA response overdue - LO", 42, 2)
                .build(),
            eventWithState("sentToDwp", "withDwp")
                .withCaseData("dwpDueDate", LocalDate.now().plusDays(7).toString())
                .withCaseData("caseManagementCategory", Map.of("value", Map.of("code", "PIP")))
                .initiatesTaskWithDelay("reviewFtaDueDate", "Review FTA Due Date - CTSC", 7, 2)
                .initiatesTaskWithDelay("ftaResponseOverdue", "Referred to Interloc - FTA response overdue - LO", 28, 2)
                .build(),
            event("directionDueToday")
                .withCaseData("directionTypeDl", Map.of("value", Map.of("code", "grantExtension")))
                .initiatesTask("reviewBfDate", "Review BF Date - CTSC", 5)
                .initiatesTask("ftaResponseOverdue", "Referred to Interloc - FTA response overdue - LO", 2)
                .build(),
            event("interlocSendToTcw")
                .initiatesTask("referredByJudge", "Referred By Judge - LO", 2)
                .build(),
            event("uploadDocument")
                .withCaseData("scannedDocumentTypes", List.of("audioDocument"))
                .initiatesTask("processAudioVideoEvidence", "Process audio/video evidence - LO", 2)
                .build(),
            event("dwpSupplementaryResponse")
                .withCaseData("scannedDocumentTypes", List.of("videoDocument", "audioDocument"))
                .initiatesTask("processAudioVideoEvidence", "Process audio/video evidence - LO", 2)
                .build(),
            event("dwpUploadResponse")
                .withCaseData("scannedDocumentTypes", List.of("audioDocument", "other"))
                .initiatesTask("processAudioVideoEvidence", "Process audio/video evidence - LO", 2)
                .build(),
            event("uploadFurtherEvidence")
                .withCaseData("scannedDocumentTypes", List.of("videoDocument"))
                .initiatesTask("processAudioVideoEvidence", "Process audio/video evidence - LO", 2)
                .build(),
            event("uploadDocumentFurtherEvidence")
                .withCaseData("scannedDocumentTypes", List.of("videoDocument"))
                .initiatesTask("processAudioVideoEvidence", "Process audio/video evidence - LO", 2)
                .build(),
            event("nonCompliant")
                .initiatesTask("reviewNonCompliantAppeal", "Review Non Compliant Appeal - LO", 2)
                .build(),
            event("draftToNonCompliant")
                .initiatesTask("reviewNonCompliantAppeal", "Review Non Compliant Appeal - LO", 2)
                .build(),
            event("updateNotListable")
                .withCaseData("action", "reviewByTcw")
                .initiatesTask("ftaNotProvidedAppointeeDetailsTcw", "FTA Not Provided Appointee Details - LO", 2)
                .build(),
            eventWithState("sORRequest", "postHearing")
                .withCaseData("sscsHearingRecordings", emptyList())
                .initiatesTask("uploadHearingRecordingSORCTSC", "Upload Hearing Recording: SOR - CTSC", 2)
                .build(),
            eventWithState("validSendToInterloc", "postHearing")
                .withCaseData("interlocReferralReason", "reviewLibertyToApplyApplication")
                .initiatesTask("reviewLibertytoApplyApplication", "Review Liberty to Apply Application - Judge", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("interlocReferralReason", "reviewCorrectionApplication")
                .initiatesTask("reviewCorrectionApplicationJudge", "Review Correction Application - Judge", 2)
                .build(),
            event("adminActionCorrection")
                .initiatesTask("reviewCorrectionApplicationJudge", "Review Correction Application - Judge", 2)
                .build(),
            eventWithState("validSendToInterloc", "postHearing")
                .withCaseData("interlocReferralReason", "statementOfReasonsApplication")
                .withCaseData("issueFinalDecisionDate", TODAY.plusDays(-28L)) // 1 month or less ago
                .initiatesTask("writeStatementofReason", "Write Statement of Reason - Judge", 28)
                .build(),
            eventWithState("setAsideRefusedSOR", "postHearing")
                .withCaseData("issueFinalDecisionDate", TODAY.plusDays(-28L)) // 1 month or less ago
                .initiatesTask("writeStatementofReason", "Write Statement of Reason - Judge", 28)
                .build(),
            eventWithState("sORExtendTime", "postHearing")
                .initiatesTask("writeStatementofReason", "Write Statement of Reason - Judge", 28)
                .build(),
            eventWithState("validSendToInterloc", "postHearing")
                .withCaseData("interlocReferralReason", "lateStatementOfReasonsApplication")
                .withCaseData("issueFinalDecisionDate", TODAY.plusDays(-32)) // over 1 month ago
                .initiatesTask("reviewLateStatementofReasonsApplication", "Review Late SOR Application - Judge", 2)
                .build(),
            eventWithState("validSendToInterloc", "postHearing")
                .withCaseData("interlocReferralReason", "reviewPermissionToAppealApplication")
                .initiatesTask("reviewPermissiontoAppealApplication", "Review Permission to Appeal Application - Judge", 2)
                .build(),
            eventWithState("validSendToInterloc", "postHearing")
                .withCaseData("interlocReferralReason", "reviewPermissionToAppealApplication")
                .withCaseData("otherParties", List.of("other party 1"))
                .initiatesTaskWithDelay("reviewPermissiontoAppealApplication", "Review Permission to Appeal Application - Judge", 21, 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("interlocReferralReason", "reviewUpperTribunalDecision")
                .initiatesTask("reviewRemittedDecisionandProvideListingDirections",
                               "Review Remitted Decision and Provide Listing Directions - Judge", 2)
                .build(),
            event("libertyToApplyGranted")
                .initiatesTask("reviewPostHearingNoticeforListingRequirements", "Review Post Hearing Notice for Listing Requirements - CTSC", 10)
                .build(),
            event("reviewAndSetAside")
                .initiatesTask("reviewPostHearingNoticeforListingRequirements", "Review Post Hearing Notice for Listing Requirements - CTSC", 10)
                .build(),
            eventWithState("validSendToInterloc", "dormantAppealState")
                .withCaseData("interlocReferralReason", "reviewSetAsideApplication")
                .initiatesTask("reviewSetAsideApplication", "Review Set Aside Application - Judge", 2)
                .build(),
            eventWithState("validSendToInterloc", "postHearing")
                .withCaseData("interlocReferralReason", "reviewSetAsideApplication")
                .withCaseData("jointParty", "Yes")
                .initiatesTaskWithDelay("reviewSetAsideApplication", "Review Set Aside Application - Judge", 21, 2)
                .build(),
            eventWithState("actionFurtherEvidence", "dormantAppealState")
                .withCaseData("scannedDocumentTypes", List.of("correctionApplication"))
                .withCaseData("furtherEvidenceAction", dynamicListValue("sendToInterlocReviewByJudge"))
                .initiatesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge - CTSC", 3)
                .build(),
            eventWithState("postHearingRequest", "dormantAppealState")
                .withCaseData("action", "correction")
                .initiatesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge - CTSC", 3)
                .build(),
            event("sendToFirstTier")
                .withCaseData("sendToFirstTier", Map.of("action", "remitted"))
                .initiatesTask("shareRemittedDecision", "Allocate Judge and Share Remitted Decision - CTSC", 20)
                .build(),
            eventWithState("sendToFirstTier", "dormantAppealState")
                .withCaseData("sendToFirstTier", Map.of("action", "remade"))
                .initiatesTask("shareRemadeDecision", "Share Remade Decision - CTSC", 20)
                .build(),
            eventWithState("sendToFirstTier", "dormantAppealState")
                .withCaseData("sendToFirstTier", Map.of("action", "refused"))
                .initiatesTask("shareRefusedDecision", "Share Refused Decision - CTSC", 20)
                .build(),
            eventWithState("libertyToApplyRequest", "dormantAppealState")
                .initiatesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge - CTSC", 3)
                .build(),
            eventWithState("permissionToAppealRequest", "dormantAppealState")
                .initiatesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge - CTSC", 3)
                .build(),
            eventWithState("setAsideRequest", "dormantAppealState")
                .initiatesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge - CTSC", 3)
                .build(),
            eventWithState("sORRequest", "dormantAppealState")
                .withCaseData("issueFinalDecisionDate", TODAY.minusDays(21))
                .initiatesTask("reviewApplicationandAllocateJudge", "Review Application and Allocate Judge - CTSC", 3)
                .build(),
            eventWithState("sORRequest", "dormantAppealState")
                .withCaseData("issueFinalDecisionDate", TODAY.minusDays(45))
                .initiatesTask("reviewLateStatementofReasonsApplicationAndAllocateJudge",
                               "Review Late SOR Application and Allocate Judge - CTSC", 2,
                               "reviewStatementofReasonsApplication")
                .build(),
            event("validSendToInterloc")
                .withCaseData("workType", "post")
                .withCaseData("action", "reviewByJudge")
                .withCaseData("interlocReferralReason", "adviceOnHowToProceed")
                .initiatesTask("referredByAdminJudgePostHearing", "Referred By Admin - Judge", 2)
                .build(),
            event("tcwReferToJudge")
                .withCaseData("workType", "post")
                .initiatesTask("referredByTcwPostHearing", "Referred By TCW - Judge", 2)
                .build(),
            event("validSendToInterloc")
                .withCaseData("interlocReferralReason", "timeExtension")
                .withCaseData("action", "reviewByJudge")
                .initiatesTask("referredToInterlocJudge", "Referred to interloc - Time extension - Judge", 2)
                .build(),
            event("hmctsResponseReviewed")
                .withCaseData("interlocReferralReason", "reviewPostponementRequest")
                .withCaseData("action", dynamicListValue("reviewByJudge"))
                .build(),
            event("validSendToInterloc")
                .withCaseData("interlocReferralReason", "reviewPostponementRequest")
                .withCaseData("action", "reviewByJudge")
                .build(),
            event("validSendToInterloc")
                .withCaseData("interlocReferralReason", "reviewReinstatementRequest")
                .withCaseData("action", "reviewByJudge")
                .build(),
            event("nonCompliantSendToInterloc")
                .withCaseData("interlocReferralReason", "reviewPostponementRequest")
                .build(),
            event("writeFinalDecision")
                .initiatesTask("issueDecisionJudge", "Issue Decision - Judge", 2)
                .build(),
            event("hmctsResponseReviewed")
                .withCaseData("action", dynamicListValue("reviewByJudge"))
                .withCaseData("interlocReferralReason", "listingDirections")
                .initiatesTask("provideListingDirections", "Provide Listing Directions - Judge", 2)
                .build(),
            event("actionPostponementRequest")
                .withCaseData("action", "reviewByTcw")
                .initiatesTask("reviewPostponementRequestTCW", "Review postponement request - LO", 2)
                .build(),
            event("actionFurtherEvidence")
                .withCaseData("scannedDocumentTypes", List.of("postponementRequest"))
                .withCaseData("furtherEvidenceAction", dynamicListValue("sendToInterlocReviewByJudge"))
                .initiatesTask("reviewPostponementRequestJudge", "Review Postponement Request - Judge", 2)
                .build(),
            event("actionFurtherEvidence")
                .withCaseData("scannedDocumentTypes", List.of("postponementRequest"))
                .withCaseData("furtherEvidenceAction", dynamicListValue("sendToInterlocReviewByTcw"))
                .build(),
            event("validSendToInterloc")
                .withCaseData("action", "reviewByJudge")
                .withCaseData("interlocReferralReason", "listingDirections")
                .build(),
            eventWithState("validSendToInterloc", "hearing")
                .withCaseData("interlocReferralReason", "reviewPostponementRequest")
                .withCaseData("action", "reviewByJudge")
                .initiatesTask("reviewPostponementRequestJudge", "Review Postponement Request - Judge", 2)
                .build(),
            eventWithState("validSendToInterloc", "dormantAppealState")
                .withCaseData("interlocReferralReason", "reviewPostponementRequest")
                .withCaseData("action", "reviewByJudge")
                .build(),
            eventWithState("validSendToInterloc", "hearing")
                .withCaseData("interlocReferralReason", "reviewPostponementRequest")
                .withCaseData("action", "reviewByTcw")
                .initiatesTask("reviewPostponementRequestTCW", "Review Postponement Request - LO", 2)
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
        assertThat(logic.getRules().size(), is(79));
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
                            "delayUtilNonWorkingCalendar", CourtSpecificCalendars.SCOTLAND_CALENDAR
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
