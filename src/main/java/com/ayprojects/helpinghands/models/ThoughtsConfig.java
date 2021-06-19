package com.ayprojects.helpinghands.models;

public class ThoughtsConfig {
    private long maxDailyLimit;
    private ThoughtsFrequency eligibilityFrequency;

    public long getMaxDailyLimit() {
        return maxDailyLimit;
    }

    public void setMaxDailyLimit(long maxDailyLimit) {
        this.maxDailyLimit = maxDailyLimit;
    }

    public ThoughtsFrequency getEligibilityFrequency() {
        return eligibilityFrequency;
    }

    public void setEligibilityFrequency(ThoughtsFrequency eligibilityFrequency) {
        this.eligibilityFrequency = eligibilityFrequency;
    }
}
