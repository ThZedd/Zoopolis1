import os

import osmnx as ox
import heapq
import math
import json
import sys

# Variável Global para o Grafo
G = None


def carregar_mapa():
    """Lê o arquivo XML do OpenStreetMap."""
    global G
    if G is None:
        try:
            # 1. Descobre onde este script (a_star.py) está guardado no disco
            diretorio_do_script = os.path.dirname(os.path.abspath(__file__))

            # 2. Cria o caminho completo para o zoo.osm (ex: /app/python/zoo.osm)
            caminho_mapa = os.path.join(diretorio_do_script, "Zoo.osm")

            # simplify=False é vital para manter a precisão das curvas
            G = ox.graph_from_xml(caminho_mapa, simplify=False)

            # Converte o mapa para "não-direcionado" (Pedestres andam nos 2 sentidos)
            G = G.to_undirected()

            # print("Mapa carregado...", file=sys.stderr)

        except FileNotFoundError:
            print(json.dumps({"status": "erro", "mensagem": " O arquivo zoo.osm não foi encontrado"}))
            sys.exit(1)
    return G


#1. Heuristica
def haversine(lat1, lon1, lat2, lon2):

    R = 6371000
    phi1 = math.radians(lat1)
    phi2 = math.radians(lat2)
    delta_phi = math.radians(lat2 - lat1)
    delta_lambda = math.radians(lon2 - lon1)

    a = math.sin(delta_phi / 2) ** 2 + \
        math.cos(phi1) * math.cos(phi2) * \
        math.sin(delta_lambda / 2) ** 2
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    return R * c


#2. Algoritmo A*
def a_star_manual(grafo, start_node, goal_node):
    """Calcula o caminho entre DOIS pontos específicos."""
    open_set = []
    heapq.heappush(open_set, (0, start_node))
    came_from = {}

    g_score = {node: float('inf') for node in grafo.nodes}
    g_score[start_node] = 0

    f_score = {node: float('inf') for node in grafo.nodes}

    # Dados do destino para a heurística
    goal_data = grafo.nodes[goal_node]
    goal_lat = goal_data['y']
    goal_lon = goal_data['x']

    f_score[start_node] = haversine(grafo.nodes[start_node]['y'], grafo.nodes[start_node]['x'], goal_lat, goal_lon)

    while open_set:
        current_f, current = heapq.heappop(open_set)

        if current == goal_node:
            path = []
            while current in came_from:
                path.append(current)
                current = came_from[current]
            path.append(start_node)
            return path[::-1]  # Retorna Inicio -> Fim

        for neighbor in grafo.neighbors(current):
            edge_data = grafo.get_edge_data(current, neighbor)[0]
            weight = float(edge_data.get('length', 1.0))

            tentative_g_score = g_score[current] + weight

            if tentative_g_score < g_score[neighbor]:
                came_from[neighbor] = current
                g_score[neighbor] = tentative_g_score

                neighbor_lat = grafo.nodes[neighbor]['y']
                neighbor_lon = grafo.nodes[neighbor]['x']
                h_score = haversine(neighbor_lat, neighbor_lon, goal_lat, goal_lon)

                f_score[neighbor] = g_score[neighbor] + h_score
                heapq.heappush(open_set, (f_score[neighbor], neighbor))

    return []  # Falha


# --- 3. PROCESSAMENTO ---

def processar_rota_multipla(json_input):
    try:
        dados = json.loads(json_input)

        # O JSON recebe uma lista de pontos ordenados
        # Ex: [Origem, PontoA, PontoB, PontoC, Destino]
        stops = dados['paragens']

        if len(stops) < 2:
            return json.dumps({"status": "erro", "mensagem": "Preciso de pelo menos 2 pontos (Origem e Destino)"})

        grafo = carregar_mapa()
        rota_total_coords = []

        # Loop que percorre os pontos par a par
        # Se temos [A, B, C], ele faz: (A->B) e depois (B->C)
        for i in range(len(stops) - 1):

            # Pegar coordenadas do ponto atual e do próximo
            ponto_atual = stops[i]
            ponto_proximo = stops[i + 1]

            lat_start = float(ponto_atual['lat'])
            lon_start = float(ponto_atual['lng'])
            lat_goal = float(ponto_proximo['lat'])
            lon_goal = float(ponto_proximo['lng'])

            # Converter GPS -> Nós
            start_node = ox.distance.nearest_nodes(grafo, X=lon_start, Y=lat_start)
            goal_node = ox.distance.nearest_nodes(grafo, X=lon_goal, Y=lat_goal)

            # Calcular o segmento usando A*
            segmento_nodes = a_star_manual(grafo, start_node, goal_node)

            if not segmento_nodes:
                return json.dumps({
                    "status": "erro",
                    "mensagem": f"Não há caminho entre o ponto {i} e {i + 1}"
                })

            # Converter nós do segmento para coordenadas
            for index, node in enumerate(segmento_nodes):
                # Evitar duplicar o ponto de conexão (o fim de A é o início de B)
                # Só adicionamos se for o primeiro segmento OU se não for o primeiro nó deste segmento
                if i == 0 or index > 0:
                    point = grafo.nodes[node]
                    rota_total_coords.append({
                        'lat': point['y'],
                        'lng': point['x']
                    })

        return json.dumps({
            "status": "sucesso",
            "pontos": len(rota_total_coords),
            "rota": rota_total_coords
        })

    except Exception as e:
        return json.dumps({"status": "erro", "mensagem": str(e)})


if __name__ == "__main__":
    #1: Chamado pelo Java (tem argumentos)
    if len(sys.argv) > 1:
        json_entrada = sys.argv[1]
        print(processar_rota_multipla(json_entrada))

    #2: Teste manual(sem argumentos)
    else:
        exemplo_json = """
        {
            "paragens": [
                {"lat": 38.741800, "lng": -9.168700}, 
                {"lat": 38.745512, "lng": -9.173001}
            ]
        }
        """
        print(processar_rota_multipla(exemplo_json))