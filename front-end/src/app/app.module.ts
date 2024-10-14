import { LOCALE_ID, NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';

import { provideEnvironmentNgxMask } from 'ngx-mask';
import { provideHttpClient } from '@angular/common/http';

import { FormsModule } from '@angular/forms';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CommonModule, registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';

import { AppComponent } from './app.component';

import { SidebarComponent } from './components/layout/sidebar/sidebar.component';
import { HeaderComponent } from './components/layout/header/header.component';
import { ClienteFormComponent } from './pages/cliente/cliente-form/cliente-form.component';
import { ClienteListComponent } from './pages/cliente/cliente-list/cliente-list.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ProdutoFormComponent } from './pages/produto/produto-form/produto-form.component';
import { ProdutoListComponent } from './pages/produto/produto-list/produto-list.component';

registerLocaleData(localePt);

@NgModule({
  declarations: [
    AppComponent,
    ProdutoListComponent,
    ProdutoFormComponent,
    ClienteListComponent,
    ClienteFormComponent,
    SidebarComponent,
    HeaderComponent,
    DashboardComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    AppRoutingModule,
    ToastrModule.forRoot()
  ],
  providers: [
    {provide: LOCALE_ID, useValue: 'pt-BR'},
    provideClientHydration(),
    provideHttpClient(),
    provideEnvironmentNgxMask()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
