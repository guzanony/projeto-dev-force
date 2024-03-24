document.getElementById("formProduto").onsubmit = function (event) {
  event.preventDefault();

  var productData = {
    nome: document.getElementById("nomeProduto").value,
    avaliacao: parseFloat(document.getElementById("avaliacaoProduto").value),
    quantidade: parseInt(document.getElementById("quantidadeProduto").value),
    preco: parseFloat(document.getElementById("precoProduto").value),
    descricao: document.getElementById("descricaoProduto").value,
  };

  fetch("http://localhost:8080/products", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(productData),
  })
    .then((reponse) => {
      if (!Response.ok) {
        throw new Error("A resposta de rede não está ok" + reponse.statusText);
      }
      return reponse.json();
    })
    .then((data) => console.log(data))
    .catch((error) => console.error("Erro", error));
};
