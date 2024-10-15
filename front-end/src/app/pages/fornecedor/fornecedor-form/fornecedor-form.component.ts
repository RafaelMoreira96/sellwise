import { Component, OnInit } from '@angular/core';
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

  endereco: Endereco = {
    idEndereco: 0,
    cep: "",
    logradouro: "",
    numero: "",
    complemento: "",
    bairro: "",
    cidade: "",
    estado: ""
  };

  contatos: Contato[] = [];
  fornecedor: Fornecedor = {
    idFornecedor: 0,
    razaoSocial: '',
    nomeFantasia: '',
    inscricaoEstadual: '',
    cnpj: '',
    endereco: this.endereco,
    contatos: this.contatos,
    dataCadastro: '',
    status: true
  }

  isEditMode: boolean = false;

  constructor(
    private toast: ToastrService,
    private fornecedorService: FornecedorService,  // Serviço do fornecedor
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.isEditMode = true;
      this.fornecedorService.getFornecedorById(parseInt(id)).subscribe(data => {
        this.fornecedor = data;
      });
    }
  }

  saveFornecedor(): void {
    let datePipe = new DatePipe('pt-BR');
    this.fornecedor.dataCadastro = datePipe.transform(new Date(), 'dd/MM/yyyy') || '';

    if (this.isEditMode) {
      this.fornecedorService.updateFornecedor(this.fornecedor.idFornecedor, this.fornecedor).subscribe(
        () => {
          this.toast.success("Fornecedor atualizado com sucesso!");
          this.router.navigate(["/fornecedores"]);
        },
        (ex) => {
          this.toast.error("Erro ao atualizar fornecedor!");
          console.error(ex);
        }
      );
    } else {
      this.fornecedorService.createFornecedor(this.fornecedor).subscribe(
        () => {
          this.toast.success("Fornecedor cadastrado com sucesso!");
          this.router.navigate(["/fornecedores"]);
        },
        (ex) => {
          this.toast.error("Erro ao cadastrar fornecedor!");
          console.error(ex);
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
}
