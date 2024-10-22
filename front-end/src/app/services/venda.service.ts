import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class VendaService {
  private baseUrl = 'http://localhost:8080/venda';

  constructor(private http: HttpClient) { }

  getAllVendas(): any {
    return this.http.get<any>(this.baseUrl);
  }

  getVendaById(id: number): any {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createVenda(venda: Object): any {
    return this.http.post(`${this.baseUrl}`, venda);
  }

  deleteVenda(id: number): any {
    return this.http.put(`${this.baseUrl}/${id}`, {});
  }

  getDashboardInfo(): any {
    return this.http.get<any>(`${this.baseUrl}/dashboard-venda-info`);
  }
}
