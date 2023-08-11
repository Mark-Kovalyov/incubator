package mayton.azure;

import com.azure.core.credential.TokenCredential;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.core.util.Context;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.resourcemanager.AzureResourceManager;
import com.azure.resourcemanager.monitor.fluent.models.AlertRuleResourceInner;
import com.azure.resourcemanager.monitor.models.ConditionOperator;
import com.azure.resourcemanager.monitor.models.RuleMetricDataSource;
import com.azure.resourcemanager.monitor.models.ThresholdRuleCondition;
import com.azure.resourcemanager.monitor.models.TimeAggregationOperator;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class Main {

    public static final String TENANT_ID = "";
    public static final String SUBSCRIPTION_ID = "";

    public static void main(String[] args) {

        AzureProfile profile = new AzureProfile(
                TENANT_ID,
                SUBSCRIPTION_ID,
                AzureEnvironment.AZURE);

        TokenCredential credential = new DefaultAzureCredentialBuilder()
                .build();

        AzureResourceManager azureResourceManager = AzureResourceManager
                .authenticate(credential, profile)
                .withDefaultSubscription();


        String namespace = "";

        String[] evenHubNs1 = {
                "mtn-hub1"};

    }

    public static void createOrUpdateAnAlertRule(AzureResourceManager azureResourceManager,
                                                 String subscription, String rgName, String ruleName,
                                                 String desc, String propName, String location,
                                                 String ruleMetricName) {
        azureResourceManager
                .diagnosticSettings()
                .manager()
                .serviceClient()
                .getAlertRules()
                .createOrUpdateWithResponse(
                        rgName,
                        ruleName,
                        new AlertRuleResourceInner()
                                .withLocation(location)
                                .withTags(mapOf())
                                .withNamePropertiesName(propName)
                                .withDescription(desc)
                                .withIsEnabled(true)
                                .withCondition(
                                        new ThresholdRuleCondition()
                                                .withDataSource(
                                                        new RuleMetricDataSource()
                                                                .withResourceUri("/subscriptions/" + subscription + "/resourceGroups/" + rgName + "/providers/Microsoft.Web/sites/" + ruleMetricName)
                                                                .withMetricName("Requests"))
                                                .withOperator(ConditionOperator.GREATER_THAN)
                                                .withThreshold(3.0)
                                                .withWindowSize(Duration.parse("PT5M"))
                                                .withTimeAggregation(TimeAggregationOperator.TOTAL))
                                .withActions(Arrays.asList()),
                        Context.NONE);
    }

    private static <T> Map<String, T> mapOf(Object... inputs) {
        Map<String, T> map = new HashMap<>();
        for (int i = 0; i < inputs.length; i += 2) {
            String key = (String) inputs[i];
            T value = (T) inputs[i + 1];
            map.put(key, value);
        }
        return map;
    }
}