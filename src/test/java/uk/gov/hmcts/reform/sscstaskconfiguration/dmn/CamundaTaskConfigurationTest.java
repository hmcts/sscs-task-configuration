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
import uk.gov.hmcts.reform.sscstaskconfiguration.utils.CaseDataBuilder;
import uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_CONFIGURATION_SSCS_BENEFIT;

class CamundaTaskConfigurationTest extends DmnDecisionTableBaseUnitTest {

    static Stream<Arguments> scenarioProvider() {
        return Stream.of(
            Arguments.of(
                "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations().build()
            ),
            Arguments.of(
                "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase()
                    .withNextHearing("1234567", "2023-03-16")
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue("priorityDate", "2023-03-16", true)
                    .expectedValue("nextHearingId", "1234567", true)
                    .expectedValue("nextHearingDate", "2023-03-16", true)
                    .build()
            ),
            Arguments.of(
                "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase()
                    .isScottishCase("Yes")
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue("dueDateNonWorkingCalendar", ConfigurationExpectationBuilder.SCOTLAND_CALENDAR, true)
                    .build()
            ),
            Arguments.of(
                "requestInfoIncompleteApplication",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue("minorPriority", "300", true)
                    .expectedValue("majorPriority", "3000", true)
                    .expectedValue("description", "[Review Information Requested](/case/SSCS/Benefit/${[CASE_REFERENCE]}/trigger/interlocInformationReceived)", true)
                    .expectedValue("dueDateIntervalDays", "3", true)
                    .build()
            ),
            Arguments.of(
                "dwpUploadResponse",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue("minorPriority", "300", true)
                    .expectedValue("majorPriority", "3000", true)
                    .expectedValue("description", "[Review Information Requested](/case/SSCS/Benefit/${[CASE_REFERENCE]}/trigger/dwpUploadResponse)", true)
                    .expectedValue("dueDateIntervalDays", "2", true)
                    .build()
            )
        );
    }

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_CONFIGURATION_SSCS_BENEFIT;
    }

    @ParameterizedTest(name = "task type: {0} case data: {1}")
    @MethodSource("scenarioProvider")
    void should_return_correct_configuration_values_for_scenario(
        String taskType, Map<String, Object> caseData,
        List<Map<String, Object>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskType", taskType);
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        resultsMatch(dmnDecisionTableResult.getResultList(), expectation);
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(21));
    }

    private void resultsMatch(List<Map<String, Object>> results, List<Map<String, Object>> expectation) {
        assertThat(results.size(), is(expectation.size()));
        for (int index = 0; index < expectation.size(); index++) {
            if ("dueDateOrigin".equals(expectation.get(index).get("name"))) {
                assertEquals(
                    results.get(index).get("canReconfigure"),
                    expectation.get(index).get("canReconfigure")
                );
                assertTrue(validNow(
                    LocalDateTime.parse(results.get(index).get("value").toString()),
                    LocalDateTime.parse(expectation.get(index).get("value").toString())
                ));
            } else {
                assertThat(results.get(index), is(expectation.get(index)));
            }
        }
    }

    private boolean validNow(LocalDateTime actual, LocalDateTime expected) {
        LocalDateTime now = LocalDateTime.now();
        return actual != null
            && (expected.isEqual(actual) || expected.isBefore(actual))
            && (now.isEqual(actual) || now.isAfter(actual));
    }
}

