document.addEventListener('DOMContentLoaded', function() {
  const apiUrlGetProducts = 'http://localhost:8080/products';
  const apiUrlActivateProduct = 'http://localhost:8080/products/{id}/activate';
  const apiUrlDeactivateProduct = 'http://localhost:8080/products/{id}/deactivade';

  // Função para carregar produtos
  function loadProducts() {
      fetch(apiUrlGetProducts)
          .then(response => {
              if (!response.ok) {
                  throw new Error(`HTTP error! status: ${response.status}`);
              }
              return response.json();
          })
          .then(data => {
              const tableBody = document.getElementById('tabela-produtos');
              tableBody.innerHTML = '';

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
                      <td>${produto.status ? 'Ativo' : 'Inativo'}</td>
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
                  statusBtn.innerText = produto.status ? 'Desativar' : 'Ativar';
                  statusBtn.className = produto.status ? 'btn btn-secondary' : 'btn btn-primary';
                  statusBtn.addEventListener('click', () => toggleProductStatus(produto.id, produto.status));
                  tdAcao.appendChild(statusBtn);

                  tr.appendChild(tdAcao);
                  tableBody.appendChild(tr);
              });
          })
          .catch(error => console.error('Erro ao buscar produtos:', error));
  }

  // Função para alternar o status do produto
  function toggleProductStatus(productId, isActive) {
      const apiUrl = isActive ? apiUrlDeactivateProduct.replace('{id}', productId) : apiUrlActivateProduct.replace('{id}', productId);

      fetch(apiUrl, {
          method: 'PATCH',
          headers: {
              'Content-Type': 'application/json',
          }
      })
      .then(response => {
          if (!response.ok) {
              throw new Error(`HTTP error! status: ${response.status}`);
          }
          return response.text(); // Processa a resposta como texto
      })
      .then(() => {
          console.log('Status do produto alterado com sucesso');
          alert('Status do produto alterado com sucesso!');
          loadProducts(); // Recarrega os dados da tabela
      })
      .catch(error => {
          console.error('Erro ao alterar status do produto:', error);
      });
  }

  // Função para visualizar produto
  function viewProduct(productId) {
      window.location.href = `detalhe-produto-page-tela-produtos.html?productId=${productId}`;
  }

  // Função para editar produto
  function editProduct(productId) {
      window.location.href = `editar-produto.html?productId=${productId}`;
  }

  loadProducts(); // Carrega produtos ao carregar a página
});
