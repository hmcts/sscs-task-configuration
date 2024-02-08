package uk.gov.hmcts.reform.sscstaskconfiguration.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaseDataBuilder {

    Map<String,Object> caseData;

    private CaseDataBuilder(Map<String,Object> caseData) {
        this.caseData = caseData;
    }

    public static CaseDataBuilder defaultCase() {
        Map<String,Object> caseData = new HashMap<>();
        caseData.put("caseNamePublic", "Joe Blogs");
        caseData.put("isScottishCase", "No");
        caseData.put("regionalProcessingCenter", Map.of(
            "name", "BRADFORD",
            "epimsId", "123456"
        ));
        caseData.put("caseManagementLocation", Map.of(
            "region", "4"
        ));
        caseData.put("caseManagementCategory", Map.of(
            "value", Map.of("label", "Personal Independence Payment")
        ));
        return new CaseDataBuilder(caseData);
    }

    public CaseDataBuilder isScottishCase(String value) {
        caseData.put("isScottishCase", value);
        return this;
    }

    public CaseDataBuilder withHearing(Map<String,Object> hearing) {
        caseData.put("hearings", new ArrayList<>(List.of(hearing)));
        return this;
    }

    public CaseDataBuilder withHearings(List<Map<String,Object>> hearings) {
        caseData.put("hearings", new ArrayList<>(hearings));
        return this;
    }

    public static Map<String,Object> createHearing(String hearingId, String hearingDate) {
        return Map.of("value", Map.of(
            "hearingId", hearingId,
            "hearingDate", hearingDate
        ));
    }

    public Map<String,Object> build() {
        return caseData;
    }
}
