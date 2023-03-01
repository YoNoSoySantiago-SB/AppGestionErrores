import { AuthService } from './services/auth.service'
import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'FrontAppGestionErrores';

  constructor(private readonly auth: AuthService){}
}
