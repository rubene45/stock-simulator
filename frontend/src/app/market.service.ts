import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment';

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
      `${environment.apiUrl}/api/market/quote/${encodeURIComponent(symbol)}`,
    );
  }
}
