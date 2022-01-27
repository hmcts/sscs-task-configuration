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

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_INITIATION_SSCS_ASYLUM;

class CamundaTaskInitiationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_INITIATION_SSCS_ASYLUM;
    }

    static Stream<Arguments> scenarioProvider() {

        return concat(of(rowOne()), rowTwo());
    }

    /*private static Arguments rowThree() {
        return Arguments.of(
            "processAudioVideo",
            null,
            Map.of("processAudioVideoAction", 2),
            singletonList(
                Map.of(
                    "taskId", "processAudioVideoEvidence",
                    "name", "Process audio / video evidence",
                    "group", "Judge",
                    "processCategories", "Review audio/video evidence"
                )
            )
        );
    }*/

    private static Stream<Arguments> rowTwo() {
        return of(
                "dwpSupplementaryResponse",
                "uploadFurtherEvidence",
                "uploadDocumentFurtherEvidence",
                "dwpUploadResponse",
                "uploadDocument"
            )
            .map(taskId ->
                     Arguments.of(
                         taskId,
                         null,
                         Map.of("addedDocument", Map.of("addedDocument", "document")),
                         singletonList(
                             Map.of(
                                 "taskId", "processAudioVideoEvidence",
                                 "name", "Process audio / video evidence",
                                 "group", "TCW",
                                 "processCategories", "Review audio/video evidence"
                             )
                         )
                     )
            );
    }

    private static Arguments rowOne() {
        return Arguments.of(
            "nonCompliant",
            null,
            null,
            singletonList(
                Map.of(
                    "taskId", "nonCompliantCase",
                    "name", "Review non-compliant appeal",
                    "group", "TCW",
                    "workingDaysAllowed", 2,
                    "processCategories", "Non-compliant appeal"
                )
            )
        );
    }

    @ParameterizedTest(name = "event id: {0} post event state: {1} appeal type: {2}")
    @MethodSource("scenarioProvider")
    void given_multiple_event_ids_should_evaluate_dmn(String eventId,
                                                      String postEventState,
                                                      Map<String, Object> caseData,
                                                      List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {

        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(3));

    }

}
