import { Component, OnInit } from '@angular/core';
import { ProdutoService } from '../../../services/produto.service';
import { Produto } from '../../../models/produto';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-produto-list',
  templateUrl: './produto-list.component.html',
  styleUrl: './produto-list.component.css'
})
export class ProdutoListComponent implements OnInit{
  produtos: Produto[] = [];

  constructor(private toastr: ToastrService, private produtoService: ProdutoService) { }

  ngOnInit(): void {
    this.produtoService.getAllProducts().subscribe(data => {
      this.produtos = data;
    });
  }

  deleteProduct(idProduto: number): void {
    this.produtoService.deleteProduct(idProduto).subscribe(() => {
      this.produtos = this.produtos.filter(product => product.idProduto!== idProduto);
      this.toastr.success("Produto removido com sucesso!");
    });
  }

}
