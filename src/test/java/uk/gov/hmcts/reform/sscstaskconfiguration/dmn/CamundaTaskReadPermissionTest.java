package uk.gov.hmcts.reform.sscstaskconfiguration.dmn;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTable.WA_TASK_PERMISSIONS_SSCS_BENEFIT;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.sscstaskconfiguration.DmnDecisionTableBaseUnitTest;

public class CamundaTaskReadPermissionTest extends DmnDecisionTableBaseUnitTest {

    private static final String[] ROLES = {
        "allocated-tribunal-caseworker",
        "tribunal-caseworker",
        "allocated-ctsc-caseworker",
        "ctsc",
        "ctsc-team-leader",
        "interloc-judge",
        "hearing-judge",
        "judge",
        "post-hearing-judge",
        "allocated-admin-caseworker",
        "regional-centre-admin",
        "regional-centre-team-leader",
        "hearing-centre-admin",
        "hearing-centre-team-leader",
        "tribunal-member-1",
        "tribunal-member-2",
        "tribunal-member-3",
        "appraiser-1",
        "appraiser-2",
        "medical",
        "fee-paid-medical",
        "leadership-judge",
        "challenged-access-legal-ops",
        "challenged-access-ctsc",
        "challenged-access-admin",
        "challenged-access-judiciary"
    };

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_PERMISSIONS_SSCS_BENEFIT;
    }

    @Test
    void checkRolesHasReadAccessToAllTasks() {
        getAllTaskIds().forEach(t -> assertTrue(checkReadOnlyAccess(t.replace("\"", ""))));
    }

    private boolean checkReadOnlyAccess(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));
        inputVariables.putValue("case", "someCaseData");
        boolean hasReadAccess = true;

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        for (String role : ROLES) {
            List<Map<String,Object>> rules = dmnDecisionTableResult.getResultList().stream()
                .filter(r -> role.equals(r.get("name"))).collect(Collectors.toList());
            if (rules.isEmpty()) {
                System.out.println(String.format("Task %s, no read access for role %s", taskType, role));
                hasReadAccess = false;
            } else if (rules.size() > 1) {
                System.out.println(String.format("Task %s, multiple entries for role %s", taskType, role));
                hasReadAccess = false;
            } else {
                String permissions = rules.get(0).get("value").toString();
                if (!permissions.contains("Read")) {
                    System.out.println(String.format("Task %s, no read access for role %s", taskType, role));
                    hasReadAccess = false;
                }
            }
        }

        return hasReadAccess;
    }
}
