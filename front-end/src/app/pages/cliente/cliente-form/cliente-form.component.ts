import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { ClienteService } from '../../../services/cliente.service';
import { Cliente } from '../../../models/cliente'; 
import { ToastrService } from 'ngx-toastr';
import { Endereco } from '../../../models/endereco';
import { Contato } from '../../../models/contato';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-cliente-form',
  templateUrl: './cliente-form.component.html',
  styleUrl: './cliente-form.component.css'
})
export class ClienteFormComponent implements OnInit, OnChanges {
  @Input() idCliente: number | null = null;
  @Output() onCloseModal = new EventEmitter<void>();
  @Output() onSave = new EventEmitter<void>();

  isEditMode: boolean = false;
  cliente: Cliente = this.createEmptyCliente();
  endereco: Endereco = this.createEmptyEndereco();
  contatos: Contato[] = [];

  constructor(
    private toast: ToastrService,
    private clienteService: ClienteService,
  ) { }

  ngOnInit(): void {
    this.checkEditMode();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['idCliente'] && changes['idCliente'].currentValue !== null) {
      this.isEditMode = true;
      this.loadCliente(this.idCliente!);
    } else {
      this.isEditMode = false;
      this.cliente = this.createEmptyCliente();
    }
  }

  checkEditMode(): void {
    if (this.idCliente!= null) {
      this.isEditMode = true;
      this.loadCliente(this.idCliente);
    }
  }

  loadCliente(id: number): void {
    this.clienteService.getClientById(id).subscribe(
      (cliente) => {
        this.cliente = cliente;
        this.endereco = cliente.endereco;
        this.contatos = cliente.contatos;
      },
      (ex) => {
        console.error("Erro ao encontrar cliente: " + ex.message);
      }
    );
  }

  saveCliente(): void {
    const [month, day, year] = this.cliente.dataNascimento.split('/');
    const date = new Date(+year, +month - 1, +day);

    if (isNaN(date.getTime())) {
      this.toast.error("Data inválida!");
      return;
    }

    let datePipe = new DatePipe('pt-BR');
    this.cliente.dataNascimento = datePipe.transform(date, 'dd/MM/yyyy') || '';
    this.cliente.dataCadastro = datePipe.transform(new Date(), 'dd/MM/yyyy') || '';

    if (this.isEditMode) {
      this.clienteService.updateCliente(this.cliente.idCliente, this.cliente).subscribe(
        () => {
          this.toast.success("Cliente atualizado com sucesso!");
          this.clearForm();
          this.onSave.emit();
          this.onCloseModal.emit();
        },
        (ex) => {
          this.toast.error("Erro ao atualizar cliente: " + ex.message);
        }
      );
    } else {
      this.clienteService.createCliente(this.cliente).subscribe(
        () => {
          this.toast.success("Cliente criado com sucesso!");
          this.clearForm();
          this.onCloseModal.emit();
        },
        (ex) => {
          this.toast.error("Erro ao cadastrar cliente: " + ex.message);
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
    this.cliente.contatos.push(newContact);
  }

  removeContact(index: number): void {
    this.cliente.contatos.splice(index, 1);
  }

  cancel(): void {
    this.clearForm();
    this.onCloseModal.emit();
  }

  clearForm(): void {
    this.cliente = this.createEmptyCliente();
    this.endereco = this.createEmptyEndereco();
    this.contatos = [];
    this.isEditMode = false;
  }

  private createEmptyCliente(): Cliente {
    return {
      idCliente: 0,
      nome: "",
      cpf: "",
      dataNascimento: "",
      endereco: this.endereco,
      contatos: this.contatos,
      dataCadastro: "",
      status: true
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
}
