import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthSuccess } from 'src/app/core/models/AuthSuccess';
import { LoginRequest } from 'src/app/core/models/LoginRequest';
import { AuthService } from 'src/app/core/services/auth.service';
import { SessionService } from 'src/app/core/services/session.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  public onError:Boolean = false;
  public form:FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.min(6)]]
  });

  constructor(private authService:AuthService,
              private fb: FormBuilder,
              private router: Router,
              private route:ActivatedRoute,
              private sessionService:SessionService
              ){ }

  public submit(): void {
      const loginType = this.route.snapshot.params['type'];
      const loginRequest = this.form.value as LoginRequest;
      this.authService.login(loginRequest, loginType).subscribe({
        next: (success: AuthSuccess) => {
          console.log(`logged in : ${success.token}, ${success.firstName}, ${success.laststName}, ${success.id}, ${success.username}, ${success.password}, ${success.authorities[0].authority}`);
          this.sessionService.logIn(success);
          if(success.authorities[0].authority === "SUPPORT"){
            this.router.navigate(['/chat']);
          }else{
            this.router.navigate(['/chat']);
          }
          
        },
        error: error => this.onError = true
      });
    }
}
