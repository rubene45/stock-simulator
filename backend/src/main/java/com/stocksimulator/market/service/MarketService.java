package com.stocksimulator.market.service;

import com.stocksimulator.market.model.MarketQuote;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Locale;

@Service
public class MarketService {

    public Map<String, String> getStatus() {
        return Map.of(
                "status", "OK",
                "message", "Stock Simulator funcionando"
        );
    }

    public MarketQuote getQuote(String symbol) {
        return new MarketQuote(
                symbol.toUpperCase(Locale.ROOT),
                new BigDecimal("247.50")
        );
    }
}
