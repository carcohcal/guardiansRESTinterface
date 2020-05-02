package guardians.model.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import guardians.model.entities.primarykeys.CalendarPK;
import guardians.model.validation.annotations.ValidSchedule;
import lombok.Data;

/**
 * The Schedule {@link Entity} represents the scheduled shifts of a specific
 * {@link Calendar}
 * 
 * Note the primary key of this entity is composite, hence the {@link IdClass}
 * annotation. Moreover, this is a weak entity, so it receives its primary key
 * from the {@link Calendar} it is associated to
 * 
 * @see ScheduleDay
 * @author miggoncan
 */
@Data
@Entity
@IdClass(CalendarPK.class)
@ValidSchedule
public class Schedule {
	
	public enum ScheduleStatus {
		NOT_CREATED,
		PENDING_CONFIRMATION,
		CONFIRMED
	}
	
	@Id
	@Column(name = "calendar_month")
	@Range(min = 1, max = 12)
	@NotNull
	private Integer month;
	@Id
	@Column(name = "calendar_year")
	@Range(min = 1970)
	@NotNull
	private Integer year;
	@MapsId
	@OneToOne
	@JsonBackReference
	private Calendar calendar;

	/**
	 * This represents the status in which this schedule is. For example, the
	 * schedule of this {@link Calendar} could not have been created yet. Or it
	 * could be waiting for approval
	 */
	@Enumerated(EnumType.STRING)
	@NotNull
	private ScheduleStatus status = ScheduleStatus.NOT_CREATED;

	@OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ScheduleDay> days;

	public Schedule(ScheduleStatus status) {
		this.status = status;
	}

	public Schedule() {
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
		if (calendar != null) {
			this.month = calendar.getMonth();
			this.year = calendar.getYear();
		}
	}
}