document.addEventListener("DOMContentLoaded", function () {
  let editBtn = document.createElement("button");
  editBtn.textContent = "Editar-Perfil";
  editBtn.className = "btn btn-warning";
  editBtn.addEventListener("click", () => {
    window.location.href = `/html/page-cadastro-cliente.html?userId=${user.id}`;
  });
  cellActions.appendChild(editBtn);
  const cartBadge = document.getElementById("cart-badge");
  console.log("cartBadge:", cartBadge);
  let itemCount = 0;

  document.querySelectorAll(".add-to-cart").forEach((button) => {
    console.log("Button found:", button);
    button.addEventListener("click", function () {
      itemCount++;
      console.log("Item count:", itemCount);
      cartBadge.textContent = itemCount;
      alert("Produto adicionado ao carrinho!");
    });
  });
  const urlParams = new URLSearchParams(window.location.search);
  const nome = urlParams.get("nomeCompleto");
  if (nome) {
    document.getElementById("nome-usuario").textContent = nome;
  } else {
    window.location.href = "page-login-cliente.html";
  }

  var token = localStorage.getItem("token");
  if (!token) {
    window.location.href = "page-login-cliente.html";
  } else {
    fetch("http://localhost:8080/auth/validateTokenCliente", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => {
        if (!response.ok) {
          localStorage.removeItem("token");
          window.location.href = "page-login-cliente.html";
        }
      })
      .catch((error) => {
        console.error("Erro ao validar o token:", error);
      });
  }
});
