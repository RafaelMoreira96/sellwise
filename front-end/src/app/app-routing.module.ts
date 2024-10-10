import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProdutoListComponent } from './components/produto/produto-list/produto-list.component';
import { ProdutoFormComponent } from './components/produto/produto-form/produto-form.component';
import { ClienteListComponent } from './components/cliente/cliente-list/cliente-list.component';
import { ClienteFormComponent } from './components/cliente/cliente-form/cliente-form.component';

const routes: Routes = [
   // Products routes 
   { path: 'products', component: ProdutoListComponent },
   { path: 'add-product', component: ProdutoFormComponent },
   { path: 'edit-product/:id', component: ProdutoFormComponent ,},
   { path: '', redirectTo: '/products', pathMatch: 'full' },

   // Customers routes
   { path: 'customers', component: ClienteListComponent },
   { path: 'add-customer', component: ClienteFormComponent },
   { path: 'edit-customer/:id', component: ClienteFormComponent },
   { path: '', redirectTo: '/customers', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
