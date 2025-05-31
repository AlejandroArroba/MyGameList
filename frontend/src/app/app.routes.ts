import { Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { VideojuegosComponent } from './components/videojuegos/videojuegos.component';
import {PerfilComponent} from './pages/perfil/perfil.component';

export const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: VideojuegosComponent },
  { path: 'perfil', component: PerfilComponent }, // 👈 MUEVE ESTA LÍNEA ARRIBA
  { path: '**', redirectTo: '' } // 👈 SIEMPRE LA ÚLTIMA
];
