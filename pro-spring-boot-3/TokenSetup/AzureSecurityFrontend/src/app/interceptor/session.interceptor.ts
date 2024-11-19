import { HttpInterceptorFn } from '@angular/common/http';

export const sessionInterceptor: HttpInterceptorFn = (req, next) => {
  let httpRequest = req.clone({withCredentials:true});
  return next(httpRequest);
};
