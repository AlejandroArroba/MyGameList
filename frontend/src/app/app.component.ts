import { Component, OnInit } from '@angular/core';

import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule],
  template: `<router-outlet></router-outlet>`
})
export class AppComponent implements OnInit {
  ngOnInit(): void {
    if (typeof window !== 'undefined') {
      console.log('Token en AppComponent:', localStorage.getItem('token'));
    }
  }
}

export default AppComponent;
