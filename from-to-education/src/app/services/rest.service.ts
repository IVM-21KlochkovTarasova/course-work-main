import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {environment} from '../../environments/environment';
import {Papa} from 'ngx-papaparse';

@Injectable({
  providedIn: 'root'
})
export class RestService {

  constructor(private http: HttpClient,
              private papa: Papa) { }


  private jsonHeaders = new HttpHeaders({'Content-Type': 'application/json; charset=UTF-8'});

  post(srcURL: string, method: string, params: any): Observable<any> {
    return this.request('POST', method, params, srcURL);
  }


  postFile(srcURL: string, method: string, params: any, file: any): Observable<any> {
    const formData = new FormData();
    if (file != null) {
      formData.append('file', file, file.name);
    };
    formData.append('data', JSON.stringify(params));
    const req = new HttpRequest('POST', srcURL + '/' + method, formData);
    return this.http.request(req);
  }

  put(srcURL: string, method: string, params: any): Observable<any> {
    return this.request('PUT', method, params, srcURL);
  }

  delete(srcURL: string, method: string, params: any): Observable<any> {
    return this.request('DELETE', method, params, srcURL);
  }

  get(srcURL: string, method: string, params: any = {}): Observable<any> {
    if (!method.startsWith('/')) {
      method = '/' + method;
    }
    const url = srcURL + method;
    return this.http.get(url, {params: params}).pipe(
      map((value: any) => {
        if (value.error_code !== undefined && value.error_code > 0) {
          throw value;
        }
        return value;
      })
    );
  }

  request(httpMethod: 'POST'|'PUT'|'DELETE', method: string, params: any, srcURL: string): Observable<any> {
    if (!method.startsWith('/')) {
      method = '/' + method;
    }
    const url = srcURL + method;

    const options = {
      body: undefined,
      headers: this.jsonHeaders
    };

    options.body = params;
    return this.http.request(httpMethod, url, options).pipe(
      map((value: any) => {
        if (value.error_code !== undefined && value.error_code > 0) {
         throw value;
        }
        return value;
      })
    );
  }

  getLocalReportData(id: string): Promise<any> {
    // @ts-ignore
    return this.http.get('assets/' + id, {responseType: 'text/csv'})
      .pipe(
        map(val => String(val)),
        map(val => this.papa.parse(val, {
          header: true,
          skipEmptyLines: true
        })),
        map(val => val.data)
      ).toPromise();
  }

  download() {

  }

  getReportDataJsonLocal(id: string): Promise<any> {
    return this.http.get('assets/' + id)
      .toPromise();
  }

  logout(): void {
    // this.post('logout', {}).subscribe(
    //   result => {
    //     this.sessionService.logout();
    //   }, error => {
    //     this.logger.error(error);
    //     this.logger.errorMessage('Ошибка',
    //       error.hasOwnProperty('error_message') ? error.error_message : 'Произошла ошибка закрытия сессии.');
    //     this.sessionService.logout();
    //   }
    // )
  }
}
