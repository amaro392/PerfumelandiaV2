function cargarNotificaciones() {
<<<<<<< HEAD
  fetch("http://192.168.2.103:8080/api/v2/notificaciones")
=======
  fetch("http://192.168.1.9:8080/api/v2/notificaciones")
>>>>>>> a4e62d2bd43a462d5020d9369e354138e2ad1c3f
    .then(res => res.json())
    .then(data => {
      const tbody = document.querySelector("#tablaNotificaciones tbody");
      tbody.innerHTML = "";

      data.forEach(n => {
        const fila = `<tr><td>${n.asunto}</td><td>${n.descripcion}</td></tr>`;
        tbody.innerHTML += fila;
      });
    });
}

window.onload = cargarNotificaciones;
