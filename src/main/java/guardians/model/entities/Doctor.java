package guardians.model.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

/**
 * This {@link Entity} represents the information of a Doctor that will be
 * stored in the database
 *  
 * {@link Doctor}s have some periodic shifts. This is, if some {@link Doctor}s
 * have a shift today, after a certain number of days, they will have another
 * one. This kind of shifts will be referred to as "cycle-shift", and should not
 * be confused with regular shifts. A "regular shift", or "shift" in short,
 * refers to the shifts that will vary from month to month and that do not occur
 * periodically. This kind of shifts are scheduled from {@link Doctor#startDate}
 * 
 * @author miggoncan
 */
@Data
@Entity
// This is the name of the property used to describe an __embedded 
// collection of Doctors
@Relation(collectionRelation = "doctors")
public class Doctor {

	public enum DoctorStatus {
		AVAILABLE,
		DELETED
	}
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(nullable = false)
	@NotBlank
	private String firstName;

	@Column(nullable = false)
	@NotBlank
	private String lastNames;

	@Email
	@NotBlank
	@Column(unique = true, nullable = false)
	private String email;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private DoctorStatus status = DoctorStatus.AVAILABLE;

	@OneToOne(optional = true, mappedBy = "doctor", cascade = {CascadeType.ALL})
	@JsonManagedReference
	private Absence absence;
	
	// Start date will be the date this doctor's reference date to calculate their
	// shift cycle
	@JsonIgnore
	@Column(nullable = false)
	private LocalDate startDate;

	public Doctor(String firsName, String lastNames, String email, LocalDate startDate) {
		this.firstName = firsName;
		this.lastNames = lastNames;
		this.email = email;
		this.startDate = startDate;
	}

	public Doctor() {
	}

	// The toString method from @Data is not used as it can create an infinite loop
	// between Absence#toString and this method
	@Override
	public String toString() {
		return Doctor.class.getSimpleName() 
				+ "("
					+ "id=" + this.id + ", "
					+ "firstName=" + this.firstName + ", "
					+ "lastNames=" + this.lastNames + ", "
					+ "email=" + this.email + ", "
					+ "status=" + this.status + ", "
					+ "absence=" + this.absence
				+ ")";
	}
}
