import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { FuncionarioService } from '../../../services/funcionario.service';
import { Funcionario } from '../../../models/funcionario';
import { ToastrService } from 'ngx-toastr';
import { Endereco } from '../../../models/endereco';
import { Contato } from '../../../models/contato';
import { DatePipe } from '@angular/common';
import { UserFuncionario } from '../../../models/userfuncionario';

@Component({
  selector: 'app-funcionario-form',
  templateUrl: './funcionario-form.component.html',
  styleUrls: ['./funcionario-form.component.css']
})
export class FuncionarioFormComponent implements OnInit, OnChanges {
  @Input() idFuncionario: number | null = null;
  @Output() onCloseModal = new EventEmitter<void>();
  @Output() onSave = new EventEmitter<void>();

  isEditMode: boolean = false;
  userFuncionario: UserFuncionario = this.createEmptyUserFuncionario();
  endereco: Endereco = this.createEmptyEndereco();
  funcionario: Funcionario = this.createEmptyFornecedor();
  contatos: Contato[] = [];

  constructor(
    private toast: ToastrService,
    private funcionarioService: FuncionarioService,
  ) { }

  ngOnInit(): void {
    this.checkEditMode();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['idFuncionario'] && changes['idFuncionario'].currentValue !== null) {
      this.isEditMode = true;
      this.loadFuncionario(this.idFuncionario!);
    } else {
      this.isEditMode = false;
      this.funcionario = this.createEmptyFornecedor();
    }
  }

  checkEditMode(): void {
    if (this.idFuncionario != null) {
      this.isEditMode = true;
      this.loadFuncionario(this.idFuncionario);
    }
  }

  loadFuncionario(id: number): void {
    this.funcionarioService.getFuncionarioById(id).subscribe(
      (funcionario) => {
        this.funcionario = funcionario;
        this.userFuncionario = funcionario.userFuncionario;
        this.endereco = funcionario.endereco;
        this.contatos = funcionario.contatos;
      },
      (ex) => {
        this.toast.error("Erro ao encontrar funcionário: " + ex.message);
      }
    );
  }

  saveFuncionario(): void {
    const [day, month, year] = this.funcionario.dataNascimento.split('/');
    const date = new Date(+year, +month - 1, +day);

    if (isNaN(date.getTime())) {
      this.toast.error('Data inválida');
      return;
    }

    let datePipe = new DatePipe('pt-BR');
    this.funcionario.dataNascimento = datePipe.transform(date, 'dd/MM/yyyy') || '';
    this.funcionario.userFuncionario = this.userFuncionario;

    if (this.isEditMode) {
      this.funcionarioService.updateFuncionario(this.funcionario.idFuncionario, this.funcionario).subscribe(
        () => {
          this.toast.success("Funcionário atualizado com sucesso!");
          this.clearForm();
          this.onSave.emit();
          this.onCloseModal.emit();
        },
        (ex) => {
          this.toast.error("Erro ao atualizar funcionário: " + ex.message);
        }
      );
    } else {
      this.funcionarioService.createFuncionario(this.funcionario).subscribe(
        () => {
          this.toast.success("Funcionário cadastrado com sucesso!");
          this.clearForm();
          this.onSave.emit();
          this.onCloseModal.emit();
        },
        (ex) => {
          this.toast.error("Erro ao cadastrar funcionário: " + ex.message);
        }
      );
    }
  }

  addContact(): void {
    const newContact: Contato = {
      idContato: 0,
      numero: "",
      tipo: ""
    };
    this.funcionario.contatos.push(newContact);
  }

  removeContact(index: number): void {
    this.funcionario.contatos.splice(index, 1);
  }

  cancel(): void {
    this.clearForm();
    this.onCloseModal.emit();
  }

  clearForm(): void {
    this.funcionario = this.createEmptyFornecedor();
    this.userFuncionario = this.createEmptyUserFuncionario();
    this.endereco = this.createEmptyEndereco();
    this.contatos = [];
    this.isEditMode = false;
  }

  private createEmptyFornecedor(): Funcionario {
    return {
      idFuncionario: 0,
      nome: "",
      cpf: "",
      userFuncionario: this.userFuncionario,
      dataNascimento: "",
      endereco: this.endereco,
      contatos: [],
      dataCadastro: "",
      status: true,
      nivelAutenticacao: 1
    };
  }

  private createEmptyEndereco(): Endereco {
    return {
      idEndereco: 0,
      cep: "",
      logradouro: "",
      numero: "",
      complemento: "",
      bairro: "",
      cidade: "",
      estado: ""
    };
  }

  private createEmptyUserFuncionario(): UserFuncionario {
    return {
      idUserFuncionario: 0,
      username: "",
      password: ""
    };
  }
}
