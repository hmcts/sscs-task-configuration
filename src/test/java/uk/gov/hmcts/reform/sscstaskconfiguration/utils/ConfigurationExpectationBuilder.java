package uk.gov.hmcts.reform.sscstaskconfiguration.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigurationExpectationBuilder {

    public static String ENGLAND_AND_WALES_CALENDAR = "https://www.gov.uk/bank-holidays/england-and-wales.json";

    public static String SCOTLAND_CALENDAR = "https://www.gov.uk/bank-holidays/scotland.json";

    private static List<String> EXPECTED_PROPERTIES = Arrays.asList(
        "caseName","caseManagementCategory","location","locationName","workType","roleCategory",
        "priorityDate","minorPriority","majorPriority","description","nextHearingId","nextHearingDate",
        "dueDateOrigin","dueDateNonWorkingCalendar","dueDateIntervalDays"
    );
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    private Map<String,Map<String,Object>> expectations = new HashMap<>();

    public static ConfigurationExpectationBuilder defaultExpectations() {
        ConfigurationExpectationBuilder builder = new ConfigurationExpectationBuilder();
        builder.expectedValue("caseName", "Joe Blogs", true);
        builder.expectedValue("caseManagementCategory", "Personal Independence Payment", true);
        builder.expectedValue("location", "123456", true);
        builder.expectedValue("locationName", "BRADFORD", true);
        builder.expectedValue("workType", "routine_work", true);
        builder.expectedValue("roleCategory", "CTSC", true);
        builder.expectedValue("priorityDate", "", true);
        builder.expectedValue("minorPriority", "500", true);
        builder.expectedValue("majorPriority", "5000", true);
        builder.expectedValue("nextHearingId", "", true);
        builder.expectedValue("nextHearingDate", "", true);
        builder.expectedValue("dueDateOrigin", now(), true);
        builder.expectedValue("dueDateNonWorkingCalendar", ENGLAND_AND_WALES_CALENDAR, true);
        builder.expectedValue("dueDateIntervalDays", "5", true);
        return builder;
    }

    public static ConfigurationExpectationBuilder defaultJudicialTaskExpectations() {
        ConfigurationExpectationBuilder builder = new ConfigurationExpectationBuilder();
        builder.expectedValue("caseName", "Joe Blogs", true);
        builder.expectedValue("caseManagementCategory", "Personal Independence Payment", true);
        builder.expectedValue("location", "123456", true);
        builder.expectedValue("locationName", "BRADFORD", true);
        builder.expectedValue("workType", "pre_hearing", true);
        builder.expectedValue("roleCategory", "Judicial", true);
        builder.expectedValue("priorityDate", "", true);
        builder.expectedValue("minorPriority", "500", true);
        builder.expectedValue("majorPriority", "5000", true);
        builder.expectedValue("nextHearingId", "", true);
        builder.expectedValue("nextHearingDate", "", true);
        builder.expectedValue("dueDateOrigin", now(), true);
        builder.expectedValue("dueDateNonWorkingCalendar", ENGLAND_AND_WALES_CALENDAR, true);
        builder.expectedValue("dueDateIntervalDays", "2", true);
        return builder;
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
