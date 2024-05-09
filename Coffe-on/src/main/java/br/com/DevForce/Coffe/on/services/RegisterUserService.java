package br.com.DevForce.Coffe.on.services;

import br.com.DevForce.Coffe.on.Validator.ValidadorCpf;
import br.com.DevForce.Coffe.on.domain.registerUser.RegisterUser;
import br.com.DevForce.Coffe.on.domain.registerUser.RegisterUserRepository;
import br.com.DevForce.Coffe.on.domain.registerUser.RequestRegisterUser;
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

    public boolean activateUser(String id) {
        return registerUserRepository.findById(id)
                .map(user -> {
                    user.setActive(true);
                    registerUserRepository.save(user);
                    return true;
                }).orElse(false);
    }

    public boolean deactivateUser(String id) {
        return registerUserRepository.findById(id)
                .map(user -> {
                    user.setActive(false);
                    registerUserRepository.save(user);
                    return true;
                }).orElse(false);
    }

    public RegisterUser updateUser(String userId, RequestRegisterUser requestRegisterUser) throws Exception {
        RegisterUser user = registerUserRepository.findById(userId)
                .orElseThrow(() -> new Exception("Usuário não encontrado"));

        user.setUsername(requestRegisterUser.getUsername());
        user.setEmail(requestRegisterUser.getEmail());
        user.setPassword(requestRegisterUser.getPassword());
        user.setCpf(requestRegisterUser.getCpf());
        user.setGrupo(requestRegisterUser.getGrupo());

        return registerUserRepository.save(user);
    }
}
