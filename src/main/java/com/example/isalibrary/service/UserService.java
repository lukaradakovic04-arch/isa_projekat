package com.example.isalibrary.service;

import com.example.isalibrary.dto.UserForm;
import com.example.isalibrary.model.AppUser;
import com.example.isalibrary.model.Role;
import com.example.isalibrary.repository.RoleRepository;
import com.example.isalibrary.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<AppUser> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public AppUser findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Korisnik nije pronadjen."));
    }

    public AppUser save(UserForm form) {
        AppUser user = form.getId() == null ? new AppUser() : findById(form.getId());
        user.setUsername(form.getUsername());
        user.setEnabled(form.isEnabled());

        if (form.getId() == null && (form.getPassword() == null || form.getPassword().isBlank())) {
            throw new IllegalArgumentException("Lozinka je obavezna za novog korisnika.");
        }
        if (form.getPassword() != null && !form.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(form.getPassword()));
        }

        List<Role> roles = form.getRoleIds() == null ? List.of() : roleRepository.findAllById(form.getRoleIds());
        user.setRoles(new HashSet<>(roles));
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserForm toForm(AppUser user) {
        UserForm form = new UserForm();
        form.setId(user.getId());
        form.setUsername(user.getUsername());
        form.setEnabled(user.isEnabled());
        form.setRoleIds(user.getRoles().stream().map(Role::getId).toList());
        return form;
    }
}
