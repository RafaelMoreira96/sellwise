import { Component } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

import { ItemVenda } from '../../../models/itemvenda';
import { Venda } from '../../../models/venda';
import { Cliente } from '../../../models/cliente';
import { Endereco } from '../../../models/endereco';
import { Funcionario } from '../../../models/funcionario';
import { FormaPagamento } from '../../../models/formapagamento';

import { ClienteService } from '../../../services/cliente.service';
import { FuncionarioService } from '../../../services/funcionario.service';
import { FormaPagamentoService } from '../../../services/forma-pagamento.service';
import { ProdutoService } from '../../../services/produto.service';
import { VendaService } from '../../../services/venda.service';

@Component({
  selector: 'app-pdv',
  templateUrl: './pdv.component.html',
  styleUrl: './pdv.component.css'
})
export class PdvComponent {
  showModal: boolean = false;

  listFormaPagamento: FormaPagamento[] = [];
  
  endereco: Endereco = {
    idEndereco: 0,
    cep: '',
    logradouro: '',
    numero: '',
    complemento: '',
    bairro: '',
    cidade: '',
    estado: '',
  };

  cliente: Cliente = {
    idCliente: 0,
    nome: '',
    cpf: '',
    status: false,
    dataNascimento: undefined,
    endereco: this.endereco,
    contatos: [],
    dataCadastro: undefined
  };

  funcionario: Funcionario = {
    idFuncionario: 0,
    nome: '',
    cpf: '',
    status: false,
    dataNascimento: undefined,
    endereco: this.endereco,
    contatos: [],
    dataCadastro: undefined,
    nivelAutenticacao: 0
  }

  listItem: ItemVenda[] = [];
  indice = 0;

  formaPagamento: FormaPagamento = {
    idFormaPagamento: 0,
    descricao: '',
    status: false,
  };

  itemVenda: ItemVenda = {
    idItemVenda: 0,
    idProduto: 0,
    descricao: '',
    codBarras: '',
    precoVendido: 0,
    quantidade: 0,
    desconto: 0,
  };

  venda: Venda = {
    idVenda: 0,
    numeroVenda: '',
    dataVenda: '',
    status: '',
    clienteId: 0,
    funcionarioId: 0,
    itensVenda: this.listItem,
    valorTotal: 0.0,
    formaPagamentoId: this.formaPagamento,
  };

  totalGeral = 0.0;
  totalDescontos = 0.0;

  constructor(
    private clienteService: ClienteService,
    private funcionarioService: FuncionarioService,
    private formaPagamentoService: FormaPagamentoService,
    private produtoService: ProdutoService,
    private vendaService: VendaService,
    private toast: ToastrService
  ) {}

  ngOnInit(): void {
    this.findAllFormaPagamento();
    console.log("Forma de Pagamento", this.listFormaPagamento);
    this.findFuncionario();
    console.log('Funcionario', this.funcionario);
    this.findCliente();
    console.log('Cliente', this.cliente);
    this.insertProduct();
    console.log('Item', this.itemVenda);
  }

  findAllFormaPagamento(): void {
    this.formaPagamentoService.getAllFormasPagamento().subscribe((data: any) => {
      this.listFormaPagamento = data;
    });
  }

  findFuncionario(): void {
    this.funcionarioService.getFuncionarioById(4).subscribe((data: any) => {
      this.funcionario = data;
    });
  }

  findCliente(): void {
    this.clienteService.getClientById(1).subscribe((data: any) => {
      this.cliente = data;
    });
  }

  insertProduct(): void {
    this.produtoService.getProductById(1).subscribe((data: any) => {
      this.itemVenda = {
        idItemVenda: this.indice,
        idProduto: data.idProduto,
        descricao: data.descricao,
        codBarras: data.codBarras,
        precoVendido: data.precoVenda,
        quantidade: 1,
        desconto: 0,
      };
      this.listItem.push(this.itemVenda);
      this.indice++;
      this.calcularTotal();
    });
  }

  calcularTotal(): void {
    this.totalGeral = 0.0;
    this.totalDescontos = 0.0;
    this.listItem.forEach((item) => {
      this.totalGeral += item.precoVendido * item.quantidade;
      this.totalDescontos += item.desconto;
    });
  }

  removeItem(item: ItemVenda): void {
    this.listItem = this.listItem.filter(i => i.idItemVenda !== item.idItemVenda);
    this.calcularTotal();
    this.toast.success('Item removido com sucesso!', 'Remoção');
  }
  
  
}
