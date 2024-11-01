import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Produto } from '../../../models/produto';
import { ProdutoService } from '../../../services/produto.service';
import { ProdutoFormComponent } from '../produto-form/produto-form.component';
import { LanguageDataTable } from '../../../components/utils/language-datatable';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-produto-list',
  templateUrl: './produto-list.component.html',
  styleUrls: ['./produto-list.component.css']
})
export class ProdutoListComponent implements OnInit, OnDestroy {
  isModalFormOpen = false;
  isModalRemoveOpen = false;
  dtTrigger: Subject<any> = new Subject();
  dtOptions = {
    language: LanguageDataTable.BRAZILIAN_DATATABLES,
    pagingType: 'full_numbers',
    pageLength: 10,
    responsive: true,
    lengthChange: true,
    reload: true
  };

  isEditMode = false;
  produtos: Produto[] = [];
  produtoSelecionado: number | null = null;

  @ViewChild(ProdutoFormComponent) produtoFormComponent!: ProdutoFormComponent;

  constructor(private toastr: ToastrService, private produtoService: ProdutoService) { }

  ngOnInit(): void {
    this.reloadProducts();
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }

  reloadProducts() {
    this.produtoService.getAllProducts().subscribe(data => {
      this.produtos = data;
      this.dtTrigger.next(null);
    }, error => {
      this.toastr.error("Erro ao carregar produtos: " + error.message);
      console.error(error);
    });
  }
  
  deleteProduct(): void {
    if (this.produtoSelecionado !== null) {
      this.produtoService.deleteProduct(this.produtoSelecionado).subscribe(() => {
        this.produtos = this.produtos.filter(product => product.idProduto !== this.produtoSelecionado);
        this.toastr.success("Produto removido com sucesso!");
        window.location.reload();
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

  closeModal(): void {
    this.isModalFormOpen = false;
    this.isModalRemoveOpen = false;
    document.body.classList.remove('modal-open');
  }

  onModalClose(): void {
    this.reloadProducts(); 
    this.closeModal();
  }
}
