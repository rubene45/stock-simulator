package com.stocksimulator.market.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.time.Instant;

@Entity
@Table(name = "market_state")
public class MarketState {

    @Id
    @Column(nullable = false, updatable = false)
    private short id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private MarketStatus status;

    @OneToOne
    @JoinColumn(name = "current_cycle_id")
    private MarketCycle currentCycle;

    @Column(name = "cycle_started_at")
    private Instant cycleStartedAt;

    @Column(name = "next_cycle_at")
    private Instant nextCycleAt;

    @Version
    @Column(nullable = false)
    private long version;

    protected MarketState() {
    }

    public short getId() { return id; }
    public MarketStatus getStatus() { return status; }
    public MarketCycle getCurrentCycle() { return currentCycle; }
    public Instant getCycleStartedAt() { return cycleStartedAt; }
    public Instant getNextCycleAt() { return nextCycleAt; }
}
