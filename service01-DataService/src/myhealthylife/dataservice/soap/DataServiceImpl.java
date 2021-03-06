package myhealthylife.dataservice.soap;

import java.util.Iterator;

import javax.jws.WebService;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.EnumUtils;

import myhealthylife.dataservice.model.CurrentHealth;
import myhealthylife.dataservice.model.MeasureHistory;
import myhealthylife.dataservice.model.MeasureTypeList;
import myhealthylife.dataservice.model.MeasureTypes;
import myhealthylife.dataservice.model.People;
import myhealthylife.dataservice.model.entities.HealthProfile;
import myhealthylife.dataservice.model.entities.Measure;
import myhealthylife.dataservice.model.entities.Person;
import myhealthylife.dataservice.model.generated.weather.Current;
import myhealthylife.dataservice.model.generated.weather.WeatherType;
import myhealthylife.dataservice.model.util.ServicesLocator;

@WebService(endpointInterface="myhealthylife.dataservice.soap.DataService",
	serviceName="DataService")
public class DataServiceImpl implements DataService{

	@Override
	public People getPeople() {
		People p=new People();
		p.setPerson(Person.getAll()); //get all person from the DB
		return p;
	}

	@Override
	public Person getPerson(long id) {
		
		return Person.getPersonById(id);
	}

	@Override
	public Person register(Person p) {
		return Person.savePerson(p);
	}

	@Override
	public Person updatePerson(Person p) {
		Person pStored=Person.getPersonById(p.getIdPerson());
		
		/*if the person do not exits return null*/
		if (pStored==null)
			return null;
		
		/*update the data of stored person with the data of the updated person*/
		if(p.getBirthdate()!=null)
			pStored.setBirthdate(p.getBirthdate());
		if(p.getFirstname()!=null)
			pStored.setFirstname(p.getFirstname());
		if(p.getLastname()!=null)
			pStored.setLastname(p.getLastname());
		if(p.getPassword()!=null)
			pStored.setPassword(p.getPassword());
		if(p.getTelegramUsername()!=null)
			pStored.setTelegramUsername(p.getTelegramUsername());
		if(p.getUsername()!=null)
			pStored.setUsername(p.getUsername());
		if(p.getTelegramID()!=null)
			pStored.setTelegramID(p.getTelegramID());
		if(p.getSex()!=null)
			pStored.setSex(p.getSex());
		if(p.getUsernameVisible()!=null){
			pStored.setUsernameVisible(p.getUsernameVisible());
		}
		
		if(p.getCity()!=null){
			pStored.setCity(p.getCity());
		}
		
		if(p.getCountry()!=null)
			pStored.setCountry(p.getCountry());
		
		return Person.updatePerson(pStored);
	}

	@Override
	public long deletePerson(long id) {
		Person.removePerson(id);
		return id;
	}

	@Override
	public CurrentHealth getCurrentHealth(long idPerson) {
		Person p=Person.getPersonById(idPerson);
		
		if(p==null)
			return null;
		
		if(p.getHealthProfile()==null)
			return null;
		
		return p.getHealthProfile().getCurrentHealth();
	}

	@Override
	public Measure saveMeasure(long idPerson, Measure measure) {
		Person p=Person.getPersonById(idPerson);
		
		/*if the person do not exits return null*/
		if(p==null)
			return null;
		
		if(p.getHealthProfile()==null){
			p.setHealthProfile(new HealthProfile());
		}
		
		/*check if the measure type is valid*/
		if(!EnumUtils.isValidEnum(MeasureTypes.class, measure.getMeasureType()))
			return null;
		
		p.getHealthProfile().addMeasure(measure);
		
		p=Person.updatePerson(p);
		
		//TODO cambiare con qualcosa di più corretto
		return p.getHealthProfile().getMeasureList().get(p.getHealthProfile().getMeasureList().size()-1);
	}

	@Override
	public Measure getMeasure(long mid) {
		
		return Measure.getMeasureById(mid);
	}

	@Override
	public Measure updateMeasure(Measure m) {
		Measure stored=Measure.getMeasureById(m.getMid());
		stored.setDateRegistered(m.getDateRegistered());
		stored.setMeasureType(m.getMeasureType());
		stored.setMeasureValue(m.getMeasureValue());
		
		
		/*check if the measure type is valid*/
		if(!EnumUtils.isValidEnum(MeasureTypes.class, stored.getMeasureType()))
			return null;
		
		return Measure.updateMeasure(stored);
	}

	@Override
	public long deleteMeasure(long idPerson, long mid) {
		//Measure.removeMeasure(mid);
		Person p=Person.getPersonById(idPerson);
		
		if(p==null)
			return 0;
		if(p.getHealthProfile()==null)
			return 0;
		if(p.getHealthProfile().getMeasureList()==null)
			return 0;
		
		Iterator<Measure> it=p.getHealthProfile().getMeasureList().iterator();
		while(it.hasNext()){
			Measure m=it.next();
			if(m.getMid()==mid){
				p.getHealthProfile().getMeasureList().remove(m);
				break;
			}
		}
		Person.updatePerson(p);
		return mid;
	}

	@Override
	public Person getPersonByUsername(String username) {
		
		return Person.getPersonByUsername(username);
	}

	@Override
	public Person getPersonByTelegramUsername(String username) {
		
		return Person.getPersonByTelegramUsername(username);
	}

	@Override
	public MeasureHistory getMeasureHistory(long personId) {
		MeasureHistory mh=new MeasureHistory();
		Person p=Person.getPersonById(personId);
		
		/*if the person do not exits return null*/
		if(p==null)
			return null;
		
		/*if the person does not have a healthprofile return null */
		if(p.getHealthProfile()==null)
			return null;
		
		mh.setMeasures(p.getHealthProfile().getMeasureList());
		
		return mh;
	}

	@Override
	public MeasureTypeList getMeasureTypesList() {
		
		return new MeasureTypeList();
	}

	@Override
	public Person getPersonByTelegramID(String telegramId) {
		
		return Person.getPersonByTelegramID(telegramId);
	}

	@Override
	public Current getWeather(long personId) {
		Person p=Person.getPersonById(personId);
		
		/*if the person do not exists return null*/
		if(p==null)
			return null;
		
		/*if the person do not save saved her city and her country do not return any weather info*/
		if(p.getCity()==null || p.getCountry()==null)
			return null;
		
		/*get the information from the external REST service*/
		Response weatherResponse=ServicesLocator.getWeatherService().path("data/2.5/weather")
				.queryParam("q",p.getCity()+","+p.getCountry()).queryParam("APPID","b6c89eb6cfe1da518c6fadab78cce896")
				.queryParam("mode","xml")
				.queryParam("units", "metric")
				.request()
				.get();
		
		if(weatherResponse.getStatus()!= Response.Status.OK.getStatusCode())
			return null;
		
		
		
		
		return weatherResponse.readEntity(Current.class);
	}

}
