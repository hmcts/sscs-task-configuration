package uk.gov.hmcts.reform.sscstaskconfiguration.dmn;

import lombok.extern.slf4j.Slf4j;
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
import uk.gov.hmcts.reform.sscstaskconfiguration.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_CONFIGURATION_SSCS_BENEFIT;

@Slf4j
class CamundaTaskConfigurationTest extends DmnDecisionTableBaseUnitTest {

    public static final String DEFAULT_TIME = "T00:00:00.000Z";

    static Stream<Arguments> nextHearingScenarioProvider() {
        return Stream.of(
            // no hearings
            Arguments.of(
            "reviewIncompleteAppeal",
            CaseDataBuilder.defaultCase()
                .build(),
            ConfigurationExpectationBuilder.defaultExpectations()
                .build()
            ),
            // past hearing only
            Arguments.of(
                "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase()
                    .withHearing(CaseDataBuilder.createHearing("1234567", DateUtils.lastMonth()))
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .build()
            ),
            // hearing today
            Arguments.of(
                "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase()
                    .withHearing(CaseDataBuilder.createHearing("1234567", DateUtils.today()))
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(ConfigurationExpectationBuilder.PRIORITY_DATE,
                                   DateUtils.today() + DEFAULT_TIME,true)
                    .expectedValue(ConfigurationExpectationBuilder.NEXT_HEARING_ID,
                                   "1234567",true)
                    .expectedValue(ConfigurationExpectationBuilder.NEXT_HEARING_DATE,
                                   DateUtils.today() + DEFAULT_TIME,true)
                    .build()
            ),
            // one future hearing
            Arguments.of(
                "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase()
                    .withHearing(CaseDataBuilder.createHearing("1234567", DateUtils.tomorrow()))
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(ConfigurationExpectationBuilder.PRIORITY_DATE,
                                   DateUtils.tomorrow() + DEFAULT_TIME,true)
                    .expectedValue(ConfigurationExpectationBuilder.NEXT_HEARING_ID,
                                   "1234567",true)
                    .expectedValue(ConfigurationExpectationBuilder.NEXT_HEARING_DATE,
                                   DateUtils.tomorrow() + DEFAULT_TIME,true)
                    .build()
            ),
            // future hearings wrong order
            Arguments.of(
                "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase()
                    .withHearings(List.of(
                        CaseDataBuilder.createHearing("2222222", DateUtils.nextWeek()),
                        CaseDataBuilder.createHearing("1111111", DateUtils.tomorrow())))
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(ConfigurationExpectationBuilder.PRIORITY_DATE,
                                   DateUtils.tomorrow() + DEFAULT_TIME, true)
                    .expectedValue(ConfigurationExpectationBuilder.NEXT_HEARING_ID,
                                   "1111111",true)
                    .expectedValue(ConfigurationExpectationBuilder.NEXT_HEARING_DATE,
                                   DateUtils.tomorrow() + DEFAULT_TIME,true)
                    .build()
            ),
            // past and future hearing
            Arguments.of(
                "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase()
                    .withHearings(List.of(
                        CaseDataBuilder.createHearing("1111111", DateUtils.lastWeek()),
                        CaseDataBuilder.createHearing("2222222", DateUtils.nextWeek()),
                        CaseDataBuilder.createHearing("3333333", DateUtils.nextMonth())))
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(ConfigurationExpectationBuilder.PRIORITY_DATE,
                                   DateUtils.nextWeek() + DEFAULT_TIME,true)
                    .expectedValue(ConfigurationExpectationBuilder.NEXT_HEARING_ID,
                                   "2222222",true)
                    .expectedValue(ConfigurationExpectationBuilder.NEXT_HEARING_DATE,
                                   DateUtils.nextWeek() + DEFAULT_TIME,true)
                    .build()
            )
        );
    }

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
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .build()
            ),
            Arguments.of(
               "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase()
                    .isScottishCase("Yes")
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue("dueDateNonWorkingCalendar", ConfigurationExpectationBuilder.SCOTLAND_CALENDAR,true)
                    .build()
            )
        );
    }

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_CONFIGURATION_SSCS_BENEFIT;
    }

    @ParameterizedTest(name = "task type: {0} case data: {1}")
    @MethodSource({"nextHearingScenarioProvider", "scenarioProvider"})
    void should_return_correct_configuration_values_for_scenario(
            String taskType,
            Map<String, Object> caseData,
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
        assertThat(logic.getRules().size(), is(15));
    }

    private void resultsMatch(List<Map<String, Object>> results, List<Map<String, Object>> expectation) {
        assertThat(results.size(), is(expectation.size()));
        for (int index = 0; index < expectation.size(); index++) {
            if ("dueDateOrigin".equals(expectation.get(index).get("name"))) {
                assertEquals(
                    results.get(index).get("canReconfigure"),
                    expectation.get(index).get("canReconfigure"));
                assertTrue(validNow(
                    LocalDateTime.parse(results.get(index).get("value").toString()),
                    LocalDateTime.parse(expectation.get(index).get("value").toString())));
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

