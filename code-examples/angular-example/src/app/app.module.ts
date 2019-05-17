import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { MycomponentComponent } from './mycomponent/mycomponent.component';
import { MyData } from './mydata';

// routes must be defined here
const appRoutes: Routes = [
  // { path: 'path1', component: AComponent },
  { path: 'foo', component: MycomponentComponent },
  { path: '',
    redirectTo: '/foo',
    pathMatch: 'full'
  }
];

@NgModule({
  // All the components of the app
  declarations: [
    AppComponent,
    MycomponentComponent
  ],
  // The imported modules used in the app
  // do not forget to complete the import with HttpClientModule and RouterModule
  // when using HTTP and Angular routes.
  imports: [
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: false }
    ),
    BrowserModule,
    HttpClientModule
  ],
  // Injected objects
  providers: [MyData],
  // The main component of the app
  bootstrap: [AppComponent]
})
export class AppModule { }
