package myhealthylife.dataservice.soap;

import javax.jws.WebService;

import myhealthylife.dataservice.model.CurrentHealth;
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
		
		return Measure.updateMeasure(stored);
	}

	@Override
	public long deleteMeasure(long mid) {
		// TODO Auto-generated method stub
		return 0;
	}

}
