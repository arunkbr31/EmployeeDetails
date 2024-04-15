package com.arunkumar.springbootwebioc.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.arunkumar.springbootwebioc.entity.TaxDeduction;
import com.arunkumar.springbootwebioc.entity.User;
import com.arunkumar.springbootwebioc.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/employee")
public class UserController {

	@Autowired
	public UserService userService;

	@PostMapping("/saveEmployee")
	public ResponseEntity<User> addEmployee(@Valid @RequestBody User user) {
		User savedEmployee = userService.saveEmployee(user);
		return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
	}

	@GetMapping("/getEmployee")
	public ResponseEntity<List<User>> getAllEmployees() {
		List<User> employees = userService.getAllEmployees();
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}

	@GetMapping("/tax-deduction")
	public ResponseEntity<List<TaxDeduction>> getTaxDeductions() {

		List<TaxDeduction> taxDeductions = new ArrayList<>();
		List<User> employees = userService.getAllEmployees();
		for (User user : employees) {
			double yearlySalary = calculateYearlySalary(user.getSalary(), user.getDoj());
			double tax = calculateTax(yearlySalary);
			double cess = calculateCess(yearlySalary);

			TaxDeduction taxDeduction = new TaxDeduction();
			taxDeduction.setEmployeeId(user.getEmployeeId());
			taxDeduction.setFirstName(user.getFirstName());
			taxDeduction.setLastName(user.getLastName());
			taxDeduction.setYearlySalary(yearlySalary);
			taxDeduction.setTaxAmount(tax);
			taxDeduction.setCessAmount(cess);
			taxDeductions.add(taxDeduction);
		}

		return new ResponseEntity<>(taxDeductions, HttpStatus.OK);

	}

	private double calculateYearlySalary(double monthlySalary, Date doj) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(doj);
		int joiningMonth = calendar.get(Calendar.MONTH);
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		int monthsWorked = currentMonth - joiningMonth + 1;
		double totalSalary = monthlySalary * monthsWorked;

		int joiningDay = calendar.get(Calendar.DAY_OF_MONTH);
		int totalDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		double lossOfPayPerDay = monthlySalary / totalDaysInMonth;
		totalSalary -= lossOfPayPerDay * (totalDaysInMonth - joiningDay + 1);

		if (joiningMonth == Calendar.MAY && joiningDay > 15) {
			totalSalary -= monthlySalary / 2;
		}

		return totalSalary;
	}

	private double calculateTax(double yearlySalary) {
		double SLAB_1_LIMIT = 250000;
		double SLAB_2_LIMIT = 500000;
		double SLAB_3_LIMIT = 1000000;
		double SLAB_1_RATE = 0.0;
		double SLAB_2_RATE = 0.05;
		double SLAB_3_RATE = 0.1;
		double SLAB_4_RATE = 0.2;

		double tax = 0.0;
		if (yearlySalary <= SLAB_1_LIMIT) {
			tax = 0;
		} else if (yearlySalary > SLAB_1_LIMIT && yearlySalary <= SLAB_2_LIMIT) {
			tax = (yearlySalary - SLAB_1_LIMIT) * SLAB_2_RATE;
		} else if (yearlySalary > SLAB_2_LIMIT && yearlySalary <= SLAB_3_LIMIT) {
			tax = (SLAB_2_LIMIT - SLAB_1_LIMIT) * SLAB_2_RATE + (yearlySalary - SLAB_2_LIMIT) * SLAB_3_RATE;
		} else {
			tax = (SLAB_2_LIMIT - SLAB_1_LIMIT) * SLAB_2_RATE + (SLAB_3_LIMIT - SLAB_2_LIMIT) * SLAB_3_RATE
					+ (yearlySalary - SLAB_3_LIMIT) * SLAB_4_RATE;
		}
		return tax;
	}

	private double calculateCess(double yearlySalary) {
		double CESS_RATE = 0.02;
		if (yearlySalary > 2500000) {
			return (yearlySalary - 2500000) * CESS_RATE;
		}
		return 0;
	}

}
