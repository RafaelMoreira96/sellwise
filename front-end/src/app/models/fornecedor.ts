import { Contato } from "./contato";
import { Endereco } from "./endereco";

export interface Fornecedor {
    idFornecedor: number;
    razaoSocial: string;
    nomeFantasia: string;
    inscricaoEstadual: string;
    cnpj: string;
    endereco: Endereco;
    contatos: Contato[];
    dataCadastro: any;
    status: boolean;
}