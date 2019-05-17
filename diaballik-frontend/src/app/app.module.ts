import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { RouterModule, Routes } from '@angular/router';
import { AppComponent } from './app.component';
import { BoardComponent } from './board/board.component';
import { Data } from './data';
import { MenuComponent } from './menu/menu.component';

const appRoutes: Routes = [
  { path: '',
    redirectTo: '/menu',
    pathMatch: 'full'
  },
  { path: 'board',
    component: BoardComponent
  },
  { path: 'menu',
    component: MenuComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    BoardComponent,
    MenuComponent
  ],
  imports: [
    RouterModule.forRoot(
      appRoutes,
      { enableTracing: false }
    ),
    BrowserModule,
    HttpClientModule
  ],
  providers: [Data],
  bootstrap: [AppComponent]
})
export class AppModule { }
