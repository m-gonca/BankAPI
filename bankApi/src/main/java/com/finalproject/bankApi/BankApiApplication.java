package com.finalproject.bankApi;

import com.finalproject.bankApi.models.users.Admin;
import com.finalproject.bankApi.models.users.Role;
import com.finalproject.bankApi.repositories.users.AdminRepository;
import com.finalproject.bankApi.repositories.users.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BankApiApplication implements CommandLineRunner {
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	String encodedPassword;
	Role role;
	private final AdminRepository adminRepository;

	public BankApiApplication(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(BankApiApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		encodedPassword = passwordEncoder.encode("123456");
		role = roleRepository.save(new Role("ADMIN", adminRepository.save(new Admin("Admin", encodedPassword))));
	}
}
