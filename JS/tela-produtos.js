function buscarProduto() {
    fetch('http://localhost:8080/products')
        .then(response => response.json())
        .then(data => {
            document.getElementById('tabela-produtos').innerHTML = '';

            const formatador = new Intl.NumberFormat('pt-BR', {
                style: 'currency',
                currency: 'BRL',
            });

            data.forEach(produto => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${produto.id}</td>
                    <td>${produto.nome}</td>
                    <td>${produto.quantidade}</td>
                    <td>${formatador.format(produto.preco)}</td>
                    <td>${produto.status = 'Ativo'}</td>
                    <td><a href="/projeto-dev-force/html/form-produtos.html">Alterar</a></td>
                `;
                document.getElementById('tabela-produtos').appendChild(tr);
            });
        })
        .catch(error => console.error('Erro ao buscar produtos:', error));
}

// Chama a função buscarProduto quando a página carregar
document.addEventListener('DOMContentLoaded', buscarProduto);
