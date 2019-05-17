import { Component, OnInit, Injectable, Sanitizer } from '@angular/core';

import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Data } from '../data';
import { Piece } from '../piece';
import { DomSanitizer } from '@angular/platform-browser';
import { VariableAst, CompileShallowModuleMetadata } from '@angular/compiler';
import { ActionSequence } from 'protractor';
import { delay } from 'q';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {

  playerLeft: string;
  playerRight: string;
  nombre: number = 3;

  private selectedPiece: boolean;
  private actions: any;
  private currentPiece: any;
  private playerColors: Map<string, string>;
  private board: Piece[][];
  private turnColor: string;
  private nextColor: string;
  private colorLeft: string;
  private colorRight: string;
  private counter: number;
  private winner: string;
  private playerRightplay: boolean;
  private playerLeftplay: boolean;
  private sourceImage: string;
  private ai: boolean;
  private aiTurn: boolean;

  private savedGame: boolean;
  private savename: string;

  constructor(private http: HttpClient, private router: Router, private data: Data, private sanitizer: DomSanitizer) {
    this.playerColors = new Map();
    this.actions = new Array();
    this.currentPiece = new Array();
  }



  async ngOnInit() {
    this.board = [];
    for (var i: number = 0; i < 7; i++) {
      this.board[i] = [];
      for (var j: number = 0; j < 7; j++) {
        this.board[i][j] = null;
      }
    }

    //     this.http.post(`/game/newGamePvP/A/B/15000/160000/enemy_among_us`, {}, {}).subscribe(response => {
    //       this.data.storage = response;
    //       this.playerColors[this.data.storage.players[0]['@id']] = this.data.storage.players[0].color.code;
    //       this.playerColors[this.data.storage.players[1]['@id']] = this.data.storage.players[1].color.code;
    //       this.turnColor = this.data.storage.players[0].color.code;
    //       this.nextColor = this.data.storage.players[1].color.code;
    //       this.colorLeft = this.data.storage.players[0].color.code;
    //       this.colorRight = this.data.storage.players[1].color.code;
    //       this.playerLeft = this.data.storage.players[0].name;
    //       this.playerRight = this.data.storage.players[1].name;

    //       this.fillBoard(this.data.storage.initialBoard.pieces);
    //     });
    // console.log(this.data.storage);


    this.playerColors[this.data.storage.players[0]['@id']] = this.data.storage.players[0].color.code;
    this.playerColors[this.data.storage.players[1]['@id']] = this.data.storage.players[1].color.code;
    this.turnColor = this.data.storage.players[0].color.code;
    this.nextColor = this.data.storage.players[1].color.code;
    this.colorLeft = this.data.storage.players[0].color.code;
    this.colorRight = this.data.storage.players[1].color.code;
    this.playerLeft = this.data.storage.players[0].name;
    this.playerRight = this.data.storage.players[1].name;
    this.ai = this.data.storage.players[1].type == 'BotPlayer';
    this.aiTurn = false;

    this.fillBoard(this.data.storage.initialBoard.pieces);

    this.counter = 0;

    for (let action of this.data.storage.done) {
      if (this.data.replay) await this.delay(1000);
      this.currentPiece = [action.oldPos.x, action.oldPos.y];
      this.doAction(action.newPos.x, action.newPos.y);
    }

    this.savename = 'save';
    this.savedGame = false;
    this.sourceImage="../../assets/triangle3.png";
  }

  public get getData(): any {
    return this.data.storage;
  }

  public tileClick(event: MouseEvent): void {
    if (this.aiTurn) return;
    if (this.winner != null) return;
    let x = (event.currentTarget as Element).getAttribute('data-x');
    let y = (event.currentTarget as Element).getAttribute('data-y');
    let selectable = ((event.currentTarget as Element).getAttribute('selectable') == 'true');
    this.actions.length = 0;
    if (this.selectedPiece) {
      if (selectable) {
        this.doAction(+x, +y);
      }
      this.selectedPiece = false;
    } else {
      if (this.board[x][y] != null && this.board[x][y].color == this.turnColor) {
        this.http.get(`/game/getActions/${x}/${y}`).subscribe(response => {
          this.currentPiece = [response[0].oldPos.x, response[0].oldPos.y];
          for (let i in response) {
            let newx = response[i].newPos.x;
            let newy = response[i].newPos.y;
            this.actions.push([newx, newy]);
          }
        });
        this.selectedPiece = true;
      }

    }
  }

  public actionsContains(x: number, y: number): boolean {
    for (let i in this.actions) {
      if (this.actions[i][0] == x) {
        if (this.actions[i][1] == y)
          return true;
      }
    }
    return null;
  }

  public async doAction(x: number, y: number) {
    if (this.board[x][y] != null) {
      this.http.put(`/game/throwBall/${this.currentPiece[0]}/${this.currentPiece[1]}/${x}/${y}`, {}, {}).subscribe(response => {
        this.data.storage = response;
        this.fillBoard(this.data.storage.pieces);
      });
      this.checkVictory(x, y);
    } else {
      this.http.put(`/game/movePiece/${this.currentPiece[0]}/${this.currentPiece[1]}/${x}/${y}`, {}, {}).subscribe(response => {
        this.data.storage = response;
        this.fillBoard(this.data.storage.pieces);
      });
    }
    // Turn change :
    this.counter++;
    if (this.counter==0){
      this.sourceImage="../../assets/triangle3.png";
    }
    if (this.counter==1){
      this.sourceImage="../../assets/triangle2.png";
    }
    if (this.counter==2){
      this.sourceImage="../../assets/triangle1.png";
    }
    if (this.counter > 2) {
      this.sourceImage="../../assets/triangle3.png";
      this.counter = 0;
      let temp = this.turnColor;
      this.turnColor = this.nextColor;
      this.nextColor = temp;
      if(this.ai) this.aiTurn = !this.aiTurn;
    }
    if(this.aiTurn) {
      this.aiPlay();
      
    }
  }

  public aiPlay() : void {
    this.http.get(`/game/getAIDecision`).subscribe(async response => {
      let action : any = response;
      await this.delay(1000);
      this.currentPiece = [action.oldPos.x, action.oldPos.y];
      this.doAction(action.newPos.x, action.newPos.y);
    });
  }

  public fillBoard(pieces: any) {
    for (let i in pieces) {
      for (let j in pieces[i]) {
        if (pieces[i][j] != null) {
          let player = pieces[i][j].player;
          let color;
          if (player instanceof Object) {
            this.playerColors[player['@id']] = player.color.code;
            player = player['@id'];
          }
          color = this.playerColors[player];
          this.board[i][j] = new Piece(player,
            color,
            pieces[i][j].hasBall);
        } else {
          this.board[i][j] = null;
        }
      }
    }
  }

  public checkVictory(x: number, y: number): void {
    if (x == 0 && this.colorLeft != this.turnColor) {
      this.winner = 'right';
    } else if (x == 6 && this.colorRight != this.turnColor) {
      this.winner = 'left';
    }
  }

  public pieceColor(x: number, y: number) {
    const color = this.board[x][y].color;
    const res = 'radial-gradient(#FFFFFF, ' + color + ' 60%)';
    return this.sanitizer.bypassSecurityTrustStyle(res);
  }

  public winningPlayer(): string {
    if (this.winner == 'left') {
      return this.playerLeft;
    } else {
      return this.playerRight;
    }
  }

  public winningColor(): string {
    if (this.winner == 'left') {
      return this.colorLeft;
    } else {
      return this.colorRight;
    }
  }

  public saveGame() {
    this.http.put(`/game/save/${this.savename}`,{}).subscribe(response => console.log(response));
    this.savedGame = true;
  }
  private delay(ms: number)
  {
    return new Promise(resolve => setTimeout(resolve, ms));
  }
}