import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProdutoListComponent } from './components/produto-list/produto-list.component';
import { ProdutoFormComponent } from './components/produto-form/produto-form.component';

const routes: Routes = [
   // Products routes 
   { path: 'products', component: ProdutoListComponent },
   { path: 'add-product', component: ProdutoFormComponent },
   { path: 'edit-product/:id', component: ProdutoFormComponent },
   { path: '', redirectTo: '/products', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
