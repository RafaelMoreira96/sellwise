import { Component, OnInit, ViewChild } from '@angular/core';
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
  isModalOpen = false;
  isEditMode = false;
  produtos: Produto[] = [];
  produtoSelecionado: number | null = null;

  @ViewChild(ProdutoFormComponent) produtoFormComponent!: ProdutoFormComponent;

  constructor(private toastr: ToastrService, private produtoService: ProdutoService) { }

  reloadProducts() {
    this.produtoService.getAllProducts().subscribe(data => {
      this.produtos = data;
    });
    this.closeModal();
  }

  ngOnInit(): void {
    this.reloadProducts();
  }

  deleteProduct(idProduto: number): void {
    this.produtoService.deleteProduct(idProduto).subscribe(() => {
      this.produtos = this.produtos.filter(product => product.idProduto !== idProduto);
      this.toastr.success("Produto removido com sucesso!");
    });
  }

  editProduct(idProduto: number): void {
    this.openModal(idProduto);
  }

  openModal(idProduto?: number): void {
    if (idProduto) {
      this.isEditMode = true; 
      this.produtoSelecionado = idProduto;

      // Força o carregamento do produto sempre que editar
      this.produtoFormComponent.loadProduct(idProduto);
    } else {
      this.isEditMode = false;
      this.produtoSelecionado = null;
      this.produtoFormComponent.clearForm(); // Limpa o formulário para um novo produto
    }
    this.isModalOpen = true;
    document.body.classList.add('modal-open');
  }

  closeModal(): void {
    this.isModalOpen = false;
    document.body.classList.remove('modal-open');
  }

  onModalClose(): void {
    this.reloadProducts();
    this.closeModal();
  }
}
