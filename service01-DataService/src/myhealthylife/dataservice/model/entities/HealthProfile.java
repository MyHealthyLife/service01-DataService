package myhealthylife.dataservice.model.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import myhealthylife.dataservice.model.CurrentHealth;
import myhealthylife.dataservice.model.MeasureTypes;
import myhealthylife.dataservice.model.dao.DataServiceDao;

@Entity
@Table(name="healthprofile")
@XmlRootElement(name="healthProfile")
public class HealthProfile implements Serializable {
	
	@Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(strategy=GenerationType.AUTO) 
    @Column(name="idHealtprofile") // maps the following attribute to a column
	@XmlTransient
    private int idHealthProfile;
	
	@OneToOne(mappedBy="healthProfile", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@XmlTransient
	private Person person;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@XmlTransient
	private List<Measure> measureList;
	
	/*the current health profile is not saved in the database, is generated on-demand using the measure history*/
	@Transient
	private CurrentHealth currentHealth;
	
	
	//@Transient
	//private List<MeasureType> measureType;

	@XmlTransient
	public int getIdHealthProfile() {
		return idHealthProfile;
	}

	public void setIdHealthProfile(int idHealthProfile) {
		this.idHealthProfile = idHealthProfile;
	}

	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@XmlTransient
	public List<Measure> getMeasureList() {
		return measureList;
	}

	public void setMeasureList(List<Measure> measureList) {
		this.measureList = measureList;
	}

	public static HealthProfile save(HealthProfile h) {
        EntityManager em = DataServiceDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(h);
        tx.commit();
        DataServiceDao.instance.closeConnections(em);
        return h;
    } 
	

	/**
	 * generates and returns the current health of the person.
	 * it takes the last measures by date and type in order to generate the current health state of the person.
	 * @return
	 */
	public CurrentHealth getCurrentHealth() {
		
		List<Measure> measures=new ArrayList<>();
		
		for (MeasureTypes t : MeasureTypes.values()){
			Measure m=findMeasureByType(t);
			if(m!=null){
				measures.add(m);
			}
		}
		currentHealth=new CurrentHealth();
		currentHealth.setMeasure(measures);
		return currentHealth;
	}

	public void setCurrentHealth(CurrentHealth currentHealth) {
		this.currentHealth = currentHealth;
	}
	
	public Measure findMeasureByType(MeasureTypes type){
		if(measureList==null){
			return null;
		}
		
		if(measureList.size()==0){
			return null;
		}
		
		List<Measure> measures=new ArrayList<>(measureList);
		Collections.sort(measures);
		
		Iterator<Measure> it=measures.iterator();
		
		while(it.hasNext()){
			Measure m=it.next();
			if(m.getMeasureType().equals(type.toString())){
				return m;
			}
		}
		
		return null;
	}

}