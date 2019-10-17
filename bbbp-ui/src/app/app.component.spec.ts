import { TestBed, async } from '@angular/core/testing';
import {ReactiveFormsModule, FormsModule} from "@angular/forms";

import { AppComponent } from './app.component';
import { RouterTestingModule } from '@angular/router/testing';
import { HeaderComponent } from './page/fragments/header/header.component';
import { FooterComponent } from './page/fragments/footer/footer.component';
import { NO_ERRORS_SCHEMA } from '@angular/core';
describe('AppComponent', () => {
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule, ReactiveFormsModule, FormsModule],
      declarations: [
        AppComponent, HeaderComponent, FooterComponent
      ],
      schemas: [ NO_ERRORS_SCHEMA]
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
  it(`should have as title 'app'`, async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app.title).toEqual('app');
  }));
  
});
