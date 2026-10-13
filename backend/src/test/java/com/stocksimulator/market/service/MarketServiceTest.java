package com.stocksimulator.market.service;

import com.stocksimulator.market.model.MarketQuote;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MarketServiceTest {

    @Test
    void shouldReturnMarketStatus() {
        MarketService marketService = new MarketService();

        Map<String, String> result = marketService.getStatus();

        assertEquals("OK", result.get("status"));
        assertEquals("Stock Simulator funcionando", result.get("message"));
    }

    @Test
    void shouldReturnSimulatedQuote() {
        MarketService marketService = new MarketService();

        MarketQuote result = marketService.getQuote("aapl");

        assertEquals("AAPL", result.getSymbol());
        assertEquals(new BigDecimal("247.50"), result.getPrice());
    }
}
