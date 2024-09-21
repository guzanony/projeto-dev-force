const formulario = document.querySelector("form");
const inome = document.querySelector("#nomeProduto");
const iavaliacao = document.querySelector("#codigo");
const iquantidade = document.querySelector("#qtd");
const ipreco = document.querySelector("#preco");
const idescricao = document.querySelector("#descricao");
const iimagem = document.querySelector("#imagem");

let produtoAtualId = null;

document.addEventListener('DOMContentLoaded', function() {
    const params = new URLSearchParams(window.location.search);
    const productId = params.get('productId');

    if (productId) {
        carregarDadosProduto(productId);
        produtoAtualId = productId;
    }
});

function carregarDadosProduto(produtoId) {
    fetch(`http://localhost:8080/products/${produtoId}`)
    .then(response => response.json())
    .then(produto => {
        inome.value = produto.nome;
        iavaliacao.value = produto.avaliacao;
        iquantidade.value = produto.quantidade;
        ipreco.value = produto.preco;
        idescricao.value = produto.descricao;
    })
    .catch(error => console.error('Erro ao carregar dados do produto:', error));
}

function salvarProduto() {
    const metodoHttp = produtoAtualId ? "PUT" : "POST";
    const url = produtoAtualId ? `http://localhost:8080/products/${produtoAtualId}` : "http://localhost:8080/products";

    const formData = new FormData(formulario);
    formData.append("nome", inome.value);
    formData.append("avaliacao", iavaliacao.value);
    formData.append("quantidade", iquantidade.value);
    formData.append("preco", ipreco.value);
    formData.append("descricao", idescricao.value);
    if (iimagem.files.length > 0) {
        formData.append("image", iimagem.files[0]);
    }

    fetch(url, {
        method: metodoHttp,
        body: formData,
    })
    .then(response => {
        if (!response.ok) throw new Error('Erro ao salvar o produto');
        return response.json();
    })
    .then(data => {
        console.log('Produto salvo com sucesso:', data);
        limparProdutos();
    })
    .catch(error => {
        console.error('Erro ao salvar o produto:', error);
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
    salvarProduto();
});
