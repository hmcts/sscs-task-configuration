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

    public static Map<String,Object> CTSC_READ_PERMISSIONS =
        permission("ctsc", "Read", "CTSC");
    public static Map<String,Object> CTSC_TEAM_LEADER_READ_PERMISSIONS =
        permission("ctsc-team-leader", "Read", "CTSC");
    public static Map<String,Object> ALLOCATED_TRIBUNAL_CASEWORKER_READ_PERMISSIONS =
        permission("allocated-tribunal-caseworker", "Read", "LEGAL_OPERATIONS");
    public static Map<String,Object> TRIBUNAL_CASEWORKER_READ_PERMISSIONS =
        permission("tribunal-caseworker", "Read", "LEGAL_OPERATIONS");
    public static Map<String,Object> INTERLOC_JUDGE_READ_PERMISSIONS =
        permission("interloc-judge", "Read", "JUDICIAL");
    public static Map<String,Object> HEARING_JUDGE_READ_PERMISSIONS =
        permission("hearing-judge", "Read", "JUDICIAL");
    public static Map<String,Object> JUDGE_READ_PERMISSIONS =
        permission("judge", "Read", "JUDICIAL");
    public static Map<String,Object> FEE_PAID_JUDGE_READ_PERMISSIONS =
        permission("fee-paid-judge", "Read", "JUDICIAL");
    public static Map<String,Object> POST_HEARING_JUDGE_READ_PERMISSIONS =
        permission("post-hearing-judge", "Read", "JUDICIAL");
    public static Map<String,Object> ALLOCATED_ADMIN_CASEWORKER_READ_PERMISSIONS =
        permission("allocated-admin-caseworker", "Read", "ADMIN");
    public static Map<String,Object> REGIONAL_CENTRE_ADMIN_READ_PERMISSIONS =
        permission("regional-centre-admin", "Read", "ADMIN");
    public static Map<String,Object> REGIONAL_CENTRE_TEAM_LEADER_READ_PERMISSIONS =
        permission("regional-centre-team-leader", "Read", "ADMIN");
    public static Map<String,Object> HEARING_CENTRE_ADMIN_READ_PERMISSIONS =
        permission("hearing-centre-admin", "Read", "ADMIN");
    public static Map<String,Object> HEARING_CENTRE_TEAM_LEADER_READ_PERMISSIONS =
        permission("hearing-centre-team-leader", "Read", "ADMIN");
    public static Map<String,Object> TRIBUNAL_MEMBER_1_READ_PERMISSIONS =
        permission("tribunal-member-1", "Read", "JUDICIAL");
    public static Map<String,Object> TRIBUNAL_MEMBER_2_READ_PERMISSIONS =
        permission("tribunal-member-2", "Read", "JUDICIAL");
    public static Map<String,Object> TRIBUNAL_MEMBER_3_READ_PERMISSIONS =
        permission("tribunal-member-3", "Read", "JUDICIAL");
    public static Map<String,Object> APPRAISER_1_READ_PERMISSIONS =
        permission("appraiser-1", "Read", "JUDICIAL");
    public static Map<String,Object> APPRAISER_2_READ_PERMISSIONS =
        permission("appraiser-2", "Read", "JUDICIAL");
    public static Map<String,Object> MEDICAL_READ_PERMISSIONS =
        permission("medical", "Read", "JUDICIAL");
    public static Map<String,Object> FEE_PAID_MEDICAL_READ_PERMISSIONS =
        permission("fee-paid-medical", "Read", "JUDICIAL");
    public static Map<String,Object> LEADERSHIP_JUDGE_READ_PERMISSIONS =
        permission("leadership-judge", "Read", "JUDICIAL");

    public static List<Map<String, Object>> defaultCtscPermissions() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            ALLOCATED_TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
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
            ),
            INTERLOC_JUDGE_READ_PERMISSIONS,
            HEARING_JUDGE_READ_PERMISSIONS,
            JUDGE_READ_PERMISSIONS,
            FEE_PAID_JUDGE_READ_PERMISSIONS,
            POST_HEARING_JUDGE_READ_PERMISSIONS,
            ALLOCATED_ADMIN_CASEWORKER_READ_PERMISSIONS,
            REGIONAL_CENTRE_ADMIN_READ_PERMISSIONS,
            REGIONAL_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            HEARING_CENTRE_ADMIN_READ_PERMISSIONS,
            HEARING_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_1_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_2_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_3_READ_PERMISSIONS,
            APPRAISER_1_READ_PERMISSIONS,
            APPRAISER_2_READ_PERMISSIONS,
            MEDICAL_READ_PERMISSIONS,
            FEE_PAID_MEDICAL_READ_PERMISSIONS,
            LEADERSHIP_JUDGE_READ_PERMISSIONS
        );
    }

    public static List<Map<String, Object>> defaultAdminCaseWorkerPermissions() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            ALLOCATED_TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            CTSC_READ_PERMISSIONS,
            CTSC_TEAM_LEADER_READ_PERMISSIONS,
            INTERLOC_JUDGE_READ_PERMISSIONS,
            HEARING_JUDGE_READ_PERMISSIONS,
            JUDGE_READ_PERMISSIONS,
            FEE_PAID_JUDGE_READ_PERMISSIONS,
            POST_HEARING_JUDGE_READ_PERMISSIONS,
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
            ),
            HEARING_CENTRE_ADMIN_READ_PERMISSIONS,
            HEARING_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_1_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_2_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_3_READ_PERMISSIONS,
            APPRAISER_1_READ_PERMISSIONS,
            APPRAISER_2_READ_PERMISSIONS,
            MEDICAL_READ_PERMISSIONS,
            FEE_PAID_MEDICAL_READ_PERMISSIONS,
            LEADERSHIP_JUDGE_READ_PERMISSIONS
        );
    }

    public static List<Map<String, Object>> defaultCtscPermissionsWithCompleteOwn() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            ALLOCATED_TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
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
            ),
            INTERLOC_JUDGE_READ_PERMISSIONS,
            HEARING_JUDGE_READ_PERMISSIONS,
            JUDGE_READ_PERMISSIONS,
            FEE_PAID_JUDGE_READ_PERMISSIONS,
            POST_HEARING_JUDGE_READ_PERMISSIONS,
            ALLOCATED_ADMIN_CASEWORKER_READ_PERMISSIONS,
            REGIONAL_CENTRE_ADMIN_READ_PERMISSIONS,
            REGIONAL_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            HEARING_CENTRE_ADMIN_READ_PERMISSIONS,
            HEARING_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_1_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_2_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_3_READ_PERMISSIONS,
            APPRAISER_1_READ_PERMISSIONS,
            APPRAISER_2_READ_PERMISSIONS,
            MEDICAL_READ_PERMISSIONS,
            FEE_PAID_MEDICAL_READ_PERMISSIONS,
            LEADERSHIP_JUDGE_READ_PERMISSIONS
        );
    }

    public static List<Map<String, Object>> defaultAdminCaseWorkerPermissionsWithCompleteOwn() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            ALLOCATED_TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            CTSC_READ_PERMISSIONS,
            CTSC_TEAM_LEADER_READ_PERMISSIONS,
            INTERLOC_JUDGE_READ_PERMISSIONS,
            HEARING_JUDGE_READ_PERMISSIONS,
            JUDGE_READ_PERMISSIONS,
            FEE_PAID_JUDGE_READ_PERMISSIONS,
            POST_HEARING_JUDGE_READ_PERMISSIONS,
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
            ),
            HEARING_CENTRE_ADMIN_READ_PERMISSIONS,
            HEARING_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_1_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_2_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_3_READ_PERMISSIONS,
            APPRAISER_1_READ_PERMISSIONS,
            APPRAISER_2_READ_PERMISSIONS,
            MEDICAL_READ_PERMISSIONS,
            FEE_PAID_MEDICAL_READ_PERMISSIONS,
            LEADERSHIP_JUDGE_READ_PERMISSIONS
        );
    }

    public static List<Map<String, Object>> defaultPermissionsJudgesTasks() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            ALLOCATED_TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            permission("tribunal-caseworker","Read,Execute,Unclaim", "LEGAL_OPERATIONS"),
            CTSC_READ_PERMISSIONS,
            CTSC_TEAM_LEADER_READ_PERMISSIONS,
            permission("interloc-judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1),
            HEARING_JUDGE_READ_PERMISSIONS,
            permission("judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
            permission("fee-paid-judge", "Read,Own,Claim,Unclaim", "JUDICIAL","368"),
            POST_HEARING_JUDGE_READ_PERMISSIONS,
            ALLOCATED_ADMIN_CASEWORKER_READ_PERMISSIONS,
            REGIONAL_CENTRE_ADMIN_READ_PERMISSIONS,
            REGIONAL_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            HEARING_CENTRE_ADMIN_READ_PERMISSIONS,
            HEARING_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_1_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_2_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_3_READ_PERMISSIONS,
            APPRAISER_1_READ_PERMISSIONS,
            APPRAISER_2_READ_PERMISSIONS,
            MEDICAL_READ_PERMISSIONS,
            FEE_PAID_MEDICAL_READ_PERMISSIONS,
            LEADERSHIP_JUDGE_READ_PERMISSIONS
        );
    }

    public static List<Map<String, Object>> defaultPermissionsHearingJudgesTasks() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            ALLOCATED_TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            permission("tribunal-caseworker","Read,Execute,Unclaim", "LEGAL_OPERATIONS"),
            CTSC_READ_PERMISSIONS,
            CTSC_TEAM_LEADER_READ_PERMISSIONS,
            INTERLOC_JUDGE_READ_PERMISSIONS,
            permission("hearing-judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1),
            permission("judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
            permission("fee-paid-judge","Read,Own,Claim,Unclaim", "JUDICIAL","368"),
            POST_HEARING_JUDGE_READ_PERMISSIONS,
            ALLOCATED_ADMIN_CASEWORKER_READ_PERMISSIONS,
            REGIONAL_CENTRE_ADMIN_READ_PERMISSIONS,
            REGIONAL_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            HEARING_CENTRE_ADMIN_READ_PERMISSIONS,
            HEARING_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_1_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_2_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_3_READ_PERMISSIONS,
            APPRAISER_1_READ_PERMISSIONS,
            APPRAISER_2_READ_PERMISSIONS,
            MEDICAL_READ_PERMISSIONS,
            FEE_PAID_MEDICAL_READ_PERMISSIONS,
            LEADERSHIP_JUDGE_READ_PERMISSIONS
        );
    }

    public static List<Map<String, Object>> defaultPermissionsJudgesReviewTasks() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            ALLOCATED_TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            permission("tribunal-caseworker","Read,Execute,Unclaim", "LEGAL_OPERATIONS"),
            CTSC_READ_PERMISSIONS,
            CTSC_TEAM_LEADER_READ_PERMISSIONS,
            permission("interloc-judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Complete", "JUDICIAL", 1),
            HEARING_JUDGE_READ_PERMISSIONS,
            permission("judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Complete", "JUDICIAL"),
            permission("fee-paid-judge","Read,Own,Claim,Unclaim", "JUDICIAL","368"),
            POST_HEARING_JUDGE_READ_PERMISSIONS,
            ALLOCATED_ADMIN_CASEWORKER_READ_PERMISSIONS,
            REGIONAL_CENTRE_ADMIN_READ_PERMISSIONS,
            REGIONAL_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            HEARING_CENTRE_ADMIN_READ_PERMISSIONS,
            HEARING_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_1_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_2_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_3_READ_PERMISSIONS,
            APPRAISER_1_READ_PERMISSIONS,
            APPRAISER_2_READ_PERMISSIONS,
            MEDICAL_READ_PERMISSIONS,
            FEE_PAID_MEDICAL_READ_PERMISSIONS,
            LEADERSHIP_JUDGE_READ_PERMISSIONS
        );
    }

    public static List<Map<String, Object>> defaultPermissionsTcwTasks() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            permission("allocated-tribunal-caseworker", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "LEGAL_OPERATIONS", 1),
            permission("tribunal-caseworker","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "LEGAL_OPERATIONS"),
            CTSC_READ_PERMISSIONS,
            CTSC_TEAM_LEADER_READ_PERMISSIONS,
            INTERLOC_JUDGE_READ_PERMISSIONS,
            HEARING_JUDGE_READ_PERMISSIONS,
            permission("judge","Read,Execute,Unclaim,UnclaimAssign", "JUDICIAL"),
            FEE_PAID_JUDGE_READ_PERMISSIONS,
            POST_HEARING_JUDGE_READ_PERMISSIONS,
            ALLOCATED_ADMIN_CASEWORKER_READ_PERMISSIONS,
            REGIONAL_CENTRE_ADMIN_READ_PERMISSIONS,
            REGIONAL_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            HEARING_CENTRE_ADMIN_READ_PERMISSIONS,
            HEARING_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_1_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_2_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_3_READ_PERMISSIONS,
            APPRAISER_1_READ_PERMISSIONS,
            APPRAISER_2_READ_PERMISSIONS,
            MEDICAL_READ_PERMISSIONS,
            FEE_PAID_MEDICAL_READ_PERMISSIONS,
            LEADERSHIP_JUDGE_READ_PERMISSIONS
        );
    }

    public static List<Map<String, Object>> defaultPermissionsPostHearingTasks() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            ALLOCATED_TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            CTSC_READ_PERMISSIONS,
            CTSC_TEAM_LEADER_READ_PERMISSIONS,
            INTERLOC_JUDGE_READ_PERMISSIONS,
            HEARING_JUDGE_READ_PERMISSIONS,
            permission("judge", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
            FEE_PAID_JUDGE_READ_PERMISSIONS,
            permission("post-hearing-judge", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1),
            ALLOCATED_ADMIN_CASEWORKER_READ_PERMISSIONS,
            REGIONAL_CENTRE_ADMIN_READ_PERMISSIONS,
            REGIONAL_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            HEARING_CENTRE_ADMIN_READ_PERMISSIONS,
            HEARING_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_1_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_2_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_3_READ_PERMISSIONS,
            APPRAISER_1_READ_PERMISSIONS,
            APPRAISER_2_READ_PERMISSIONS,
            MEDICAL_READ_PERMISSIONS,
            FEE_PAID_MEDICAL_READ_PERMISSIONS,
            LEADERSHIP_JUDGE_READ_PERMISSIONS
        );
    }

    public static List<Map<String, Object>> defaultJudicalMember1Permissions() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            ALLOCATED_TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            CTSC_READ_PERMISSIONS,
            CTSC_TEAM_LEADER_READ_PERMISSIONS,
            INTERLOC_JUDGE_READ_PERMISSIONS,
            HEARING_JUDGE_READ_PERMISSIONS,
            JUDGE_READ_PERMISSIONS,
            FEE_PAID_JUDGE_READ_PERMISSIONS,
            POST_HEARING_JUDGE_READ_PERMISSIONS,
            ALLOCATED_ADMIN_CASEWORKER_READ_PERMISSIONS,
            REGIONAL_CENTRE_ADMIN_READ_PERMISSIONS,
            REGIONAL_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            HEARING_CENTRE_ADMIN_READ_PERMISSIONS,
            HEARING_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            Map.of(
                "name", "tribunal-member-1",
                "value", "Read,Own,Manage,Complete",
                "assignmentPriority", 1,
                "roleCategory", "JUDICIAL",
                "autoAssignable", true
            ),
            TRIBUNAL_MEMBER_2_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_3_READ_PERMISSIONS,
            APPRAISER_1_READ_PERMISSIONS,
            APPRAISER_2_READ_PERMISSIONS,
            MEDICAL_READ_PERMISSIONS,
            FEE_PAID_MEDICAL_READ_PERMISSIONS,
            LEADERSHIP_JUDGE_READ_PERMISSIONS
        );
    }

    public static List<Map<String, Object>> defaultJudicalMember2Permissions() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            ALLOCATED_TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            CTSC_READ_PERMISSIONS,
            CTSC_TEAM_LEADER_READ_PERMISSIONS,
            INTERLOC_JUDGE_READ_PERMISSIONS,
            HEARING_JUDGE_READ_PERMISSIONS,
            JUDGE_READ_PERMISSIONS,
            FEE_PAID_JUDGE_READ_PERMISSIONS,
            POST_HEARING_JUDGE_READ_PERMISSIONS,
            ALLOCATED_ADMIN_CASEWORKER_READ_PERMISSIONS,
            REGIONAL_CENTRE_ADMIN_READ_PERMISSIONS,
            REGIONAL_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            HEARING_CENTRE_ADMIN_READ_PERMISSIONS,
            HEARING_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_1_READ_PERMISSIONS,
            Map.of(
                "name", "tribunal-member-2",
                "value", "Read,Own,Manage,Complete",
                "assignmentPriority", 1,
                "roleCategory", "JUDICIAL",
                "autoAssignable", true
            ),
            TRIBUNAL_MEMBER_3_READ_PERMISSIONS,
            APPRAISER_1_READ_PERMISSIONS,
            APPRAISER_2_READ_PERMISSIONS,
            MEDICAL_READ_PERMISSIONS,
            FEE_PAID_MEDICAL_READ_PERMISSIONS,
            LEADERSHIP_JUDGE_READ_PERMISSIONS
        );
    }

    public static List<Map<String, Object>> defaultJudicalMember3Permissions() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            ALLOCATED_TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            TRIBUNAL_CASEWORKER_READ_PERMISSIONS,
            CTSC_READ_PERMISSIONS,
            CTSC_TEAM_LEADER_READ_PERMISSIONS,
            INTERLOC_JUDGE_READ_PERMISSIONS,
            HEARING_JUDGE_READ_PERMISSIONS,
            JUDGE_READ_PERMISSIONS,
            FEE_PAID_JUDGE_READ_PERMISSIONS,
            POST_HEARING_JUDGE_READ_PERMISSIONS,
            ALLOCATED_ADMIN_CASEWORKER_READ_PERMISSIONS,
            REGIONAL_CENTRE_ADMIN_READ_PERMISSIONS,
            REGIONAL_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            HEARING_CENTRE_ADMIN_READ_PERMISSIONS,
            HEARING_CENTRE_TEAM_LEADER_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_1_READ_PERMISSIONS,
            TRIBUNAL_MEMBER_2_READ_PERMISSIONS,
            Map.of(
                "name", "tribunal-member-3",
                "value", "Read,Own,Manage,Complete",
                "assignmentPriority", 1,
                "roleCategory", "JUDICIAL",
                "autoAssignable", true
            ),
            APPRAISER_1_READ_PERMISSIONS,
            APPRAISER_2_READ_PERMISSIONS,
            MEDICAL_READ_PERMISSIONS,
            FEE_PAID_MEDICAL_READ_PERMISSIONS,
            LEADERSHIP_JUDGE_READ_PERMISSIONS
        );
    }
}
