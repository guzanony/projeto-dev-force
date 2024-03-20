package br.com.DevForce.Coffe.on.services;

import br.com.DevForce.Coffe.on.domain.user.registerUser.RegisterUser;
import br.com.DevForce.Coffe.on.domain.user.registerUser.RegisterUserRepository;
import br.com.DevForce.Coffe.on.domain.user.registerUser.RequestRegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterUserService {

    private final RegisterUserRepository registerUserRepository;

    @Autowired
    public RegisterUserService(RegisterUserRepository registerUserRepository) {
        this.registerUserRepository = registerUserRepository;

    }

    public List<RegisterUser> listarUsuarios() {
        return registerUserRepository.findAll();
    }

    public RegisterUser registerNewUser(RequestRegisterUser requestRegisterUser) {
        RegisterUser newUser = new RegisterUser(requestRegisterUser);
        return registerUserRepository.save(newUser);
    }
}
