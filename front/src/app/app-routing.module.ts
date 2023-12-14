import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RegisterComponent } from './modules/auth/components/register/register.component';
import { LoginComponent } from './modules/auth/components/login/login.component';
import { HomeComponent } from './modules/home/component/home/home.component';
import { CreateDiscussionComponent } from './modules/chatroom/components/create-discussion/create-discussion.component';
import { ChatComponent } from './modules/chatroom/components/chat/chat.component';
import { DiscussionComponent } from './modules/chatroom/components/discussion/discussion.component';
import { UnauthGuard } from './core/guards/unauth.guard';
import { AuthGuard } from './core/guards/auth.guard';

const routes: Routes = [
  {
    path:"",
    canActivate: [UnauthGuard],
    component: RegisterComponent
  },
  {
    path:"login/:type",
    canActivate: [UnauthGuard],
    component: LoginComponent
  },
  {
    path:"home",
    canActivate: [UnauthGuard],
    component: HomeComponent
  },
  {
    path:"chat",
    canActivate: [AuthGuard],
    component: DiscussionComponent
  },
  {
    path:"chat/:id",
    canActivate: [AuthGuard],
    component: ChatComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
