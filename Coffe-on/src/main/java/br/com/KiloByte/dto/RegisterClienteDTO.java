package br.com.KiloByte.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;

public record RegisterClienteDTO(
        String nomeCompleto,
        LocalDate dataNascimento,
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
