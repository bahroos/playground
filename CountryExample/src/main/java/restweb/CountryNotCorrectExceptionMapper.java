package restweb;

import exception.CountryNotAcceptableException;
import exception.CountryNotFoundException;
import model.ExceptionMessage;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CountryNotCorrectExceptionMapper implements ExceptionMapper<CountryNotAcceptableException>{

	@Override
	public Response toResponse(CountryNotAcceptableException cnfe) {
		
		ExceptionMessage exceptionMessage= new ExceptionMessage(cnfe.getExceptionMessage(),"406");
		return Response.status(Status.NOT_ACCEPTABLE).entity(exceptionMessage).build();
	}

	
}
