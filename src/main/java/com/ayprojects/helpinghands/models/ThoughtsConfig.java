package com.ayprojects.helpinghands.models;

public class ThoughtsConfig {
    private long maxDailyLimit;
    private ThoughtsFrequency eligibilityFrequency;
    private String note;
    private String strForSystemGeneratedThoughts;

    public String getStrForSystemGeneratedThoughts() {
        return strForSystemGeneratedThoughts;
    }

    public void setStrForSystemGeneratedThoughts(String strForSystemGeneratedThoughts) {
        this.strForSystemGeneratedThoughts = strForSystemGeneratedThoughts;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

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
