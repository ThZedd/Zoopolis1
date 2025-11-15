let map; // Variável global para o mapa

async function fetchAnimal() {
    try {
        let animalId = 1; // ID fixo para teste
        // Faz a requisição para o backend
        let res = await $.ajax({
            url: `http://localhost:8080/api/animalsDTO/${animalId}`,
            method: "GET",
            dataType: "json"
        });

        console.log(res); // Verifica a resposta da API

        let imageUrl = res.imageUrl
            .replace("http://10.0.2.2", "http://localhost") // Substitui '10.0.2.2' por 'localhost'
            .replace(".jpg", "-icon.png"); // Adiciona '-icon' antes do sufixo '.jpg'
        // Redimensiona a imagem para ícone antes de adicionar o marcador
        const resizedIcon = await resizeImage(imageUrl, 64, 64); // Define 64x64 pixels
        addMarker(res.name, resizedIcon);

    } catch (error) {
        console.error(error);
        alert("Error fetching animal data.");
    }
}

function initMap() {
    // Inicializa o mapa
    map = new google.maps.Map(document.getElementById("map"), {
        center: { lat: 38.743208524415394, lng: -9.169150258828324 },
        zoom: 18,
        mapId: "87491901c1328aae",
    });

    // Chama fetchAnimal para obter os dados
    fetchAnimal();
}

function addMarker(title, imageUrl) {
    new google.maps.Marker({
        position: { lat: 38.74381102136896, lng: -9.17045917666804 },
        map,
        animation: google.maps.Animation.DROP,
        title: title,
        icon: imageUrl, // URL da imagem para o ícone do marcador
    });
}

async function resizeImage(imageUrl, width, height) {
    return new Promise((resolve, reject) => {
        const img = new Image();
        img.crossOrigin = "anonymous"; // Habilita o uso de CORS
        img.onload = () => {
            const canvas = document.createElement("canvas");
            canvas.width = 64;
            canvas.height = 100;
            const ctx = canvas.getContext("2d");
            ctx.drawImage(img, 0, 0, width, height);
            resolve(canvas.toDataURL()); // Retorna a imagem redimensionada como Data URL
        };
        img.onerror = reject;
        img.src = imageUrl;
    });
}
