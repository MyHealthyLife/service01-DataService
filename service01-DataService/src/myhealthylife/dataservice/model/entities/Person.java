package myhealthylife.dataservice.model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;

import myhealthylife.dataservice.model.dao.DataServiceDao;

@Entity
@Table(name="Person")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
@XmlRootElement(name="person")
public class Person implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(strategy=GenerationType.AUTO) 
    @Column(name="idPerson") // maps the following attribute to a column
    private long idPerson;
	
	@Column(name="name")
	private String firstname;
	
	@Column(name="lastname")
	private String lastname;
	
	@Column(name="sex")
	private String sex;
	
	@Temporal(TemporalType.DATE) // defines the precision of the date attribute
    @Column(name="birthdate")
	private Date birthdate;
	
	@Column(name="username",unique=true)
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="telegram_username",unique=true)
	private String telegramUsername;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private HealthProfile healthProfile;
	
	@Column(name="telgram_id",unique=true)
	private String telegramID;

	public long getIdPerson() {
		return idPerson;
	}

	public void setIdPerson(long idPerson) {
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
	
	public static Person getPersonById(long personId) {
        EntityManager em = DataServiceDao.instance.createEntityManager();
        Person p = em.find(Person.class, personId);
        DataServiceDao.instance.closeConnections(em);
        return p;
    }
	
	public static Person savePerson(Person p) {
        EntityManager em = DataServiceDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        DataServiceDao.instance.closeConnections(em);
        return p;
    } 

    public static Person updatePerson(Person p) {
        EntityManager em = DataServiceDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        DataServiceDao.instance.closeConnections(em);
        return p;
    }
    
    public static void removePerson(long id) {
    	Person p=getPersonById(id);
    	
    	if(p==null)
    		return;
    	
        EntityManager em = DataServiceDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        DataServiceDao.instance.closeConnections(em);
    }
    
    public static Person getPersonByUsername(String username){
    	EntityManager em = DataServiceDao.instance.createEntityManager();
    	TypedQuery<Person> query=em.createQuery("SELECT p FROM Person p WHERE p.username=?1",Person.class);
    	query.setParameter(1, username);
    	
    	List<Person> list=query.getResultList();
    	DataServiceDao.instance.closeConnections(em);
    	
    	if(list==null)
    		return null;
    	if(list.size()==0)
    		return null;
    	
    	return list.get(0);
    }
    
    public static Person getPersonByTelegramUsername(String username){
    	EntityManager em = DataServiceDao.instance.createEntityManager();
    	TypedQuery<Person> query=em.createQuery("SELECT p FROM Person p WHERE p.telegramUsername=?1",Person.class);
    	query.setParameter(1, username);
    	
    	List<Person> list=query.getResultList();
    	DataServiceDao.instance.closeConnections(em);
    	
    	if(list==null)
    		return null;
    	if(list.size()==0)
    		return null;
    	
    	return list.get(0);
    }
    
    public static Person getPersonByTelegramID(String telegramID){
    	EntityManager em = DataServiceDao.instance.createEntityManager();
    	TypedQuery<Person> query=em.createQuery("SELECT p FROM Person p WHERE p.telegramID=?1",Person.class);
    	query.setParameter(1, telegramID);
    	
    	List<Person> list=query.getResultList();
    	DataServiceDao.instance.closeConnections(em);
    	
    	if(list==null)
    		return null;
    	if(list.size()==0)
    		return null;
    	
    	return list.get(0);
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelegramUsername() {
		return telegramUsername;
	}

	public void setTelegramUsername(String telegramUsername) {
		this.telegramUsername = telegramUsername;
	}

	public HealthProfile getHealthProfile() {
		return healthProfile;
	}

	public void setHealthProfile(HealthProfile healthProfile) {
		this.healthProfile = healthProfile;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelegramID() {
		return telegramID;
	}

	public void setTelegramID(String telegramID) {
		this.telegramID = telegramID;
	}
}
