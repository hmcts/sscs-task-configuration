package uk.gov.hmcts.reform.sscstaskconfiguration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DmnDecisionTable {
    WA_TASK_ALLOWED_DAYS_SSCS_BENEFIT("wa-task-allowed-days-sscs-benefit", "wa-task-allowed-days-sscs-benefit.dmn"),
    WA_TASK_CANCELLATION_SSCS_BENEFIT("wa-task-cancellation-sscs-benefit", "wa-task-cancellation-sscs-benefit.dmn"),
    WA_TASK_COMPLETION_SSCS_BENEFIT("wa-task-completion-sscs-benefit", "wa-task-completion-sscs-benefit.dmn"),
    WA_TASK_CONFIGURATION_SSCS_BENEFIT("wa-task-configuration-sscs-benefit", "wa-task-configuration-sscs-benefit.dmn"),
    WA_TASK_INITIATION_SSCS_BENEFIT("wa-task-initiation-sscs-benefit", "wa-task-initiation-sscs-benefit.dmn"),
    WA_TASK_PERMISSIONS_SSCS_BENEFIT("wa-task-permissions-sscs-benefit", "wa-task-permissions-sscs-benefit.dmn"),
    WA_TASK_TYPES_SSCS_BENEFIT("wa-task-types-sscs-benefit", "wa-task-types-sscs-benefit.dmn");

    @JsonValue
    private final String key;
    private final String fileName;

    DmnDecisionTable(String key, String fileName) {
        this.key = key;
        this.fileName = fileName;
    }

    public String getKey() {
        return key;
    }

    public String getFileName() {
        return fileName;
    }
}
