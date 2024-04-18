document.addEventListener('DOMContentLoaded', function() {
    const formulario = document.getElementById('cadastroClienteForm');
    const botaoAdicionarEndereco = document.getElementById('adicionarEnderecoEntrega');
    const divEnderecosEntrega = document.getElementById('enderecosEntrega');
    let contadorEnderecos = 0;


    function validarCPF(cpf) {
        cpf = cpf.replace(/[^\d]+/g, ''); // Remove tudo o que não é dígito
        if (cpf.length !== 11) {
            return false;
        }

        var numeros, digitos, soma, i, resultado, digitos_iguais;
        digitos_iguais = 1;

        for (i = 0; i < cpf.length - 1; i++) {
            if (cpf.charAt(i) !== cpf.charAt(i + 1)) {
                digitos_iguais = 0;
                break;
            }
        }

        if (!digitos_iguais) {
            numeros = cpf.substring(0, 9);
            digitos = cpf.substring(9);
            soma = 0;

            for (i = 10; i > 1; i--) {
                soma += numeros.charAt(10 - i) * i;
            }

            resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            if (resultado !== Number(digitos.charAt(0))) {
                return false;
            }

            numeros = cpf.substring(0, 10);
            soma = 0;

            for (i = 11; i > 1; i--) {
                soma += numeros.charAt(11 - i) * i;
            }

            resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);

            if (resultado !== Number(digitos.charAt(1))) {
                return false;
            }

            return true;
        } else {
            return false;
        }
    }

    botaoAdicionarEndereco.addEventListener('click', function() {
        const novoEndereco = document.createElement('div');
        novoEndereco.innerHTML = `
     
         <!-- Tela Adicionar Novo Endereço -->
                        <div class="form-group form-check">
                            <input class="form-check-input" type="radio" name="endereco_selecionado"
                                value="${contadorEnderecos}" ${contadorEnderecos===0 ? 'checked' : '' }>
                            <label class="form-label">Selecionar como endereço de entrega:</label>
                        </div>
    
                        <div class="form-group">
                            <label class="form-label">CEP:</label>
                            <input class="form-control type=" text" name="cep" required>
                        </div>
    
                        <div class="form-group">
                            <label class="form-label">Logradouro:</label>
                            <input class="form-control type=" text" name="logradouro" required>
                        </div>
    
                        <div class="form-group">
                            <label class="form-label">Número:</label>
                            <input class="form-control type=" text" name="numero" required>
                        </div>
    
                        <div class="form-group">
                            <label class="form-label">Complemento:</label>
                            <input class="form-control type=" text" name="complemento">
                        </div>
    
                        <div class="form-group">
                            <label class="form-label">Bairro:</label>
                            <input class="form-control type=" text" name="bairro" required>
                        </div>
    
                        <div class="form-group">
                            <label class="form-label">Cidade:</label>
                            <input class="form-control type=" text" name="cidade" required>
                        </div>
    
                        <div class="form-group">
                            <label class="form-label">UF:</label>
                            <input class="form-control type=" text" name="uf" required>
                        </div>
                    
                        <button type="button" class="removerEnderecoEntrega btn btn-danger">Remover</button>
    
        `;
        divEnderecosEntrega.appendChild(novoEndereco);
        contadorEnderecos++;

        // Adiciona evento de remover para o botão recém-criado
        novoEndereco.querySelector('.removerEnderecoEntrega').addEventListener('click', function() {
            novoEndereco.remove();
            // Se o endereço removido estava selecionado, seleciona o primeiro endereço restante
            if (novoEndereco.querySelector('input[type="radio"]').checked && divEnderecosEntrega.children.length > 0) {
                divEnderecosEntrega.querySelector('input[type="radio"]').checked = true;
            }
            // Tira o contador de endereços se um endereço é removido
            contadorEnderecos--;
        });
    });

    // Submissão do formulário
    formulario.addEventListener('submit', function(event) {
        event.preventDefault();
        const form =new FormData(formulario);
        const formObjeto = Object.fromEntries(form.entries());

        formObjeto.enderecoEntrega = [];
        const adicionarEndereco = ["cep","logradouro","numero","complemento","bairro","cidade","uf"];
        const quantidadeEndereco = formulario.querySelectorAll("input[name=cep]").length;

            for (let i = 0; i <= quantidadeEndereco; i++){
                let nomeCamposEndereco = {};
                adicionarEndereco.forEach(campo => {
                    nomeCamposEndereco[campo] = form.get(`${campo}`, i)
                })
                formObjeto.enderecoEntrega.push(nomeCamposEndereco);
            }

        fetch("http://localhost:8080/auth/registerCliente", {
            method: "POST",
                body: JSON.stringify(formObjeto)
            })
                .then(response=>{
                    if(!response.ok){
                        throw new Error("Resposta com erro!");
                    }
                    return response.json();
                })
                .then(valor =>{console.log("Sucesso",valor);
                })
                .catch((erro)=>{console.error("Erro", erro);
                });

    });
});
