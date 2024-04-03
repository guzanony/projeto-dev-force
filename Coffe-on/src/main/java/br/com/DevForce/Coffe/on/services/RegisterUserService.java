package br.com.DevForce.Coffe.on.services;

import br.com.DevForce.Coffe.on.Validator.ValidadorCpf;
import br.com.DevForce.Coffe.on.domain.user.registerUser.RegisterUser;
import br.com.DevForce.Coffe.on.domain.user.registerUser.RegisterUserRepository;
import br.com.DevForce.Coffe.on.domain.user.registerUser.RequestRegisterUser;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterUserService  {

    private final RegisterUserRepository registerUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterUserService(RegisterUserRepository registerUserRepository, PasswordEncoder passwordEncoder) {
        this.registerUserRepository = registerUserRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public List<RegisterUser> listarUsuarios() {
        return registerUserRepository.findAll();
    }

    public RegisterUser registerNewUser(RequestRegisterUser requestRegisterUser) {
        if(!ValidadorCpf.isValid(requestRegisterUser.cpf())) {
            throw new IllegalArgumentException("CPF inválido");
        }
        RegisterUser newUser = new RegisterUser(requestRegisterUser);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        return registerUserRepository.save(newUser);
    }
}
