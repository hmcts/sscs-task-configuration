package uk.gov.hmcts.reform.sscstaskconfiguration.utils;

public interface EventLink {

    String SEND_TO_ADMIN = eventLink("Send to admin","sendToAdmin");
    String INTERLOC_REVIEW_STATE_AMEND = eventLink("Amend interloc review state","interlocReviewStateAmend");
    String DIRECTION_ISSUED = eventLink("Issue directions notice","directionIssued");
    String DECISION_ISSUED = eventLink("Issue interlocutory decision","decisionIssued");
    String ACTION_FURTHER_EVIDENCE = eventLink("Action Further Evidence","actionFurtherEvidence");
    String VALID_SEND_TO_INTERLOC = eventLink("Send to interloc", "validSendToInterloc");
    String INTERLOC_INFORMATION_RECEIVED = eventLink("Information received", "interlocInformationReceived");
    String TCW_REFER_TO_JUDGE = eventLink("Send to Judge","tcwReferToJudge");
    String INTERLOC_SEND_TO_TCW = eventLink("Send case to TCW", "interlocSendToTcw");
    String REQUEST_FOR_INFORMATION = eventLink("Request Information From Party", "requestForInformation");
    String HMCTS_RESPONSE_REVIEWED = eventLink("Response reviewed", "hmctsResponseReviewed");
    String REQUEST_TRANSLATION_FROM_WLU = eventLink("Request translation from WLU", "requestTranslationFromWLU");
    String UPDATE_LISTING_REQUIREMENTS = eventLink("Update Listing Requirements", "updateListingRequirements");
    String READY_TO_LIST = eventLink("Ready to list", "readyToList");
    String RESENT_CASE_TO_GAPS2 = eventLink("Resend case to GAPS 2","resendCaseToGAPS2");
    String AMEND_DUE_DATE = eventLink("Amend due date", "amendDueDate");
    String CREATE_BUNDLE = eventLink("Create Bundle","createBundle");
    String ACTION_POSTPONEMENT_REQUEST = eventLink("Action Postponement Request","actionPostponementRequest");
    String PROCESS_AUDIO_VIDEO = eventLink("Process audio/video evidence","processAudioVideo");
    String STRUCK_OUT = eventLink("Strike out case","struckOut");
    String SEND_TO_INTERLOC_LATE_SOR_APPLICATION = eventLink("Send to Interloc - Late Statement of Reasons Application", "validSendToInterloc");
    String ADMIN_ACTION_CORRECTION = eventLink("Admin - action correction","adminActionCorrection");
    String ADD_A_NOTE = eventLink("Add a note","addNote");
    String REVIEW_CONFIDENTIALITY_REQUEST = eventLink("Review confidentiality request","reviewConfidentialityRequest");
    String ISSUE_FINAL_DECISION = eventLink("Issue final decision","issueFinalDecision");
    String SEND_TO_JUDGE = eventLink("Send to Judge","tcwReferToJudge");
    String ABATE_CASE = eventLink("Abate case", "abateCase");
    String WRITE_FINAL_DECISION = eventLink("Write final decision", "writeFinalDecision");
    String REVIEW_PHE_REQUEST = eventLink("Review PHE request", "reviewPhmeRequest");

    String WRITE_ADJOURNMENT_NOTICE = eventLink("Write adjournment notice", "adjournCase");
    String ISSUE_ADJOURNMENT_NOTICE = eventLink("Issue adjournment notice", "issueAdjournmentNotice");
    String CONFIRM_PANEL_COMPOSITION = eventLink("Confirm Panel Composition", "confirmPanelComposition");
    String REVIEW_PH_APP = eventLink("Review Post Hearing App", "postHearingReview");
    String WRITE_SOR = eventLink("Write and Issue SOR", "sORWrite");

    static String eventLink(String description, String eventId) {
        return String.format("[%s](/case/SSCS/Benefit/${[CASE_REFERENCE]}/trigger/%s)", description, eventId);
    }

    static String caseLink(String description, String tab) {
        return String.format("[%s](/case/SSCS/Benefit/${[CASE_REFERENCE]}#%s)", description, tab);
    }
}
