package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="CUSTOMER_INFO")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	
	
	@Id
	@Column(name="CUSTOMER_ID")
	private int id;
	@Column(name="FIRST_NAME")
	private String firstName;
	@Column(name="SECOND_NAME")
	private String secondName;
	@Column(name="EMAIL")
	private String emailID;
	@Column(name="GENDER")
	private String gender;
	@Column(name="CONTACT")
	private String phoneNum;
	@Column(name="COUNTRY")
	private String country;
	@Column(name="DOB")
	private String dateOfBirth;
	
		

}
