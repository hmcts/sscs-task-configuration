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

import java.util.List;
import java.util.Map;
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
            Arguments.of(
                "nonCompliant",
                List.of(
                    Map.of(
                        "taskType", "reviewTheAppeal",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "requestInfoIncompleteApplication",
                List.of(
                    Map.of(
                        "taskType", "reviewIncompleteAppeal",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "interlocInformationReceived",
                List.of(
                    Map.of(
                        "taskType", "reviewInformationRequested",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "validSendToInterloc",
                List.of(
                    Map.of(
                        "taskType", "reviewInformationRequested",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "interlocSendToTcw",
                List.of(
                    Map.of(
                        "taskType", "reviewInformationRequested",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "actionFurtherEvidence",
                List.of(
                    Map.of(
                        "taskType", "actionUnprocessedCorrespondence",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "hmctsResponseReviewed",
                List.of(
                    Map.of(
                        "taskType", "reviewFtaResponse",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "requestTranslationFromWLU",
                List.of(
                    Map.of(
                        "taskType", "reviewBilingualDocument",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "uploadWelshDocument",
                List.of(
                    Map.of(
                        "taskType", "reviewValidAppeal",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "updateListingRequirement",
                List.of(
                    Map.of(
                        "taskType", "reviewListingError",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "createBundle",
                List.of(
                    Map.of(
                        "taskType", "createBundleAndAllocateCaseRoles",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "directionIssued",
                List.of(
                    Map.of(
                        "taskType", "reviewFtaValidityChallenge",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "ftaResponseOverdue",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "decisionIssued",
                List.of(
                    Map.of(
                        "taskType", "reviewFtaValidityChallenge",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "sendToAdmin",
                List.of(
                    Map.of(
                        "taskType", "reviewFtaValidityChallenge",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "tcwReferToJudge",
                List.of(
                    Map.of(
                        "taskType", "reviewFtaValidityChallenge",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "interlocReviewStateAmend",
                List.of(
                    Map.of(
                        "taskType", "reviewFtaValidityChallenge",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "ftaResponseOverdue",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "processAudioVideoEvidence",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "processAudioVideo",
                List.of(
                    Map.of(
                        "taskType", "processAudioVideoEvidence",
                        "completionMode", "Auto"
                    )
                )
            )
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
        assertThat(logic.getRules().size(), is(13));

    }


}
