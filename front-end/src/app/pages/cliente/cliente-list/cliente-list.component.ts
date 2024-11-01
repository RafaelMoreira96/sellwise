import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { Cliente } from '../../..//models/cliente';
import { ToastrService } from 'ngx-toastr';
import { ClienteService } from '../../../services/cliente.service';
import { Subject } from 'rxjs';
import { LanguageDataTable } from '../../../components/utils/language-datatable';
import { ClienteFormComponent } from '../cliente-form/cliente-form.component';

@Component({
  selector: 'app-cliente-list',
  templateUrl: './cliente-list.component.html',
  styleUrl: './cliente-list.component.css'
})
export class ClienteListComponent implements OnInit, OnDestroy {
  isModalFormOpen = false;
  isModalRemoveOpen = false;
  dtTrigger: Subject<any> = new Subject();
  dtOptions = {
    language: LanguageDataTable.BRAZILIAN_DATATABLES,
    pagingType: 'full_numbers',
    pageLength: 10,
    responsive: true,
    lengthChange: true,
  };

  isEditMode = false;
  clientes: Cliente[] = [];
  clienteSelecionado: number | null = null;

  @ViewChild(ClienteFormComponent) clienteFormComponent!: ClienteFormComponent;

  constructor(
    private toastr: ToastrService,
    private clienteService: ClienteService
  ) { }

  ngOnInit(): void {
    this.reloadClientes();
  }

  ngOnDestroy(): void {
    this.dtTrigger.unsubscribe();
  }

  reloadClientes(): void {
    this.clienteService.getAllClients().subscribe(data => {
      this.clientes = data.map((cliente: { dataNascimento: string | Date; }) => {
        cliente.dataNascimento = this.convertToDate(cliente.dataNascimento as string);
        return cliente;
      });
    }, error => {
      this.toastr.error('Erro ao carregar clientes: ' + error.message);
    });
  }

  deleteCustomer(): void {
    if (this.clienteSelecionado !== null) {
      this.clienteService.deleteClient(this.clienteSelecionado).subscribe(() => {
        this.clientes = this.clientes.filter(
          cliente => cliente.idCliente !== this.clienteSelecionado
        );
        this.toastr.success('Cliente removido com sucesso!');
      }, error => {
        this.toastr.error('Erro ao remover o cliente: ' + error.message);
        console.error(error);
      });
      this.closeModal();
    }
  }

  editCliente(idCliente: number): void {
    this.openModal(idCliente);
  }
 
  openModal(idCliente?: number): void {
    if (idCliente) {
      this.isEditMode = true;
      this.clienteSelecionado = idCliente;
      this.clienteFormComponent.loadCliente(idCliente);
    } else {
      this.isEditMode = false;
      this.clienteSelecionado = null;
      this.clienteFormComponent.clearForm();
    }
    this.isModalFormOpen = true;
    document.body.classList.add('modal-open');
  }

  openRemoveModal(idCliente: number): void {
    this.clienteSelecionado = idCliente;
    this.isModalRemoveOpen = true;
    document.body.classList.add('modal-open');
  }

  closeModal(): void {
    this.isModalFormOpen = false;
    this.isModalRemoveOpen = false;
    document.body.classList.remove('modal-open');
  }

  convertToDate(dateString: string): Date {
    const parts = dateString.split('/');
    return new Date(+parts[2], +parts[1] - 1, +parts[0]); // ano, mês, dia
  }

  onModalClose(): void {
    this.reloadClientes(); 
    this.closeModal();
  }
}
