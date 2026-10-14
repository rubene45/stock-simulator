import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', loadComponent: () => import('./pages/home/home').then((m) => m.HomePage) },
  { path: 'registro', loadComponent: () => import('./pages/register/register').then((m) => m.RegisterPage) },
  { path: '**', redirectTo: '' },
];
