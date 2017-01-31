package myhealthylife.dataservice.soap;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import myhealthylife.dataservice.model.entities.*;

import myhealthylife.dataservice.model.People;

@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL)
public interface DataService {
	
	@WebMethod(operationName="listPeople")
	@WebResult(name="people")
	public People getPeople();

}
