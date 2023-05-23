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
        assertThat(logic.getRules().size(), is(10));

    }

}
