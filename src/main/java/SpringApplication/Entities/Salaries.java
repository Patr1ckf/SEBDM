package SpringApplication.Entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Salaries {

    private int id_salary;

    @NotNull(message = "Date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Min(value = 1, message = "Gross salary must be greater than zero")
    private double gross_salary;

    @Min(value = 1, message = "Net salary must be greater than zero")
    private double net_salary;

    @Min(value = 1, message = "Employee ID must be greater than zero")
    private int id_employee;

    public Salaries() {
    }

    public Salaries(int id_salary, LocalDate date, double gross_salary, double net_salary, int id_employee) {
        super();
        this.id_salary = id_salary;
        this.date = date;
        this.gross_salary = gross_salary;
        this.net_salary = net_salary;
        this.id_employee = id_employee;
    }

    public int getId_salary() {
        return id_salary;
    }

    public void setId_salary(int id_salary) {
        this.id_salary = id_salary;
    }

    public double getGross_salary() {
        return gross_salary;
    }

    public void setGross_salary(double gross_salary) {
        this.gross_salary = gross_salary;
    }

    public double getNet_salary() {
        return net_salary;
    }

    public void setNet_salary(double net_salary) {
        this.net_salary = net_salary;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
    }

    @Override
    public String toString() {
        return "Salaries{" +
                "id_salary=" + id_salary +
                ", date='" + date +
                ", gross_salary=" + gross_salary +
                ", net_salary=" + net_salary +
                ", id_employee=" + id_employee +
                '}';
    }
}
