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
	
	@WebMethod(operationName="getPerson")
	@WebResult(name="person")
	/**
	 * get a person by id
	 * @param id
	 * @return
	 */
	public Person getPerson(long id);

	@WebMethod(operationName="register")
	@WebResult(name="person")
	/**
	 * register new person into the system
	 * @param p person to save
	 * @return saved person
	 */
	public Person register(Person p);
	
	@WebMethod(operationName="updatePerson")
	@WebResult(name="person")
	/**
	 * update an already existing peron
	 * @param p
	 * @return
	 */
	public Person updatePerson(Person p);
	
	@WebMethod(operationName="deletePerson")
	@WebResult(name="idPerson")
	/**
	 * delete a person from the database
	 * @param id
	 * @return
	 */
	public long deletePerson(long id);
}
