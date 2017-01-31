package myhealthylife.dataservice.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import myhealthylife.dataservice.model.dao.DataServiceDao;

@Entity
@Table(name="Person")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
@XmlRootElement(name="person")
public class Person implements Serializable{

	@Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(strategy=GenerationType.AUTO) 
	@TableGenerator(name="sqlite_person", table="sqlite_sequence",pkColumnName="name", valueColumnName="seq", pkColumnValue="Person")
    @Column(name="idPerson") // maps the following attribute to a column
    private int idPerson;
	
	@Column(name="name")
	private String firstname;
	
	@Column(name="lastname")
	private String lastname;
	
	@Temporal(TemporalType.DATE) // defines the precision of the date attribute
    @Column(name="birthdate")
	private Date birthdate;

	public int getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	public static List<Person> getAll() {
        EntityManager em = DataServiceDao.instance.createEntityManager();
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
            .getResultList();
        DataServiceDao.instance.closeConnections(em);
        return list;
    }
}
