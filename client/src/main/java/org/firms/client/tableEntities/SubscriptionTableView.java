package org.firms.client.tableEntities;

import java.util.UUID;

/**
 * Отображение таблицы подписок
 */
public class SubscriptionTableView {

    private UUID id;
    private String shortName;
    private String fullName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public SubscriptionTableView() {
    }

    public SubscriptionTableView(UUID id, String shortName, String fullName) {
        this.id = id;
        this.shortName = shortName;
        this.fullName = fullName;
    }


}
