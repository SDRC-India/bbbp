import { Injectable } from '@angular/core';
import * as $PouchDB from 'pouchdb';
const PouchDB = $PouchDB['default'];

@Injectable()
export class PouchdbService { 
  private db:any;
  private constructor() { 
    this.db = new PouchDB('sdrcdb');
  }

  getDb(){
    return this.db;
  }
}
