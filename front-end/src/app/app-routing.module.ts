import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SidebarComponent } from './components/layout/sidebar/sidebar.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ProdutoListComponent } from './pages/produto/produto-list/produto-list.component';
import { ProdutoFormComponent } from './pages/produto/produto-form/produto-form.component';
import { ClienteListComponent } from './pages/cliente/cliente-list/cliente-list.component';
import { ClienteFormComponent } from './pages/cliente/cliente-form/cliente-form.component';
import { FornecedorListComponent } from './pages/fornecedor/fornecedor-list/fornecedor-list.component';
import { FornecedorFormComponent } from './pages/fornecedor/fornecedor-form/fornecedor-form.component';
import { FuncionarioListComponent } from './pages/funcionario/funcionario-list/funcionario-list.component';
import { FuncionarioFormComponent } from './pages/funcionario/funcionario-form/funcionario-form.component';
import { FormaPagamentoListComponent } from './pages/forma-pagamento/forma-pagamento-list/forma-pagamento-list.component';
import { FormaPagamentoFormComponent } from './pages/forma-pagamento/forma-pagamento-form/forma-pagamento-form.component';
import { ConfigPageComponent } from './pages/configuration/config-page/config-page.component';
import { PdvComponent } from './pages/pdv/pdv/pdv.component';
import { EntradaEstoqueComponent } from './pages/entrada-estoque/entrada-estoque/entrada-estoque.component';
import { LoginScreenComponent } from './pages/login-screen/login-screen.component';

const routes: Routes = [
  { path: 'login', component: LoginScreenComponent},
  {
    path: '',
    component: SidebarComponent,
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'configs', component: ConfigPageComponent },
      { path: 'products', component: ProdutoListComponent },

      // Customers routes
      { path: 'customers', component: ClienteListComponent },
      { path: 'add-customer', component: ClienteFormComponent },
      { path: 'edit-customer/:id', component: ClienteFormComponent },

      // Suppliers routes
      { path: 'suppliers', component: FornecedorListComponent },
      { path: 'add-supplier', component: FornecedorFormComponent },
      { path: 'edit-supplier/:id', component: FornecedorFormComponent },

      // Employee routes
      { path: 'employees', component: FuncionarioListComponent },
      { path: 'add-employee', component: FuncionarioFormComponent },
      { path: 'edit-employee/:id', component: FuncionarioFormComponent },

      // Payment methods routes
      { path: 'payment-methods', component: FormaPagamentoListComponent },
      { path: 'add-payment-method', component: FormaPagamentoFormComponent },
      { path: 'edit-payment-method/:id', component: FormaPagamentoFormComponent },

      // PDV route
      { path: 'pdv', component: PdvComponent },

      // Entrada de Estoque route
      { path: 'stock-entry', component: EntradaEstoqueComponent }
    ]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
