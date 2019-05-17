import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { CompileShallowModuleMetadata } from '@angular/compiler';

@Injectable()
export class Data {
  public storage : any;
  public replay : boolean = false;

  constructor() {
    //this.storage.parse(' {"@id": 4,"pieces": [[{"@id": 5,"x": 0,"y": 0,"player": 2,"hasBall": false},{"@id": 6,"x": 0,"y": 1,"player": 2,"hasBall": false},{"@id": 7,"x": 0,"y": 2,"player": 2,"hasBall": false},{"@id": 8,"x": 0,"y": 3,"player": 2,"hasBall": true},{"@id": 9,"x": 0,"y": 4,"player": 2,"hasBall": false},{"@id": 10,"x": 0,"y": 5,"player": 2,"hasBall": false},{"@id": 11,"x": 0,"y": 6,"player": 2,"hasBall": false}],[null,null,null,null,null,null,null],[null,null,null,null,null,null,null],[null,null,null,null,null,null,null],[null,null,null,null,null,null,null],[null,null,null,null,null,null,null],[{"@id": 12,"x": 6,"y": 0,"player": 3,"hasBall": false},{"@id": 13,"x": 6,"y": 1,"player": 3,"hasBall": false},{"@id": 14,"x": 6,"y": 2,"player": 3,"hasBall": false},{"@id": 15,"x": 6,"y": 3,"player": 3,"hasBall": true},{"@id": 16,"x": 6,"y": 4,"player": 3,"hasBall": false},{"@id": 17,"x": 6,"y": 5,"player": 3,"hasBall": false},{"@id": 18,"x": 6,"y": 6,"player": 3,"hasBall": false}]]}')
    //this.storage=JSON.parse('{"@id": 1,"finished": false,"players": [{"type": "Player","@id": 2,"name": "A","color": {"code": "#880088"}},{"type": "Player","@id": 3,"name": "B","color": {"code": "#008800"}}],"done": [],"nbTurns": 0,"initialBoard": {"@id": 4,"pieces": [[{"@id": 5,"x": 0,"y": 0,"player": 2,"hasBall": false},{"@id": 6,"x": 0,"y": 1,"player": 2,"hasBall": false},{"@id": 7,"x": 0,"y": 2,"player": 2,"hasBall": false},{"@id": 8,"x": 0,"y": 3,"player": 2,"hasBall": true},{"@id": 9,"x": 0,"y": 4,"player": 2,"hasBall": false},{"@id": 10,"x": 0,"y": 5,"player": 2,"hasBall": false},{"@id": 11,"x": 0,"y": 6,"player": 2,"hasBall": false}],[null,null,null,null,null,null,null],[null,null,null,null,null,null,null],[null,null,null,null,null,null,null],[null,null,null,null,null,null,null],[null,null,null,null,null,null,null],[{"@id": 12,"x": 6,"y": 0,"player": 3,"hasBall": false},{"@id": 13,"x": 6,"y": 1,"player": 3,"hasBall": false},{"@id": 14,"x": 6,"y": 2,"player": 3,"hasBall": false},{"@id": 15,"x": 6,"y": 3,"player": 3,"hasBall": true},{"@id": 16,"x": 6,"y": 4,"player": 3,"hasBall": false},{"@id": 17,"x": 6,"y": 5,"player": 3,"hasBall": false},{"@id": 18,"x": 6,"y": 6,"player": 3,"hasBall": false}]]}}');
  }

}