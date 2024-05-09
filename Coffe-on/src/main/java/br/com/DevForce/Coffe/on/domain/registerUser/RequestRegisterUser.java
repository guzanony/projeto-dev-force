package br.com.DevForce.Coffe.on.domain.registerUser;

public record RequestRegisterUser(String username, String email, String password, String cpf, String grupo) {
    @Override
    public String username() {
        return username;
    }
    @Override
    public String email() {
        return email;
    }
    @Override
    public String password() {
        return password;
    }
    @Override
    public String cpf() {
        return cpf;
    }
    @Override
    public String grupo() {
        return grupo;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getCpf() {
        return cpf;
    }

    public String getGrupo() {
        return grupo;
    }
}
