document.addEventListener('DOMContentLoaded', function() {
    const formulario = document.getElementById('cadastroClienteForm');
    const botaoAdicionarEndereco = document.getElementById('adicionarEnderecoEntrega');
    const divEnderecosEntrega = document.getElementById('enderecosEntrega');
    let contadorEnderecos = 0;

    botaoAdicionarEndereco.addEventListener('click', function() {
        const novoEndereco = document.createElement('div');
        novoEndereco.innerHTML = `
     
         <!-- Tela Adicionar Novo Endereço -->
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            
            <div class="modal-dialog">
    
                <div class="modal-content">
    
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Adicione um endereço</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
    
                    <div class="modal-body">
    
                        <div class="form-group form-check">
                            <input class="form-check-input" type="radio" name="endereco_selecionado"
                                value="${contadorEnderecos}" ${contadorEnderecos===0 ? 'checked' : '' }>
                            <label class="form-label">Selecionar como endereço de entrega:</label>
                        </div>
    
                        <div class="form-group">
                            <label class="form-label">CEP:</label>
                            <input class="form-control type=" text" name="cep_entrega[]" required>
                        </div>
    
                        <div class="form-group">
                            <label class="form-label">Logradouro:</label>
                            <input class="form-control type=" text" name="logradouro_entrega[]" required>
                        </div>
    
                        <div class="form-group">
                            <label class="form-label">Número:</label>
                            <input class="form-control type=" text" name="numero_entrega[]" required>
                        </div>
    
                        <div class="form-group">
                            <label class="form-label">Complemento:</label>
                            <input class="form-control type=" text" name="complemento_entrega[]">
                        </div>
    
                        <div class="form-group">
                            <label class="form-label">Bairro:</label>
                            <input class="form-control type=" text" name="bairro_entrega[]" required>
                        </div>
    
                        <div class="form-group">
                            <label class="form-label">Cidade:</label>
                            <input class="form-control type=" text" name="cidade_entrega[]" required>
                        </div>
    
                        <div class="form-group">
                            <label class="form-label">UF:</label>
                            <input class="form-control type=" text" name="uf_entrega[]" required>
                        </div>
    
                    </div>
    
                    <div class="modal-footer">
                        <button type="button" class="removerEnderecoEntrega btn btn-danger">Remover</button>
                        <button type="button" class="btn btn-primary">Guardar</button>
                    </div>
    
                </div>
            </div>
        </div>
    
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
