package myhealthylife.dataservice.soap;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import myhealthylife.dataservice.model.entities.*;
import myhealthylife.dataservice.model.CurrentHealth;
import myhealthylife.dataservice.model.MeasureHistory;
import myhealthylife.dataservice.model.MeasureTypeList;
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
	
	@WebMethod(operationName="getPersonByUsername")
	@WebResult(name="person")
	/**
	 * This method returns a person filtered by the username.
	 * the username is unique in db
	 * @param username
	 * @return
	 */
	public Person getPersonByUsername(String username);
	
	@WebMethod(operationName="getPersonByTelegramUsername")
	@WebResult(name="person")
	/**
	 * This method returns a person filtered by the telegram username.
	 * the telegram username is unique in db
	 * @param username
	 * @return
	 */
	public Person getPersonByTelegramUsername(String username);
	
	@WebMethod(operationName="getPersonByTelegramId")
	@WebResult(name="person")
	/**
	 * this method return a person filtered by the telegram id.
	 * @param telegramId
	 * @return
	 */
	public Person getPersonByTelegramID(String telegramId);
	
	@WebMethod(operationName="getCurrentHealth")
	@WebResult(name="currentHealth")
	/**
	 * This method returns the current health profile of a person.
	 * @param idPerson the id of the person
	 * @return
	 */
	public CurrentHealth getCurrentHealth(long idPerson);
	
	@WebMethod(operationName="saveMeasure")
	@WebResult(name="measure")
	/**
	 * Save a new measure on a health profile of a person
	 * @param idPerson the id of the person on which the measure is correlated.
	 * @param measure the measure which will be saved
	 * @return
	 */
	public Measure saveMeasure(long idPerson, Measure measure);
	
	@WebMethod(operationName="getMeasure")
	@WebResult(name="measure")
	/**
	 * This method return a Measure object filtered by ID.
	 * @param mid the id of the measure
	 * @return
	 */
	public Measure getMeasure(long mid);
	
	@WebMethod(operationName="getMeasureHistory")
	@WebResult(name="measureHistory")
	/**
	 * This method returns a least of Measure which represent
	 *  the whole measure history of a person.
	 * @param personId the id of the person
	 * @return
	 */
	public MeasureHistory getMeasureHistory(long personId);
	
	@WebMethod(operationName="updateMeasure")
	@WebResult(name="measure")
	/**
	 * This method updates an already existing measure
	 * @param m measure to update
	 * @return
	 */
	public Measure updateMeasure(Measure m);
	
	@WebMethod(operationName="deleteMeasure")
	@WebResult(name="mid")
	/**
	 * This method deletes a measure from the database
	 * @param personId the id of the person which owns the measure.
	 * @param mid the id of the measure to delete.
	 * @return
	 */
	public long deleteMeasure(long personId, long mid);
	
	@WebMethod(operationName="getMeasureTypes")
	@WebResult(name="measureTypes")
	/**
	 * this method return all the measure type available
	 * @return
	 */
	public MeasureTypeList getMeasureTypesList();
	
}
