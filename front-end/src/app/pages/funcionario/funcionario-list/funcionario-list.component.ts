import { Component, OnInit, ViewChild } from '@angular/core';
import { Funcionario } from '../../../models/funcionario';
import { ToastrService } from 'ngx-toastr';
import { FuncionarioService } from '../../../services/funcionario.service';
import { Subject } from 'rxjs';
import { LanguageDataTable } from '../../../components/utils/language-datatable';
import { FuncionarioFormComponent } from '../funcionario-form/funcionario-form.component';

@Component({
  selector: 'app-funcionario-list',
  templateUrl: './funcionario-list.component.html',
  styleUrls: ['./funcionario-list.component.css']
})
export class FuncionarioListComponent implements OnInit {
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

  isEditMode = false;
  funcionarios: Funcionario[] = [];
  funcionarioSelecionado: number | null = null;

  @ViewChild(FuncionarioFormComponent) funcionarioFormComponent!: FuncionarioFormComponent;

  constructor(
    private toastr: ToastrService,
    private funcionarioService: FuncionarioService
  ) { }

  ngOnInit(): void {
    this.reloadFuncionarios();
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }

  reloadFuncionarios(): void {
    this.funcionarioService.getAllFuncionarios().subscribe(data => {
      this.funcionarios = data.map((funcionario: { dataNascimento: string | Date; }) => {
        funcionario.dataNascimento = this.convertToDate(funcionario.dataNascimento as string);
        return funcionario;
      });
    }, error => {
      this.toastr.error('Erro ao carregar funcionários:' + error.message);
    });
  }

  deleteFuncionario(): void {
    if (this.funcionarioSelecionado !== null) {
      this.funcionarioService.deleteFuncionario(this.funcionarioSelecionado).subscribe(() => {
        this.funcionarios = this.funcionarios.filter(
          funcionario => funcionario.idFuncionario !== this.funcionarioSelecionado
        );
        this.toastr.success('Funcionário removido com sucesso!');
      }, error => {
        this.toastr.error('Erro ao remover o funcionário: ' + error.message);
      });
      this.closeModal();
    }
  }

  editFuncionario(idFuncionario: number): void {
    this.openModal(idFuncionario);
  }

  openModal(idFuncionario?: number): void {
    if (idFuncionario) {
      this.isEditMode = true;
      this.funcionarioSelecionado = idFuncionario;
      this.funcionarioFormComponent.loadFuncionario(idFuncionario);
    } else {
      this.isEditMode = false;
    }
    this.isModalFormOpen = true;
    document.body.classList.add('modal-open');
  }

  openRemoveModal(idFuncionario: number): void {
    this.funcionarioSelecionado = idFuncionario;
    this.isModalRemoveOpen = true;
    document.body.classList.add('modal-open');
  }

  closeModal(): void {
    this.isModalFormOpen = false;
    this.isModalRemoveOpen = false;
    document.body.classList.remove('modal-open');
  }

  onModalClose(): void {
    this.reloadFuncionarios();
    this.closeModal();
  }

  convertToDate(dateString: string): Date {
    const parts = dateString.split('/');
    return new Date(+parts[2], +parts[1] - 1, +parts[0]); // ano, mês, dia
  }
}
