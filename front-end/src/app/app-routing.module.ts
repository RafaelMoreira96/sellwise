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

const routes: Routes = [
  {
    path: '',
    component: SidebarComponent,
    children: [
      // Dashboard route
      { path: 'dashboard', component: DashboardComponent },

      // Products routes 
      { path: 'products', component: ProdutoListComponent },
      { path: 'add-product', component: ProdutoFormComponent },
      { path: 'edit-product/:id', component: ProdutoFormComponent, },

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
    ]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
