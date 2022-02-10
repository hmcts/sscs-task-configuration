package uk.gov.hmcts.reform.sscstaskconfiguration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DmnDecisionTable {
    WA_TASK_ALLOWED_DAYS_SSCS_ASYLUM("wa-task-allowed-days-sscs-asylum", "wa-task-allowed-days-sscs-asylum.dmn"),
    WA_TASK_CANCELLATION_SSCS_ASYLUM("wa-task-cancellation-sscs-asylum", "wa-task-cancellation-sscs-asylum.dmn"),
    WA_TASK_COMPLETION_SSCS_ASYLUM("wa-task-completion-sscs-asylum", "wa-task-completion-sscs-asylum.dmn"),
    WA_TASK_CONFIGURATION_SSCS_ASYLUM("wa-task-configuration-sscs-asylum", "wa-task-configuration-sscs-asylum.dmn"),
    WA_TASK_INITIATION_SSCS_ASYLUM("wa-task-initiation-sscs-asylum", "wa-task-initiation-sscs-asylum.dmn"),
    WA_TASK_PERMISSIONS_SSCS_ASYLUM("wa-task-permissions-sscs-asylum", "wa-task-permissions-sscs-asylum.dmn");

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
