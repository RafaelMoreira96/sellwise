import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private baseUrl = 'http://localhost:8080/cliente';

  constructor(private http: HttpClient) { }

  getAllClients(): Observable<any> {
    return this.http.get<any>(this.baseUrl);
  }

  getClientById(id: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  createClient(client: Object): Observable<Object> {
    return this.http.post(`${this.baseUrl}`, client);
  }

  updateClient(id: number, client: Object): Observable<Object> {
    return this.http.put(`${this.baseUrl}/${id}`, client);
  }

  deleteClient(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  // funções adicionais
  getFiveLastClientes(): any {
    return this.http.get<any>(`${this.baseUrl}/catchLastAddedClientes`);
  }
}
