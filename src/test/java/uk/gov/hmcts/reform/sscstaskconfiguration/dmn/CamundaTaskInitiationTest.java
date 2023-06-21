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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_INITIATION_SSCS_BENEFIT;

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
                "dwpSupplementaryResponse",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "actionUnprocessedCorrespondence",
                        "name", "Action Unprocessed Correspondence",
                        "workingDaysAllowed", 10,
                        "processCategories", "Routine work"
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
                Arrays.asList(
                    Map.of(
                        "taskId", "actionUnprocessedCorrespondence",
                        "name", "Action Unprocessed Correspondence",
                        "workingDaysAllowed", 10,
                        "processCategories", "Routine work"
                    ),
                    Map.of(
                        "taskId", "reviewBilingualDocument",
                        "name", "Review Bi-Lingual Document",
                        "workingDaysAllowed", 10,
                        "processCategories", "reviewBilingualDocument"
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
                        "processCategories", "Routine work"
                    )
                )
            ),
            Arguments.of(
                "uploadDocument",
                null,
                Map.of("Data", Map.of("languagePreferenceWelsh", true)),
                Arrays.asList(
                    Map.of(
                        "taskId", "actionUnprocessedCorrespondence",
                        "name", "Action Unprocessed Correspondence",
                        "workingDaysAllowed", 10,
                        "processCategories", "Routine work"
                    ),
                    Map.of(
                        "taskId", "reviewBilingualDocument",
                        "name", "Review Bi-Lingual Document",
                        "workingDaysAllowed", 10,
                        "processCategories", "reviewBilingualDocument"
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
                        "processCategories", "Routine work"
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
                Arrays.asList(
                    Map.of(
                        "taskId", "actionUnprocessedCorrespondence",
                        "name", "Action Unprocessed Correspondence",
                        "workingDaysAllowed", 10,
                        "processCategories", "Routine work"
                    ),
                    Map.of(
                        "taskId", "reviewBilingualDocument",
                        "name", "Review Bi-Lingual Document",
                        "workingDaysAllowed", 10,
                        "processCategories", "reviewBilingualDocument"
                    )
                )
            ),
            Arguments.of(
                "uploadDocumentFurtherEvidence",
                null,
                Map.of("Data", Map.of("languagePreferenceWelsh", true)),
                Arrays.asList(
                    Map.of(
                        "taskId", "actionUnprocessedCorrespondence",
                        "name", "Action Unprocessed Correspondence",
                        "workingDaysAllowed", 10,
                        "processCategories", "Routine work"
                    ),
                    Map.of(
                        "taskId", "reviewBilingualDocument",
                        "name", "Review Bi-Lingual Document",
                        "workingDaysAllowed", 10,
                        "processCategories", "reviewBilingualDocument"
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
                        "processCategories", "Routine work"
                    )
                )
            ),
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
                "listingError",
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
                "reviewBfDateRequired",
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
                "createBundleAndAllocateCaseRolesRequired",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "createBundleAndAllocateCaseRoles",
                        "name", "Create Bundle And Allocate Case Roles",
                        "workingDaysAllowed", 3,
                        "processCategories", "createBundleAndAllocateCaseRoles"
                    )
                )
            ),
            Arguments.of(
                "reviewOutstandingDraftDecisionRequired",
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
                "updateHearingDetailsRequired",
                null,
                null,
                singletonList(
                    Map.of(
                        "taskId", "updateHearingDetails",
                        "name", "Update Hearing Details",
                        "workingDaysAllowed", 2,
                        "processCategories", "updateHearingDetails"
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
                "sendToDwp",
                null,
                Map.of("Data",
                       Map.of("caseManagementCategory",
                              Map.of("value",
                                     Map.of ("code", "childSupport")
                              )
                       )
                ),
                singletonList(
                    Map.of(
                        "taskId", "ftaResponseOverdue",
                        "name", "Referred to Interloc - FTA response overdue",
                        "delayDuration", 42,
                        "workingDaysAllowed", 2,
                        "processCategories", "ftaResponseOverdue"
                    )
                )
            ),
            Arguments.of(
                "sendToDwp",
                null,
                Map.of("Data",
                       Map.of("caseManagementCategory",
                              Map.of("value",
                                     Map.of ("code", "PIP")
                              )
                       )
                ),
                singletonList(
                    Map.of(
                        "taskId", "ftaResponseOverdue",
                        "name", "Referred to Interloc - FTA response overdue",
                        "delayDuration", 28,
                        "workingDaysAllowed", 2,
                        "processCategories", "ftaResponseOverdue"
                    )
                )
            ),
            Arguments.of(
                "ftaResponseOverdue",
                null,
                Map.of("Data",
                       Map.of("directionTypeDl",
                              Map.of("value",
                                     Map.of ("code", "grantExtension")
                              )
                       )
                ),
                singletonList(
                    Map.of(
                        "taskId", "ftaResponseOverdue",
                        "name", "Referred to Interloc - FTA response overdue",
                        "workingDaysAllowed", 2,
                        "processCategories", "ftaResponseOverdue"
                    )
                )
            ),
            Arguments.of(
                "uploadDocument",
                null,
                Map.of("Data", Map.of("scannedDocumentTypes", Arrays.asList("audioDocument"))),
                Arrays.asList(
                    Map.of(
                        "taskId", "actionUnprocessedCorrespondence",
                        "name", "Action Unprocessed Correspondence",
                        "workingDaysAllowed", 10,
                        "processCategories", "Routine work"
                    ),
                    Map.of(
                        "taskId", "processAudioVideoEvidence",
                        "name", "Process audio/video evidence",
                        "workingDaysAllowed", 2,
                        "processCategories", "processAudioVideoEvidence"
                    )
                )
            ),
            Arguments.of(
                "dwpSupplementaryResponse",
                null,
                Map.of("Data", Map.of("scannedDocumentTypes", Arrays.asList("videoDocument", "audioDocument"))),
                Arrays.asList(
                    Map.of(
                        "taskId", "actionUnprocessedCorrespondence",
                        "name", "Action Unprocessed Correspondence",
                        "workingDaysAllowed", 10,
                        "processCategories", "Routine work"
                    ),
                    Map.of(
                        "taskId", "processAudioVideoEvidence",
                        "name", "Process audio/video evidence",
                        "workingDaysAllowed", 2,
                        "processCategories", "processAudioVideoEvidence"
                    )
                )
            ),
            Arguments.of(
                "dwpUploadResponse",
                null,
                Map.of("Data", Map.of("scannedDocumentTypes", Arrays.asList("audioDocument", "other"))),
                singletonList(
                    Map.of(
                        "taskId", "processAudioVideoEvidence",
                        "name", "Process audio/video evidence",
                        "workingDaysAllowed", 2,
                        "processCategories", "processAudioVideoEvidence"
                    )
                )
            ),
            Arguments.of(
                "uploadFurtherEvidence",
                null,
                Map.of("Data", Map.of("scannedDocumentTypes", Arrays.asList("videoDocument"))),
                singletonList(
                    Map.of(
                        "taskId", "processAudioVideoEvidence",
                        "name", "Process audio/video evidence",
                        "workingDaysAllowed", 2,
                        "processCategories", "processAudioVideoEvidence"
                    )
                )
            ),
            Arguments.of(
                "uploadDocumentFurtherEvidence",
                null,
                Map.of("Data", Map.of("scannedDocumentTypes", Arrays.asList("videoDocument"))),
                 Arrays.asList(
                    Map.of(
                        "taskId", "actionUnprocessedCorrespondence",
                        "name", "Action Unprocessed Correspondence",
                        "workingDaysAllowed", 10,
                        "processCategories", "Routine work"
                    ),
                    Map.of(
                        "taskId", "processAudioVideoEvidence",
                        "name", "Process audio/video evidence",
                        "workingDaysAllowed", 2,
                        "processCategories", "processAudioVideoEvidence"
                    )
                )
            ),
            Arguments.of(
                "validSendToInterloc",
                null,
                Map.of("Data", Map.of("action", "reviewByTcw")),
                singletonList(
                    Map.of(
                        "taskId", "referredByAdminTcw",
                        "name", "Referred by Admin",
                        "workingDaysAllowed", 2,
                        "processCategories", "referredByAdminTcw"
                    )
                )
            ),
            Arguments.of(
                "createBundle",
                null,
                Map.of("Data", Map.of(
                    "assignedCaseRoles", Arrays.asList("tribunal-member-1", "tribunal-member-2", "tribunal-member-3"))),
                Arrays.asList(
                    Map.of(
                        "taskId", "prepareForHearingTribunalMember1",
                        "name", "Prepare for hearing",
                        "workingDaysAllowed", 2,
                        "processCategories", "prepareForHearingTribunalMember"
                    ),
                    Map.of(
                        "taskId", "prepareForHearingTribunalMember2",
                        "name", "Prepare for hearing",
                        "workingDaysAllowed", 2,
                        "processCategories", "prepareForHearingTribunalMember"
                    ),
                    Map.of(
                        "taskId", "prepareForHearingTribunalMember3",
                        "name", "Prepare for hearing",
                        "workingDaysAllowed", 2,
                        "processCategories", "prepareForHearingTribunalMember"
                    )
                )
            ),
            Arguments.of(
                "createBundle",
                null,
                Map.of("Data", Map.of(
                    "assignedCaseRoles", Arrays.asList("tribunal-member-1","appraiser-1"))),
                Arrays.asList(
                    Map.of(
                        "taskId", "prepareForHearingTribunalMember1",
                        "name", "Prepare for hearing",
                        "workingDaysAllowed", 2,
                        "processCategories", "prepareForHearingTribunalMember"
                    ),
                    Map.of(
                        "taskId", "prepareHearingAppraiser1",
                        "name", "Prepare for hearing",
                        "workingDaysAllowed", 2,
                        "processCategories", "prepareHearingAppraiser"
                    )
                )
            ),
            Arguments.of(
                "createBundle",
                null,
                Map.of("Data", Map.of(
                    "assignedCaseRoles", Arrays.asList("tribunal-member-2","appraiser-2"))),
                Arrays.asList(
                    Map.of(
                        "taskId", "prepareForHearingTribunalMember2",
                        "name", "Prepare for hearing",
                        "workingDaysAllowed", 2,
                        "processCategories", "prepareForHearingTribunalMember"
                    ),
                    Map.of(
                        "taskId", "prepareHearingAppraiser2",
                        "name", "Prepare for hearing",
                        "workingDaysAllowed", 2,
                        "processCategories", "prepareHearingAppraiser"
                    )
                )
            ),
            Arguments.of(
                "createBundle",
                null,
                Map.of("Data", Map.of(
                    "assignedCaseRoles", Arrays.asList("tribunal-member-3"))),
                Arrays.asList(
                    Map.of(
                        "taskId", "prepareForHearingTribunalMember3",
                        "name", "Prepare for hearing",
                        "workingDaysAllowed", 2,
                        "processCategories", "prepareForHearingTribunalMember"
                    )
                )
            ),
            Arguments.of(
                "createBundle",
                null,
                null,
                Arrays.asList()
            )
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
        assertThat(logic.getRules().size(), is(23));

    }

}
