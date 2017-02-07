package myhealthylife.dataservice.soap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.jws.WebService;

import org.apache.commons.lang3.EnumUtils;

import myhealthylife.dataservice.model.CurrentHealth;
import myhealthylife.dataservice.model.MeasureHistory;
import myhealthylife.dataservice.model.MeasureTypeList;
import myhealthylife.dataservice.model.MeasureTypes;
import myhealthylife.dataservice.model.People;
import myhealthylife.dataservice.model.entities.HealthProfile;
import myhealthylife.dataservice.model.entities.Measure;
import myhealthylife.dataservice.model.entities.Person;

@WebService(endpointInterface="myhealthylife.dataservice.soap.DataService",
	serviceName="DataService")
public class DataServiceImpl implements DataService{

	@Override
	public People getPeople() {
		People p=new People();
		p.setPerson(Person.getAll());
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
		
		if (pStored==null)
			return null;
		
		pStored.setBirthdate(p.getBirthdate());
		pStored.setFirstname(p.getFirstname());
		pStored.setLastname(p.getLastname());
		pStored.setPassword(p.getPassword());
		pStored.setTelegramUsername(p.getTelegramUsername());//TODO memorizzrlo nel tier superiore
		pStored.setUsername(p.getUsername());
		
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
		
		if(p==null)
			return null;
		
		if(p.getHealthProfile()==null)
			return null;
		
		mh.setMeasures(p.getHealthProfile().getMeasureList());
		
		return mh;
	}

	@Override
	public MeasureTypeList getMeasureTypesList() {
		
		return new MeasureTypeList();
	}

}
