<<<<<<< HEAD
const API_URL="http://192.168.2.103:8080/api/v2/reportes";
=======
const API_URL="http://192.168.1.9:8080/api/v2/reportes";
>>>>>>> a4e62d2bd43a462d5020d9369e354138e2ad1c3f

function enviarR(){
    const nombre = document.getElementById("nombre").value.trim();
    const email = document.getElementById("email").value.trim();
    const asunto = document.getElementById("asunto").value.trim();
      if (!nombre || !email || !asunto) {
        alert("completa todos los campos.");
        return;
      }

<<<<<<< HEAD
      fetch("http://192.168.2.103:8080/api/v2/reportes", {
=======
      fetch("http://192.168.1.9:8080/api/v2/reportes", {
>>>>>>> a4e62d2bd43a462d5020d9369e354138e2ad1c3f
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nombre, email, asunto })
      })
      .then(response => {
        if (response.ok) {
          alert("Reporte Enviado correctamente!");
          window.location.href = "index.html";
        } else {
          alert("Error Verifiaca los campos.");
        }
      });
    }