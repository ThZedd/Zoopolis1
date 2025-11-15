window.onload = async function() {
    // Initial fetch or other setup code can be added here
};

async function fetchAnimal() {
    try {
        let animalId = document.getElementById("animalId").value;

        // Faz a requisição para o backend
        let res = await $.ajax({
            url: `http://localhost:8080/api/animalsDTO/${animalId}`,
            method: "GET",
            dataType: "json"
        });

        console.log(res); // Verifica a resposta da API

        // Substitui '10.0.2.2' por 'localhost' na URL da imagem
        let imageUrl = res.imageUrl.replace("http://10.0.2.2", "http://localhost");

        document.getElementById("animalName").innerText = res.name;
        document.getElementById("animalCiName").innerText = res.ciName;
        document.getElementById("animalDescription").innerText = res.description;
        document.getElementById("animalImage").src = imageUrl;

    } catch (error) {
        console.error(error);
        document.getElementById("animalInfo").innerText = "Error fetching animal data.";
    }
}





