import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit{
  constructor(private authService: AuthService, private router:Router){}
  ngOnInit(): void {
    if(this.authService.isLoggedIn()){
      console.log("GOle")
      this.router.navigate(['/dashboard'])
    }
    console.log("OLA?")
  }

  logIn(){
    this.authService.signIn()
  }
}
