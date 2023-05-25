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
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_CANCELLATION_SSCS_BENEFIT;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.CancellationScenarioBuilder.event;

class CamundaTaskCancellationTest extends DmnDecisionTableBaseUnitTest {
    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_CANCELLATION_SSCS_BENEFIT;
    }

    static Stream<Arguments> scenarioProvider() {
        return Stream.of(
            event("nonCompliant").cancelAll().build(),
            event("addHearing").reconfigureAll().build(),
            event("caseUpdated").reconfigureAll().build(),
            event("voidCase")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewInformationRequested")
                .cancel("reviewFtaResponse")
                .cancel("reviewValidAppeal")
                .cancel("reviewListingError")
                .cancel("createBundleAndAllocateCaseRoles").build(),
            event("appealWithdrawn")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewInformationRequested")
                .cancel("reviewFtaResponse")
                .cancel("reviewValidAppeal")
                .cancel("reviewListingError")
                .cancel("createBundleAndAllocateCaseRoles").build(),
            event("appealDormant")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewInformationRequested")
                .cancel("reviewFtaResponse")
                .cancel("reviewValidAppeal")
                .cancel("reviewListingError")
                .cancel("createBundleAndAllocateCaseRoles").build(),
            event("confirmLapsed")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewInformationRequested")
                .cancel("reviewFtaResponse")
                .cancel("createBundleAndAllocateCaseRoles").build(),
            event("struckOut")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewInformationRequested")
                .cancel("reviewFtaResponse")
                .cancel("reviewValidAppeal")
                .cancel("reviewListingError")
                .cancel("createBundleAndAllocateCaseRoles").build(),
            event("validSendToInterloc")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewBfDate").build(),
            event("makeCaseUrgent")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewInformationRequested")
                .cancel("reviewBfDate").build(),
            event("readyToList")
                .cancel("reviewIncompleteAppeal")
                .cancel("reviewInformationRequested")
                .cancel("reviewFtaResponse").build(),
            event("decisionIssued")
                .cancel("reviewIncompleteAppeal").build(),
            event("cancelTranslations")
                .cancel("reviewBilingualDocument").build(),
            event("interlocSendToTcw")
                .cancel("reviewBfDate").build()
        );
    }

    @ParameterizedTest(name = "from state: {0}, event id: {1}, state: {2}")
    @MethodSource("scenarioProvider")
    void given_multiple_event_ids_should_evaluate_dmn(String fromState,
                                                      String eventId,
                                                      String state,
                                                      List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("fromState", fromState);
        inputVariables.putValue("event", eventId);
        inputVariables.putValue("state", state);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {

        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(3));
        assertThat(logic.getOutputs().size(), is(4));
        assertThat(logic.getRules().size(), is(10));

    }
}
