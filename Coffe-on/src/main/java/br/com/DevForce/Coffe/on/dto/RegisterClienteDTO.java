package br.com.DevForce.Coffe.on.dto;

import java.util.Date;
import java.util.List;

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
        String email,
        String cpf,
        String password,

        List<EnderecoEntregaDTO> enderecosEntrega
) {}
