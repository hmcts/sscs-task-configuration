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
import uk.gov.hmcts.reform.sscstaskconfiguration.utils.CourtSpecificCalendars;
import uk.gov.hmcts.reform.sscstaskconfiguration.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_CONFIGURATION_SSCS_BENEFIT;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.DESCRIPTION;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.DUE_DATE_INTERVAL_DAYS;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.DUE_DATE_NON_WORKING_CALENDAR;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.MAJOR_PRIORITY;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.MINOR_PRIORITY;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.NEXT_HEARING_ID;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.NEXT_HEARING_DATE;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.PRIORITY_DATE;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.ROLE_CATEGORY;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.WORK_TYPE;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.eventLink;

@Slf4j
class CamundaTaskConfigurationTest extends DmnDecisionTableBaseUnitTest {

    public static final String YES = "Yes";

    static Stream<Arguments> nextHearingScenarioProvider() {
        return Stream.of(
            // no hearings
            Arguments.of(
                "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase()
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue("description","[Request Information From Party](/case/SSCS/Benefit/"
                        + "${[CASE_REFERENCE]}/trigger/requestInfoIncompleteApplication)",true)
                    .build()
            ),
            // past hearing only
            Arguments.of(
                "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase()
                    .withHearing(CaseDataBuilder.createHearing("1234567", DateUtils.lastMonth()))
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue("description","[Request Information From Party](/case/SSCS/Benefit/"
                        + "${[CASE_REFERENCE]}/trigger/requestInfoIncompleteApplication)",true)
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
                                   DateUtils.today(-10),true)
                    .expectedValue(ConfigurationExpectationBuilder.NEXT_HEARING_ID,
                                   "1234567",true)
                    .expectedValue(ConfigurationExpectationBuilder.NEXT_HEARING_DATE,
                                   DateUtils.today(),true)
                    .expectedValue("description","[Request Information From Party](/case/SSCS/Benefit/"
                        + "${[CASE_REFERENCE]}/trigger/requestInfoIncompleteApplication)",true)
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
                                   DateUtils.tomorrow(-10),true)
                    .expectedValue(ConfigurationExpectationBuilder.NEXT_HEARING_ID,
                                   "1234567",true)
                    .expectedValue(ConfigurationExpectationBuilder.NEXT_HEARING_DATE,
                                   DateUtils.tomorrow(),true)
                    .expectedValue("description","[Request Information From Party](/case/SSCS/Benefit/"
                        + "${[CASE_REFERENCE]}/trigger/requestInfoIncompleteApplication)",true)
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
                                   DateUtils.tomorrow(-10), true)
                    .expectedValue(ConfigurationExpectationBuilder.NEXT_HEARING_ID,
                                   "1111111",true)
                    .expectedValue(ConfigurationExpectationBuilder.NEXT_HEARING_DATE,
                                   DateUtils.tomorrow(),true)
                    .expectedValue("description","[Request Information From Party](/case/SSCS/Benefit/"
                        + "${[CASE_REFERENCE]}/trigger/requestInfoIncompleteApplication)",true)
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
                                   DateUtils.nextWeek(-10),true)
                    .expectedValue(ConfigurationExpectationBuilder.NEXT_HEARING_ID,
                                   "2222222",true)
                    .expectedValue(ConfigurationExpectationBuilder.NEXT_HEARING_DATE,
                                   DateUtils.nextWeek(),true)
                    .expectedValue("description","[Request Information From Party](/case/SSCS/Benefit/"
                        + "${[CASE_REFERENCE]}/trigger/requestInfoIncompleteApplication)",true)
                    .build()
            )
        );
    }

    static Stream<Arguments> scenarioProvider() {
        return Stream.of(
            Arguments.of(
                "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue("description","[Request Information From Party](/case/SSCS/Benefit/"
                        + "${[CASE_REFERENCE]}/trigger/requestInfoIncompleteApplication)",true)
                    .build()
            ),
            Arguments.of(
                "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase()
                    .isScottishCase(YES)
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(DUE_DATE_NON_WORKING_CALENDAR,
                                   CourtSpecificCalendars.SCOTLAND_CALENDAR, true)
                    .expectedValue("description","[Request Information From Party](/case/SSCS/Benefit/"
                        + "${[CASE_REFERENCE]}/trigger/requestInfoIncompleteApplication)",true)
                    .build()
            ),
            Arguments.of(
                "reviewInformationRequested",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(DESCRIPTION, "[Review Information Requested](/case/SSCS/Benefit/"
                        + "${[CASE_REFERENCE]}/trigger/interlocInformationReceived)",true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "3", true)
                    .build()
            ),
            Arguments.of(
                "reviewFtaResponse",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(DESCRIPTION, "[Review FTA Response](/case/SSCS/Benefit/"
                        + "${[CASE_REFERENCE]}/trigger/hmctsResponseReviewed)", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "reviewBilingualDocument",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(DESCRIPTION, "[Request translation from WLU](/case/SSCS/Benefit/"
                        + "${[CASE_REFERENCE]}/trigger/requestTranslationFromWLU)", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .build()
            ),
            Arguments.of(
                "issueOutstandingTranslation",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(DESCRIPTION,"[Action Further Evidence](/case/SSCS/Benefit/"
                        + "${[CASE_REFERENCE]}/trigger/actionFurtherEvidence)", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .build()
            ),
            Arguments.of(
                "reviewAdminAction",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(DESCRIPTION,
                        "[Send to Judge](/case/SSCS/Benefit/${[CASE_REFERENCE]}/trigger/validSendToInterloc)<br/>"
                        + "[Send to TCW](/case/SSCS/Benefit/${[CASE_REFERENCE]}/trigger/interlocSendToTcw)<br/>"
                        + "[Interloc Information Received](/case/SSCS/Benefit/${[CASE_REFERENCE]}"
                            + "/trigger/interlocInformationReceived)", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true).build()
            ),
            Arguments.of(
                "actionUnprocessedCorrespondence",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(DESCRIPTION, "[Action Unprocessed Correspondence](/case/SSCS/Benefit"
                        + "/${[CASE_REFERENCE]}/trigger/actionUnprocessedCorrespondence)", true)
                    .expectedValue(ConfigurationExpectationBuilder.DUE_DATE_INTERVAL_DAYS, "10", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .build()
            ),
            Arguments.of(
                "reviewFtaDueDate",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "6000", true)
                    .expectedValue(DESCRIPTION, "[Send to TCW](/case/SSCS/Benefit/${[CASE_REFERENCE]}"
                        + "/trigger/interlocSendToTcw)", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "reviewConfidentialityRequest",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(WORK_TYPE, "pre_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "Judicial", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "reviewReinstatementRequestJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(WORK_TYPE, "pre_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "Judicial", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "reviewValidAppeal",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "6000", true)
                    .expectedValue(DESCRIPTION, "[Review Valid Appeal](/case/SSCS/Benefit"
                        + "/${[CASE_REFERENCE]}/trigger/reviewValidAppeal)", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .build()
            ),
            Arguments.of(
                "reviewListingError",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(DESCRIPTION,"[Update Listing Requirements](/case/SSCS/Benefit/"
                        + "${[CASE_REFERENCE]}/trigger/updateListingRequirements)<br/>"
                        + "[Review Listing Error](/case/SSCS/Benefit/"
                        + "${[CASE_REFERENCE]}/trigger/reviewListingError)", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "3", true)
                    .build()
            ),
            Arguments.of(
                "reviewRoboticFail",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "6000", true)
                    .expectedValue(DESCRIPTION, "[Re-sends the case to GAPS 2](/case/SSCS/Benefit"
                        + "/${[CASE_REFERENCE]}/trigger/resendCaseToGAPS2)", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "3", true)
                    .build()
            ),
            Arguments.of(
                "reviewBfDate",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(DESCRIPTION, "[Amend due date](/case/SSCS/Benefit"
                      + "/${[CASE_REFERENCE]}/trigger/amendDueDate)", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .build()
            ),
            Arguments.of(
                "allocateCaseRolesAndCreateBundle",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(DESCRIPTION, "[Create a bundle in the case](/case/SSCS/Benefit"
                        + "/${[CASE_REFERENCE]}/trigger/createBundle)", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "3", true)
                    .expectedValue("workType", "hearing_work", true)
                    .expectedValue("roleCategory", "ADMIN", true)
                    .build()
            ),
            Arguments.of(
                "reviewOutstandingDraftDecision",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue("workType", "hearing_work", true)
                    .expectedValue(DESCRIPTION, eventLink("Send to Judge","tcwReferToJudge"), true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
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
        assertThat(logic.getRules().size(), is(38));
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

    static Stream<Arguments> scenarioProviderCourtSpecificCalendars() {
        return Stream.of(
            Arguments.of(
                Map.of(),
                singletonList(Map.of("nonWorkingDayCalendar", CourtSpecificCalendars.ENGLAND_AND_WALES_CALENDAR))
            ),
            Arguments.of(
                Map.of("isScottishCase", "No"),
                singletonList(Map.of("nonWorkingDayCalendar", CourtSpecificCalendars.ENGLAND_AND_WALES_CALENDAR))
            ),
            Arguments.of(
                Map.of("isScottishCase", "Yes"),
                singletonList(Map.of("nonWorkingDayCalendar", CourtSpecificCalendars.SCOTLAND_CALENDAR))
            ),
            Arguments.of(
                Map.of("isScottishCase", "Yes",
                       "processingVenue", "Dundee"),
                singletonList(Map.of("nonWorkingDayCalendar", CourtSpecificCalendars.SCOTLAND_CALENDAR_DUNDEE))
            ),
            Arguments.of(
                Map.of("isScottishCase", "Yes",
                       "processingVenue", "Edinburgh"),
                singletonList(Map.of("nonWorkingDayCalendar", CourtSpecificCalendars.SCOTLAND_CALENDAR_EDINBURGH))
            ),
            Arguments.of(
                Map.of("isScottishCase", "Yes",
                       "processingVenue", "AnywhereElse"),
                singletonList(Map.of("nonWorkingDayCalendar", CourtSpecificCalendars.SCOTLAND_CALENDAR))
            )
        );
    }

    @ParameterizedTest(name = "caseData: {0}")
    @MethodSource("scenarioProviderCourtSpecificCalendars")
    void use_correct_court_specific_calendar_for_venue(Map<String, Object> caseData,
                                            List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateRequiredDecision(
            "sscs-task-configuration-non-working-days", inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }
}
