const formulario = document.querySelector("form");
const inome = document.querySelector("#nomeProduto");
const iavaliacao = document.querySelector("#codigo");
const iquantidade = document.querySelector("#qtd");
const ipreco = document.querySelector("#preco");
const idescricao = document.querySelector("#descricao");
const iimagem = document.querySelector("#imagem");

function cadastrarProduto() {
    const formData = new FormData();
    formData.append("nome", inome.value);
    formData.append("avaliacao", iavaliacao.value);
    formData.append("quantidade", iquantidade.value);
    formData.append("preco", ipreco.value);
    formData.append("descricao", idescricao.value);
    formData.append("image", iimagem.files[0]);

    fetch("http://localhost:8080/products", {
        method: "POST",
        body: formData,
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Algo deu errado no servidor!');
        }
    })

    .then(function (data) {
        console.log(data);
        limparProdutos();
    })
    .catch(function (error) {
        console.log(error);
    });
}

function limparProdutos() {
    inome.value = "";
    iavaliacao.value = "";
    iquantidade.value = "";
    ipreco.value = "";
    idescricao.value = "";
    iimagem.value = "";
}

formulario.addEventListener("submit", function (event) {
    event.preventDefault();
    cadastrarProduto();
});
