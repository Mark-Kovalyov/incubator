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
import com.azure.resourcemanager.network.models.Networks;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static mayton.azure.Common.SUBSCRIPTION_ID;
import static mayton.azure.Common.TENANT_ID;


public final class Main {

    String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1LSTNROW5OUjdiUm9meG1lWm9YcWJIWkdldyIsImtpZCI6Ii1LSTNROW5OUjdiUm9meG1lWm9YcWJIWkdldyJ9.eyJhdWQiOiJodHRwczovL21hbmFnZW1lbnQuY29yZS53aW5kb3dzLm5ldC8iLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC9iNDFiNzJkMC00ZTlmLTRjMjYtOGE2OS1mOTQ5ZjM2N2M5MWQvIiwiaWF0IjoxNjkxNDI0Mzg2LCJuYmYiOjE2OTE0MjQzODYsImV4cCI6MTY5MTQyOTI4NCwiYWNyIjoiMSIsImFpbyI6IkFZUUFlLzhVQUFBQS9tVjh4S3VwWFNHUWlOQU9pS3RtN3R5ODJrMWtqVzFOc0dZdGRDdDc3VVl3a2RVdVZxZ1Z4b1prZUxkVDN3a1JVeTB3djBVODdhbFpvbUpoazhYcHd4NDhsRHNBZjA0ajZRdnhGUlg2YmFOUDAxUUdVUm9QZ0Rkd2hnQ2F1eWl1MEZDUkJPMXllY0Myb0Q1NlJ6TDJITjI5bXRRY2xlUDdNZzUrYitFRks4RT0iLCJhbXIiOlsicHdkIiwicnNhIiwibWZhIl0sImFwcGlkIjoiMDRiMDc3OTUtOGRkYi00NjFhLWJiZWUtMDJmOWUxYmY3YjQ2IiwiYXBwaWRhY3IiOiIwIiwiZGV2aWNlaWQiOiI2NDUwZGI2Yi1hY2ZhLTRjYmQtYmZlZC1hYWZhMzliZDc2YmUiLCJmYW1pbHlfbmFtZSI6IktvdmFsZXYiLCJnaXZlbl9uYW1lIjoiTWFyayIsImdyb3VwcyI6WyI1NTQ1YzMwMi03ZmZkLTQ0MTgtOTcyMS0xZjhhMzk4NTJlYWUiLCJjNGU5NDIwNC02ZjQ5LTRlNWYtYjNjOS0wOTQxZmFlZmYwOTciLCI4NGVlYjQwNC0yNGNjLTRhZGMtODE5Mi02NjM4OWYwY2JmMmIiLCIwNmE1OWQwNi1mZDY3LTRhZGMtOTY1MC02Zjk4MjI1OGEzYWIiLCI5MDQ0YTYwZC1mYTU1LTQxMDYtYjQ4Zi03ZTEzZDJhYzM3YmIiLCJhYWJmZDEwZS1kN2JkLTRmOGUtYTg3Ny0wMzQ2NTQxYzM3YmEiLCIyMmZkNDkxNS1mYzQ1LTRjZWUtODU0Ni1iMWUwYjcxZDMyZWEiLCJjZTg3NTUxNS05ZTg1LTRhODgtYmM0NS03Njg0NTM0Yjc0YzEiLCI5M2ZmNTUxNS00NGMyLTQ5ODUtYmUzZC0xYmQxZWJmZjM4NjkiLCI1YWNkMmUxNi04NDk1LTQ4NTctYTQ5Yy00ZDJjNjVkZTM4MmUiLCJmMzMwZmIyOS02MTI2LTQyNmYtYjE2Zi02NGM3N2UwZDI0ZGEiLCJhMTY0ZjUyZS00M2M1LTQwNTctYjczMS1iYmJiNGY4YjQyNzEiLCJhNDdiYjkyZi04Mzg0LTQxNjUtODU4ZS1iN2M1Y2Y5MmEzZmQiLCI2OGNiY2YyZi0wMTg0LTQxOTEtYmQ1Mi1hYzk0ZDAxZGVlNTAiLCI3YWZhNDIzNi04Y2I5LTQ0NWItYjc3YS1mMjhmNjY3NDBlNjEiLCI2OTNlNjUzNy05MDI5LTRkZDctOTY4Ny1jNmNmOTRiZDhlMzQiLCJmZGEzNTczYy05ZmI5LTRlMDgtOGM0OC1iNjI4MDVkYzcwMzciLCJlZjEyN2I0MC1hYmUxLTRmYTUtOTA5Yi1mMDczOGUzZWJjMzIiLCIwN2U2Mzg1MC03OGY1LTRjMzQtYWZiNC02ZDdlOGNkNjIzMDUiLCI4ZmYyZDI1My0xYWJhLTRlMjQtOTRiYi1hNjQ0ZWJmZDc4M2EiLCI2ZWRiNjg1ZC1iNTE0LTQ2ZWUtYWMwZC1lMTJiYTMzOGRiNDMiLCIyYmM2M2Q1Zi1lOGRjLTQyNjctYjk1Yy1lOTI5MzhmMTFmNTUiLCI3MTk2NjI2OS02ZWE0LTRkNWItYjQ1MC0wNjFiMzJmYWQ2MDciLCJlZTViMGI2ZC02ZWNhLTQzNzItYjQ4NC00YzY0MTBjZTUzMTciLCIyMjE5ZWY2Zi1iMzhmLTRiZTktYjI5My1mYTc1ZDQ5MzUxMDQiLCI2ZTQzOGY3Yi0wYmQyLTRkMWMtYjg4ZS00MmE5OTI5YzAzYzkiLCI5MGU4MmM4Mi1kYzM3LTQ4Y2QtYTk1ZC0wMzc2Mzc0MjM1ZGYiLCIwZjViNzQ4Mi1mNmFkLTRhYTMtYTYxMi0yMzhlNTBmMjlhMWYiLCJjMTYxNTE4NC01ODgyLTQxNmYtYjk2MC1iMDFlMjhlZjgxMWYiLCIzMGIzNmM4NS0yNGM4LTQ5ZDctODI4Yy1iODI5ZmU3ZTFiOTYiLCIyNTNkOTk5MC1jYzZlLTRjOWQtYTE3ZS0zMmE4NjBjNDVmNzQiLCI1OTBkZTg5MC0wNmRkLTQyYzMtYTU0NS02ZTk1N2U0ZjNlY2UiLCIyZWIyMjI5NC03MDA1LTQxOGItYjVhMy05NjRmOTU4NzcwNGIiLCIxYTA3ZDg5YS1iZjFjLTQyMGUtYjRjNi1jYWMwNzQ4MzNjM2EiLCIyMGQ4MWFhMy05ZDA1LTQ2MWItOWYyNS00MDY2NDRjZWM0MjgiLCI5NzNiZWJhNS00MGFjLTQ5YzQtYTBjNS0wMmYzZjMyYjY1ODMiLCI4ZjAwN2NhOS1kMmM1LTQwOGEtYjUyNC1jODU1ZTM5NTVhNzMiLCIwZDU1YzJhOS0wOGU1LTQ5MmEtOWM3My01N2IzNDgwYzk2ZWUiLCIwNjE2MTlhYi03NzJlLTRiODYtYjVmNi1iOTQwZDc0NWI1ZGIiLCIyZmQ3MWNhZC1lYjljLTRiYWItODQwMC02Njk4MTAzZjQ5MjUiLCI1MGUwNzNhZC02MmNiLTRhMzctYmY5YS0xYzhkZTE5NzQxNGMiLCIyZTY4MTRhZi04ZTkyLTQ5ZGEtYWI2NC03NWRlNzk5ZTYxOWUiLCIwZWFlNzZiNy04MzhkLTRlMTAtOTE5Zi0xNWQ3ZTNmNDhjYTkiLCJmOGQ4YjhiOC02NGIzLTQ3NzEtYWUyZi05OGMyMjVhNGI1MTYiLCIxZGU0ZDJiOC1mYzMwLTQzMDItODAyMy04ODcyNDMwYzk4OGIiLCI1ZmU0MjViYy00Y2ZjLTQ0YWUtOWQxYS1kMGRlNmRlODI3NjQiLCI4MTM4YmJiZS1kYTlmLTRkMmYtYjA4MS1mMjdmMzUzNTE2N2QiLCJkMzA3ZjJjOS1iZTIwLTQ0YWYtOGZiMC1hYTdjMTI3MmFlOGQiLCJiMTJiMGRkMS0wYTVjLTRlMTctYTdkMS02Y2I3OWU3NzhkMzYiLCJjNjVhMzNkYy1kMWY2LTQ0ZTEtYjYwOC03ZWRhOTM1MDdlZGYiLCJjNjI4YzhkZS1mOTI5LTQ5Y2YtYTdmMi05NDVmYTQ1ZTI4ODAiLCJjMWNhODllNS03YjIzLTRjYzktOGY2ZS1jMWEyMWIyNTU3NDUiLCIxOThjNGJlYy04NTYwLTQ2YTQtODgxYy0wMmIxYWZjZTJmOTkiXSwiaXBhZGRyIjoiMTc4LjEzMy4xNTMuMjM5IiwibmFtZSI6Ik1hcmsgS292YWxldiIsIm9pZCI6Ijg3NjhlZWJhLTZmNWYtNDFjZi04Y2IxLWZlOGVjNTM3OTNiMCIsIm9ucHJlbV9zaWQiOiJTLTEtNS0yMS0xMjkyNDI4MDkzLTExMzAwNzcxNC0xMDYwMjg0Mjk4LTQzODYxNyIsInB1aWQiOiIxMDAzM0ZGRkFGODQ4OTNCIiwicmgiOiIwLkFRa0EwSElidEo5T0preUthZmxKODJmSkhVWklmM2tBdXRkUHVrUGF3ZmoyTUJNSkFPWS4iLCJzY3AiOiJ1c2VyX2ltcGVyc29uYXRpb24iLCJzdWIiOiJWeFhmcUt0OHVmNEN0RjlXWE50cjNUUE1WZVNsc3htRUlXaGRoYWExc0RVIiwidGlkIjoiYjQxYjcyZDAtNGU5Zi00YzI2LThhNjktZjk0OWYzNjdjOTFkIiwidW5pcXVlX25hbWUiOiJNYXJrX0tvdmFsZXZAZXBhbS5jb20iLCJ1cG4iOiJNYXJrX0tvdmFsZXZAZXBhbS5jb20iLCJ1dGkiOiJxa1RYZXh0Z0trZTJhN2VrN084VUFBIiwidmVyIjoiMS4wIiwid2lkcyI6WyJiNzlmYmY0ZC0zZWY5LTQ2ODktODE0My03NmIxOTRlODU1MDkiXSwieG1zX2NjIjpbIkNQMSJdLCJ4bXNfdGNkdCI6MTQwODc2ODAzN30.ngMyWbNwjMPmR-pziHg9TYEuLCTdEOK2cQfK-bJmTLk4_sCur_ch2YEXLfTLdGQog2MrOaOBHh4C5TmqrrGboi1kt5gTbweaBDF1_qf0AzCNJhZ0oqHyKKAW_N8VTWcw8VR4_As4hcDdSBfV24sYKl1e0S7J9kFzbpvvWxTpjECX8E4kvEKPzWxSC5_Ok-XoG1fJSvBqJgzSogMxECpWwngd1tyPivTZis1aBl8O8XWD5kYv525ycFBYDlwosJZCfvdebL7p9cIH9Mdn3bF2KN0_Cdsws0Dg0sLTtMOIPjIzfieacBRopPaUK1uz6IK6TYY-fVBE_Av3_7cdkwlBOg";

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
                "rocs-linehaul-curated",
                "rocs-linehaul-deadletter",
                "rocs-linehaul-deadletter-curated",
                "rocs-linehaul-raw",
                "rocs-linehaul-standardized"};

        /*for(String eventHub : Arrays.asList("")) {
            createOrUpdateAnAlertRule(azureResourceManager, "", "", "", "", "", "", eventHub);
        }*/

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

    @SuppressWarnings("unchecked")
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