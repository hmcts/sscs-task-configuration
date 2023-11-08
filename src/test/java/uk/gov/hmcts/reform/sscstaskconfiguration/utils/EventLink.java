package uk.gov.hmcts.reform.sscstaskconfiguration.utils;

public interface EventLink {

    static final String SEND_TO_ADMIN = eventLink("Send to admin","sendToAdmin");
    static final String INTERLOC_REVIEW_STATE_AMEND = eventLink("Amend interloc review state","interlocReviewStateAmend");
    static final String DIRECTION_ISSUED = eventLink("Issue directions notice","directionIssued");
    static final String DECISION_ISSUED = eventLink("Issue interlocutory decision","decisionIssued");
    static final String ACTION_FURTHER_EVIDENCE = eventLink("Action Further Evidence","actionFurtherEvidence");
    static final String VALID_SEND_TO_INTERLOC = eventLink("Send to interloc", "validSendToInterloc");
    static final String INTERLOC_INFORMATION_RECEIVED = eventLink("Information received", "interlocInformationReceived");
    static final String TCW_REFER_TO_JUDGE = eventLink("Send to Judge","tcwReferToJudge");
    static final String INTERLOC_SEND_TO_TCW = eventLink("Send case to TCW", "interlocSendToTcw");
    static final String REQUEST_FOR_INFORMATION = eventLink("Request Information From Party", "requestForInformation");
    static final String HMCTS_RESPNSE_REVIEWED = eventLink("Response reviewed", "hmctsResponseReviewed");
    static final String REQUEST_TRANSLATION_FROM_WLU = eventLink("Request translation from WLU", "requestTranslationFromWLU");
    static final String UPDATE_LISTING_REQUIREMENTS = eventLink("Update Listing Requirements", "updateListingRequirements");
    static final String READY_TO_LIST = eventLink("Ready to list", "readyToList");
    static final String RESENT_CASE_TO_GAPS2 = eventLink("Resend case to GAPS 2","resendCaseToGAPS2");
    static final String AMEND_DUE_DATE = eventLink("Amend due date", "amendDueDate");
    static final String CREATE_BUNDLE = eventLink("Create Bundle","createBundle");
    static final String ACTION_POSTPONEMENT_REQUEST = eventLink("Action Postponement Request","actionPostponementRequest");
    static final String PROCESS_AUDIO_VIDEO = eventLink("Process audio/video evidence","processAudioVideo");
    static final String STRUCK_OUT = eventLink("Strike out case","struckOut");
    static final String SEND_TO_INTERLOC_LATE_SOR_APPLICATION = eventLink("Send to Interloc - Late Statement of Reasons Application", "validSendToInterloc");

    static String eventLink(String description, String eventId) {
        return String.format("[%s](/case/SSCS/Benefit/${[CASE_REFERENCE]}/trigger/%s)", description, eventId);
    }
}
