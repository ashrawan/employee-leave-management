package com.lb.employeeleave.security;

import com.lb.employeeleave.entity.Employee;
import com.lb.employeeleave.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service("userService")
public class CustomUserDetailsService implements  UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = employeeRepository.findByUsername(username);
        if (employee == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Collection<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(employee.getRole());

         return new JwtUserDetails(employee.getUsername(), employee.getPassword(), employee.getId(), grantedAuthorities);
//        return new org.springframework.security.core.userdetails.User(employee.getUsername(), employee.getPassword(), getAuthority(employee));
    }

//    private List getAuthority(Employee employee) {
//
//        return Arrays.asList(new SimpleGrantedAuthority(String.valueOf(employee.getRole())));
//    }


}