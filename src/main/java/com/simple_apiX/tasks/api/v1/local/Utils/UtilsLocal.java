
package com.simple_apiX.tasks.api.v1.local.Utils;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import org.springframework.validation.ObjectError;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;


public class UtilsLocal {
    
    public static Validator getValidatorDto(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator;
     }
    
    public static String getErrorMessagesList ( List<String> errors  ){
        StringBuilder messages = new StringBuilder();
        messages.append("Lista_errores:");
        errors.forEach( err -> messages.append( "- "+err ) );
        return messages.toString();
     }
    
    public static ArrayList<ObjectError> emptyErrorList(){
        return new ArrayList<ObjectError>();
    }
    
    public static String arrayStringToString( String [] array ){
        String str = String.join(", ", array );
        return str;
    }
    
    public static String numbersOrNull (){
        return "^[0-9]+$|^(null)$";
    }
    
    public static String onlyNumbers (){
        return "^[0-9]+$";
    }
    
    public static String getRealPath(HttpServletRequest req){
        return req.getSession().getServletContext().getRealPath("/");
    }

    public static String getDateTimeNow(){
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeNow = DateTime.now(DateTimeZone.forID("America/Bogota")).toString(formatter);
        return dateTimeNow;
    }

    public static ZonedDateTime getZonedDateTimeNow(){
        ZonedDateTime zdtWithZoneOffset = ZonedDateTime.parse(DateTime.now(DateTimeZone.forID("America/Bogota")).toString() );
        UtilsLocal.log(" ......... zdtWithZoneOffset: "); UtilsLocal.log( zdtWithZoneOffset );
        return zdtWithZoneOffset;
    }

	/**
	 * Convertir una fecha de String a Timestamp
	 * Ejemplos formatos [ format ]
	 * "yyyy-MM-dd hh:mm:ss.SSS"
	 * "yyyy-MM-dd hh:mm:ss"
	 * "yyyy-MM-dd"
	 */
	public static Timestamp strDateToTimestamp ( String strDate, String format )
    {
		SimpleDateFormat dateFormat = new SimpleDateFormat( format );

		Timestamp timestamp = null;
		Date parsedDate;
		try {
		    parsedDate = dateFormat.parse( strDate );
		    timestamp = new java.sql.Timestamp( parsedDate.getTime() );
		}
		catch ( ParseException e ){
		    System.out.println( "Error convirtiendo String a Timestamp: " );
		    System.out.println( e.getMessage() );
		}
		System.out.println( "Fecha convertida: " ); System.out.println( timestamp );
		return timestamp;
    }
    
    public static void log( Object info ){
        System.out.println( info );
    }

    public static String getIpAddr(HttpServletRequest req ) {
        String ip = req.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        return ip;
    }
    
}
