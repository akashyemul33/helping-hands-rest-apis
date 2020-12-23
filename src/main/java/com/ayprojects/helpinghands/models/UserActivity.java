package com.ayprojects.helpinghands.models;

import java.util.List;
import java.util.Set;

public class UserActivity {
    private List<String> recentSearchKeywordsInMorning;//1AM to 12PM
    private List<String> recentSearchKeywordsInAfternoon;//12PM to 6 PM
    private List<String> recentSearchKeywordsInEvening;//6PM to 12 AM
    
    private List<String> interestedPlaces;
    private List<String> interestedPosts;
    private List<String> interestedRequirements;

    public List<String> getRecentSearchKeywordsInMorning() {
        return recentSearchKeywordsInMorning;
    }

    public void setRecentSearchKeywordsInMorning(List<String> recentSearchKeywordsInMorning) {
        this.recentSearchKeywordsInMorning = recentSearchKeywordsInMorning;
    }

    public List<String> getRecentSearchKeywordsInAfternoon() {
        return recentSearchKeywordsInAfternoon;
    }

    public void setRecentSearchKeywordsInAfternoon(List<String> recentSearchKeywordsInAfternoon) {
        this.recentSearchKeywordsInAfternoon = recentSearchKeywordsInAfternoon;
    }

    public List<String> getRecentSearchKeywordsInEvening() {
        return recentSearchKeywordsInEvening;
    }

    public void setRecentSearchKeywordsInEvening(List<String> recentSearchKeywordsInEvening) {
        this.recentSearchKeywordsInEvening = recentSearchKeywordsInEvening;
    }

    public List<String> getInterestedPlaces() {
        return interestedPlaces;
    }

    public void setInterestedPlaces(List<String> interestedPlaces) {
        this.interestedPlaces = interestedPlaces;
    }

    public List<String> getInterestedPosts() {
        return interestedPosts;
    }

    public void setInterestedPosts(List<String> interestedPosts) {
        this.interestedPosts = interestedPosts;
    }

    public List<String> getInterestedRequirements() {
        return interestedRequirements;
    }

    public void setInterestedRequirements(List<String> interestedRequirements) {
        this.interestedRequirements = interestedRequirements;
    }

    public UserActivity(List<String> recentSearchKeywordsInMorning, List<String> recentSearchKeywordsInAfternoon, List<String> recentSearchKeywordsInEvening, List<String> interestedPlaces, List<String> interestedPosts, List<String> interestedRequirements) {
        this.recentSearchKeywordsInMorning = recentSearchKeywordsInMorning;
        this.recentSearchKeywordsInAfternoon = recentSearchKeywordsInAfternoon;
        this.recentSearchKeywordsInEvening = recentSearchKeywordsInEvening;
        this.interestedPlaces = interestedPlaces;
        this.interestedPosts = interestedPosts;
        this.interestedRequirements = interestedRequirements;
    }
}
