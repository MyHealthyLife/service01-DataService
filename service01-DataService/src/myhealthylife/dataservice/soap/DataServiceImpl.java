package myhealthylife.dataservice.soap;

import javax.jws.WebService;

import myhealthylife.dataservice.model.People;
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

}
