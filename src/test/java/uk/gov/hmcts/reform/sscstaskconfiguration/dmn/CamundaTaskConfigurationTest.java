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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_CONFIGURATION_SSCS_BENEFIT;

class CamundaTaskConfigurationTest extends DmnDecisionTableBaseUnitTest {

    static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    static LocalDate today = LocalDate.now();
    static String DEFAULT_CALENDER = "https://www.gov.uk/bank-holidays/england-and-wales.json";
    static String LAST_MONTH = today.minusMonths(1).format(dateFormat);
    static String TOMORROW = today.plusDays(1).format(dateFormat);
    static String NEXT_MONTH = today.plusMonths(1).format(dateFormat);

    static List<Map<String, Object>> hearings = List.of(
        Map.of(
            "value", Map.of(
                "hearingId", "1000",
                "hearingDate", LAST_MONTH
            )
        ),
        Map.of(
            "value", Map.of(
                "hearingId", "2000",
                "hearingDate", TOMORROW
            )
        ),
        Map.of(
            "value", Map.of(
                "hearingId", "3000",
                "hearingDate", NEXT_MONTH
            ))
    );

    static Arguments reviewIncompleteAppealScenario() {
        return Arguments.of(
            "reviewIncompleteAppeal",
            Map.of(
                "caseNamePublic", "Joe Blogs",
                "hearings", hearings,
                "regionalProcessingCenter", Map.of(
                    "name", "BRADFORD",
                    "epimsId", "123456"
                ),
                "caseManagementCategory", Map.of(
                    "value", Map.of("code", "PIP")
                )
            ),
            List.of(
                expectedValue("caseName", "Joe Blogs", true),
                expectedValue("caseManagementCategory", "PIP", true),
                expectedValue("location", "123456", true),
                expectedValue("locationName", "BRADFORD", true),
                expectedValue("work_type", "routine_work", true),
                expectedValue("roleCategory", "CTSC", true),
                expectedValue("priorityDate", TOMORROW, true),
                expectedValue("minorPriority", "500", true),
                expectedValue("majorPriority", "5000", true),
                expectedValue(
                    "description",
                    "[Request Information From Party](/case/SSCS/Benefit/${[CASE_REFERENCE]}/trigger/requestInfoFromParty)",
                    true
                ),
                expectedValue("nextHearingId", "2000", true),
                expectedValue("nextHearingDate", TOMORROW, true),
                expectedValue("dueDateOrigin", now(), true),
                expectedValue("dueDateNonWorkingCalendar", DEFAULT_CALENDER, true),
                expectedValue("dueDateIntervalDays", "5", true)
            )
        );
    }

    static Stream<Arguments> scenarioProvider() {
        return Stream.of(
            reviewIncompleteAppealScenario()
        );
    }

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_CONFIGURATION_SSCS_BENEFIT;
    }

    @ParameterizedTest(name = "task type: {0} case data: {1}")
    @MethodSource("scenarioProvider")
    void should_return_correct_configuration_values_for_scenario(String taskType, Map<String, Object> caseData, List<Map<String, Object>> expectation) {
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

    private static Map<String, Object> expectedValue(String name, Object value, boolean canReconfigure) {
        Map<String, Object> rule = new HashMap<>();
        rule.put("name", name);
        rule.put("value", value);
        rule.put("canReconfigure", canReconfigure);
        return rule;
    }

    private void resultsMatch(List<Map<String, Object>> results, List<Map<String, Object>> expectation) {
        assertThat(results.size(), is(expectation.size()));
        for (int index = 0; index < expectation.size(); index++) {
            if ("dueDateOrigin".equals(expectation.get(index).get("name"))) {
                assertEquals(
                    results.get(index).get("canReconfigure"),
                    expectation.get(index).get("canReconfigure"));
                assertTrue(validNow(
                    parseCamundaTimestamp(results.get(index).get("value").toString()),
                    (ZonedDateTime) expectation.get(index).get("value")));
            } else {
                assertThat(results.get(index), is(expectation.get(index)));
            }
        }
    }

    private ZonedDateTime parseCamundaTimestamp(String datetime) {
        String[] parts = datetime.split("@");
        return ZonedDateTime.of(LocalDateTime.parse(parts[0]), ZoneId.of(parts[1]));
    }

    private boolean validNow(ZonedDateTime actual, ZonedDateTime expected) {
        ZonedDateTime now = now();
        return actual != null
            && (expected.isEqual(actual) || expected.isBefore(actual))
            && (now.isEqual(actual) || now.isAfter(actual));
    }

    private static ZonedDateTime now() {
        return ZonedDateTime.now(ZoneId.of("UTC"));
    }
}

