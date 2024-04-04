function buscarProduto() {
    var input = document.getElementById("busca-produto");
    var filtro = input.value.toLowerCase();
    var tabela = document.getElementById("tabela-produtos");

    // Fazer uma requisição para o servidor back-end
    fetch(`http://seuservidor.com/products?nome=${filtro}`)
        .then(response => response.json())
        .then(produtos => {
            // Limpar tabela
            tabela.innerHTML = "";

            // Preencher tabela com produtos retornados pelo servidor
            produtos.forEach(produto => {
                var linha = tabela.insertRow();
                linha.insertCell(0).textContent = produto.id;
                linha.insertCell(1).textContent = produto.nome;
                linha.insertCell(2).textContent = produto.quantidade;
                linha.insertCell(3).textContent = produto.preco;
                linha.insertCell(4).textContent = produto.avaliacao; // Ou outro atributo que você queira mostrar
                var celulaAcao = linha.insertCell(5);
                var linkAlterar = document.createElement("a");
                linkAlterar.href = `#/alterar/${produto.id}`;
                linkAlterar.textContent = "Alterar";
                celulaAcao.appendChild(linkAlterar);
            });
        })
        .catch(error => {
            console.error('Erro ao buscar produtos:', error);
        });

    // Atualizar paginação (a implementar)
}
