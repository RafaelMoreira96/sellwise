import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Produto } from '../../../models/produto';
import { ProdutoService } from '../../../services/produto.service';
import { ProdutoFormComponent } from '../produto-form/produto-form.component';

@Component({
  selector: 'app-produto-list',
  templateUrl: './produto-list.component.html',
  styleUrls: ['./produto-list.component.css']
})
export class ProdutoListComponent implements OnInit {
  isModalInactiveOpen = false;
  
  isModalFormOpen = false;
  isModalRemoveOpen = false;
  isEditMode = false;

  produtos: Produto[] = [];
  produtoSelecionado: number | null = null;

  @Output() onUpdateInactiveProducts = new EventEmitter<void>(); // Adicionado
  @ViewChild(ProdutoFormComponent) produtoFormComponent!: ProdutoFormComponent;

  constructor(private toastr: ToastrService, private produtoService: ProdutoService) {
  }


  ngOnInit(): void {
    this.reloadProducts();
  }

  reloadProducts() {
    this.produtoService.produtos$.subscribe(data => {
      this.produtos = data;
    }, (ex) => {
      if (ex.error.errors) {
        ex.error.errors.forEach(
          (element: { message: string | undefined }) => {
            this.toastr.info(element.message);
          }
        );
      } else {
        this.toastr.info(ex.error.message);
      }
    });
    this.produtoService.getAllProducts();
  }

  deleteProduct(): void {
    if (this.produtoSelecionado !== null) {
      this.produtoService.deleteProduct(this.produtoSelecionado).subscribe(() => {
        this.produtos = this.produtos.filter(product => product.idProduto !== this.produtoSelecionado);
        this.toastr.success("Produto removido com sucesso!");
        this.onUpdateInactiveProducts.emit(); 
      }, error => {
        this.toastr.error("Erro ao remover o produto: " + error.message);
        console.error(error);
      });
      this.closeModal();
    }
  }

  editProduct(idProduto: number): void {
    this.openModal(idProduto);
  }

  openModal(idProduto?: number): void {
    if (idProduto) {
      this.isEditMode = true;
      this.produtoSelecionado = idProduto;
      this.produtoFormComponent.loadProduct(idProduto);
    } else {
      this.isEditMode = false;
      this.produtoSelecionado = null;
      this.produtoFormComponent.clearForm();
    }
    this.isModalFormOpen = true;
    document.body.classList.add('modal-open');
  }

  openRemoveModal(idProduto: number): void {
    this.produtoSelecionado = idProduto;
    this.isModalRemoveOpen = true;
    document.body.classList.add('modal-open');
  }

  openInactiveListModal() {
    this.isModalInactiveOpen = true;
    document.body.classList.add('modal-open');
  }

  closeModal(): void {
    this.isModalFormOpen = false;
    this.isModalRemoveOpen = false;
    this.isModalInactiveOpen = false;
    document.body.classList.remove('modal-open');
  }

  onModalClose(): void {
    this.closeModal();
    this.reloadProducts();
  }
}
