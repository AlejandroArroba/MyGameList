import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';
import { VideojuegosComponent } from './pages/videojuegos/videojuegos.component';
import { PerfilComponent } from './pages/perfil/perfil.component';
import {DetallesVideojuegoComponent} from './pages/detalles-videojuego/detalles-videojuego.component';


export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'reset-password', component: ResetPasswordComponent },
  { path: 'home', component: VideojuegosComponent },
  { path: 'perfil', component: PerfilComponent },
  { path: 'juegos/:id', component: DetallesVideojuegoComponent }
];

