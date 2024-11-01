import { Component, Input, OnInit, Output, EventEmitter, SimpleChanges } from '@angular/core';
import { ProdutoService } from '../../../services/produto.service';
import { Produto } from '../../../models/produto';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-produto-form',
  templateUrl: './produto-form.component.html',
  styleUrls: ['./produto-form.component.css']
})
export class ProdutoFormComponent implements OnInit {
  @Input() idProduto: number | null = null; 
  @Output() onCloseModal = new EventEmitter<void>(); 
  @Output() onSave = new EventEmitter<void>();

  isEditMode: boolean = false; 
  produto: Produto = this.createEmptyProduct(); 

  constructor(
    private toast: ToastrService, 
    private produtoService: ProdutoService
  ) {}

  ngOnInit(): void {
    this.checkEditMode();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['idProduto'] && changes['idProduto'].currentValue !== null) {
      this.isEditMode = true;
      this.loadProduct(this.idProduto!); 
    } else {
      this.isEditMode = false;
      this.produto = this.createEmptyProduct();
    }
  }

  checkEditMode(): void {
    if (this.idProduto != null) {
      this.isEditMode = true;
      this.loadProduct(this.idProduto);
    }
  }

  loadProduct(id: number): void {
    this.produtoService.getProductById(id).subscribe(
      (data) => {
        this.produto = data;
      },
      (error) => {
        this.toast.error("Erro ao carregar produto: " + error.message);
      }
    );
  }

  saveProduct(): void {
    if (this.isEditMode) {
      this.produtoService.updateProduct(this.produto.idProduto, this.produto).subscribe(
        () => {
          this.toast.success("Produto atualizado com sucesso!");
          this.clearForm();
          this.onSave.emit(); 
          this.onCloseModal.emit(); 
        },
        (ex) => {
          this.toast.error("Erro ao atualizar produto: " + ex.message);
        }
      );
    } else {
      this.produtoService.createProduct(this.produto).subscribe(
        () => {
          this.toast.success("Produto criado com sucesso!");
          this.clearForm();
          this.onCloseModal.emit(); 
        },
        (ex) => {
          this.toast.error("Erro ao cadastrar produto: " + ex.message);
        }
      );
    }
  }

  cancel(): void {
    this.clearForm();
    this.onCloseModal.emit(); 
  }

  clearForm(): void {
    this.produto = this.createEmptyProduct(); 
    this.isEditMode = false;
  }

  private createEmptyProduct(): Produto {
    return {
      idProduto: 0,
      descricao: "",
      codBarras: "",
      precoAtacado: 0,
      precoVarejo: 0,
      status: true,
      qteEstoque: 0,
      qteMin: 0,
      qteMax: 0
    };
  }
}
