package br.com.DevForce.Coffe.on.dto;

import java.util.Date;

public record RegisterClienteDTO(
        String nomeCompleto,
        Date dataNascimento,
        String genero,
        String cepFaturamento,
        String logradouroFaturamento,
        String numeroFaturamento,
        String complementoFaturamento,
        String bairroFaturamento,
        String cidadeFaturamento,
        String ufFaturamento,
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf,
        String password,
        String email,
        String cpf
) {}
