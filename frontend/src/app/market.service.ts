import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface MarketQuote {
  symbol: string;
  price: number;
}

@Injectable({
  providedIn: 'root',
})
export class MarketService {
  private readonly http = inject(HttpClient);

  getQuote(symbol: string): Observable<MarketQuote> {
    return this.http.get<MarketQuote>(
      `/api/market/quote/${encodeURIComponent(symbol)}`,
    );
  }
}
