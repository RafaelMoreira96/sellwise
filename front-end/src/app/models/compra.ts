import { ItemCompra } from "./itemcompra";

export interface Compra{
    idCompra: number;
    numeroCompra: string;
    dataCompra: string;
    status: string;
    fornecedorId: number;
    funcionarioId: number;
    itensCompra: ItemCompra[];
    valorTotal: number;
}