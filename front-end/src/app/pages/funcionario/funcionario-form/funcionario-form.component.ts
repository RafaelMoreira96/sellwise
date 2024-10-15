import { Component, OnInit } from '@angular/core';
import { FuncionarioService } from '../../../services/funcionario.service';
import { ActivatedRoute, Router } from '@angular/router';
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
export class FuncionarioFormComponent implements OnInit {
  userFuncionario: UserFuncionario = {
    idUserFuncionario: 0,
    username: "",
    password: ""
  };

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
  funcionario: Funcionario = {
    idFuncionario: 0,
    nome: "",
    cpf: "",
    userFuncionario: this.userFuncionario,
    dataNascimento: "",
    endereco: this.endereco,
    contatos: this.contatos,
    dataCadastro: "",
    status: true,
    nivelAutenticacao: 1 // Ajuste conforme necessário
  }

  isEditMode: boolean = false;

  constructor(
    private toast: ToastrService,
    private funcionarioService: FuncionarioService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.isEditMode = true;
      this.funcionarioService.getFuncionarioById(parseInt(id)).subscribe(data => {
        this.funcionario = data;
      });
    }
  }

  saveFuncionario(): void {
    const [day, month, year] = this.funcionario.dataNascimento.split('/');

    const date = new Date(+year, +month - 1, +day);

    if (isNaN(date.getTime())) {
      console.error('Data inválida');
      console.log(date);
      return;
    }

    let datePipe = new DatePipe('pt-BR');

    this.funcionario.dataNascimento = datePipe.transform(date, 'dd/MM/yyyy') || '';
    this.funcionario.userFuncionario = this.userFuncionario;
    if (this.isEditMode) {
      this.funcionarioService.updateFuncionario(this.funcionario.idFuncionario, this.funcionario).subscribe(
        () => {
          this.toast.success("Funcionário atualizado com sucesso!");
          this.router.navigate(["/employees"]);
        },
        (ex) => {
          this.toast.error("Erro ao atualizar funcionário!");
          console.error(ex);
        }
      );
    } else {
      this.funcionarioService.createFuncionario(this.funcionario).subscribe(
        () => {
          this.toast.success("Funcionário criado com sucesso!");
          this.router.navigate(["/employees"]);
        },
        (ex) => {
          this.toast.error("Erro ao cadastrar funcionário!");
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
    this.funcionario.contatos.push(newContact);
  }

  removeContact(index: number): void {
    this.funcionario.contatos.splice(index, 1);
  }
}
