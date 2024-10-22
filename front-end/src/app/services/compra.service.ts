import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CompraService {
  private baseUrl = 'http://localhost:8080/compra'; 

  constructor(private http: HttpClient) { }

  getAllCompras(): any {
    return this.http.get<any>(this.baseUrl);
  }

  getCompraById(id: number): any {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createCompra(compra: Object): any {
    return this.http.post(`${this.baseUrl}`, compra);
  }

  deleteCompra(id: number): any {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  getDashboardInfo(): any {
    return this.http.get<any>(`${this.baseUrl}/dashboard-compra-info`);
  }
}
