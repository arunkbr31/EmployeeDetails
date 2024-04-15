package com.arunkumar.springbootwebioc.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "emp_details")

public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Employee ID is required")
	@Column(unique = true)
	private String employeeId;

	@NotBlank(message = "First Name is required")
	private String firstName;

	@NotBlank(message = "Last Name is required")
	private String lastName;

	@NotBlank(message = "Email is required")
	@Email(message = "Invalid email format")
	private String email;

	@ElementCollection
	private List<@Pattern(regexp = "[0-9]+", message = "Invalid phone number format") String> phoneNumbers;

	@NotNull(message = "DOJ is required")
	@Temporal(TemporalType.DATE)
	private Date doj;

	@Positive(message = "Salary must be positive")
	private double salary;

	public User(@NotBlank(message = "Employee ID is required") String employeeId,
			@NotBlank(message = "First Name is required") String firstName,
			@NotBlank(message = "Last Name is required") String lastName,
			@NotBlank(message = "Email is required") String email,
			List<@Pattern(regexp = "[0-9]+", message = "Invalid phone number format") String> phoneNumbers,
			@NotNull(message = "DOJ is required") Date doj,
			@Positive(message = "Salary must be positive") double salary) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumbers = phoneNumbers;
		this.doj = doj;
		this.salary = salary;
	}

	public Long getId() {
		return id;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public Date getDoj() {
		return doj;
	}

	public double getSalary() {
		return salary;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

}
