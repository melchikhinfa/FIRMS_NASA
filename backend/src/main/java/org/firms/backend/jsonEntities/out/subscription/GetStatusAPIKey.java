package org.firms.backend.jsonEntities.out.subscription;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO по статусу ключа API
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetStatusAPIKey {
    private int currentTransactions;
    private int transactionLimit;
}
