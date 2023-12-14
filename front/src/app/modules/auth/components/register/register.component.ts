import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterRequest } from 'src/app/core/models/RegisterRequest';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  public onError:Boolean = false;
  public form:FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    firstName: ['', [Validators.required, Validators.min(2), Validators.max(40)]],
    lastName: ['', [Validators.required, Validators.min(2), Validators.max(40)]],
    password: ['', [Validators.required, Validators.min(6)]]
  });

  constructor(private authService:AuthService,
              private fb: FormBuilder,
              private router: Router,
              ){
                this.form = this.fb.group({
                  email: ['', [Validators.required, Validators.email]],
                  firstName: ['', [Validators.required, Validators.min(2), Validators.max(40)]],
                  lastName: ['', [Validators.required, Validators.min(2), Validators.max(40)]],
                  password: ['', [Validators.required, Validators.min(6)]]
                });
              }

  public submit(): void {
      const registerRequest = this.form.value as RegisterRequest;
      this.authService.register(registerRequest).subscribe({
        next: (_: void) => {
          console.log("Successfull registration");
          this.router.navigate(['/auth/login', 'user'])
        },
        error: error => this.onError = true
      });
    }
  
}
