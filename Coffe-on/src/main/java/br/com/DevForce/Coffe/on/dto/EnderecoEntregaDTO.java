package br.com.DevForce.Coffe.on.dto;

public record EnderecoEntregaDTO(
        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf
) {}
