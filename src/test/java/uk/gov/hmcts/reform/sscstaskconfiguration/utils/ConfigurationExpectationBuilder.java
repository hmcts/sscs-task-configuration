package uk.gov.hmcts.reform.sscstaskconfiguration.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationExpectationBuilder {

    public static String CASE_NAME = "caseName";
    public static String CASE_MANAGEMENT_CATEGORY = "caseManagementCategory";
    public static String LOCATION = "location";
    public static String LOCATION_NAME = "locationName";
    public static String WORK_TYPE = "work_type";
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
    public static String ENGLAND_AND_WALES_CALENDAR = "https://www.gov.uk/bank-holidays/england-and-wales.json";
    public static String SCOTLAND_CALENDAR = "https://www.gov.uk/bank-holidays/scotland.json";

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
        builder.expectedValue(
            DESCRIPTION,
            "[Request Information From Party](/case/SSCS/Benefit/${[CASE_REFERENCE]}/trigger/requestInfoFromParty)",
            true
        );
        builder.expectedValue(NEXT_HEARING_ID, "", true);
        builder.expectedValue(NEXT_HEARING_DATE, "", true);
        builder.expectedValue(DUE_DATE_ORIGIN, now(), true);
        builder.expectedValue(DUE_DATE_NON_WORKING_CALENDAR, ENGLAND_AND_WALES_CALENDAR, true);
        builder.expectedValue(DUE_DATE_INTERVAL_DAYS, "5", true);
        return builder;
    }

    public List<Map<String,Object>> build() {
        return Arrays.asList(
            expectations.get(CASE_NAME),
            expectations.get(CASE_MANAGEMENT_CATEGORY),
            expectations.get(LOCATION),
            expectations.get(LOCATION_NAME),
            expectations.get(WORK_TYPE),
            expectations.get(ROLE_CATEGORY),
            expectations.get(PRIORITY_DATE),
            expectations.get(MINOR_PRIORITY),
            expectations.get(MAJOR_PRIORITY),
            expectations.get(DESCRIPTION),
            expectations.get(NEXT_HEARING_ID),
            expectations.get(NEXT_HEARING_DATE),
            expectations.get(DUE_DATE_ORIGIN),
            expectations.get(DUE_DATE_NON_WORKING_CALENDAR),
            expectations.get(DUE_DATE_INTERVAL_DAYS)
        );
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
