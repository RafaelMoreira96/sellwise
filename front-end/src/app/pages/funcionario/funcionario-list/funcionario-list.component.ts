import { Component, OnInit } from '@angular/core';
import { Funcionario } from '../../../models/funcionario'; 
import { ToastrService } from 'ngx-toastr';
import { FuncionarioService } from '../../../services/funcionario.service';

@Component({
  selector: 'app-funcionario-list',
  templateUrl: './funcionario-list.component.html',
  styleUrls: ['./funcionario-list.component.css']
})
export class FuncionarioListComponent implements OnInit {
  funcionarios: Funcionario[] = [];
 
  constructor(
    private toastr: ToastrService,
    private funcionarioService: FuncionarioService
  ) {}

  ngOnInit(): void {
    this.funcionarioService.getAllFuncionarios().subscribe(data => {
      this.funcionarios = data.map((funcionario: { dataNascimento: string | Date; }) => {
        funcionario.dataNascimento = this.convertToDate(funcionario.dataNascimento as string);
        return funcionario;
      });
    });
  }

  deleteEmployee(idFuncionario: number): void {
    this.funcionarioService.deleteFuncionario(idFuncionario).subscribe(() => {
      this.funcionarios = this.funcionarios.filter(
        funcionario => funcionario.idFuncionario !== idFuncionario
      );
      this.toastr.success('Funcionário removido com sucesso!');
    });
  }

  convertToDate(dateString: string): Date {
    const parts = dateString.split('/');
    return new Date(+parts[2], +parts[1] - 1, +parts[0]); // ano, mês, dia
  }
}
