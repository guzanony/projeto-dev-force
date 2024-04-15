document.addEventListener('DOMContentLoaded', function() {
    const formulario = document.getElementById('cadastroClienteForm');
    const botaoAdicionarEndereco = document.getElementById('adicionarEnderecoEntrega');
    const divEnderecosEntrega = document.getElementById('enderecosEntrega');
    let contadorEnderecos = 0;

    botaoAdicionarEndereco.addEventListener('click', function() {
        const novoEndereco = document.createElement('div');
        novoEndereco.innerHTML = `
            <div class="form-group">
                <label>Selecionar como endereço de entrega:</label>
                <input type="radio" name="endereco_selecionado" value="${contadorEnderecos}" ${contadorEnderecos === 0 ? 'checked' : ''}>
            </div>
            <div class="form-group">
                <label>CEP:</label>
                <input type="text" name="cep_entrega[]" required>
            </div>
            <div class="form-group">
                <label>Logradouro:</label>
                <input type="text" name="logradouro_entrega[]" required>
            </div>
            <div class="form-group">
                <label>Número:</label>
                <input type="text" name="numero_entrega[]" required>
            </div>
            <div class="form-group">
                <label>Complemento:</label>
                <input type="text" name="complemento_entrega[]">
            </div>
            <div class="form-group">
                <label>Bairro:</label>
                <input type="text" name="bairro_entrega[]" required>
            </div>
            <div class="form-group">
                <label>Cidade:</label>
                <input type="text" name="cidade_entrega[]" required>
            </div>
            <div class="form-group">
                <label>UF:</label>
                <input type="text" name="uf_entrega[]" required>
            </div>
            <button type="button" class="removerEnderecoEntrega">Remover</button>
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
