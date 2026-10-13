package com.stocksimulator.market.controller;

import com.stocksimulator.market.model.MarketQuote;
import com.stocksimulator.market.service.MarketService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/market")
public class MarketController {

    private final MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @GetMapping("/status")
    public Map<String, String> status() {
        return marketService.getStatus();
    }

    @GetMapping("/quote/{symbol}")
    public MarketQuote quote(@PathVariable String symbol) {
        return marketService.getQuote(symbol);
    }
}
