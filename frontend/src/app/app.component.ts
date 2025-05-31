import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `<router-outlet></router-outlet>`
})
export class AppComponent implements OnInit {
  ngOnInit(): void {
    if (typeof window !== 'undefined') {
      console.log('Token en AppComponent:', localStorage.getItem('token'));
    }
  }
}
