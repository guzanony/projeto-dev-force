function getProductIdFromUrl() {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get('productId');
}

function loadProductDetails(productId) {
    fetch('http://localhost:8080/products/${productId}')
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(product => {
            // Atualize o conteúdo da página com os detalhes do produto
            document.getElementById('product-title').textContent = product.nome;
        })
        .catch(e => {
            console.error('Não foi possível carregar os detalhes do produto:', e);
        });
}

document.addEventListener('DOMContentLoaded', () => {
    const productId = getProductIdFromUrl();
    if (productId) {
        loadProductDetails(productId);
    } else {
        console.error('ID do produto não fornecido.');
    }
});
