import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Produto } from '../../../models/produto';
import { ProdutoService } from '../../../services/produto.service';

@Component({
  selector: 'app-produto-list',
  templateUrl: './produto-list.component.html',
  styleUrls: ['./produto-list.component.css']
})
export class ProdutoListComponent implements OnInit {
  isModalOpen = false;
  isEditMode = false;

  produtos: Produto[] = [];

  constructor(private toastr: ToastrService, private produtoService: ProdutoService) { }

  openModal() {
    this.isEditMode = false; // Muda para true se você quiser editar um produto
    this.isModalOpen = true; // Abre o modal
    document.body.classList.add('modal-open'); // Adiciona a classe para evitar scroll
  }

  closeModal() {
    this.isModalOpen = false; // Fecha o modal
    document.body.classList.remove('modal-open'); // Remove a classe
  }

  reloadProducts() {
    console.log('Lista de produtos recarregada.');
    this.produtoService.getAllProducts().subscribe(data => {
      this.produtos = data; // Atualiza a lista de produtos
    });
    this.closeModal(); // Fecha o modal após salvar
  }
  
  ngOnInit(): void {
    this.produtoService.getAllProducts().subscribe(data => {
      this.produtos = data;
    });
  }

  deleteProduct(idProduto: number): void {
    this.produtoService.deleteProduct(idProduto).subscribe(() => {
      this.produtos = this.produtos.filter(product => product.idProduto !== idProduto);
      this.toastr.success("Produto removido com sucesso!");
    });
  }

  editProduct(product: Produto): void {
    this.isEditMode = true;
    this.openModal();
  }

  onModalClose(): void {
    this.closeModal();
  }
}
