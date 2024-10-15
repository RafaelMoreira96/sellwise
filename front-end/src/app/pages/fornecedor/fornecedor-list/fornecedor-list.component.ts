import { Component } from '@angular/core';
import { Fornecedor } from '../../..//models/fornecedor'; 
import { ToastrService } from 'ngx-toastr';
import { FornecedorService } from '../../../services/fornecedor.service';

@Component({
  selector: 'app-fornecedor-list',
  templateUrl: './fornecedor-list.component.html',
  styleUrl: './fornecedor-list.component.css'
})
export class FornecedorListComponent {
  fornecedores: Fornecedor[] = [];
 
  constructor(
    private toastr: ToastrService,
    private fornecedorService: FornecedorService
  ) {}

  ngOnInit(): void {
    this.fornecedorService.getAllFornecedores().subscribe(data => {
      this.fornecedores = data.map((fornecedor: { dataCadastro: string | Date; }) => {
        fornecedor.dataCadastro = this.convertToDate(fornecedor.dataCadastro as string);
        return fornecedor;
      });
    });
  }

  deleteFornecedor(idFornecedor: number): void {
    this.fornecedorService.deleteFornecedor(idFornecedor).subscribe(() => {
      this.fornecedores = this.fornecedores.filter(
        fornecedor => fornecedor.idFornecedor !== idFornecedor
      );
      this.toastr.success('Fornecedor removido com sucesso!');
    });
  }

  convertToDate(dateString: string): Date {
    const parts = dateString.split('/');
    return new Date(+parts[2], +parts[1] - 1, +parts[0]); // ano, mês, dia
  }
}
