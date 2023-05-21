package mayton.adf.entities;

public class AdfPolicy {

    private String timeout;
    private int retry;
    private int retryIntervalInSeconds;
    private boolean secureOutput;
    private boolean secureInput;

    public AdfPolicy() {

    }

    public AdfPolicy(String timeout, int retry, int retryIntervalInSeconds, boolean secureOutput, boolean secureInput) {
        this.timeout = timeout;
        this.retry = retry;
        this.retryIntervalInSeconds = retryIntervalInSeconds;
        this.secureOutput = secureOutput;
        this.secureInput = secureInput;
    }
}
