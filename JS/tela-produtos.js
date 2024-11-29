document.addEventListener('DOMContentLoaded', function() {
  const apiUrlGetProducts = 'http://localhost:8080/products';
  const apiUrlActivateProduct = 'http://localhost:8080/products/{id}/activate';
  const apiUrlDeactivateProduct = 'http://localhost:8080/products/{id}/deactivade';
  const inputSearch = document.getElementById('search-input');
  let allProducts = [];

  function loadProducts() {
      fetch(apiUrlGetProducts)
          .then(response => {
              if (!response.ok) {
                  throw new Error(`HTTP error! status: ${response.status}`);
              }
              return response.json();
          })
          .then(data => {
              allProducts = data;
              displayProducts(allProducts);
          })
          .catch(error => console.error('Erro ao buscar produtos:', error));
  }

  function displayProducts(products) {
      const tableBody = document.getElementById('tabela-produtos');
      tableBody.innerHTML = '';

      const formatador = new Intl.NumberFormat('pt-BR', {
          style: 'currency',
          currency: 'BRL',
      });

      products.forEach(produto => {
          const tr = document.createElement('tr');
          tr.innerHTML = `
              <td>${produto.id}</td>
              <td>${produto.nome}</td>
              <td>${produto.quantidade}</td>
              <td>${formatador.format(produto.preco)}</td>
              <td>${produto.active ? 'Ativo' : 'Inativo'}</td>
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
          statusBtn.innerText = produto.active ? 'Desativar' : 'Ativar';
          statusBtn.className = produto.active ? 'btn btn-secondary' : 'btn btn-primary';
          statusBtn.addEventListener('click', () => toggleProductStatus(produto.id, produto.active));
          tdAcao.appendChild(statusBtn);

          tr.appendChild(tdAcao);
          tableBody.appendChild(tr);
      });
  }

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
          return response.text();
      })
      .then(() => {
          console.log('Status do produto alterado com sucesso');
          alert('Status do produto alterado com sucesso!');
          loadProducts();
      })
      .catch(error => {
          console.error('Erro ao alterar status do produto:', error);
      });
  }

  function viewProduct(productId) {
      window.location.href = `detalhe-produto-page-tela-produtos.html?productId=${productId}`;
  }

  function editProduct(productId) {
      window.location.href = `form-produtos.html?productId=${productId}`;
  }

  function filterProducts(searchTerm) {
      const filteredProducts = allProducts.filter(produto =>
          produto.nome.toLowerCase().includes(searchTerm.toLowerCase())
      );
      displayProducts(filteredProducts);
  }

  inputSearch.addEventListener('input', function(event) {
      const searchTerm = event.target.value;
      filterProducts(searchTerm);
  });

  loadProducts();
});
