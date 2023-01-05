import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {SettingsComponentComponent} from './components/settings-component/settings-component.component';
import {ResultsComponentComponent} from './components/results-component/results-component.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatIconModule} from "@angular/material/icon";
import {HttpClientModule} from "@angular/common/http";
import {MatToolbarModule} from "@angular/material/toolbar";


@NgModule({
  declarations: [
    AppComponent,
    SettingsComponentComponent,
    ResultsComponentComponent
  ],
  imports: [
    HttpClientModule,
    MatToolbarModule,
    BrowserModule,
    MatIconModule,
    BrowserAnimationsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
