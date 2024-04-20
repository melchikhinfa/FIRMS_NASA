package org.firms.client.jsonEntities.out.subscription;

public class GetStatusAPIKey {
    private int currentTransactions;
    private int transactionLimit;

    public int getCurrentTransactions() {
        return currentTransactions;
    }

    public void setCurrentTransactions(int currentTransactions) {
        this.currentTransactions = currentTransactions;
    }

    public int getTransactionLimit() {
        return transactionLimit;
    }

    public void setTransactionLimit(int transactionLimit) {
        this.transactionLimit = transactionLimit;
    }

    public GetStatusAPIKey() {
    }

    public GetStatusAPIKey(int currentTransactions, int transactionLimit) {
        this.currentTransactions = currentTransactions;
        this.transactionLimit = transactionLimit;
    }
}
