import { FormaPagamento } from "./formapagamento";
import { ItemVenda } from "./itemvenda";

export interface Venda{
    idVenda: number;
    numeroVenda: string;
    dataVenda: string;
    status: string;
    clienteId: number;
    funcionarioId: number;
    itensVenda: ItemVenda[];
    valorTotal: number;
    formaPagamentoId: FormaPagamento;
}