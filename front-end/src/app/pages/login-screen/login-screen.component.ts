import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login-screen',
  templateUrl: './login-screen.component.html',
  styleUrl: './login-screen.component.css'
})
export class LoginScreenComponent {
  constructor(private toastr: ToastrService, private router: Router) {
  }

  logar(): void {
    console.log("logado");
    this.router.navigate(['/dashboard']);
    this.toastr.info("Logado!")
  }
}
