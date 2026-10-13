import { Component, inject, signal } from '@angular/core';
import { MarketQuote, MarketService } from './market.service';

@Component({
  selector: 'app-root',
  styleUrl: './app.scss',
  templateUrl: './app.html',
})
export class App {
  private readonly marketService = inject(MarketService);

  protected readonly title = signal('Stock Simulator');
  protected readonly symbol = signal('AAPL');
  protected readonly quote = signal<MarketQuote | null>(null);
  protected readonly loading = signal(false);
  protected readonly error = signal('');

  protected updateSymbol(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.symbol.set(input.value);
  }

  protected search(event: SubmitEvent): void {
    event.preventDefault();

    const normalizedSymbol = this.symbol().trim().toUpperCase();
    if (!normalizedSymbol) {
      return;
    }

    this.loading.set(true);
    this.quote.set(null);
    this.error.set('');

    this.marketService.getQuote(normalizedSymbol).subscribe({
      next: (quote) => {
        this.quote.set(quote);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('No se pudo obtener la cotización.');
        this.loading.set(false);
      },
    });
  }
}
