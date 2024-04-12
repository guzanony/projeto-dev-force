package br.com.DevForce.Coffe.on.domain.registerUser;

public record RequestRegisterUser(String username, String email, String password, String cpf, String grupo) {
}
