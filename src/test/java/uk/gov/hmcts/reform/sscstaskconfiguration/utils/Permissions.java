package uk.gov.hmcts.reform.sscstaskconfiguration.utils;

import java.util.List;
import java.util.Map;

public class Permissions {

    private Permissions() {
        // Hide Utility Class Constructor
    }

    public static Map<String,Object> DEFAULT_CASE_ALLOCATOR_PERMISSIONS = Map.of(
        "name", "case-allocator",
        "value", "Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim",
        "autoAssignable", false
    );
    public static Map<String,Object> DEFAULT_TASK_SUPERVISOR_PERMISSIONS = Map.of(
        "name", "task-supervisor",
        "value", "Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim",
        "autoAssignable", false
    );

    public static List<Map<String, Object>> defaultCtscPermissions() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            Map.of(
                "name", "allocated-ctsc-caseworker",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
                "assignmentPriority", 1,
                "roleCategory", "CTSC",
                "autoAssignable", true
            ),
            Map.of(
                "name", "ctsc",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
                "assignmentPriority", 2,
                "roleCategory", "CTSC",
                "autoAssignable", false
            ),
            Map.of(
                "name", "ctsc-team-leader",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel",
                "assignmentPriority", 3,
                "roleCategory", "CTSC",
                "autoAssignable", false
            )
        );
    }

    public static List<Map<String, Object>> defaultAdminCaseWorkerPermissions() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            Map.of(
                "name", "allocated-admin-caseworker",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
                "assignmentPriority", 1,
                "roleCategory", "ADMIN",
                "autoAssignable", true
            ),
            Map.of(
                "name", "regional-centre-admin",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign",
                "assignmentPriority", 2,
                "roleCategory", "ADMIN",
                "autoAssignable", false
            ),
            Map.of(
                "name", "regional-centre-team-leader",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel",
                "assignmentPriority", 3,
                "roleCategory", "ADMIN",
                "autoAssignable", false
            )
        );
    }

    public static List<Map<String, Object>> defaultCtscPermissionsWithCompleteOwn() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            Map.of(
                "name", "allocated-ctsc-caseworker",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,CompleteOwn",
                "assignmentPriority", 1,
                "roleCategory", "CTSC",
                "autoAssignable", true
            ),
            Map.of(
                "name", "ctsc",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,CompleteOwn",
                "assignmentPriority", 2,
                "roleCategory", "CTSC",
                "autoAssignable", false
            ),
            Map.of(
                "name", "ctsc-team-leader",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel,CompleteOwn",
                "assignmentPriority", 3,
                "roleCategory", "CTSC",
                "autoAssignable", false
            )
        );
    }

    public static List<Map<String, Object>> defaultAdminCaseWorkerPermissionsWithCompleteOwn() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            Map.of(
                "name", "allocated-admin-caseworker",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,CompleteOwn",
                "assignmentPriority", 1,
                "roleCategory", "ADMIN",
                "autoAssignable", true
            ),
            Map.of(
                "name", "regional-centre-admin",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,CompleteOwn",
                "assignmentPriority", 2,
                "roleCategory", "ADMIN",
                "autoAssignable", false
            ),
            Map.of(
                "name", "regional-centre-team-leader",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel,CompleteOwn",
                "assignmentPriority", 3,
                "roleCategory", "ADMIN",
                "autoAssignable", false
            )
        );
    }
}
