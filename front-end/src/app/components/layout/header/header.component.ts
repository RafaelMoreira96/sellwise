import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  constructor(private toast: ToastrService, private router: Router) { }
  logout(): void {
    console.log('Logout here!');
    this.toast.info('Logout!');
    this.router.navigate(['/login']);
  }
}
