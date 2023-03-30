package uk.gov.hmcts.reform.sscstaskconfiguration.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationExpectationBuilder {

    public static String ENGLAND_AND_WALES_CALENDAR = "https://www.gov.uk/bank-holidays/england-and-wales.json";

    public static String SCOTLAND_CALENDAR = "https://www.gov.uk/bank-holidays/scotland.json";

    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private Map<String,Map<String,Object>> expectations = new HashMap<>();

    public static ConfigurationExpectationBuilder defaultExpectations() {
        ConfigurationExpectationBuilder builder = new ConfigurationExpectationBuilder();
        builder.expectedValue("caseName", "Joe Blogs", true);
        builder.expectedValue("caseManagementCategory", "Personal Independence Payment", true);
        builder.expectedValue("location", "123456", true);
        builder.expectedValue("locationName", "BRADFORD", true);
        builder.expectedValue("work_type", "routine_work", true);
        builder.expectedValue("roleCategory", "CTSC", true);
        builder.expectedValue("priorityDate", "", true);
        builder.expectedValue("minorPriority", "500", true);
        builder.expectedValue("majorPriority", "5000", true);
        builder.expectedValue(
            "description",
            "[Request Information From Party](/case/SSCS/Benefit/${[CASE_REFERENCE]}/trigger/requestInfoFromParty)",
            true
        );
        builder.expectedValue("nextHearingId", "", true);
        builder.expectedValue("nextHearingDate", "", true);
        builder.expectedValue("dueDateOrigin", now(), false);
        builder.expectedValue("dueDateNonWorkingCalendar", ENGLAND_AND_WALES_CALENDAR, true);
        builder.expectedValue("dueDateIntervalDays", "5", true);
        return builder;
    }

    public List<Map<String,Object>> build() {
        return Arrays.asList(
            expectations.get("caseName"),
            expectations.get("caseManagementCategory"),
            expectations.get("location"),
            expectations.get("locationName"),
            expectations.get("work_type"),
            expectations.get("roleCategory"),
            expectations.get("priorityDate"),
            expectations.get("minorPriority"),
            expectations.get("majorPriority"),
            expectations.get("description"),
            expectations.get("nextHearingId"),
            expectations.get("nextHearingDate"),
            expectations.get("dueDateOrigin"),
            expectations.get("dueDateNonWorkingCalendar"),
            expectations.get("dueDateIntervalDays")
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
