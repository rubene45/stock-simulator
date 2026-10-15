package com.stocksimulator.market.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, unique = true, length = 10)
    private String symbol;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 50)
    private String sector;

    @Column(length = 500)
    private String description;

    @Column(name = "current_price", nullable = false, precision = 19, scale = 4)
    private BigDecimal currentPrice;

    @Column(name = "total_shares", nullable = false)
    private long totalShares;

    @Column(name = "available_shares", nullable = false)
    private long availableShares;

    @Column(nullable = false)
    private boolean active;

    @Version
    @Column(nullable = false)
    private long version;

    protected Company() {
    }

    public UUID getId() { return id; }
    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public String getSector() { return sector; }
    public String getDescription() { return description; }
    public BigDecimal getCurrentPrice() { return currentPrice; }
    public long getTotalShares() { return totalShares; }
    public long getAvailableShares() { return availableShares; }
    public boolean isActive() { return active; }
}
