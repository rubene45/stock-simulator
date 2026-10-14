import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable, switchMap } from 'rxjs';
import { environment } from '../environments/environment';

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
    return this.http.get<{ token: string }>(`${environment.apiUrl}/api/auth/csrf`, { withCredentials: true }).pipe(
      switchMap(({ token }) => this.http.post<UserResponse>(
        `${environment.apiUrl}/api/auth/register`,
        { username, email, password },
        {
          withCredentials: true,
          headers: new HttpHeaders({ 'X-XSRF-TOKEN': token }),
        },
      )),
    );
  }
}
