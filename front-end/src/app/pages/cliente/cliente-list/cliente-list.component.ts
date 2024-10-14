import { Component } from '@angular/core';
import { Cliente } from '../../..//models/cliente'; 
import { ToastrService } from 'ngx-toastr';
import { ClienteService } from '../../../services/cliente.service';

@Component({
  selector: 'app-cliente-list',
  templateUrl: './cliente-list.component.html',
  styleUrl: './cliente-list.component.css'
})
export class ClienteListComponent {
  clientes: Cliente[] = [];
 
  constructor(
    private toastr: ToastrService,
    private clienteService: ClienteService
  ) {}

  ngOnInit(): void {
    this.clienteService.getAllClients().subscribe(data => {
      this.clientes = data.map((cliente: { dataNascimento: string | Date; }) => {
        cliente.dataNascimento = this.convertToDate(cliente.dataNascimento as string);
        return cliente;
      });
    });
    
  }

  deleteCustomer(idCliente: number): void {
    this.clienteService.deleteClient(idCliente).subscribe(() => {
      this.clientes = this.clientes.filter(
        cliente => cliente.idCliente !== idCliente
      );
      this.toastr.success('Cliente removido com sucesso!');
    });
  }

  convertToDate(dateString: string): Date {
    const parts = dateString.split('/');
    return new Date(+parts[2], +parts[1] - 1, +parts[0]); // ano, mês, dia
  }
}
