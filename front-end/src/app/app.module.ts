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
import { FornecedorListComponent } from './pages/fornecedor/fornecedor-list/fornecedor-list.component';
import { FornecedorFormComponent } from './pages/fornecedor/fornecedor-form/fornecedor-form.component';
import { FuncionarioFormComponent } from './pages/funcionario/funcionario-form/funcionario-form.component';
import { FuncionarioListComponent } from './pages/funcionario/funcionario-list/funcionario-list.component';
import { FormaPagamentoListComponent } from './pages/forma-pagamento/forma-pagamento-list/forma-pagamento-list.component';
import { FormaPagamentoFormComponent } from './pages/forma-pagamento/forma-pagamento-form/forma-pagamento-form.component';
import { ConfigPageComponent } from './pages/configuration/config-page/config-page.component';
import { PdvComponent } from './pages/pdv/pdv/pdv.component';
import { EntradaEstoqueComponent } from './pages/entrada-estoque/entrada-estoque/entrada-estoque.component';
import { SidebarItemComponent } from './components/layout/sidebar/sidebar-item/sidebar-item.component';
import { LoginScreenComponent } from './pages/login-screen/login-screen.component';
import { DashboardCardComponent } from './components/dashboard-card/dashboard-card.component';
import { DashboardClienteItemComponent } from './components/dashboard-cliente-item/dashboard-cliente-item.component';
import { VendaItemListComponent } from './components/dashboard/table-vendas-dashboard/table-vendas-dashboard.component';

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
    DashboardComponent,
    FornecedorListComponent,
    FornecedorFormComponent,
    FuncionarioFormComponent,
    FuncionarioListComponent,
    FormaPagamentoListComponent,
    FormaPagamentoFormComponent,
    ConfigPageComponent,
    PdvComponent,
    EntradaEstoqueComponent,
    SidebarItemComponent,
    LoginScreenComponent,
    DashboardCardComponent,
    DashboardClienteItemComponent,
    VendaItemListComponent
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
