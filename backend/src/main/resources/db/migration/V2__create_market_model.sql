CREATE TABLE market_cycles (
    id UUID PRIMARY KEY,
    cycle_number BIGINT NOT NULL,
    status VARCHAR(16) NOT NULL,
    started_at TIMESTAMPTZ NOT NULL,
    ended_at TIMESTAMPTZ,
    CONSTRAINT uq_market_cycles_cycle_number UNIQUE (cycle_number),
    CONSTRAINT ck_market_cycles_status CHECK (status IN ('ACTIVE', 'COMPLETED'))
);

CREATE TABLE market_state (
    id SMALLINT PRIMARY KEY,
    status VARCHAR(16) NOT NULL,
    current_cycle_id UUID,
    cycle_started_at TIMESTAMPTZ,
    next_cycle_at TIMESTAMPTZ,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT ck_market_state_singleton CHECK (id = 1),
    CONSTRAINT ck_market_state_status CHECK (status IN ('OPEN', 'PAUSED', 'CLOSED')),
    CONSTRAINT fk_market_state_current_cycle
        FOREIGN KEY (current_cycle_id) REFERENCES market_cycles(id)
);

CREATE TABLE companies (
    id UUID PRIMARY KEY,
    symbol VARCHAR(10) NOT NULL,
    name VARCHAR(100) NOT NULL,
    sector VARCHAR(50),
    description VARCHAR(500),
    current_price NUMERIC(19, 4) NOT NULL,
    total_shares BIGINT NOT NULL,
    available_shares BIGINT NOT NULL,
    active BOOLEAN NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT uq_companies_symbol UNIQUE (symbol),
    CONSTRAINT ck_companies_current_price CHECK (current_price >= 0),
    CONSTRAINT ck_companies_total_shares CHECK (total_shares >= 0),
    CONSTRAINT ck_companies_available_shares CHECK (available_shares >= 0 AND available_shares <= total_shares)
);

CREATE TABLE portfolios (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    cash_balance NUMERIC(19, 2) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT uq_portfolios_user_id UNIQUE (user_id),
    CONSTRAINT ck_portfolios_cash_balance CHECK (cash_balance >= 0),
    CONSTRAINT fk_portfolios_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE holdings (
    id UUID PRIMARY KEY,
    portfolio_id UUID NOT NULL,
    company_id UUID NOT NULL,
    quantity BIGINT NOT NULL,
    average_price NUMERIC(19, 4) NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT uq_holdings_portfolio_company UNIQUE (portfolio_id, company_id),
    CONSTRAINT ck_holdings_quantity CHECK (quantity >= 0),
    CONSTRAINT ck_holdings_average_price CHECK (average_price >= 0),
    CONSTRAINT fk_holdings_portfolio FOREIGN KEY (portfolio_id) REFERENCES portfolios(id),
    CONSTRAINT fk_holdings_company FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE TABLE market_transactions (
    id UUID PRIMARY KEY,
    portfolio_id UUID NOT NULL,
    company_id UUID NOT NULL,
    cycle_id UUID,
    type VARCHAR(8) NOT NULL,
    quantity BIGINT NOT NULL,
    unit_price NUMERIC(19, 4) NOT NULL,
    total_amount NUMERIC(19, 2) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT ck_market_transactions_type CHECK (type IN ('BUY', 'SELL')),
    CONSTRAINT ck_market_transactions_quantity CHECK (quantity > 0),
    CONSTRAINT ck_market_transactions_unit_price CHECK (unit_price >= 0),
    CONSTRAINT ck_market_transactions_total_amount CHECK (total_amount >= 0),
    CONSTRAINT fk_market_transactions_portfolio FOREIGN KEY (portfolio_id) REFERENCES portfolios(id),
    CONSTRAINT fk_market_transactions_company FOREIGN KEY (company_id) REFERENCES companies(id),
    CONSTRAINT fk_market_transactions_cycle FOREIGN KEY (cycle_id) REFERENCES market_cycles(id)
);

CREATE TABLE stock_price_history (
    id UUID PRIMARY KEY,
    cycle_id UUID NOT NULL,
    company_id UUID NOT NULL,
    opening_price NUMERIC(19, 4) NOT NULL,
    closing_price NUMERIC(19, 4) NOT NULL,
    variation_percent NUMERIC(9, 4) NOT NULL,
    CONSTRAINT uq_stock_price_history_cycle_company UNIQUE (cycle_id, company_id),
    CONSTRAINT ck_stock_price_history_opening_price CHECK (opening_price >= 0),
    CONSTRAINT ck_stock_price_history_closing_price CHECK (closing_price >= 0),
    CONSTRAINT fk_stock_price_history_cycle FOREIGN KEY (cycle_id) REFERENCES market_cycles(id),
    CONSTRAINT fk_stock_price_history_company FOREIGN KEY (company_id) REFERENCES companies(id)
);

CREATE INDEX idx_holdings_portfolio_id ON holdings(portfolio_id);
CREATE INDEX idx_market_transactions_portfolio_created_at ON market_transactions(portfolio_id, created_at DESC);
CREATE INDEX idx_market_transactions_company_created_at ON market_transactions(company_id, created_at DESC);
CREATE INDEX idx_stock_price_history_company_cycle ON stock_price_history(company_id, cycle_id);
