
package com.simple_apiX.tasks.api.v1.local.Utils;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;


public class ResponseLocal {
    
    public boolean success;
    public Integer code;
    public Object data;
    public String message;
    public String description;
    
    public ResponseLocal(  ){
        this.success = true;
        this.code = 200;
    }   
    
    public HttpStatus validateService(
        Object data,
        String message,
        String class_path,
        String payload,
        HttpServletRequest req
    ){
        System.out.println( "\n============ validateService ================" );
        if( UtilsService.isErrorService(data) )
        {
            ErrorService errorService = ( ErrorService ) data;
            String message_ = errorService.getMessage();
            String description_ = errorService.getDescription();
            String class_path_ = errorService.getClass_path();
            int code_ = errorService.getCode();
            this.setError( code_, 
                message_, 
                description_, 
                new ArrayList<ObjectError>(), 
                class_path_, 
                payload, 
                req
            );
            System.out.println( "===>  Error validateService:\n" + description_ );
            HttpStatus httpStatus = this.getHttpStatus( code_ );
            return httpStatus;
        }
        else
        {
            this.success = true;
            this.code = 200;
            this.message = message;
            //this.data = data;
            this.data = ( data != null ) ? data : false;
            this.setSuccess( 
                class_path, 
                payload, 
                req
            );
            return HttpStatus.OK;
        }
    }
    
    
    public void setSuccess(
        String class_path,
        String payload,
        HttpServletRequest req
    ){
        this.description = "";
        //String ip = UtilsLocal.getIpAddr(req);

    }
    
    
    public void setError(
        Integer code, 
        String message, 
        String description, 
        List<ObjectError> listErrors,
        String class_path,
        String payload,
        HttpServletRequest req
    ){
        this.success = false;
        if( code == null ) this.code = 500;
        else this.code = code;

        if( this.code == 200 ) this.success = true;
        
        if( listErrors.size() != 0 ){
            StringBuilder messages_ = new StringBuilder();
            messages_.append("Lista_errores:");
            listErrors.forEach( err -> messages_.append( "- "+err.getDefaultMessage() ) );
            this.message = messages_.toString();
        }
        else{
            this.message = message;
        }
        
        if( description == null || description.equals("") ) description = this.message;
        this.description = description;
        
        // verificar:
        //this.data = false;
    }


    private String getUrl(HttpServletRequest req){
        String queryString = ( req.getQueryString() != null )? "?" + req.getQueryString() : "";
        return req.getRequestURL().toString() + queryString;
    }
    
    
    private HttpStatus getHttpStatus( int code ){
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if( code == 400 ) httpStatus = HttpStatus.BAD_REQUEST;
        else if( code == 401 ) httpStatus = HttpStatus.UNAUTHORIZED;
        else if( code == 403 ) httpStatus = HttpStatus.FORBIDDEN;
        else if( code == 404 ) httpStatus = HttpStatus.NOT_FOUND;
        else if( code == 405 ) httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        else if( code == 406 ) httpStatus = HttpStatus.NOT_ACCEPTABLE;
        else if( code == 409 ) httpStatus = HttpStatus.CONFLICT;
        else if( code == 408 ) httpStatus = HttpStatus.REQUEST_TIMEOUT;
        
        else if( code == 501 ) httpStatus = HttpStatus.NOT_IMPLEMENTED;
        else if( code == 502 ) httpStatus = HttpStatus.BAD_GATEWAY;
        else if( code == 503 ) httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
        else if( code == 504 ) httpStatus = HttpStatus.GATEWAY_TIMEOUT;

        return httpStatus;
    }

}
