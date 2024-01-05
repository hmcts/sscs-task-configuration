package uk.gov.hmcts.reform.sscstaskconfiguration.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigurationExpectationBuilder {

    public static String CASE_NAME = "caseName";
    public static String CASE_MANAGEMENT_CATEGORY = "caseManagementCategory";
    public static String REGION = "region";
    public static String LOCATION = "location";
    public static String LOCATION_NAME = "locationName";
    public static String WORK_TYPE = "workType";
    public static String ROLE_CATEGORY = "roleCategory";
    public static String PRIORITY_DATE = "priorityDate";
    public static String MINOR_PRIORITY = "minorPriority";
    public static String MAJOR_PRIORITY = "majorPriority";
    public static String DESCRIPTION = "description";
    public static String NEXT_HEARING_ID = "nextHearingId";
    public static String NEXT_HEARING_DATE = "nextHearingDate";
    public static String DUE_DATE_ORIGIN = "dueDateOrigin";
    public static String DUE_DATE_NON_WORKING_CALENDAR = "dueDateNonWorkingCalendar";
    public static String DUE_DATE_INTERVAL_DAYS = "dueDateIntervalDays";
    public static String DUE_DATE_NON_WORKING_DAYS_OF_WEEK = "dueDateNonWorkingDaysOfWeek";

    private static List<String> EXPECTED_PROPERTIES = Arrays.asList(
        CASE_NAME,CASE_MANAGEMENT_CATEGORY,REGION,LOCATION,LOCATION_NAME,WORK_TYPE,ROLE_CATEGORY,
        PRIORITY_DATE, MINOR_PRIORITY, MAJOR_PRIORITY, DESCRIPTION, NEXT_HEARING_ID, NEXT_HEARING_DATE,
        DUE_DATE_ORIGIN, DUE_DATE_NON_WORKING_CALENDAR, DUE_DATE_INTERVAL_DAYS, DUE_DATE_NON_WORKING_DAYS_OF_WEEK
    );

    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private Map<String,Map<String,Object>> expectations = new HashMap<>();

    public static ConfigurationExpectationBuilder defaultExpectations() {
        ConfigurationExpectationBuilder builder = new ConfigurationExpectationBuilder();
        builder.expectedValue(CASE_NAME, "Joe Blogs", true);
        builder.expectedValue(CASE_MANAGEMENT_CATEGORY, "Personal Independence Payment", true);
        builder.expectedValue(REGION, "4", true);
        builder.expectedValue(LOCATION, "123456", true);
        builder.expectedValue(LOCATION_NAME, "BRADFORD", true);
        builder.expectedValue(WORK_TYPE, "routine_work", true);
        builder.expectedValue(ROLE_CATEGORY, "CTSC", true);
        builder.expectedValue(PRIORITY_DATE, "", true);
        builder.expectedValue(MINOR_PRIORITY, "500", true);
        builder.expectedValue(MAJOR_PRIORITY, "5000", true);
        builder.expectedValue(NEXT_HEARING_ID, "", true);
        builder.expectedValue(NEXT_HEARING_DATE, "", true);
        builder.expectedValue(DUE_DATE_ORIGIN, now(), false);
        builder.expectedValue(DUE_DATE_NON_WORKING_CALENDAR, CourtSpecificCalendars.ENGLAND_AND_WALES_CALENDAR, true);
        builder.expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true);
        builder.expectedValue(DUE_DATE_NON_WORKING_DAYS_OF_WEEK, "SATURDAY,SUNDAY", true);
        return builder;
    }

    public static ConfigurationExpectationBuilder defaultExpectationsPostHearings() {
        ConfigurationExpectationBuilder builder = new ConfigurationExpectationBuilder();
        builder.expectedValue(CASE_NAME, "Joe Blogs", true);
        builder.expectedValue(CASE_MANAGEMENT_CATEGORY, "Personal Independence Payment", true);
        builder.expectedValue(REGION, "4", true);
        builder.expectedValue(LOCATION, "123456", true);
        builder.expectedValue(LOCATION_NAME, "BRADFORD", true);
        builder.expectedValue(WORK_TYPE, "post_hearing", true);
        builder.expectedValue(ROLE_CATEGORY, "CTSC", true);
        builder.expectedValue(MINOR_PRIORITY, "500", true);
        builder.expectedValue(MAJOR_PRIORITY, "5000", true);
        builder.expectedValue(NEXT_HEARING_ID, "", true);
        builder.expectedValue(NEXT_HEARING_DATE, "", true);
        builder.expectedValue(DUE_DATE_ORIGIN, now(), false);
        builder.expectedValue(DUE_DATE_NON_WORKING_CALENDAR, CourtSpecificCalendars.ENGLAND_AND_WALES_CALENDAR, true);
        builder.expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true);
        builder.expectedValue(DUE_DATE_NON_WORKING_DAYS_OF_WEEK, "SATURDAY,SUNDAY", true);
        return builder;
    }

    public static ConfigurationExpectationBuilder defaultExpectationsSpecificAccess() {
        ConfigurationExpectationBuilder builder = new ConfigurationExpectationBuilder();
        builder.expectedValue(CASE_NAME, "Joe Blogs", true);
        builder.expectedValue(CASE_MANAGEMENT_CATEGORY, "Personal Independence Payment", true);
        builder.expectedValue(REGION, "4", true);
        builder.expectedValue(LOCATION, "123456", true);
        builder.expectedValue(LOCATION_NAME, "BRADFORD", true);
        builder.expectedValue(WORK_TYPE, "access_requests", true);
        builder.expectedValue(MINOR_PRIORITY, "500", true);
        builder.expectedValue(MAJOR_PRIORITY, "5000", true);
        builder.expectedValue(NEXT_HEARING_ID, "", true);
        builder.expectedValue(NEXT_HEARING_DATE, "", true);
        builder.expectedValue(DUE_DATE_ORIGIN, now(), false);
        builder.expectedValue(DUE_DATE_NON_WORKING_CALENDAR, CourtSpecificCalendars.ENGLAND_AND_WALES_CALENDAR, true);
        builder.expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true);
        builder.expectedValue(DUE_DATE_NON_WORKING_DAYS_OF_WEEK, "SATURDAY,SUNDAY", true);
        return builder;
    }

    public static ConfigurationExpectationBuilder defaultJudicialTaskExpectations() {
        ConfigurationExpectationBuilder builder = new ConfigurationExpectationBuilder();
        builder.expectedValue(CASE_NAME, "Joe Blogs", true);
        builder.expectedValue(CASE_MANAGEMENT_CATEGORY, "Personal Independence Payment", true);
        builder.expectedValue(REGION, "4", true);
        builder.expectedValue(LOCATION, "123456", true);
        builder.expectedValue(LOCATION_NAME, "BRADFORD", true);
        builder.expectedValue(WORK_TYPE, "pre_hearing", true);
        builder.expectedValue(ROLE_CATEGORY, "JUDICIAL", true);
        builder.expectedValue(PRIORITY_DATE, "", true);
        builder.expectedValue(MINOR_PRIORITY, "500", true);
        builder.expectedValue(MAJOR_PRIORITY, "5000", true);
        builder.expectedValue(NEXT_HEARING_ID, "", true);
        builder.expectedValue(NEXT_HEARING_DATE, "", true);
        builder.expectedValue(DUE_DATE_ORIGIN, now(), false);
        builder.expectedValue(DUE_DATE_NON_WORKING_CALENDAR, CourtSpecificCalendars.ENGLAND_AND_WALES_CALENDAR, true);
        builder.expectedValue(DUE_DATE_INTERVAL_DAYS, "2", true);
        builder.expectedValue(DUE_DATE_NON_WORKING_DAYS_OF_WEEK, "SATURDAY,SUNDAY", true);
        return builder;
    }

    public static String buildDescription(String... lines) {
        return Arrays.stream(lines).collect(Collectors.joining("<br/>"));
    }

    public List<Map<String,Object>> build() {
        return EXPECTED_PROPERTIES.stream()
            .filter(p -> expectations.containsKey(p))
            .map(p -> expectations.get(p))
            .collect(Collectors.toList());
    }

    public ConfigurationExpectationBuilder expectedValue(String name, Object value, boolean canReconfigure) {
        expectations.put(name, Map.of(
            "name", name,
            "value", value,
            "canReconfigure", canReconfigure
        ));
        return this;
    }

    public static Map dynamicListValue(String code) {
        return Map.of("value", Map.of("code", code));
    }

    public static String now() {
        return LocalDateTime.now().format(dateTimeFormat);
    }
}
