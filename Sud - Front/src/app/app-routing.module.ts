import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SudComponent } from './components/sud/sud.component';
import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './components/about/about.component';

const routes: Routes =  [
{ path: 'sud', component: SudComponent },
{ path: 'home', component: HomeComponent },
{ path: 'o-nama', component: AboutComponent },
{ path: 'home', component: HomeComponent },
{ path: '', redirectTo: '/home', pathMatch: 'full'}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }