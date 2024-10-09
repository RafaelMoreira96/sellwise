export interface Produto {
    idProduto: number;
    descricao: string;
    codBarras: string;
    precoAtacado: number;
    precoVarejo: number;
    status: boolean;
    qteEstoque: number;
    qteMin: number;
    qteMax: number;
}