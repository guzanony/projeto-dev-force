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
                    <td id="status-${produto.id}">${produto.status}</td>
                `;
                const tdAcao = document.createElement('td');

                let viewBtn = document.createElement('button');
                const statusId = `status-${produto.id}`;
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
                const statusId = `status-${produto.id}`;
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

     const statusCell = document.getElementById(statusBtn.id.split('-')[1]);
        const newStatus = statusBtn.innerText === 'Desativar' ? 'Inativo' : 'Ativo';

        // Atualizar texto e classe do botão baseado no novo status
          statusBtn.innerText = newStatus === 'Inativo' ? 'Ativar' : 'Desativar';
          statusBtn.className = newStatus === 'Inativo' ? 'btn btn-primary' : 'btn btn-secondary';
          statusCell.innerText = newStatus; // Atualizar texto de status na tabela

        // Preparar o corpo da requisição com o status atualizado
      const requestBody = {
        id: productId,
        status: newStatus
      };

         // Atualizar o status do produto no servidor
          fetch(`http://localhost:8080/products/${productId}`, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestBody)
          })
            .then(response => {
              if (response.ok) {
                console.log(`Status do produto ${productId} atualizado para ${newStatus} com sucesso`);
              } else {
                console.error(`Erro ao atualizar status do produto ${productId}: ${response.statusText}`);
              }
            })

            .catch(error => console.error('Erro ao enviar requisição de atualização:', error));
        }

document.addEventListener('DOMContentLoaded', buscarProduto);
