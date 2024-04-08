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
                `;
                const tdAcao = document.createElement('td');

                let viewBtn = document.createElement('button');
                viewBtn.innerText = 'Visualizar';
                viewBtn.className = 'btn btn-info';
                viewBtn.addEventListener('click', () => viewProduct(produto.id));
                tdAcao.appendChild(viewBtn);

                let editBtn = document.createElement('button');
                editBtn.innerText = 'Editar';
                editBtn.className = 'btn btn-warning';
                editBtn.addEventListener('click', () => editProduct(produto.id));
                tdAcao.appendChild(editBtn);

                let statusBtn = document.createElement('button');
                statusBtn.innerText = produto.status = 'Desativar';
                statusBtn.className = produto.status ? 'btn btn-secondary' : 'btn btn-primary';
                statusBtn.addEventListener('click', () => toggleProductStatus(produto.id, produto.status));
                tdAcao.appendChild(statusBtn);

                tr.appendChild(tdAcao);
                document.getElementById('tabela-produtos').appendChild(tr);
            });
        })
        .catch(error => console.error('Erro ao buscar produtos:', error));
}

function viewProduct(productId) {
   window.location.href = 'detalhe-produto-page-tela-produtos.html?productId=${productId}';
}

function editProduct(productId) {
    console.log('Editar produto', productId);
}

function toggleProductStatus(productId, status) {
    console.log('Ativar/Desativar produto', productId, status);
}

document.addEventListener('DOMContentLoaded', buscarProduto);
