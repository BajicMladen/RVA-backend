package rva.jpa;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the grupa database table.
 * 
 */
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
@Entity
@NamedQuery(name="Grupa.findAll", query="SELECT g FROM Grupa g")
public class Grupa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GRUPA_ID_GENERATOR", sequenceName="GRUPA_SEQ",allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GRUPA_ID_GENERATOR")
	private Integer id;

	private String oznaka;

	//bi-directional many-to-one association to Smer
	@ManyToOne
	@JoinColumn(name="smer")
	private Smer smer;

	//bi-directional many-to-one association to Student
	@JsonIgnore
	@OneToMany(mappedBy="grupa",cascade = {CascadeType.DETACH, CascadeType.REMOVE})
	private List<Student> students;

	public Grupa() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOznaka() {
		return this.oznaka;
	}

	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public Smer getSmer() {
		return this.smer;
	}

	public void setSmer(Smer smer) {
		this.smer = smer;
	}

	public List<Student> getStudents() {
		return this.students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Student addStudent(Student student) {
		getStudents().add(student);
		student.setGrupa(this);

		return student;
	}

	public Student removeStudent(Student student) {
		getStudents().remove(student);
		student.setGrupa(null);

		return student;
	}

}