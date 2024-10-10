import { Contato } from "./contato";
import { Endereco } from "./endereco";

export interface Cliente {
    idCliente: number;
    nome: string;
    cpf: string;
    dataNascimento: any;
    endereco: Endereco;
    contatos: Contato[];
    dataCadastro: any;
    status: boolean;

}
