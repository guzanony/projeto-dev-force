document.addEventListener('DOMContentLoaded', function() {
    const params = new URLSearchParams(window.location.search);
    const productId = params.get('productId');

    if (productId) {
        carregarDetalhesProduto(productId);
    }

    function carregarDetalhesProduto(produtoId) {
        fetch(`http://localhost:8080/products/${produtoId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao buscar detalhes do produto');
            }
            return response.json();
        })
        .then(produto => {
            document.getElementById('product-name').textContent = produto.nome;
            document.getElementById('product-description').textContent = produto.descricao;
            document.getElementById('product-price').textContent = `R$ ${produto.preco.toFixed(2)}`;
            document.getElementById('product-quantity').textContent = produto.quantidade;
            const base64Image = produto.image;
            const imageElement = document.getElementById('product-image');
            imageElement.src = `data:image/jpeg;base64,${base64Image}`;
        })
        .catch(error => {
            console.error('Erro ao carregar os detalhes do produto:', error);
            alert('Erro ao carregar os detalhes do produto.');
        });
    }
});
