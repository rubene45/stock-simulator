import { Component, inject, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { MarketQuote, MarketService } from '../../market.service';

@Component({ standalone: true, imports: [RouterLink], styleUrl: './home.scss', templateUrl: './home.html' })
export class HomePage {
  private readonly marketService = inject(MarketService);
  protected readonly symbol = signal('AAPL');
  protected readonly quote = signal<MarketQuote | null>(null);
  protected readonly loading = signal(false);
  protected readonly error = signal('');
  protected updateSymbol(event: Event): void { this.symbol.set((event.target as HTMLInputElement).value); }
  protected search(event: SubmitEvent): void {
    event.preventDefault();
    const symbol = this.symbol().trim().toUpperCase();
    if (!symbol) return;
    this.loading.set(true); this.error.set('');
    this.marketService.getQuote(symbol).subscribe({
      next: (quote) => { this.quote.set(quote); this.loading.set(false); },
      error: () => { this.error.set('No se pudo obtener la cotización. Comprueba que el backend esté iniciado.'); this.loading.set(false); },
    });
  }
}
