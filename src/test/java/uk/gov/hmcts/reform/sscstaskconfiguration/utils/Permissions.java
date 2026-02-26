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

    public static Map<String, Object> permission(String role, String value, String roleCategory, boolean autoAssignable) {
        return Map.of(
            "name", role,
            "value", value,
            "roleCategory", roleCategory,
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
    public static final Map<String, Object> DEFAULT_ALLOCATED_CASEWORKER_PERMISSIONS = Map.of(
        "name", "allocated-tribunal-caseworker",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "LEGAL_OPERATIONS"
    );
    public static final Map<String, Object> DEFAULT_TRIBUNAL_CASEWORKER_PERMISSIONS = Map.of(
        "name", "tribunal-caseworker",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "LEGAL_OPERATIONS"
    );
    public static final Map<String, Object> DEFAULT_LEGAL_OPS_CHALLENGED_ACCESS_PERMISSIONS = Map.of(
        "name", "challenged-access-legal-ops",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "LEGAL_OPERATIONS"
    );
    public static final Map<String, Object> DEFAULT_ALLOCATED_CTSC_CASEWORKER_PERMISSIONS = Map.of(
        "name", "allocated-ctsc-caseworker",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "CTSC"
    );
    public static final Map<String, Object> DEFAULT_CTSC_PERMISSIONS = Map.of(
        "name", "ctsc",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "CTSC"
    );
    public static final Map<String, Object> DEFAULT_CTSC_CHALLENGED_ACCESS_PERMISSIONS = Map.of(
        "name", "challenged-access-ctsc",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "CTSC"
    );
    public static final Map<String, Object> DEFAULT_CTSC_TEAM_LEAD_PERMISSIONS = Map.of(
        "name", "ctsc-team-leader",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "CTSC"
    );
    public static final Map<String, Object> DEFAULT_INTERLOC_JUDGE_PERMISSIONS = Map.of(
        "name", "interloc-judge",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "JUDICIAL"
    );
    public static final Map<String, Object> DEFAULT_HEARING_JUDGE_PERMISSIONS = Map.of(
        "name", "hearing-judge",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "JUDICIAL"
    );
    public static final Map<String, Object> DEFAULT_JUDGE_PERMISSIONS = Map.of(
        "name", "judge",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "JUDICIAL"
    );
    public static final Map<String, Object> DEFAULT_JUDICIARY_CHALLENGED_ACCESS_PERMISSIONS = Map.of(
        "name", "challenged-access-judiciary",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "JUDICIAL"
    );
    public static final Map<String, Object> DEFAULT_POST_HEARING_JUDGE_PERMISSIONS = Map.of(
        "name", "post-hearing-judge",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "JUDICIAL"
    );
    public static final Map<String, Object> DEFAULT_ALLOCATED_ADMIN_CASEWORKER_PERMISSIONS = Map.of(
        "name", "allocated-admin-caseworker",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "ADMIN"
    );
    public static final Map<String, Object> DEFAULT_REGIONAL_CENTER_ADMIN_PERMISSIONS = Map.of(
        "name", "regional-centre-admin",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "ADMIN"
    );
    public static final Map<String, Object> DEFAULT_REGIONAL_CENTER_TEAM_LEADER_PERMISSIONS = Map.of(
        "name", "regional-centre-team-leader",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "ADMIN"
    );
    public static final Map<String, Object> DEFAULT_HEARING_CENTER_ADMIN_PERMISSIONS = Map.of(
        "name", "hearing-centre-admin",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "ADMIN"
    );
    public static final Map<String, Object> DEFAULT_HEARING_CENTER_TEAM_LEADER_PERMISSIONS = Map.of(
        "name", "hearing-centre-team-leader",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "ADMIN"
    );
    public static final Map<String, Object> DEFAULT_ADMIN_CHALLENGED_ACCESS_PERMISSIONS = Map.of(
        "name", "challenged-access-admin",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "ADMIN"
    );

    public static final Map<String, Object> DEFAULT_TRIBUNAL_MEMBER_1_PERMISSIONS = Map.of(
        "name", "tribunal-member-1",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "JUDICIAL"
    );
    public static final Map<String, Object> DEFAULT_TRIBUNAL_MEMBER_2_PERMISSIONS = Map.of(
        "name", "tribunal-member-2",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "JUDICIAL"
    );
    public static final Map<String, Object> DEFAULT_TRIBUNAL_MEMBER_3_PERMISSIONS = Map.of(
        "name", "tribunal-member-3",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "JUDICIAL"
    );
    public static final Map<String, Object> DEFAULT_APPRAISER_1_PERMISSIONS = Map.of(
        "name", "appraiser-1",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "JUDICIAL"
    );
    public static final Map<String, Object> DEFAULT_APPRAISER_2_PERMISSIONS = Map.of(
        "name", "appraiser-2",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "JUDICIAL"
    );
    public static final Map<String, Object> DEFAULT_MEDICAL_PERMISSIONS = Map.of(
        "name", "medical",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "JUDICIAL"
    );
    public static final Map<String, Object> DEFAULT_FEE_PAID_MEDICAL_PERMISSIONS = Map.of(
        "name", "fee-paid-medical",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "JUDICIAL"
    );
    public static final Map<String, Object> DEFAULT_LEADERSHIP_JUDGE_PERMISSIONS = Map.of(
        "name", "leadership-judge",
        "value", "Read",
        "autoAssignable", false,
        "roleCategory", "JUDICIAL"
    );

    public static List<Map<String, Object>> defaultCtscPermissionsWithCancelOwn(){
        return List.of(
            Permissions.DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            Permissions.DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            Permissions.DEFAULT_ALLOCATED_CASEWORKER_PERMISSIONS,
            Permissions.DEFAULT_TRIBUNAL_CASEWORKER_PERMISSIONS,
            Permissions.DEFAULT_LEGAL_OPS_CHALLENGED_ACCESS_PERMISSIONS,
            Permissions.DEFAULT_INTERLOC_JUDGE_PERMISSIONS,
            Permissions.DEFAULT_HEARING_JUDGE_PERMISSIONS,
            Permissions.DEFAULT_JUDGE_PERMISSIONS,
            Permissions.DEFAULT_JUDICIARY_CHALLENGED_ACCESS_PERMISSIONS,
            Permissions.DEFAULT_POST_HEARING_JUDGE_PERMISSIONS,
            Permissions.DEFAULT_ALLOCATED_ADMIN_CASEWORKER_PERMISSIONS,
            Permissions.DEFAULT_REGIONAL_CENTER_ADMIN_PERMISSIONS,
            Permissions.DEFAULT_REGIONAL_CENTER_TEAM_LEADER_PERMISSIONS,
            Permissions.DEFAULT_HEARING_CENTER_ADMIN_PERMISSIONS,
            Permissions.DEFAULT_HEARING_CENTER_TEAM_LEADER_PERMISSIONS,
            Permissions.DEFAULT_ADMIN_CHALLENGED_ACCESS_PERMISSIONS,
            Permissions.DEFAULT_TRIBUNAL_MEMBER_1_PERMISSIONS,
            Permissions.DEFAULT_TRIBUNAL_MEMBER_2_PERMISSIONS,
            Permissions.DEFAULT_TRIBUNAL_MEMBER_3_PERMISSIONS,
            Permissions.DEFAULT_APPRAISER_1_PERMISSIONS,
            Permissions.DEFAULT_APPRAISER_2_PERMISSIONS,
            Permissions.DEFAULT_MEDICAL_PERMISSIONS,
            Permissions.DEFAULT_FEE_PAID_MEDICAL_PERMISSIONS,
            Permissions.DEFAULT_LEADERSHIP_JUDGE_PERMISSIONS,
            Map.of(
                "name", "allocated-ctsc-caseworker",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,CompleteOwn,CancelOwn",
                "assignmentPriority", 1,
                "roleCategory", "CTSC",
                "autoAssignable", true
            ),
            Map.of(
                "name", "ctsc",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,CompleteOwn,CancelOwn",
                "roleCategory", "CTSC",
                "autoAssignable", false
            ),
            Map.of(
                "name", "challenged-access-ctsc",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,CompleteOwn,CancelOwn",
                "roleCategory", "CTSC",
                "autoAssignable", false
            ),
            Map.of(
                "name", "ctsc-team-leader",
                "value", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Assign,Unassign,Cancel,Complete",
                "roleCategory", "CTSC",
                "autoAssignable", false
            )
        );
    }

    public static List<Map<String, Object>> defaultCtscPermissions() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            DEFAULT_ALLOCATED_CASEWORKER_PERMISSIONS,
            DEFAULT_TRIBUNAL_CASEWORKER_PERMISSIONS,
            DEFAULT_LEGAL_OPS_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_INTERLOC_JUDGE_PERMISSIONS,
            DEFAULT_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_JUDGE_PERMISSIONS,
            DEFAULT_JUDICIARY_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_POST_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_ALLOCATED_ADMIN_CASEWORKER_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_HEARING_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_HEARING_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_ADMIN_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_1_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_2_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_3_PERMISSIONS,
            DEFAULT_APPRAISER_1_PERMISSIONS,
            DEFAULT_APPRAISER_2_PERMISSIONS,
            DEFAULT_MEDICAL_PERMISSIONS,
            DEFAULT_FEE_PAID_MEDICAL_PERMISSIONS,
            DEFAULT_LEADERSHIP_JUDGE_PERMISSIONS,
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
                "name", "challenged-access-ctsc",
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
            DEFAULT_ALLOCATED_CASEWORKER_PERMISSIONS,
            DEFAULT_TRIBUNAL_CASEWORKER_PERMISSIONS,
            DEFAULT_LEGAL_OPS_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_ALLOCATED_CTSC_CASEWORKER_PERMISSIONS,
            DEFAULT_CTSC_PERMISSIONS,
            DEFAULT_CTSC_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_CTSC_TEAM_LEAD_PERMISSIONS,
            DEFAULT_INTERLOC_JUDGE_PERMISSIONS,
            DEFAULT_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_JUDGE_PERMISSIONS,
            DEFAULT_JUDICIARY_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_POST_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_HEARING_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_HEARING_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_1_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_2_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_3_PERMISSIONS,
            DEFAULT_APPRAISER_1_PERMISSIONS,
            DEFAULT_APPRAISER_2_PERMISSIONS,
            DEFAULT_MEDICAL_PERMISSIONS,
            DEFAULT_FEE_PAID_MEDICAL_PERMISSIONS,
            DEFAULT_LEADERSHIP_JUDGE_PERMISSIONS,
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
                "name", "challenged-access-admin",
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
            DEFAULT_ALLOCATED_CASEWORKER_PERMISSIONS,
            DEFAULT_TRIBUNAL_CASEWORKER_PERMISSIONS,
            DEFAULT_LEGAL_OPS_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_INTERLOC_JUDGE_PERMISSIONS,
            DEFAULT_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_JUDGE_PERMISSIONS,
            DEFAULT_JUDICIARY_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_POST_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_ALLOCATED_ADMIN_CASEWORKER_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_HEARING_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_HEARING_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_ADMIN_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_1_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_2_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_3_PERMISSIONS,
            DEFAULT_APPRAISER_1_PERMISSIONS,
            DEFAULT_APPRAISER_2_PERMISSIONS,
            DEFAULT_MEDICAL_PERMISSIONS,
            DEFAULT_FEE_PAID_MEDICAL_PERMISSIONS,
            DEFAULT_LEADERSHIP_JUDGE_PERMISSIONS,
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
                "name", "challenged-access-ctsc",
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
            DEFAULT_ALLOCATED_CASEWORKER_PERMISSIONS,
            DEFAULT_TRIBUNAL_CASEWORKER_PERMISSIONS,
            DEFAULT_LEGAL_OPS_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_ALLOCATED_CTSC_CASEWORKER_PERMISSIONS,
            DEFAULT_CTSC_PERMISSIONS,
            DEFAULT_CTSC_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_CTSC_TEAM_LEAD_PERMISSIONS,
            DEFAULT_INTERLOC_JUDGE_PERMISSIONS,
            DEFAULT_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_JUDGE_PERMISSIONS,
            DEFAULT_JUDICIARY_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_POST_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_HEARING_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_HEARING_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_1_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_2_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_3_PERMISSIONS,
            DEFAULT_APPRAISER_1_PERMISSIONS,
            DEFAULT_APPRAISER_2_PERMISSIONS,
            DEFAULT_MEDICAL_PERMISSIONS,
            DEFAULT_FEE_PAID_MEDICAL_PERMISSIONS,
            DEFAULT_LEADERSHIP_JUDGE_PERMISSIONS,
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
                "name", "challenged-access-admin",
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
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            DEFAULT_ALLOCATED_CASEWORKER_PERMISSIONS,
            DEFAULT_ALLOCATED_CTSC_CASEWORKER_PERMISSIONS,
            DEFAULT_CTSC_PERMISSIONS,
            DEFAULT_CTSC_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_CTSC_TEAM_LEAD_PERMISSIONS,
            DEFAULT_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_POST_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_ALLOCATED_ADMIN_CASEWORKER_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_HEARING_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_HEARING_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_ADMIN_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_1_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_2_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_3_PERMISSIONS,
            DEFAULT_APPRAISER_1_PERMISSIONS,
            DEFAULT_APPRAISER_2_PERMISSIONS,
            DEFAULT_MEDICAL_PERMISSIONS,
            DEFAULT_FEE_PAID_MEDICAL_PERMISSIONS,
            DEFAULT_LEADERSHIP_JUDGE_PERMISSIONS,
            permission("tribunal-caseworker","Read,Execute,Unclaim", "LEGAL_OPERATIONS"),
            permission("challenged-access-legal-ops","Read,Execute,Unclaim", "LEGAL_OPERATIONS"),
            permission("interloc-judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1),
            permission("judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
            permission("challenged-access-judiciary","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
            permission("fee-paid-judge", "Read,Own,Claim,Unclaim", "JUDICIAL","368")
        );
    }

    public static List<Map<String, Object>> defaultPermissionsHearingJudgesTasks() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            DEFAULT_ALLOCATED_CASEWORKER_PERMISSIONS,
            DEFAULT_ALLOCATED_CTSC_CASEWORKER_PERMISSIONS,
            DEFAULT_CTSC_PERMISSIONS,
            DEFAULT_CTSC_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_CTSC_TEAM_LEAD_PERMISSIONS,
            DEFAULT_INTERLOC_JUDGE_PERMISSIONS,
            DEFAULT_POST_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_ALLOCATED_ADMIN_CASEWORKER_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_HEARING_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_HEARING_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_ADMIN_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_1_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_2_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_3_PERMISSIONS,
            DEFAULT_APPRAISER_1_PERMISSIONS,
            DEFAULT_APPRAISER_2_PERMISSIONS,
            DEFAULT_MEDICAL_PERMISSIONS,
            DEFAULT_FEE_PAID_MEDICAL_PERMISSIONS,
            DEFAULT_LEADERSHIP_JUDGE_PERMISSIONS,
            permission("tribunal-caseworker","Read,Execute,Unclaim", "LEGAL_OPERATIONS"),
            permission("challenged-access-legal-ops","Read,Execute,Unclaim", "LEGAL_OPERATIONS"),
            permission("hearing-judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1),
            permission("judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
            permission("challenged-access-judiciary","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
            permission("fee-paid-judge","Read,Own,Claim,Unclaim", "JUDICIAL","368")
        );
    }

    public static List<Map<String, Object>> defaultPermissionsJudgesReviewTasks() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            DEFAULT_ALLOCATED_CASEWORKER_PERMISSIONS,
            DEFAULT_ALLOCATED_CTSC_CASEWORKER_PERMISSIONS,
            DEFAULT_CTSC_PERMISSIONS,
            DEFAULT_CTSC_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_CTSC_TEAM_LEAD_PERMISSIONS,
            DEFAULT_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_POST_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_ALLOCATED_ADMIN_CASEWORKER_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_HEARING_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_HEARING_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_ADMIN_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_1_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_2_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_3_PERMISSIONS,
            DEFAULT_APPRAISER_1_PERMISSIONS,
            DEFAULT_APPRAISER_2_PERMISSIONS,
            DEFAULT_MEDICAL_PERMISSIONS,
            DEFAULT_FEE_PAID_MEDICAL_PERMISSIONS,
            DEFAULT_LEADERSHIP_JUDGE_PERMISSIONS,
            permission("tribunal-caseworker","Read,Execute,Unclaim", "LEGAL_OPERATIONS"),
            permission("challenged-access-legal-ops","Read,Execute,Unclaim", "LEGAL_OPERATIONS"),
            permission("interloc-judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Complete", "JUDICIAL", 1),
            permission("judge","Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Complete", "JUDICIAL"),
            permission("challenged-access-judiciary","Read,Own,Claim,Unclaim,Manage,UnclaimAssign,Complete", "JUDICIAL"),
            permission("fee-paid-judge","Read,Own,Claim,Unclaim", "JUDICIAL","368")
        );
    }

    public static List<Map<String, Object>> defaultPermissionsTcwTasks() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            DEFAULT_ALLOCATED_CTSC_CASEWORKER_PERMISSIONS,
            DEFAULT_CTSC_PERMISSIONS,
            DEFAULT_CTSC_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_CTSC_TEAM_LEAD_PERMISSIONS,
            DEFAULT_INTERLOC_JUDGE_PERMISSIONS,
            DEFAULT_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_POST_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_ALLOCATED_ADMIN_CASEWORKER_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_HEARING_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_HEARING_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_ADMIN_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_1_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_2_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_3_PERMISSIONS,
            DEFAULT_APPRAISER_1_PERMISSIONS,
            DEFAULT_APPRAISER_2_PERMISSIONS,
            DEFAULT_MEDICAL_PERMISSIONS,
            DEFAULT_FEE_PAID_MEDICAL_PERMISSIONS,
            DEFAULT_LEADERSHIP_JUDGE_PERMISSIONS,
            permission("allocated-tribunal-caseworker", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "LEGAL_OPERATIONS", 1),
            permission("tribunal-caseworker","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "LEGAL_OPERATIONS"),
            permission("challenged-access-legal-ops","Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "LEGAL_OPERATIONS"),
            permission("judge","Read,Execute,Unclaim,UnclaimAssign", "JUDICIAL"),
            permission("challenged-access-judiciary","Read,Execute,Unclaim,UnclaimAssign", "JUDICIAL")
        );
    }

    public static List<Map<String, Object>> defaultPermissionsPostHearingTasks() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            DEFAULT_ALLOCATED_CASEWORKER_PERMISSIONS,
            DEFAULT_TRIBUNAL_CASEWORKER_PERMISSIONS,
            DEFAULT_LEGAL_OPS_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_ALLOCATED_CTSC_CASEWORKER_PERMISSIONS,
            DEFAULT_CTSC_PERMISSIONS,
            DEFAULT_CTSC_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_CTSC_TEAM_LEAD_PERMISSIONS,
            DEFAULT_INTERLOC_JUDGE_PERMISSIONS,
            DEFAULT_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_ALLOCATED_ADMIN_CASEWORKER_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_HEARING_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_HEARING_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_ADMIN_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_1_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_2_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_3_PERMISSIONS,
            DEFAULT_APPRAISER_1_PERMISSIONS,
            DEFAULT_APPRAISER_2_PERMISSIONS,
            DEFAULT_MEDICAL_PERMISSIONS,
            DEFAULT_FEE_PAID_MEDICAL_PERMISSIONS,
            DEFAULT_LEADERSHIP_JUDGE_PERMISSIONS,
            permission("judge", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
            permission("challenged-access-judiciary", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL"),
            permission("post-hearing-judge", "Read,Own,Claim,Unclaim,Manage,UnclaimAssign", "JUDICIAL", 1)
        );
    }

    public static List<Map<String, Object>> defaultJudicalMember1Permissions() {
        return List.of(
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            DEFAULT_ALLOCATED_CASEWORKER_PERMISSIONS,
            DEFAULT_TRIBUNAL_CASEWORKER_PERMISSIONS,
            DEFAULT_LEGAL_OPS_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_ALLOCATED_CTSC_CASEWORKER_PERMISSIONS,
            DEFAULT_CTSC_PERMISSIONS,
            DEFAULT_CTSC_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_CTSC_TEAM_LEAD_PERMISSIONS,
            DEFAULT_INTERLOC_JUDGE_PERMISSIONS,
            DEFAULT_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_JUDGE_PERMISSIONS,
            DEFAULT_JUDICIARY_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_POST_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_ALLOCATED_ADMIN_CASEWORKER_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_HEARING_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_HEARING_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_ADMIN_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_2_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_3_PERMISSIONS,
            DEFAULT_APPRAISER_1_PERMISSIONS,
            DEFAULT_APPRAISER_2_PERMISSIONS,
            DEFAULT_MEDICAL_PERMISSIONS,
            DEFAULT_FEE_PAID_MEDICAL_PERMISSIONS,
            DEFAULT_LEADERSHIP_JUDGE_PERMISSIONS,
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
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            DEFAULT_ALLOCATED_CASEWORKER_PERMISSIONS,
            DEFAULT_TRIBUNAL_CASEWORKER_PERMISSIONS,
            DEFAULT_LEGAL_OPS_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_ALLOCATED_CTSC_CASEWORKER_PERMISSIONS,
            DEFAULT_CTSC_PERMISSIONS,
            DEFAULT_CTSC_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_CTSC_TEAM_LEAD_PERMISSIONS,
            DEFAULT_INTERLOC_JUDGE_PERMISSIONS,
            DEFAULT_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_JUDGE_PERMISSIONS,
            DEFAULT_JUDICIARY_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_POST_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_ALLOCATED_ADMIN_CASEWORKER_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_HEARING_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_HEARING_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_ADMIN_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_1_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_3_PERMISSIONS,
            DEFAULT_APPRAISER_1_PERMISSIONS,
            DEFAULT_APPRAISER_2_PERMISSIONS,
            DEFAULT_MEDICAL_PERMISSIONS,
            DEFAULT_FEE_PAID_MEDICAL_PERMISSIONS,
            DEFAULT_LEADERSHIP_JUDGE_PERMISSIONS,
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
            DEFAULT_CASE_ALLOCATOR_PERMISSIONS,
            DEFAULT_TASK_SUPERVISOR_PERMISSIONS,
            DEFAULT_ALLOCATED_CASEWORKER_PERMISSIONS,
            DEFAULT_TRIBUNAL_CASEWORKER_PERMISSIONS,
            DEFAULT_LEGAL_OPS_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_ALLOCATED_CTSC_CASEWORKER_PERMISSIONS,
            DEFAULT_CTSC_PERMISSIONS,
            DEFAULT_CTSC_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_CTSC_TEAM_LEAD_PERMISSIONS,
            DEFAULT_INTERLOC_JUDGE_PERMISSIONS,
            DEFAULT_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_JUDGE_PERMISSIONS,
            DEFAULT_JUDICIARY_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_POST_HEARING_JUDGE_PERMISSIONS,
            DEFAULT_ALLOCATED_ADMIN_CASEWORKER_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_REGIONAL_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_HEARING_CENTER_ADMIN_PERMISSIONS,
            DEFAULT_HEARING_CENTER_TEAM_LEADER_PERMISSIONS,
            DEFAULT_ADMIN_CHALLENGED_ACCESS_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_1_PERMISSIONS,
            DEFAULT_TRIBUNAL_MEMBER_2_PERMISSIONS,
            DEFAULT_APPRAISER_1_PERMISSIONS,
            DEFAULT_APPRAISER_2_PERMISSIONS,
            DEFAULT_MEDICAL_PERMISSIONS,
            DEFAULT_FEE_PAID_MEDICAL_PERMISSIONS,
            DEFAULT_LEADERSHIP_JUDGE_PERMISSIONS,
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
