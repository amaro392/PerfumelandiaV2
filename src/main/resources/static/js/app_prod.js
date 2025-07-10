    
<<<<<<< HEAD
const API_URL = "http://192.168.2.103:8080/api/v2/productos";
=======
const API_URL = "http://192.168.1.9:8080/api/v2/productos";
>>>>>>> a4e62d2bd43a462d5020d9369e354138e2ad1c3f
    
    function agregarProducto() {
      const nombre = document.getElementById("nombre").value;
      const marca = document.getElementById("marca").value;
      const stock = parseInt(document.getElementById("stock").value);
      const precio = parseInt(document.getElementById("precio").value);

<<<<<<< HEAD
      fetch("http://192.168.2.103:8080/api/v2/productos", {
=======
      fetch("http://192.168.1.9:8080/api/v2/productos", {
>>>>>>> a4e62d2bd43a462d5020d9369e354138e2ad1c3f
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({ nombre, marca, stock, precio })
      })
      .then(res => {
        if (res.ok) {
          alert("Producto agregado correctamente");
        } else {
          alert("Error al agregar producto");
        }
      });
    }