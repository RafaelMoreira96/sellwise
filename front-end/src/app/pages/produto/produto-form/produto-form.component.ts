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
  @Input() idProduto: number | null = null; // Recebe o id do produto para edição ou null para novo produto
  @Output() onCloseModal = new EventEmitter<void>(); 

  isEditMode: boolean = false; 
  produto: Produto = this.createEmptyProduct(); // Inicializa com um produto vazio

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
      this.loadProduct(this.idProduto!); // Carrega o produto selecionado para edição
    } else {
      this.isEditMode = false;
      this.produto = this.createEmptyProduct(); // Reseta o formulário se for um novo produto
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
        console.error("Erro ao carregar produto:", error);
        this.toast.error("Erro ao carregar produto!");
      }
    );
  }

  saveProduct(): void {
    if (this.isEditMode) {
      this.produtoService.updateProduct(this.produto.idProduto, this.produto).subscribe(
        () => {
          this.toast.success("Produto atualizado com sucesso!");
          this.clearForm();
          this.onCloseModal.emit(); 
        },
        (ex) => {
          this.toast.error("Erro ao atualizar produto!");
          console.error(ex);
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
          this.toast.error("Erro ao cadastrar produto!");
          console.error(ex);
        }
      );
    }
  }

  cancel(): void {
    this.clearForm(); // Limpa o formulário ao cancelar
    this.onCloseModal.emit(); 
  }

  clearForm(): void {
    this.produto = this.createEmptyProduct(); // Reseta o objeto produto
    this.isEditMode = false;
  }

  // Cria um produto vazio para ser usado na inicialização e reset do formulário
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
