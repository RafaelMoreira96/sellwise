import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-table-vendas-dashboard',
  templateUrl: './table-vendas-dashboard.component.html',
  styleUrl: './table-vendas-dashboard.component.css'
})
export class TableVendasDashboardComponent {
  @Input() vendas: any[] = [];
}
