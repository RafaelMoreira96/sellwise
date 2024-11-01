import { Component, EventEmitter, Input, OnInit, Output, SimpleChanges } from '@angular/core';
import { FornecedorService } from '../../../services/fornecedor.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Fornecedor } from '../../../models/fornecedor';
import { ToastrService } from 'ngx-toastr';
import { Endereco } from '../../../models/endereco';
import { Contato } from '../../../models/contato';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-fornecedor-form',
  templateUrl: './fornecedor-form.component.html',
  styleUrl: './fornecedor-form.component.css'
})
export class FornecedorFormComponent implements OnInit {
  @Input() idFornecedor: number | null = null;
  @Output() onCloseModal = new EventEmitter<void>();
  @Output() onSave = new EventEmitter<void>();

  isEditMode: boolean = false;
  fornecedor: Fornecedor = this.createEmptyFornecedor();
  endereco: Endereco = this.createEmptyEndereco();
  contatos: Contato[] = [];

  constructor(
    private toast: ToastrService,
    private fornecedorService: FornecedorService,
  ) { }

  ngOnInit(): void {
    this.checkEditMode();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['idFornecedor'] && changes['idFornecedor'].currentValue!== null) {
      this.isEditMode = true;
      this.loadFornecedor(this.idFornecedor!);
    } else {
      this.isEditMode = false;
      this.fornecedor = this.createEmptyFornecedor();
    }
  }

  checkEditMode(): void {
    if (this.idFornecedor!= null) {
      this.isEditMode = true;
      this.loadFornecedor(this.idFornecedor);
    }
  }

  loadFornecedor(id: number): void {
    this.fornecedorService.getFornecedorById(id).subscribe(
      (fornecedor) => {
        this.fornecedor = fornecedor;
        this.endereco = fornecedor.endereco;
        this.contatos = fornecedor.contatos;
      },
      (error) => {
        this.toast.error("Erro ao carregar fornecedor: " + error.message);
      }
    );
  }

  saveFornecedor(): void {
    let datePipe = new DatePipe('pt-BR');
    this.fornecedor.dataCadastro = datePipe.transform(new Date(), 'dd/MM/yyyy') || '';

    if (this.isEditMode) {
      this.fornecedorService.updateFornecedor(this.fornecedor.idFornecedor, this.fornecedor).subscribe(
        () => {
          this.toast.success("Fornecedor atualizado com sucesso!");
          this.clearForm();
          this.onSave.emit();
          this.onCloseModal.emit();
        },
        (ex) => {
          this.toast.error("Erro ao atualizar fornecedor: " + ex.message);
        }
      );
    } else {
      this.fornecedorService.createFornecedor(this.fornecedor).subscribe(
        () => {
          this.toast.success("Fornecedor cadastrado com sucesso!");
          this.clearForm();
          this.onCloseModal.emit();
        },
        (ex) => {
          this.toast.error("Erro ao cadastrar fornecedor: " + ex.message);
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
    this.fornecedor.contatos.push(newContact);
  }

  removeContact(index: number): void {
    this.fornecedor.contatos.splice(index, 1);
  }

  cancel(): void {
    this.clearForm();
    this.onCloseModal.emit();
  }

  clearForm(): void {
    this.fornecedor = this.createEmptyFornecedor();
    this.endereco = this.createEmptyEndereco();
    this.contatos = [];
    this.isEditMode = false;
  }

  private createEmptyFornecedor(): Fornecedor {
    return {
      idFornecedor: 0,
      razaoSocial: "",
      nomeFantasia: "",
      inscricaoEstadual: "",
      cnpj: "",
      endereco: this.endereco,
      contatos: [],
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
