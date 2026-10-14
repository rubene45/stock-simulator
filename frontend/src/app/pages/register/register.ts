import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../auth.service';

@Component({ standalone: true, imports: [FormsModule, RouterLink], styleUrl: './register.scss', templateUrl: './register.html' })
export class RegisterPage {
  private readonly authService = inject(AuthService);
  protected username = '';
  protected email = '';
  protected password = '';
  protected confirmPassword = '';
  protected readonly error = signal('');
  protected readonly success = signal('');
  protected readonly loading = signal(false);

  protected submit(event: SubmitEvent): void {
    event.preventDefault(); this.error.set(''); this.success.set('');
    if (this.password !== this.confirmPassword) { this.error.set('Las contraseñas no coinciden.'); return; }
    this.loading.set(true);
    this.authService.register(this.username, this.email, this.password).subscribe({
      next: (user) => { this.success.set(`¡Cuenta creada, ${user.username}! Ya puedes iniciar sesión.`); this.loading.set(false); },
      error: (error: HttpErrorResponse) => {
        const message = error.error?.message;
        this.error.set(typeof message === 'string' ? message : error.status === 400 ? 'Revisa los datos: nombre (3–32 caracteres), email válido y contraseña (8–72 caracteres).' : 'No se pudo crear la cuenta. Comprueba que el backend esté iniciado.');
        this.loading.set(false);
      },
    });
  }
}
