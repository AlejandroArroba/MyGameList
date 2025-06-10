import { Component, OnInit } from '@angular/core';
import {Router, RouterModule} from '@angular/router';


@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {
  nombreUsuario: string | null = '';

  ngOnInit(): void {
    if (typeof window !== 'undefined') {
      this.nombreUsuario = localStorage.getItem('nombreUsuario');
    }
  }

  logout(): void {
    if (confirm('¿Seguro que quieres cerrar sesión?')) {
      localStorage.removeItem('token');
      localStorage.removeItem('nombreUsuario');
      window.location.href = '/';
    }
  }
}
