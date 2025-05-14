import { Component, OnInit } from '@angular/core';
import { JuegoService, Juego } from './services/juego.service';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

  juegos: Juego[] = [];

  constructor(private juegoService: JuegoService) {}

  ngOnInit(): void {
    this.juegoService.obtenerJuegos().subscribe(data => {
      this.juegos = data;
    });
  }
}
