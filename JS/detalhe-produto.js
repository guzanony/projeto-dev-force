document.addEventListener('DOMContentLoaded', function() {
    const params = new URLSearchParams(window.location.search);
    const productId = params.get('productId');
    const apiUrlGetProductDetails = `http://localhost:8080/products/${productId}`;

    function loadProductDetails() {
        fetch(apiUrlGetProductDetails)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(product => {
                document.getElementById('product-id').textContent = product.id;
                document.getElementById('product-name').textContent = product.nome;
                document.getElementById('product-quantity').textContent = product.quantidade;
                document.getElementById('product-price').textContent = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' }).format(product.preco);
                document.getElementById('product-description').textContent = product.descricao;

                const imagesContainer = document.getElementById('product-images');
                imagesContainer.innerHTML = '';
                product.images.forEach(image => {
                    const imgElement = document.createElement('img');
                    imgElement.src = `data:image/jpeg;base64,${image.image}`;
                    imgElement.alt = 'Product Image';
                    imagesContainer.appendChild(imgElement);
                });
            })
            .catch(error => console.error('Erro ao buscar detalhes do produto:', error));
    }

    loadProductDetails();
});
