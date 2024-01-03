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
import uk.gov.hmcts.reform.sscstaskconfiguration.utils.EventLink;

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
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.NEXT_HEARING_DATE;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.NEXT_HEARING_ID;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.PRIORITY_DATE;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.ROLE_CATEGORY;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.WORK_TYPE;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.ConfigurationExpectationBuilder.buildDescription;
import static uk.gov.hmcts.reform.sscstaskconfiguration.utils.EventLink.caseLink;

@Slf4j
class CamundaTaskConfigurationTest extends DmnDecisionTableBaseUnitTest {

    public static final String YES = "Yes";

    static Stream<Arguments> nextHearingScenarioProvider() {
        return Stream.of(
            // no hearings
            Arguments.of(
            "reviewIncompleteAppeal",
            CaseDataBuilder.defaultCase().build(),
            ConfigurationExpectationBuilder.defaultExpectations()
                .expectedValue(DESCRIPTION, EventLink.REQUEST_FOR_INFORMATION, true)
                .build()
            ),
            // past hearing only
            Arguments.of(
                "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase()
                    .withHearing(CaseDataBuilder.createHearing("1234567", DateUtils.lastMonth()))
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(DESCRIPTION, EventLink.REQUEST_FOR_INFORMATION,
                        true)
                    .build()
            ),
            // hearing today
            Arguments.of(
                "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase()
                    .withHearing(CaseDataBuilder.createHearing("1234567", DateUtils.today()))
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(PRIORITY_DATE, DateUtils.today(-10),true)
                    .expectedValue(NEXT_HEARING_ID,"1234567",true)
                    .expectedValue(NEXT_HEARING_DATE, DateUtils.today(),true)
                    .expectedValue(DESCRIPTION, EventLink.REQUEST_FOR_INFORMATION,
                        true)
                    .build()
            ),
            // one future hearing
            Arguments.of(
                "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase()
                    .withHearing(CaseDataBuilder.createHearing("1234567", DateUtils.tomorrow()))
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(PRIORITY_DATE, DateUtils.tomorrow(-10),true)
                    .expectedValue(NEXT_HEARING_ID, "1234567",true)
                    .expectedValue(NEXT_HEARING_DATE, DateUtils.tomorrow(),true)
                    .expectedValue(DESCRIPTION, EventLink.REQUEST_FOR_INFORMATION,
                        true)
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
                    .expectedValue(PRIORITY_DATE, DateUtils.tomorrow(-10), true)
                    .expectedValue(NEXT_HEARING_ID, "1111111",true)
                    .expectedValue(NEXT_HEARING_DATE, DateUtils.tomorrow(),true)
                    .expectedValue(DESCRIPTION, EventLink.REQUEST_FOR_INFORMATION,
                        true)
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
                    .expectedValue(PRIORITY_DATE, DateUtils.nextWeek(-10),true)
                    .expectedValue(NEXT_HEARING_ID, "2222222",true)
                    .expectedValue(NEXT_HEARING_DATE, DateUtils.nextWeek(),true)
                    .expectedValue(DESCRIPTION, EventLink.REQUEST_FOR_INFORMATION, true)
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
                    .expectedValue(DESCRIPTION, EventLink.REQUEST_FOR_INFORMATION, true)
                    .build()
            ),
            Arguments.of(
                "reviewIncompleteAppeal",
                CaseDataBuilder.defaultCase()
                    .isScottishCase(YES)
                    .build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(DESCRIPTION, EventLink.REQUEST_FOR_INFORMATION, true)
                    .expectedValue(DUE_DATE_NON_WORKING_CALENDAR,
                                   CourtSpecificCalendars.SCOTLAND_CALENDAR, true)
                    .expectedValue(DESCRIPTION,
                        EventLink.REQUEST_FOR_INFORMATION,true)
                    .build()
            ),
            Arguments.of(
                "reviewInformationRequested",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(DESCRIPTION, EventLink.INTERLOC_INFORMATION_RECEIVED,true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "3", true)
                    .build()
            ),
            Arguments.of(
                "reviewFtaResponse",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(DESCRIPTION, EventLink.HMCTS_RESPONSE_REVIEWED, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "reviewBilingualDocument",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(DESCRIPTION, EventLink.REQUEST_TRANSLATION_FROM_WLU, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .build()
            ),
            Arguments.of(
                "issueOutstandingTranslation",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(DESCRIPTION, EventLink.ACTION_FURTHER_EVIDENCE, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .build()
            ),
            Arguments.of(
                "reviewAdminAction",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(DESCRIPTION, buildDescription(
                        EventLink.VALID_SEND_TO_INTERLOC,
                        EventLink.INTERLOC_SEND_TO_TCW,
                        EventLink.INTERLOC_INFORMATION_RECEIVED), true)
                    .expectedValue(ConfigurationExpectationBuilder.DUE_DATE_INTERVAL_DAYS, "10", true).build()
            ),
            Arguments.of(
                "actionUnprocessedCorrespondence",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(DESCRIPTION, EventLink.ACTION_FURTHER_EVIDENCE, true)
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
                    .expectedValue(DESCRIPTION, EventLink.HMCTS_RESPONSE_REVIEWED, true)
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
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "reviewUrgentHearingRequest",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "100", true)
                    .expectedValue(MAJOR_PRIORITY, "1000", true)
                    .expectedValue(WORK_TYPE, "pre_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "referredByTcwPreHearing",
                    CaseDataBuilder.defaultCase().build(),
                    ConfigurationExpectationBuilder.defaultExpectations()
                        .expectedValue(MINOR_PRIORITY, "500", true)
                        .expectedValue(MAJOR_PRIORITY, "5000", true)
                        .expectedValue(WORK_TYPE, "pre_hearing", true)
                        .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                        .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                        .build()
            ),
            Arguments.of(
                "reviewReinstatementRequestJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultJudicialTaskExpectations().build()
            ),
            Arguments.of(
                "reviewPheRequestJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultJudicialTaskExpectations().build()
            ),
            Arguments.of(
                "ftaNotProvidedAppointeeDetailsJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultJudicialTaskExpectations().build()
            ),
            Arguments.of(
                "reviewPostponementRequestJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultJudicialTaskExpectations()
                    .expectedValue(MINOR_PRIORITY, "100", true)
                    .expectedValue(MAJOR_PRIORITY, "1000", true)
                    .build()
            ),
            Arguments.of(
                "prepareForHearingJudge",
                    CaseDataBuilder.defaultCase().build(),
                    ConfigurationExpectationBuilder.defaultExpectations()
                        .expectedValue(MINOR_PRIORITY, "500", true)
                        .expectedValue(MAJOR_PRIORITY, "5000", true)
                        .expectedValue(WORK_TYPE, "hearing_work", true)
                        .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                        .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                        .build()
                ),
            Arguments.of(
                "reviewValidAppeal",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "6000", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .build()
            ),
            Arguments.of(
                "reviewListingError",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(DESCRIPTION, buildDescription(
                        EventLink.UPDATE_LISTING_REQUIREMENTS,
                        EventLink.READY_TO_LIST), true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "3", true)
                    .build()
            ),
            Arguments.of(
                "reviewRoboticFail",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "6000", true)
                    .expectedValue(DESCRIPTION, EventLink.RESENT_CASE_TO_GAPS2, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "3", true)
                    .build()
            ),
            Arguments.of(
                "reviewBfDate",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(WORK_TYPE, "routine_work", true)
                    .expectedValue(ROLE_CATEGORY, "CTSC", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .expectedValue(DESCRIPTION, EventLink.AMEND_DUE_DATE, true)
                    .build()
            ),
            Arguments.of(
                "writeDecisionJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "100", true)
                    .expectedValue(MAJOR_PRIORITY, "1000", true)
                    .expectedValue(WORK_TYPE, "hearing_work", true)
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "allocateCaseRolesAndCreateBundle",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(DESCRIPTION, EventLink.CREATE_BUNDLE, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "3", true)
                    .expectedValue(WORK_TYPE, "hearing_work", true)
                    .expectedValue(ROLE_CATEGORY, "ADMIN", true)
                    .build()
            ),
            Arguments.of(
                "reviewOutstandingDraftDecision",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(WORK_TYPE, "hearing_work", true)
                    .expectedValue(DESCRIPTION, EventLink.TCW_REFER_TO_JUDGE, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .build()
            ),
            Arguments.of(
                "referredByAdminJudgePreHearing",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(WORK_TYPE, "pre_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "confirmPanelComposition",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(WORK_TYPE, "pre_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "referredToInterlocJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultJudicialTaskExpectations().build()
            ),
            Arguments.of(
                "contactParties",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(ROLE_CATEGORY, "ADMIN", true)
                    .expectedValue(MINOR_PRIORITY, "100", true)
                    .expectedValue(MAJOR_PRIORITY, "1000", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "1", true)
                    .build()
            ),
            Arguments.of(
                "reviewCorrectionApplicationAdmin",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(WORK_TYPE, "post_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "CTSC", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "3", true)
                    .expectedValue(DESCRIPTION, EventLink.ADMIN_ACTION_CORRECTION, true)
                    .build()
            ),
            Arguments.of(
                "reviewFtaValidityChallenge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(DESCRIPTION, buildDescription(
                        EventLink.INTERLOC_REVIEW_STATE_AMEND,
                        EventLink.DECISION_ISSUED,
                        EventLink.DIRECTION_ISSUED,
                        EventLink.SEND_TO_ADMIN,
                        EventLink.TCW_REFER_TO_JUDGE), true)
                    .expectedValue(ROLE_CATEGORY, "LEGAL_OPERATIONS", true)
                    .expectedValue(WORK_TYPE, "pre_hearing", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "ftaRequestTimeExtension",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(DESCRIPTION, buildDescription(
                        EventLink.DIRECTION_ISSUED,
                        EventLink.INTERLOC_REVIEW_STATE_AMEND), true)
                    .expectedValue(WORK_TYPE, "pre_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "prepareForHearingTribunalMember1",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue(WORK_TYPE, "hearing_work", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "prepareForHearingTribunalMember2",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue(WORK_TYPE, "hearing_work", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "prepareForHearingTribunalMember3",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue(WORK_TYPE, "hearing_work", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "prepareHearingAppraiser1",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(DESCRIPTION, caseLink("View hearing bundle","Documents"), true)
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue(WORK_TYPE, "hearing_work", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "prepareHearingAppraiser2",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(DESCRIPTION, caseLink("View hearing bundle", "Documents"), true)
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue(WORK_TYPE, "hearing_work", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "reviewPostponementRequestTCW",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "100", true)
                    .expectedValue(MAJOR_PRIORITY, "1000", true)
                    .expectedValue(DESCRIPTION, EventLink.ACTION_POSTPONEMENT_REQUEST, true)
                    .expectedValue(WORK_TYPE, "pre_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "referredToInterlocTCW",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(DESCRIPTION, buildDescription(
                        EventLink.DIRECTION_ISSUED,
                        EventLink.DECISION_ISSUED,
                        EventLink.SEND_TO_ADMIN,
                        EventLink.TCW_REFER_TO_JUDGE,
                        EventLink.VALID_SEND_TO_INTERLOC,
                        EventLink.INTERLOC_REVIEW_STATE_AMEND), true)
                    .expectedValue(WORK_TYPE, "pre_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "ftaResponseOverdue",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(DESCRIPTION, buildDescription(
                        EventLink.DIRECTION_ISSUED,
                        EventLink.INTERLOC_REVIEW_STATE_AMEND), true)
                    .expectedValue(ROLE_CATEGORY, "LEGAL_OPERATIONS", true)
                    .expectedValue(WORK_TYPE, "pre_hearing", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "referredByJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(DESCRIPTION, buildDescription(
                        EventLink.DIRECTION_ISSUED,
                        EventLink.SEND_TO_ADMIN,
                        EventLink.TCW_REFER_TO_JUDGE,
                        EventLink.INTERLOC_REVIEW_STATE_AMEND), true)
                    .expectedValue(WORK_TYPE, "routine_work", true)
                    .expectedValue(ROLE_CATEGORY, "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "processAudioVideoEvidence",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(DESCRIPTION, buildDescription(
                        EventLink.INTERLOC_REVIEW_STATE_AMEND,
                        EventLink.PROCESS_AUDIO_VIDEO), true)
                    .expectedValue(ROLE_CATEGORY, "LEGAL_OPERATIONS", true)
                    .expectedValue(WORK_TYPE, "pre_hearing", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "reviewNonCompliantAppeal",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(DESCRIPTION, buildDescription(
                        EventLink.DIRECTION_ISSUED,
                        EventLink.DECISION_ISSUED,
                        EventLink.SEND_TO_ADMIN,
                        EventLink.TCW_REFER_TO_JUDGE,
                        EventLink.INTERLOC_REVIEW_STATE_AMEND), true)
                    .expectedValue(WORK_TYPE, "pre_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "ftaNotProvidedAppointeeDetailsTcw",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(DESCRIPTION, buildDescription(
                        EventLink.DIRECTION_ISSUED,
                        EventLink.DECISION_ISSUED,
                        EventLink.SEND_TO_ADMIN,
                        EventLink.TCW_REFER_TO_JUDGE,
                        EventLink.INTERLOC_REVIEW_STATE_AMEND), true)
                    .expectedValue(WORK_TYPE, "pre_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "LEGAL_OPERATIONS", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "referredByAdminTcw",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(DESCRIPTION, buildDescription(
                        EventLink.INTERLOC_REVIEW_STATE_AMEND,
                        EventLink.DIRECTION_ISSUED,
                        EventLink.SEND_TO_ADMIN,
                        EventLink.TCW_REFER_TO_JUDGE), true)
                    .expectedValue(ROLE_CATEGORY, "LEGAL_OPERATIONS", true)
                    .expectedValue(WORK_TYPE, "routine_work", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "reviewStatementofReasonsApplication",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue("workType", "post_hearing", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "reviewLibertytoApplyApplication",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue("workType", "post_hearing", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "reviewCorrectionApplicationJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue(MINOR_PRIORITY, "100", true)
                    .expectedValue(MAJOR_PRIORITY, "1000", true)
                    .expectedValue("workType", "post_hearing", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "writeStatementofReason",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue("workType", "post_hearing", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "28", true)
                    .build()
            ),
            Arguments.of(
                "reviewStatementofReasons",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue("workType", "post_hearing", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "reviewPermissiontoAppealApplication",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue("workType", "post_hearing", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "reviewRemittedDecisionandProvideListingDirections",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue("workType", "post_hearing", true)
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "reviewPostHearingNoticeforListingRequirements",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue("workType", "post_hearing", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "10", true)
                    .build()
            ),
            Arguments.of(
                "reviewSetAsideApplication",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue(WORK_TYPE, "post_hearing", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "shareRemittedDecision",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue(MAJOR_PRIORITY, "6000", true)
                    .expectedValue(WORK_TYPE, "post_hearing", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "20", true)
                    .build()
            ),
            Arguments.of(
                "shareRemadeDecision",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "6000", true)
                    .expectedValue(WORK_TYPE, "post_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "CTSC", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "20", true)
                    .build()
            ),
            Arguments.of(
                "shareRefusedDecision",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "6000", true)
                    .expectedValue(WORK_TYPE, "post_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "CTSC", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "20", true)
                    .build()
            ),
            Arguments.of(
                "reviewApplicationandAllocateJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(WORK_TYPE, "post_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "CTSC", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "3", true)
                    .expectedValue(DESCRIPTION, EventLink.VALID_SEND_TO_INTERLOC, true)
                    .build()
            ),
            Arguments.of(
                "updateHearingDetails",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectations()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(ROLE_CATEGORY, "ADMIN", true)
                    .expectedValue(WORK_TYPE, "hearing_work", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true)
                    .build()
            ),
            Arguments.of(
                "referredByAdminJudgePostHearing",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(WORK_TYPE, "post_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "referredByTcwPostHearing",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue(MINOR_PRIORITY, "500", true)
                    .expectedValue(MAJOR_PRIORITY, "5000", true)
                    .expectedValue(WORK_TYPE, "post_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "reviewLateStatementofReasonsApplicationAndAllocateJudge",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(WORK_TYPE, "post_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "CTSC", true)
                    .expectedValue(DESCRIPTION, EventLink.SEND_TO_INTERLOC_LATE_SOR_APPLICATION, true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
                    .build()
            ),
            Arguments.of(
                "reviewLateStatementofReasonsApplication",
                CaseDataBuilder.defaultCase().build(),
                ConfigurationExpectationBuilder.defaultExpectationsPostHearings()
                    .expectedValue(MINOR_PRIORITY, "300", true)
                    .expectedValue(MAJOR_PRIORITY, "3000", true)
                    .expectedValue(WORK_TYPE, "post_hearing", true)
                    .expectedValue(ROLE_CATEGORY, "JUDICIAL", true)
                    .expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true)
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
        assertThat(logic.getRules().size(), is(59));
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
