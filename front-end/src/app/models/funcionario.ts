import { Contato } from "./contato";
import { Endereco } from "./endereco";
import { UserFuncionario } from "./userfuncionario";

export interface Funcionario{
    idFuncionario: number;
    nome: string;
    cpf: string;
    userFuncionario: UserFuncionario;
    dataNascimento: any;
    endereco: Endereco;
    contatos: Contato[];
    dataCadastro: any;
    status: boolean;
    nivelAutenticacao: number;
}