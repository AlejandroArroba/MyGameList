import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptorFn, HttpHandlerFn } from '@angular/common/http';
import { Observable } from 'rxjs';

// Definición del interceptor como función
export const jwtInterceptor: HttpInterceptorFn = (request: HttpRequest<any>, next: HttpHandlerFn): Observable<HttpEvent<any>> => {
  let token = null;

  // Verifica si está en el lado del cliente (navegador)
  if (typeof window !== 'undefined') {
    token = localStorage.getItem('token');
  }

  // Si hay un token, lo agrega a los headers de la solicitud
  if (token) {
    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  // Llama al siguiente manejador de la solicitud HTTP
  return next(request); // Uso de next con el request clonado
};
