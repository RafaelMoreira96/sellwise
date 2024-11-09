import { Component, EventEmitter, Output, OnInit } from '@angular/core';
import { ProdutoService } from '../../../services/produto.service';
import { ToastrService } from 'ngx-toastr';
import { Produto } from '../../../models/produto';

@Component({
  selector: 'app-produto-inativo-list',
  templateUrl: './produto-inativo-list.component.html',
  styleUrls: ['./produto-inativo-list.component.css'] 
})
export class ProdutoInativoListComponent implements OnInit { 
  @Output() onCloseModal = new EventEmitter<void>();
  @Output() onSave = new EventEmitter<number>();

  produtosInativos: Produto[] = [];

  constructor(private service: ProdutoService, private toast: ToastrService) { }

  ngOnInit(): void {
    this.loadingInactiveProducts();
  }

  loadingInactiveProducts(): void {  
    this.service.produtosInativos$.subscribe(data => {
      this.produtosInativos = data;
    }, ex => {
      this.toast.info(ex.error.message);
    });
  }

  reactiveProduct(id: number): void { 
    this.service.reactiveProduct(id).subscribe(() => {
      this.loadingInactiveProducts();
      this.toast.success("Produto reativado com sucesso!");
      this.onSave.emit(id);
      this.onCloseModal.emit();
    }, ex => {
      this.handleError(ex);
    });
  }

  private handleError(ex: any): void {  // Criando um método para tratamento de erro
    if (ex.error.errors) {
      ex.error.errors.forEach((element: { message: string | undefined }) => {
        this.toast.error(element.message);  // Usando toast.error para erros
      });
    } else {
      this.toast.error(ex.error.message);  // Usando toast.error para erros
    }
  }

  updateInactiveProducts(): void {
    this.loadingInactiveProducts();
  }
}
