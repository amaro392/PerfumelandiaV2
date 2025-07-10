document.addEventListener("DOMContentLoaded", () => {
<<<<<<< HEAD
  fetch("http://192.168.2.103:8080/api/v2/productos")
=======
  fetch("http://192.168.1.9:8080/api/v2/productos")
>>>>>>> a4e62d2bd43a462d5020d9369e354138e2ad1c3f
    .then(res => res.json())
    .then(data => {
      const productos = data._embedded?.productoList || [];
      productos.forEach(producto => {
        const stockSpan = document.querySelector(`.stock[data-id='${producto.id}']`);
        if (stockSpan) {
          stockSpan.textContent = producto.stock;
        }
      });
    })
    .catch(err => {
      console.error("Error cargando el stock:", err);
    });
});
