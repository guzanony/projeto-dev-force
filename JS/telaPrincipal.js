document.addEventListener("DOMContentLoaded", function() {
    const role = localStorage.getItem('role');
    console.log('Role recuperada do localStorage:', role);

    if (!role) {
        alert('Role ausente ou inválida');
        window.location.href = '../html/login.html';
        return;
    }

    const main = document.querySelector(".main");

    if (role === 'ROLE_ADMIN') {
        main.innerHTML += `
            <div class="card shadow p-5" style="width: 18rem; height: 22rem">
                <img src="../img/SeacrhProduct.svg" class="card-img-top" alt="usersimg">
                <div class="card-body mt-5">
                    <a href="../html/TabelaUsuarios.html" class="btn btn-outline-success col-12">Lista de Usuarios</a>
                </div>
            </div>
        `;
    }

    main.innerHTML += `
        <div class="card shadow p-5" style="width: 18rem; height: 22rem">
            <img src="../img/SeacrhProduct.svg" class="card-img-top" alt="productimg">
            <div class="card-body mt-5">
                <a href="../html/tela-produtos.html" class="btn btn-outline-success col-12">Lista de Produtos</a>
            </div>
        </div>
    `;

    if (role === 'ROLE_ESTOQUISTA') {
        main.innerHTML += `
            <div class="card shadow p-5" style="width: 18rem; height: 22rem">
                <img src="../img/SeacrhProduct.svg" class="card-img-top" alt="ordersimg">
                <div class="card-body mt-5">
                    <a href="../html/telaPedidos.html" class="btn btn-outline-success col-12">Lista de Pedidos</a>
                </div>
            </div>
        `;
    }
});
