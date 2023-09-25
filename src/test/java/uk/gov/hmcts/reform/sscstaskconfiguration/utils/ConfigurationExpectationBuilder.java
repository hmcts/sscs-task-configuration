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
        "caseName","caseManagementCategory","location","locationName","workType","roleCategory",
        "priorityDate","minorPriority","majorPriority","description","nextHearingId","nextHearingDate",
        "dueDateOrigin","dueDateNonWorkingCalendar","dueDateIntervalDays", "dueDateNonWorkingDaysOfWeek"
    );

    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private Map<String,Map<String,Object>> expectations = new HashMap<>();

    public static ConfigurationExpectationBuilder defaultExpectations() {
        ConfigurationExpectationBuilder builder = new ConfigurationExpectationBuilder();
        builder.expectedValue(CASE_NAME, "Joe Blogs", true);
        builder.expectedValue(CASE_MANAGEMENT_CATEGORY, "Personal Independence Payment", true);
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

    public static String eventLink(String description, String eventId) {
        return String.format("[%s](/case/SSCS/Benefit/${[CASE_REFERENCE]}/trigger/%s)", description, eventId);
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

    public static String now() {
        return LocalDateTime.now().format(dateTimeFormat);
    }
}
