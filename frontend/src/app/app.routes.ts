import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component'; // Importar el componente ResetPassword
import { VideojuegosComponent } from './components/videojuegos/videojuegos.component';
import { PerfilComponent } from './pages/perfil/perfil.component'; // Cambié 'ProfileComponent' a 'PerfilComponent'

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent }, // Ruta para registro
  { path: 'forgot-password', component: ForgotPasswordComponent }, // Ruta para forgot-password
  { path: 'reset-password', component: ResetPasswordComponent }, // Ruta para reset-password
  { path: 'home', component: VideojuegosComponent }, // Ruta a videojuegos (home)
  { path: 'perfil', component: PerfilComponent }, // Ruta para perfil (nuevo)
];
