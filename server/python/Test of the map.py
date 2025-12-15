import osmnx as ox
import xml.etree.ElementTree as ET

print("1. A Carregar o Grafo (caminhos) ")

try:
    # Lê o arquivo zoo.osm (Grifo/Ruas)
    # simplify=False mantém a geometria exata das curvas do zoo
    G = ox.graph_from_xml("zoo.osm", simplify=False)

    print(f"Sucesso! O grafo foi carregado.")
    print(f"   -> Nós (pontos de conexão): {len(G.nodes)}")
    print(f"   -> Arestas (caminhos): {len(G.edges)}")

    # Desenha o gráfico (opcional, para conferência visual)
    print("   -> Gerando imagem das linhas... (feche a janela para continuar)")
    ox.plot_graph(G, node_size=0, edge_color='red', bgcolor='white')

except FileNotFoundError:
    print("ERRO: O arquivo 'zoo.osm' não está na pasta.")
    exit()
except Exception as e:
    print(f"Ocorreu um erro no grafo: {e}")
    exit()

print("\n2. A Mapear as atrações (animais/locais) ---")

try:
    # Carrega o mesmo arquivo como XML puro para ler os nomes
    tree = ET.parse('zoo.osm')
    root = tree.getroot()

    # Dicionário auxiliar para guardar onde fica cada nó (ID -> Lat/Lon)
    coordenadas_temp = {}

    # Lista final para você usar
    meus_locais = {}

    # 1: Ler a posição de TODOS os nós
    for node in root.findall('node'):
        id_no = node.get('id')
        lat = float(node.get('lat'))
        lon = float(node.get('lon'))
        coordenadas_temp[id_no] = (lat, lon)

        # Verifica se é um ponto com nome (Ex: Bebedouro, Estátua)
        nome = None
        for tag in node.findall('tag'):
            if tag.get('k') == 'name':
                nome = tag.get('v')

        if nome:
            meus_locais[nome] = (lat, lon)
            print(f"[PONTO] {nome}: {lat}, {lon}")

    # 2: Ler as Áreas (Ways) (Ex: Jaula dos Leões, Restaurantes)
    for way in root.findall('way'):
        nome = None
        for tag in way.findall('tag'):
            if tag.get('k') == 'name':
                nome = tag.get('v')

        if nome:
            # Calcula o centro da área (média das coordenadas dos cantos)
            lats = []
            lons = []
            for nd in way.findall('nd'):
                ref = nd.get('ref')
                if ref in coordenadas_temp:
                    lats.append(coordenadas_temp[ref][0])
                    lons.append(coordenadas_temp[ref][1])

            if lats:
                centro_lat = sum(lats) / len(lats)
                centro_lon = sum(lons) / len(lons)
                meus_locais[nome] = (centro_lat, centro_lon)
                print(f"[ÁREA]  {nome}: {centro_lat:.6f}, {centro_lon:.6f}")

    print(f"\nConcluído! Encontramos {len(meus_locais)} locais com nome.")

except Exception as e:
    print(f"Erro ao ler atrações: {e}")