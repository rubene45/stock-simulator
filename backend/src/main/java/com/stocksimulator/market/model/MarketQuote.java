package com.stocksimulator.market.model;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class MarketQuote {
    String symbol;
    BigDecimal price;
}
