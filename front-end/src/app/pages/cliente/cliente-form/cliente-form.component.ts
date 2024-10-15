import { Component, OnInit } from '@angular/core';
import { ClienteService } from '../../../services/cliente.service';
import { ActivatedRoute, Router } from '@angular/router';
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
export class ClienteFormComponent implements OnInit {
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
  client: Cliente = {
    idCliente: 0,
    nome: "",
    cpf: "",
    dataNascimento: "",
    endereco: this.endereco,
    contatos: this.contatos,
    dataCadastro: "",
    status: true
  }

  isEditMode: boolean = false;

  constructor(
    private toast: ToastrService,
    private clienteService: ClienteService,  // Serviço do cliente
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.isEditMode = true;
      this.clienteService.getClientById(parseInt(id)).subscribe(data => {
        this.client = data;
      });
    }
  }

  saveClient(): void {
    const [month, day, year] = this.client.dataNascimento.split('/');

    // Convertendo para um objeto Date
    const date = new Date(+year, +month - 1, +day);

    // Verifica se a data é válida
    if (isNaN(date.getTime())) {
      console.error('Data inválida');
      console.log(date);
      return; // Adicione tratamento de erro aqui
    }

    let datePipe = new DatePipe('pt-BR');
    
    // Formatando a data de volta para o formato dd/MM/yyyy se necessário
    this.client.dataNascimento = datePipe.transform(date, 'dd/MM/yyyy') || '';

    if (this.isEditMode) {
      this.clienteService.updateClient(this.client.idCliente, this.client).subscribe(
        () => {
          this.toast.success("Cliente atualizado com sucesso!");
          this.router.navigate(["/customers"]);
        },
        (ex) => {
          this.toast.error("Erro ao atualizar cliente!");
          console.error(ex);
        }
      );
    } else {
      this.clienteService.createClient(this.client).subscribe(
        () => {
          this.toast.success("Cliente criado com sucesso!");
          this.router.navigate(["/customers"]);
        },
        (ex) => {
          this.toast.error("Erro ao cadastrar cliente!");
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
    this.client.contatos.push(newContact);
  }

  removeContact(index: number): void {
    this.client.contatos.splice(index, 1);
  }
}
