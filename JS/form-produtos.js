const formulario = document.querySelector("form");
const inome = document.querySelector("#nomeProduto");
const iavaliacao = document.querySelector("#codigo");
const iquantidade = document.querySelector("#qtd");
const ipreco = document.querySelector("#preco");
const idescricao = document.querySelector("#descricao");

function cadastrarProduto() {
  fetch("http://localhost:8080/products", {
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
    method: "POST",
    body: JSON.stringify({
      nome: inome.value,
      avaliacao: iavaliacao.value,
      quantidade: iquantidade.value,
      preco: ipreco.value,
      descricao: idescricao.value,
    }),
  })
    .then(function (resp) {
      console.log(resp);
    })
    .catch(function (resp) {
      console.log(resp);
    });
}

function limparProdutos() {
  (inome.value = ""),
    (iavaliacao.value = ""),
    (iquantidade.value = ""),
    (ipreco.value = ""),
    (idescricao.value = "");
}

formulario.addEventListener("submit", function (event) {
  event.preventDefault();
  cadastrarProduto();
  limparProdutos();
});
