package uk.gov.hmcts.reform.sscstaskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTableBaseUnitTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_CONFIGURATION_SSCS_BENEFIT;

class CamundaTaskConfigurationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_CONFIGURATION_SSCS_BENEFIT;
    }

    private static Stream<Arguments> rowOne() {
        return Stream.of(
            Arguments.of(
                singletonList(
                    Map.of(
                        "name", "location",
                        "value", 1
                    )
                )
            )
        );
    }

        private static Stream<Arguments> rowTwo() {
        return Stream.of(
            Arguments.of(
                singletonList(
                    Map.of(
                        "name", "locationName",
                        "value", "HMCTS"
                    )
                )
            )
        );
    }

    private static Stream<Arguments> rowThree() {
        return Stream.of(
            Arguments.of(
                singletonList(
                    Map.of(
                        "name", "workType",
                        "value", "routine_work"
                    )
                )
            )
        );
    }

    private static Stream<Arguments> rowFour() {
        return Stream.of(
            Arguments.of(
                singletonList(
                    Map.of(
                        "name", "roleCategory",
                        "value", "LEGAL_OPERATIONS"
                    )
                )
            )
        );
    }

    private static Stream<Arguments> rowFive() {
        return Stream.of(
            Arguments.of(
                singletonList(
                    Map.of(
                        "name", "description",
                        "value", "Description of what needs to be done to complete the task"
                    )
                )
            )
        );
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(5));
    }

    private void getExpectedValue(List<Map<String, String>> rules, String name, String value) {
        Map<String, String> rule = new HashMap<>();
        rule.put("name", name);
        rule.put("value", value);
        rules.add(rule);
    }

}

