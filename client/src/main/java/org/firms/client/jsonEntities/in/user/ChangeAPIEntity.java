package org.firms.client.jsonEntities.in.user;

public class ChangeAPIEntity {
    private String apiKey;

    public ChangeAPIEntity(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public ChangeAPIEntity() {
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
