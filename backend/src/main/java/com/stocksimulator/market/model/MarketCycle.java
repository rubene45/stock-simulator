package com.stocksimulator.market.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "market_cycles")
public class MarketCycle {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(name = "cycle_number", nullable = false, unique = true)
    private long cycleNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private MarketCycleStatus status;

    @Column(name = "started_at", nullable = false, updatable = false)
    private Instant startedAt;

    @Column(name = "ended_at")
    private Instant endedAt;

    protected MarketCycle() {
    }

    public UUID getId() { return id; }
    public long getCycleNumber() { return cycleNumber; }
    public MarketCycleStatus getStatus() { return status; }
    public Instant getStartedAt() { return startedAt; }
    public Instant getEndedAt() { return endedAt; }
}
