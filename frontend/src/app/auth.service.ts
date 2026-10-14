import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, switchMap } from 'rxjs';

export interface UserResponse {
  id: string;
  username: string;
  email: string;
  role: 'USER' | 'ADMIN';
  createdAt: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  register(username: string, email: string, password: string): Observable<UserResponse> {
    return this.http.get<{ token: string }>('/api/auth/csrf').pipe(
      switchMap(() => this.http.post<UserResponse>('/api/auth/register', { username, email, password })),
    );
  }
}
