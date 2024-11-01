import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Fornecedor } from '../../..//models/fornecedor';
import { ToastrService } from 'ngx-toastr';
import { FornecedorService } from '../../../services/fornecedor.service';
import { Subject } from 'rxjs';
import { LanguageDataTable } from '../../../components/utils/language-datatable';
import { FornecedorFormComponent } from '../fornecedor-form/fornecedor-form.component';

@Component({
  selector: 'app-fornecedor-list',
  templateUrl: './fornecedor-list.component.html',
  styleUrl: './fornecedor-list.component.css'
})
export class FornecedorListComponent implements OnInit, OnDestroy {
  isModalFormOpen = false;
  isModalRemoveOpen = false;
  dtTrigger: Subject<any> = new Subject();
  dtOptions = {
    language: LanguageDataTable.BRAZILIAN_DATATABLES,
    pagingType: 'full_numbers',
    pageLength: 10,
    responsive: true,
    lengthChange: true,
  };

  isEditMode: boolean = false;
  fornecedores: Fornecedor[] = [];
  fornecedorSelecionado: number | null = null;

  @ViewChild(FornecedorFormComponent) fornecedorFormComponent!: FornecedorFormComponent;

  constructor(
    private toastr: ToastrService,
    private fornecedorService: FornecedorService
  ) { }

  ngOnInit(): void {
    this.reloadFornecedores();
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }

  reloadFornecedores(): void {
    this.fornecedorService.getAllFornecedores().subscribe(data => {
      this.fornecedores = data;
    }, error => {
      this.toastr.error('Erro ao carregar fornecedores: ' + error.message);
    });
  }

  deleteFornecedor(): void {
    if (this.fornecedorSelecionado !== null) {
      this.fornecedorService.deleteFornecedor(this.fornecedorSelecionado).subscribe(() => {
        this.fornecedores = this.fornecedores.filter(
          fornecedor => fornecedor.idFornecedor !== this.fornecedorSelecionado
        );
        this.toastr.success('Fornecedor removido com sucesso!');
      }, error => {
        this.toastr.error('Erro ao remover o fornecedor: ' + error.message);
      });
      this.closeModal();
    }
  }

  editFornecedor(idFornecedor: number): void {
    this.openModal(idFornecedor);
  }

  openModal(idFornecedor?: number): void {
    if (idFornecedor) {
      this.isEditMode = true;
      this.fornecedorSelecionado = idFornecedor;
      this.fornecedorFormComponent.loadFornecedor(idFornecedor);
    } else {
      this.isEditMode = false;
    }
    this.isModalFormOpen = true;
    document.body.classList.add('modal-open');
  }

  openRemoveModal(idFornecedor: number): void {
    this.fornecedorSelecionado = idFornecedor;
    this.isModalRemoveOpen = true;
    document.body.classList.add('modal-open');
  }

  closeModal(): void {
    this.isModalFormOpen = false;
    this.isModalRemoveOpen = false;
    document.body.classList.remove('modal-open');
  }

  onModalClose(): void {
    this.reloadFornecedores();
    this.closeModal();
  }

  convertToDate(dateString: string): Date {
    const parts = dateString.split('/');
    return new Date(+parts[2], +parts[1] - 1, +parts[0]); // ano, mês, dia
  }
}
