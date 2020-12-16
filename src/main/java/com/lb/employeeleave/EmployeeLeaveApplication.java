package com.lb.employeeleave;

import com.lb.employeeleave.entity.Employee;
import com.lb.employeeleave.repository.EmployeeRepository;
import com.lb.employeeleave.util.enums.EmployeeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EmployeeLeaveApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeLeaveApplication.class);

    @Value("${app.security.initial.admin-username:admin}")
    private String initialAdminUsername;

    @Value("${app.security.initial.admin-pass:admin}")
    private String initialAdminPass;

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private EmployeeRepository employeeRepository;

    public static void main(String[] args) {
        SpringApplication.run(EmployeeLeaveApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Employee admin = employeeRepository.findByUsername(initialAdminUsername);
        if(admin == null){
            LOGGER.warn("Initial Admin-User Not Found, initializing default admin with username: ", initialAdminUsername);
            String initialAdminPassword = passwordEncoder.encode(initialAdminPass);
            Employee defaultAdmin = new Employee();
            defaultAdmin.setUsername(initialAdminUsername);
            defaultAdmin.setFirstName(initialAdminUsername);
            defaultAdmin.setLastName(initialAdminUsername);
            defaultAdmin.setPhoneNumber("000000000");
            defaultAdmin.setPassword(initialAdminPassword);
            defaultAdmin.setRole("ROLE_ADMIN");
            defaultAdmin.setStatus(EmployeeStatus.ACTIVE);
            employeeRepository.save(defaultAdmin);
            LOGGER.info("Default Admin-User Initialization Successful");
        }
    }

}
