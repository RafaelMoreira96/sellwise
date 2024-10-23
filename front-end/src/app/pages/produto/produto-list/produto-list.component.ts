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
  produtoSelecionado: number | null = null;

  constructor(private toastr: ToastrService, private produtoService: ProdutoService) { }

  reloadProducts() {
    console.log('Lista de produtos recarregada.');
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

  openModal(idProduto?: number) {
    if (idProduto) {
      console.log('Editando produto com id:', idProduto);
      this.isEditMode = true; 
      this.produtoSelecionado = idProduto;
    } else {
      this.isEditMode = false;
      this.produtoSelecionado = null;
    }
    this.isModalOpen = true; 
    document.body.classList.add('modal-open');
  }

  closeModal() {
    this.isModalOpen = false; 
    document.body.classList.remove('modal-open'); 
  }
  
  onModalClose(): void {
    this.reloadProducts();
    this.closeModal();
  }
}
