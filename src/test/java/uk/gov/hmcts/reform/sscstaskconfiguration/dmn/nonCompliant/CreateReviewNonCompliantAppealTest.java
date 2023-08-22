package uk.gov.hmcts.reform.sscstaskconfiguration.dmn.nonCompliant;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_INITIATION_SSCS_BENEFIT;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTableBaseUnitTest;

public class CreateReviewNonCompliantAppealTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_INITIATION_SSCS_BENEFIT;
    }

    static Stream<Arguments> nonCompliantScenarioProvider() {

        return Stream.of(
            Arguments.of(
                "nonCompliant",
                singletonList(
                    Map.of(
                        "taskId", "nonCompliantCase",
                        "name", "Review non-compliant appeal",
                        "group", "TCW",
                        "processCategories", "Non-compliant appeal"

                    )
                )
            )
        );
    }

    static Stream<Arguments> draftToNonCompliantScenarioProvider() {

        return Stream.of(
            Arguments.of(
                "draftToNonCompliant",
                singletonList(
                    Map.of(
                        "taskId", "nonCompliantCase",
                        "name", "Review non-compliant appeal",
                        "group", "TCW",
                        "processCategories", "Non-compliant appeal"

                    )
                )
            )
        );
    }


    @ParameterizedTest(name = "event id: {0} ")
    @MethodSource("nonCompliantScenarioProvider")
    void testNonCompliantDmn(String eventId,
                             List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);


        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectation));

    }


    @ParameterizedTest(name = "event id: {0} ")
    @MethodSource("draftToNonCompliantScenarioProvider")
    void testDraftToNonCompliantDmn(String eventId,
                                    List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);


        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectation));

    }
}
