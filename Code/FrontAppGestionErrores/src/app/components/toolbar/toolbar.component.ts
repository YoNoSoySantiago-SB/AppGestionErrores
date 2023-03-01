import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserAuth } from 'src/app/model/UserAuth.interface';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})

export class ToolbarComponent implements OnInit{

  userLogged ?: UserAuth

  constructor(private router:Router, private authService:AuthService){}

  ngOnInit(): void {
    const userAuth:string|null = window.localStorage.getItem('userProfile')
    if(userAuth==null){
      this.router.navigate(['/'])
      return
    }
    console.log("OLA?")
    this.userLogged = JSON.parse(userAuth)
  }

  logout(){
    this.authService.signOut()
    this.router.navigate(['/'])
  }
}
