package SpringApplication.Entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class Employees {
    private int id_employee;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Surname cannot be empty")
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
    private String surname;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotNull(message = "Date of birth is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth_date;

    @Pattern(regexp = "^[0-9]{11}$", message = "PESEL must have 11 digits")
    @NotBlank(message = "PESEL is required")
    private String personal_id;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "^[0-9]{26}$", message = "Bank account must have 26 digits")
    @NotBlank(message = "Bank account is required")
    private String bank_account;

    // 🔹 Konstruktor domyślny
    public Employees() {}

    // 🔹 Konstruktor z parametrami
    public Employees(int id_employee, String name, String surname, String gender, LocalDate birth_date, String personal_id, String email, String bank_account) {
        this.id_employee = id_employee;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birth_date = birth_date;
        this.personal_id = personal_id;
        this.email = email;
        this.bank_account = bank_account;
    }

    // 🔹 Gettery i Settery
    public int getId_employee() {
        return id_employee;
    }

    public void setId_employee(int id_employee) {
        this.id_employee = id_employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public String getPersonal_id() {
        return personal_id;
    }

    public void setPersonal_id(String personal_id) {
        this.personal_id = personal_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBank_account() {
        return bank_account;
    }

    public void setBank_account(String bank_account) {
        this.bank_account = bank_account;
    }

    @Override
    public String toString() {
        return "Employees{" +
                "id_employee=" + id_employee +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", gender='" + gender + '\'' +
                ", birth_date=" + birth_date +
                ", personal_id='" + personal_id + '\'' +
                ", email='" + email + '\'' +
                ", bank_account='" + bank_account + '\'' +
                '}';
    }
}
