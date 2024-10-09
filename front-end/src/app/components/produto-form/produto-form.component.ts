import { Component, OnInit } from '@angular/core';
import { ProdutoService } from '../../services/produto.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Produto } from '../../models/produto';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-produto-form',
  templateUrl: './produto-form.component.html',
  styleUrl: './produto-form.component.css'
})
export class ProdutoFormComponent implements OnInit {
  product: Produto = {
    idProduto: 0,
    descricao: "",
    codBarras: "",
    precoAtacado: 0,
    precoVarejo: 0,
    status: true,
    qteEstoque: 0,
    qteMin: 0,
    qteMax: 0,
  }

  isEditMode: boolean = false;

  constructor(private toast: ToastrService, private produtoService: ProdutoService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.isEditMode = true;
      this.produtoService.getProductById(parseInt(id)).subscribe(data => {
        this.product = data;
      });
    }
  }

  saveProduct(): void {
    if (this.isEditMode) {
      this.produtoService.updateProduct(this.product.idProduto, this.product).subscribe(
        () => {
          this.toast.success("Produto atualizado com sucesso!");
          this.router.navigate(["/products"]);
        },
        (ex) => {
          this.toast.error("Erro ao atualizar produto!");
          console.error(ex);
        }
      );
    } else {
      this.produtoService.createProduct(this.product).subscribe(
        () => {
          this.toast.success("Produto criado com sucesso!");
          this.router.navigate(["/products"]);
        },
        (ex) => {
          this.toast.error("Erro ao cadastrar produto!");
          console.error(ex);
        }
      );
    }
  }

}
