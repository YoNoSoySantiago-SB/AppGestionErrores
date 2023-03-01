import { NgModule }from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { RouterModule, Routes } from '@angular/router';
import { WelcomeComponent } from './components/welcome/welcome.component';

const routes: Routes = [
  {path:'' , redirectTo:'welcome' , pathMatch:'full'},
  {path:'welcome', component:WelcomeComponent},
  {path:'dashboard' , component:DashboardComponent},
  {path:'auth/google', component:WelcomeComponent}
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports:[RouterModule]
})
export class AppRoutingModule { }
export const routingComponents = [DashboardComponent]
