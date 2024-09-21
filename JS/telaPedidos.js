document.addEventListener("DOMContentLoaded", function() {
    fetch('http://localhost:8080/pedidos/all', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(pedidos => {
        const ordersTableBody = document.getElementById('ordersTableBody');
        pedidos.forEach(pedido => {
            const tr = document.createElement('tr');

            const dataTd = document.createElement('td');
            dataTd.textContent = new Date(pedido.dataCriacao).toLocaleString();
            tr.appendChild(dataTd);

            const numeroTd = document.createElement('td');
            numeroTd.textContent = pedido.numeroPedido;
            tr.appendChild(numeroTd);

            const valorTd = document.createElement('td');
            valorTd.textContent = pedido.valorTotal ? `R$ ${pedido.valorTotal.toFixed(2)}` : 'N/A';
            tr.appendChild(valorTd);

            const statusTd = document.createElement('td');
            statusTd.textContent = pedido.status;
            tr.appendChild(statusTd);

            const actionTd = document.createElement('td');
            const button = document.createElement('button');
            button.textContent = 'Marcar como Entregue';
            button.classList.add('btn', 'btn-primary');
            button.addEventListener('click', function() {
                fetch(`http://localhost:8080/pedidos/${pedido.id}/status`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify('Entregue')
                })
                .then(response => {
                    if (response.ok) {
                        statusTd.textContent = 'Entregue';
                        button.disabled = true;
                    } else {
                        alert('Erro ao atualizar o status do pedido.');
                    }
                });
            });
            actionTd.appendChild(button);
            tr.appendChild(actionTd);

            ordersTableBody.appendChild(tr);
        });
    })
    .catch(error => {
        console.error('Erro ao buscar pedidos:', error);
    });
});
