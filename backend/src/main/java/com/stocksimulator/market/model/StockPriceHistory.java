package com.stocksimulator.market.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "stock_price_history")
public class StockPriceHistory {

    @Id
    @Column(nullable = false, updatable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cycle_id", nullable = false)
    private MarketCycle cycle;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(name = "opening_price", nullable = false, precision = 19, scale = 4)
    private BigDecimal openingPrice;

    @Column(name = "closing_price", nullable = false, precision = 19, scale = 4)
    private BigDecimal closingPrice;

    @Column(name = "variation_percent", nullable = false, precision = 9, scale = 4)
    private BigDecimal variationPercent;

    protected StockPriceHistory() {
    }

    public UUID getId() { return id; }
    public MarketCycle getCycle() { return cycle; }
    public Company getCompany() { return company; }
    public BigDecimal getOpeningPrice() { return openingPrice; }
    public BigDecimal getClosingPrice() { return closingPrice; }
    public BigDecimal getVariationPercent() { return variationPercent; }
}
