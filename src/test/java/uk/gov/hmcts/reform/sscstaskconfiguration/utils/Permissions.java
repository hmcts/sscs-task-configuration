package uk.gov.hmcts.reform.sscstaskconfiguration.utils;

import java.util.List;
import java.util.Map;

public class Permissions {

    private Permissions() {
        // Hide Utility Class Constructor
    }

    public static Map<String, Object> permission(String role, String value) {
        return Map.of(
            "name", role,
            "value", value,
            "autoAssignable", false
        );
    }

    public static Map<String, Object> permission(String role, String value, String roleCategory) {
        return Map.of(
            "name", role,
            "value", value,
            "roleCategory", roleCategory,
            "autoAssignable", false
        );
    }

    public static Map<String, Object> permission(String role, String value, String roleCategory, int assignmentPriority) {
        return Map.of(
            "name", role,
            "value", value,
            "roleCategory", roleCategory,
            "assignmentPriority", assignmentPriority,
            "autoAssignable", true
        );
    }

    public static Map<String, Object> permission(String role, String value, String roleCategory, int assignmentPriority, boolean autoAssignable) {
        return Map.of(
            "name", role,
            "value", value,
            "roleCategory", roleCategory,
            "assignmentPriority", assignmentPriority,
            "autoAssignable", autoAssignable
        );
    }

    public static Map<String, Object> permission(String role, String value, String roleCategory, String authorisations) {
        return Map.of(
            "name", role,
            "value", value,
            "roleCategory", roleCategory,
            "authorisations", authorisations,
            "autoAssignable", false
        );
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
                "roleCategory", "CTSC",
                "autoAssignable", false
            ),
            Map.of(
                "name", "ctsc-team-leader",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel",
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
                "roleCategory", "ADMIN",
                "autoAssignable", false
            ),
            Map.of(
                "name", "regional-centre-team-leader",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel",
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
                "roleCategory", "CTSC",
                "autoAssignable", false
            ),
            Map.of(
                "name", "ctsc-team-leader",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel,CompleteOwn",
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
                "roleCategory", "ADMIN",
                "autoAssignable", false
            ),
            Map.of(
                "name", "regional-centre-team-leader",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel,CompleteOwn",
                "roleCategory", "ADMIN",
                "autoAssignable", false
            )
        );
    }

    public static List<Map<String, Object>> defaultPermissionsJudgesTasks() {
        return List.of(
            permission("case-allocator","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
            permission("task-supervisor","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
            permission("tribunal-caseworker","Read,Execute,Unclaim", "LEGAL_OPERATIONS"),
            permission("interloc-judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1),
            permission("judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
            permission("fee-paid-judge", "Read,Own,Claim,Unclaim", "JUDICIAL","368")
        );
    }

    public static List<Map<String, Object>> defaultPermissionsHearingJudgesTasks() {
        return List.of(
            permission("case-allocator","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
            permission("task-supervisor","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
            permission("tribunal-caseworker","Read,Execute,Unclaim", "LEGAL_OPERATIONS"),
            permission("hearing-judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1),
            permission("judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
            permission("fee-paid-judge","Read,Own,Claim,Unclaim", "JUDICIAL","368")
        );
    }

    public static List<Map<String, Object>> defaultPermissionsJudgesReviewTasks() {
        return List.of(
            permission("case-allocator","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
            permission("task-supervisor","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
            permission("tribunal-caseworker","Read,Execute,Unclaim", "LEGAL_OPERATIONS"),
            permission("interloc-judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Complete", "JUDICIAL", 1),
            permission("judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Complete", "JUDICIAL"),
            permission("fee-paid-judge","Read,Own,Claim,Unclaim", "JUDICIAL","368")
        );
    }

    public static List<Map<String, Object>> defaultPermissionsTcwTasks() {
        return List.of(
            permission("case-allocator","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
            permission("task-supervisor","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
            permission("allocated-tribunal-caseworker", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "LEGAL_OPERATIONS", 1),
            permission("tribunal-caseworker","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "LEGAL_OPERATIONS"),
            permission("judge","Read,Execute,Unclaim,UnclaimAssign", "JUDICIAL")
        );
    }

    public static List<Map<String, Object>> defaultPermissionsPostHearingTasks() {
        return List.of(
            permission("case-allocator","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
            permission("task-supervisor","Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim"),
            permission("judge", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
            permission("post-hearing-judge", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1)
        );
    }

    public static List<Map<String, Object>> defaultJudicalMember1Permissions() {
        return List.of(
            Map.of(
                "name", "case-allocator",
                "value", "Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim",
                "autoAssignable", false
            ),
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim",
                "autoAssignable", false
            ),
            Map.of(
                "name", "tribunal-member-1",
                "value", "Read,Own,Manage,Complete",
                "assignmentPriority", 1,
                "roleCategory", "JUDICIAL",
                "autoAssignable", true
            )
        );
    }
    public static List<Map<String, Object>> defaultJudicalMember2Permissions() {
        return List.of(
            Map.of(
                "name", "case-allocator",
                "value", "Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim",
                "autoAssignable", false
            ),
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim",
                "autoAssignable", false
            ),
            Map.of(
                "name", "tribunal-member-2",
                "value", "Read,Own,Manage,Complete",
                "assignmentPriority", 1,
                "roleCategory", "JUDICIAL",
                "autoAssignable", true
            )
        );
    }
    public static List<Map<String, Object>> defaultJudicalMember3Permissions() {
        return List.of(
            Map.of(
                "name", "case-allocator",
                "value", "Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim",
                "autoAssignable", false
            ),
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Manage,Complete,Cancel,Assign,Unassign,Claim,Unclaim",
                "autoAssignable", false
            ),
            Map.of(
                "name", "tribunal-member-3",
                "value", "Read,Own,Manage,Complete",
                "assignmentPriority", 1,
                "roleCategory", "JUDICIAL",
                "autoAssignable", true
            )
        );
    }
}
