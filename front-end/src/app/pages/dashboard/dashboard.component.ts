import { Component, OnInit } from "@angular/core";
import { Cliente } from "../../models/cliente";
import { Venda } from "../../models/venda";
import { ClienteService } from "../../services/cliente.service";
import { VendaService } from "../../services/venda.service";
import { CompraService } from "../../services/compra.service";

interface CardInfo {
  icon: string;
  category: string;
  title: number;
}

interface ClienteItem {
  nome: string;
  dataCadastro: string;
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  cards_info: CardInfo[] = [];
  itens_cliente_info: ClienteItem[] = [];

  quantidade_vendas_hoje = 0;
  valor_vendas_hoje = 0.0;
  quantidade_compras_hoje = 0;
  valor_compras_hoje = 0.0;

  constructor(
    private vendaService: VendaService,
    private clienteService: ClienteService,
    private compraService: CompraService) { }

  ngOnInit() {
    this.vendaService.getDashboardInfo().subscribe((data: any) => {
      this.quantidade_vendas_hoje = data.quantidadeVendas;
      this.valor_vendas_hoje = data.valorDasVendas;
      this.updateCardsInfo();
    });

    this.compraService.getDashboardInfo().subscribe((data: any) => {
      this.quantidade_compras_hoje = data.quantidadeCompras;
      this.valor_compras_hoje = data.valorDasCompras;
      this.updateCardsInfo();
    });

    this.clienteService.getFiveLastClientes().subscribe((data: any) => {
      if (Array.isArray(data)) {
        this.itens_cliente_info = data.map((cliente: ClienteItem) => ({
          nome: cliente.nome,
          dataCadastro: cliente.dataCadastro
        }));
      } else if (data && Array.isArray(data.clientes)) {
        this.itens_cliente_info = data.clientes.map((cliente: ClienteItem) => ({
          nome: cliente.nome,
          dataCadastro: cliente.dataCadastro
        }));
      }
    });
  }

  updateCardsInfo() {
    this.cards_info = [];
    this.cards_info.push({ icon: 'fas fa-shopping-cart', category: 'Vendas realizadas', title: this.quantidade_vendas_hoje });
    this.cards_info.push({ icon: 'fas fa-dollar-sign', category: 'Valor entrada ($)', title: this.valor_vendas_hoje });
    this.cards_info.push({ icon: 'fas fa-truck-loading', category: 'Compras realizadas', title: this.quantidade_compras_hoje });
    this.cards_info.push({ icon: 'fas fa-dollar-sign', category: 'Valor saída ($)', title: this.valor_compras_hoje });
  }


}
