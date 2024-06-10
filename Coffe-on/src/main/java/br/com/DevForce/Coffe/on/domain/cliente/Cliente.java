package br.com.DevForce.Coffe.on.domain.cliente;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    private Date dataNascimento;
    private String genero;
    private String cepFaturamento;
    private String logradouroFaturamento;
    private String numeroFaturamento;
    private String complementoFaturamento;
    private String bairroFaturamento;
    private String cidadeFaturamento;
    private String ufFaturamento;
    private String password;
    private String email;
    private String cpf;
    private String userId;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<EnderecoEntrega> enderecosEntrega = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCepFaturamento() {
        return cepFaturamento;
    }

    public void setCepFaturamento(String cepFaturamento) {
        this.cepFaturamento = cepFaturamento;
    }

    public String getLogradouroFaturamento() {
        return logradouroFaturamento;
    }

    public void setLogradouroFaturamento(String logradouroFaturamento) {
        this.logradouroFaturamento = logradouroFaturamento;
    }

    public String getNumeroFaturamento() {
        return numeroFaturamento;
    }

    public void setNumeroFaturamento(String numeroFaturamento) {
        this.numeroFaturamento = numeroFaturamento;
    }

    public String getComplementoFaturamento() {
        return complementoFaturamento;
    }

    public void setComplementoFaturamento(String complementoFaturamento) {
        this.complementoFaturamento = complementoFaturamento;
    }

    public String getBairroFaturamento() {
        return bairroFaturamento;
    }

    public void setBairroFaturamento(String bairroFaturamento) {
        this.bairroFaturamento = bairroFaturamento;
    }

    public String getCidadeFaturamento() {
        return cidadeFaturamento;
    }

    public void setCidadeFaturamento(String cidadeFaturamento) {
        this.cidadeFaturamento = cidadeFaturamento;
    }

    public String getUfFaturamento() {
        return ufFaturamento;
    }

    public void setUfFaturamento(String ufFaturamento) {
        this.ufFaturamento = ufFaturamento;
    }

    public List<EnderecoEntrega> getEnderecosEntrega() {
        return enderecosEntrega;
    }

    public void setEnderecosEntrega(List<EnderecoEntrega> enderecosEntrega) {
        this.enderecosEntrega = enderecosEntrega;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", genero='" + genero + '\'' +
                ", cepFaturamento='" + cepFaturamento + '\'' +
                ", logradouroFaturamento='" + logradouroFaturamento + '\'' +
                ", numeroFaturamento='" + numeroFaturamento + '\'' +
                ", complementoFaturamento='" + complementoFaturamento + '\'' +
                ", bairroFaturamento='" + bairroFaturamento + '\'' +
                ", cidadeFaturamento='" + cidadeFaturamento + '\'' +
                ", ufFaturamento='" + ufFaturamento + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", userId='" + userId + '\'' +
                ", enderecosEntrega=" + enderecosEntrega +
                '}';
    }
}


