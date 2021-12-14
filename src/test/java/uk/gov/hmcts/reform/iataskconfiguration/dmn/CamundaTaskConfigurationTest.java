package uk.gov.hmcts.reform.iataskconfiguration.dmn;

import lombok.Builder;
import lombok.Value;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.StringUtils;
import uk.gov.hmcts.reform.iataskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyMap;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.reform.iataskconfiguration.DmnDecisionTable.WA_TASK_CONFIGURATION_IA_ASYLUM;

class CamundaTaskConfigurationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_CONFIGURATION_IA_ASYLUM;
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(14));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "refusalOfHumanRights, Human rights",
        "refusalOfEu, EEA",
        "deprivation, DoC",
        "protection, Protection",
        "revocationOfProtection, Revocation",
        "NULL_VALUE, ''",
        "'', ''"
    }, nullValues = "NULL_VALUE")
    void when_caseData_then_return_expected_appealType(String appealType, String expectedAppealType) {
        VariableMap inputVariables = new VariableMapImpl();
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        caseData.put("appealType", appealType);
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "appealType",
            "value", expectedAppealType
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "caseManagementCategory",
            "value", expectedAppealType
        )));
    }

    @Test
    void when_caseData_then_return_expected_case_management_category() {
        String refusalOfEuLabel = "Refusal of application under the EEA regulations";
        String revocationLabel = "Revocation of a protection status";
        List<Map<String, Object>> caseManagementCategories = List.of(
            Map.of(
                "value",
                Map.of("code", "refusalOfHumanRights", "label", "Refusal of a human rights claim"),
                "list_items",
                List.of(Map.of("code", "refusalOfHumanRights", "label", "Refusal of a human rights claim"))
            ),
            Map.of(
                "value", Map.of("code", "refusalOfEu", "label", "Refusal of application under the EEA regulations"),
                "list_items", List.of(Map.of("code", "refusalOfEu", "label", refusalOfEuLabel))
            ),
            Map.of(
                "value", Map.of("code", "deprivation", "label", "Deprivation of citizenship"),
                "list_items", List.of(Map.of("code", "deprivation", "label", "Deprivation of citizenship"))
            ),
            Map.of(
                "value", Map.of("code", "protection", "label", "Refusal of protection claim"),
                "list_items", List.of(Map.of("code", "protection", "label", "Refusal of protection claim"))
            ),
            Map.of(
                "value", Map.of("code", "revocationOfProtection", "label", "Revocation of a protection status"),
                "list_items", List.of(Map.of("code", "revocationOfProtection", "label", revocationLabel))
            )
        );

        List<String> expectedCaseManagementCategories = List.of(
            "Human rights",
            "EEA",
            "DoC",
            "Protection",
            "Revocation"
        );

        for (int i = 0; i < caseManagementCategories.size(); i++) {
            Map<String, Object> caseManagementCategory = caseManagementCategories.get(i);
            VariableMap inputVariables = new VariableMapImpl();
            Map<String, Object> caseData = new HashMap<>(); // allow null values
            caseData.put("caseManagementCategory", caseManagementCategory);
            inputVariables.putValue("caseData", caseData);

            DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

            assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                "name", "caseManagementCategory",
                "value", expectedCaseManagementCategories.get(i)
            )));
        }
    }

    @ParameterizedTest
    @CsvSource({"arrangeOfflinePayment", "markCaseAsPaid"})
    void when_taskId_then_return_Routine_Work(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        assertEquals(1, workTypeResultList.size());

        assertEquals(Map.of(
            "name", "workType",
            "value", "routine_work"
        ), workTypeResultList.get(0));
    }

    @ParameterizedTest
    @CsvSource({
        "reviewTheAppeal", "followUpOverdueRespondentEvidence", "reviewRespondentEvidence",
        "followUpOverdueCaseBuilding",
        "reviewAppealSkeletonArgument", "followUpOverdueReasonsForAppeal", "reviewReasonsForAppeal",
        "followUpOverdueClarifyingAnswers", "reviewClarifyingAnswers", "followUpOverdueRespondentReview",
        "reviewRespondentResponse", "followUpOverdueCMARequirements", "reviewCmaRequirements",
        "reviewAdditionalHomeOfficeEvidence", "reviewAdditionalAppellantEvidence", "reviewAddendumHomeOfficeEvidence",
        "reviewAddendumAppellantEvidence", "reviewAddendumEvidence", "processReviewDecisionApplication",
        "reviewAdditionalEvidence"
    })
    void when_taskId_then_return_Decision_making_work(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        assertEquals(1, workTypeResultList.size());

        assertEquals(Map.of(
            "name", "workType",
            "value", "decision_making_work"
        ), workTypeResultList.get(0));
    }

    @ParameterizedTest
    @CsvSource({
        "addListingDate", "createHearingBundle", "reviewHearingBundle", "generateDraftDecisionAndReasons",
        "uploadDecision", "uploadHearingRecording", "updateHearingRequirements", "editListing",
        "followUpOverdueHearingRequirements", "reviewHearingRequirements"
    })
    void when_taskId_then_return_Hearing_Work(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        assertEquals(1, workTypeResultList.size());

        assertEquals(Map.of(
            "name", "workType",
            "value", "hearing_work"
        ), workTypeResultList.get(0));
    }

    @ParameterizedTest
    @CsvSource({
        "processApplication", "processHearingRequirementsApplication", "processHearingCentreApplication",
        "processApplicationToExpedite", "processApplicationToTransfer", "processApplicationForTimeExtension",
        "processApplicationToWithdraw", "processAppealDetailsApplication", "processLinkedCaseApplication",
        "processReinstatementApplication"
    })
    void when_taskId_then_return_Applications(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        assertEquals(1, workTypeResultList.size());

        assertEquals(Map.of(
            "name", "workType",
            "value", "applications"
        ), workTypeResultList.get(0));
    }

    @ParameterizedTest
    @CsvSource({
        "allocationFTPAToJudge", "decideOnFTPA"
    })
    void when_taskId_then_return_Upper_Tribunal(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        assertEquals(1, workTypeResultList.size());

        assertEquals(Map.of(
            "name", "workType",
            "value", "upper_tribunal"
        ), workTypeResultList.get(0));
    }

    @ParameterizedTest
    @CsvSource({
        "reviewHearingBundle","generateDraftDecisionAndReasons","uploadDecision","reviewAddendumHomeOfficeEvidence",
        "reviewAddendumAppellantEvidence","reviewAddendumEvidence"
    })
    void when_taskId_then_return_judicial_role_category(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        assertEquals(1, workTypeResultList.size());

        assertEquals(Map.of(
            "name", "roleCategory",
            "value", "JUDICIAL"
        ), workTypeResultList.get(0));
    }

    @ParameterizedTest
    @CsvSource({
        "arrangeOfflinePayment","markCaseAsPaid","addListingDate","allocateHearingJudge","uploadHearingRecording"
    })
    void when_taskId_then_return_administrator_role_category(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        assertEquals(1, workTypeResultList.size());

        assertEquals(Map.of(
            "name", "roleCategory",
            "value", "ADMINISTRATOR"
        ), workTypeResultList.get(0));
    }

    @ParameterizedTest
    @CsvSource({
        "processApplication","reviewTheAppeal","decideOnTimeExtension","reviewRespondentEvidence",
        "reviewAppealSkeletonArgument","reviewReasonsForAppeal","reviewClarifyingQuestionsAnswers",
        "reviewCmaRequirements","attendCma","reviewRespondentResponse","createCaseSummary","createHearingBundle",
        "startDecisionsAndReasonsDocument","reviewHearingRequirements","followUpOverdueRespondentEvidence",
        "followUpOverdueCaseBuilding","followUpOverdueReasonsForAppeal","followUpOverdueClarifyingAnswers",
        "followUpOverdueCmaRequirements","followUpOverdueRespondentReview","followUpOverdueHearingRequirements",
        "followUpNonStandardDirection","followUpNoticeOfChange","reviewAdditionalEvidence",
        "reviewAdditionalHomeOfficeEvidence"
    })
    void when_taskId_then_return_legal_operations_role_category(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        assertEquals(1, workTypeResultList.size());

        assertEquals(Map.of(
            "name", "roleCategory",
            "value", "LEGAL_OPERATIONS"
        ), workTypeResultList.get(0));
    }

    @ParameterizedTest
    @MethodSource("nameAndValueScenarioProvider")
    void when_caseData_and_taskType_then_return_expected_name_and_value_rows(Scenario scenario) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", scenario.caseData);
        inputVariables.putValue("taskAttributes", scenario.getTaskAttributes());

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(getExpectedValues(scenario)));
    }

    private static Stream<Scenario> nameAndValueScenarioProvider() {
        Scenario givenCaseDataIsMissedThenDefaultToTaylorHouseScenario = Scenario.builder()
            .caseData(emptyMap())
            .expectedCaseNameValue(null)
            .expectedAppealTypeValue("")
            .expectedRegionValue("1")
            .expectedLocationValue("765324")
            .expectedLocationNameValue("Taylor House")
            .expectedCaseManagementCategoryValue("")
            .build();

        String refusalOfEuLabel = "Refusal of a human rights claim";
        Scenario givenCaseDataIsPresentThenReturnNameAndValueScenario = Scenario.builder()
            .caseData(Map.of(
                "appealType", "refusalOfHumanRights",
                "appellantGivenNames", "some appellant given names",
                "appellantFamilyName", "some appellant family name",
                "caseManagementLocation", Map.of(
                    "region", "some other region",
                    "baseLocation", "some other location"
                ),
                "staffLocation", "some other location name",
                "caseManagementCategory", Map.of(
                    "value", Map.of("code", "refusalOfHumanRights", "label", "Refusal of a human rights claim"),
                    "list_items", List.of(Map.of("code", "refusalOfHumanRights", "label", refusalOfEuLabel))
                )
            ))
            .expectedCaseNameValue("some appellant given names some appellant family name")
            .expectedAppealTypeValue("Human rights")
            .expectedRegionValue("some other region")
            .expectedLocationValue("some other location")
            .expectedLocationNameValue("some other location name")
            .expectedCaseManagementCategoryValue("Human rights")
            .build();

        Scenario givenSomeCaseDataAndArrangeOfflinePaymentTaskIdThenReturnExpectedNameAndValueScenario =
            Scenario.builder()
                .caseData(Map.of(
                    "appealType", "refusalOfHumanRights",
                    "appellantGivenNames", "some appellant given names",
                    "appellantFamilyName", "some appellant family name",
                    "caseManagementLocation", Map.of(
                        "region", "some other region",
                        "baseLocation", "some other location"
                    ),
                    "staffLocation", "some other location name",
                    "caseManagementCategory", Map.of(
                        "value", Map.of("code", "refusalOfHumanRights", "label", "Refusal of a human rights claim"),
                        "list_items", List.of(Map.of("code", "refusalOfHumanRights", "label", refusalOfEuLabel))
                    )
                ))
                .taskAttributes(Map.of("taskType", "arrangeOfflinePayment"))
                .expectedCaseNameValue("some appellant given names some appellant family name")
                .expectedAppealTypeValue("Human rights")
                .expectedRegionValue("some other region")
                .expectedLocationValue("some other location")
                .expectedLocationNameValue("some other location name")
                .expectedCaseManagementCategoryValue("Human rights")
                .expectedWorkType("routine_work")
                .expectedRoleCategory("ADMINISTRATOR")
                .build();

        Scenario givenSomeCaseDataAndTaskTypeIsEmptyThenExpectNoWorkTypeRuleScenario =
            Scenario.builder()
                .caseData(Map.of(
                    "appealType", "refusalOfHumanRights",
                    "appellantGivenNames", "some appellant given names",
                    "appellantFamilyName", "some appellant family name",
                    "caseManagementLocation", Map.of(
                        "region", "some other region",
                        "baseLocation", "some other location"
                    ),
                    "staffLocation", "some other location name",
                    "caseManagementCategory", Map.of(
                        "value", Map.of("code", "refusalOfHumanRights", "label", "Refusal of a human rights claim"),
                        "list_items", List.of(Map.of("code", "refusalOfHumanRights", "label", refusalOfEuLabel))
                    )
                ))
                .taskAttributes(Map.of("taskType", ""))
                .expectedCaseNameValue("some appellant given names some appellant family name")
                .expectedAppealTypeValue("Human rights")
                .expectedRegionValue("some other region")
                .expectedLocationValue("some other location")
                .expectedLocationNameValue("some other location name")
                .expectedCaseManagementCategoryValue("Human rights")
                .build();

        Scenario givenNoCaseDataAndSomeTaskTypeThenExpectOnlyTheWorkTypeRuleScenario =
            Scenario.builder()
                .caseData(emptyMap())
                .taskAttributes(Map.of("taskType", "markCaseAsPaid"))
                .expectedCaseNameValue(null)
                .expectedAppealTypeValue("")
                .expectedRegionValue("1")
                .expectedLocationValue("765324")
                .expectedLocationNameValue("Taylor House")
                .expectedCaseManagementCategoryValue("")
                .expectedWorkType("routine_work")
                .expectedRoleCategory("ADMINISTRATOR")
                .build();

        return Stream.of(
            givenCaseDataIsMissedThenDefaultToTaylorHouseScenario,
            givenCaseDataIsPresentThenReturnNameAndValueScenario,
            givenSomeCaseDataAndArrangeOfflinePaymentTaskIdThenReturnExpectedNameAndValueScenario,
            givenSomeCaseDataAndTaskTypeIsEmptyThenExpectNoWorkTypeRuleScenario,
            givenNoCaseDataAndSomeTaskTypeThenExpectOnlyTheWorkTypeRuleScenario
        );
    }

    @Value
    @Builder
    private static class Scenario {
        Map<String, Object> caseData;
        Map<String, Object> taskAttributes;
        String expectedCaseNameValue;
        String expectedAppealTypeValue;
        String expectedRegionValue;
        String expectedLocationValue;
        String expectedLocationNameValue;
        String expectedCaseManagementCategoryValue;
        String expectedWorkType;
        String expectedRoleCategory;
    }

    private List<Map<String, String>> getExpectedValues(Scenario scenario) {
        List<Map<String, String>> rules = new ArrayList<>();

        getExpectedValue(rules, "caseName", scenario.getExpectedCaseNameValue());
        getExpectedValue(rules, "appealType", scenario.getExpectedAppealTypeValue());
        getExpectedValue(rules, "region", scenario.getExpectedRegionValue());
        getExpectedValue(rules, "location", scenario.getExpectedLocationValue());
        getExpectedValue(rules, "locationName", scenario.getExpectedLocationNameValue());
        getExpectedValue(rules, "caseManagementCategory", scenario.getExpectedCaseManagementCategoryValue());
        if (!Objects.isNull(scenario.getTaskAttributes())
            && StringUtils.isNotBlank(scenario.taskAttributes.get("taskType").toString())) {
            getExpectedValue(rules, "workType", scenario.getExpectedWorkType());
            getExpectedValue(rules, "roleCategory", scenario.getExpectedRoleCategory());
        }

        return rules;
    }

    private void getExpectedValue(List<Map<String, String>> rules, String name, String value) {
        Map<String, String> rule = new HashMap<>();
        rule.put("name", name);
        rule.put("value", value);
        rules.add(rule);
    }

}

