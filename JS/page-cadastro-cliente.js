document.addEventListener('DOMContentLoaded', function() {
    const formulario = document.getElementById('cadastroClienteForm');
    const botaoAdicionarEndereco = document.getElementById('adicionarEnderecoEntrega');
    const divEnderecosEntrega = document.getElementById('enderecosEntrega');
    let contadorEnderecos = 0;

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

/*-------------- CHAMADA DA API DO CORREIOS ---------------------*/

const addressForm = document.querySelector("#cadastroClienteForm");
const cepInput = document.querySelector("#cep-faturamento");
const addressInput = document.querySelector("#logradouro-faturamento");
const cityInput = document.querySelector("#cidade-faturamento");
const neighborhoodInput = document.querySelector("#bairro-faturamento");
const regionInput = document.querySelector("#uf-faturamento");
const formInputs = document.querySelectorAll("[data-input]");

const closeButton = document.querySelector("#close-message");

// Valida input CEP
cepInput.addEventListener("keypress", (e) => {
    const onlyNumbers = /[0-9]|\./;
    const key = String.fromCharCode(e.keyCode);

    console.log(key);

    console.log(onlyNumbers.test(key));

    // allow only numbers
    if (!onlyNumbers.test(key)) {
        e.preventDefault();
        return;
    }
});

// Evento get address
cepInput.addEventListener("keyup", (e) => {
    const inputValue = e.target.value;

    //   Check if we have a CEP
    if (inputValue.length === 8) {
        getAddress(inputValue);
    }
});

// Pega o endereço direto da API
const getAddress = async (cep) => {

    cepInput.blur();

    const apiUrl = `https://viacep.com.br/ws/${cep}/json/`;

    const response = await fetch(apiUrl);

    const data = await response.json();

    console.log(data);
    console.log(formInputs);
    console.log(data.erro);

    // Show error and reset form
    if (data.erro === "true") {
        if (!addressInput.hasAttribute("disabled")) {
            toggleDisabled();
        }

        addressForm.reset();
        toggleLoader();
        toggleMessage("CEP Inválido, tente novamente.");
        return;
    }

    // Activate disabled attribute if form is empty
    if (addressInput.value === "") {
        toggleDisabled();
    }

    addressInput.value = data.logradouro;
    cityInput.value = data.localidade;
    neighborhoodInput.value = data.bairro;
    regionInput.value = data.uf;

};

// Add or remove disable attribute
const toggleDisabled = () => {
    if (regionInput.hasAttribute("disabled")) {
        formInputs.forEach((input) => {
            input.removeAttribute("disabled");
        });
    } else {
        formInputs.forEach((input) => {
            input.setAttribute("disabled", "disabled");
        });
    }
};



// Show or hide message
const toggleMessage = (msg) => {
    const fadeElement = document.querySelector("#fade");
    const messageElement = document.querySelector("#message");

    const messageTextElement = document.querySelector("#message p");

    messageTextElement.innerText = msg;

    fadeElement.classList.toggle("hide");
    messageElement.classList.toggle("hide");
};


// closeButton.addEventListener("click", () => toggleMessage());

// Salva endereço
addressForm.addEventListener("submit", (e) => {
    e.preventDefault();

    toggleLoader();

    setTimeout(() => {
        toggleLoader();

        toggleMessage("Endereço salvo com sucesso!");

        addressForm.reset();

        toggleDisabled();
    }, 1000);
});

