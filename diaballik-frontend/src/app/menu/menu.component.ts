import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Data } from '../data';
import { Router } from '@angular/router';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  name1 : string;
  name2 : string;
  color1 : string;
  color2 : string;

  ai : boolean;
  aitype : string;

  scenario : string;

  save : string;

  constructor(private router: Router, private http: HttpClient, private data: Data) { }

  ngOnInit() {
    this.name1 = 'Player1';
    this.name2 = 'Player2';
    this.color1 = '#FF0000';
    this.color2 = '#0000FF';
    this.ai = false;
    this.aitype = 'noob';
    this.scenario = 'standard';
    this.save = 'file';
  }

  public launchGame() : void {
    let request : string;
    let c1 = parseInt(this.color1.slice(-6), 16);
    let c2 = parseInt(this.color2.slice(-6), 16);
    if (this.ai) {
      request = `/game/newGamePvE/${this.name1}/${this.name2}/${c1}/${c2}/${this.aitype}/${this.scenario}`;
    } else {
      request = `/game/newGamePvP/${this.name1}/${this.name2}/${c1}/${c2}/${this.scenario}`;
    }
    this.http.post(request, {}, {}).subscribe(response => {
      this.setData(response);
      this.router.navigate(['/board']);
    });
  }

  public loadGame() : void {
    this.http.post(`/game/load/${this.save}/true`, {}, {}).subscribe(response => {
      if (response != null) {
        this.setData(response);
        this.router.navigate(['/board']);
      }
    });
  }

  public replayGame() : void {
    this.http.post(`/game/load/${this.save}/true`, {}, {}).subscribe(response => {
      if (response != null) {
        this.setData(response);
        this.data.replay = true;
        this.router.navigate(['/board']);
      }
    });
  }

  private setData(o : Object) {
    this.data.storage = o;
  }

}
