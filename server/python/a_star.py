from queue import PriorityQueue
import math
import psycopg2
from psycopg2 import sql

# Configuração da conexão à base de dados
DB_CONFIG = {
    'host': 'localhost',
    'port': 5434,  # ou 5432 se conectares diretamente ao primary
    'database': 'postgres',
    'user': 'postgres',
    'password': 'ThZ3d1112'
}

def get_person_position():
    """Busca a posição da pessoa da base de dados"""
    try:
        conn = psycopg2.connect(**DB_CONFIG)
        cursor = conn.cursor()

        # Buscar a primeira pessoa (ou podes modificar para buscar por ID)
        cursor.execute("""
                       SELECT per_id, per_name
                       FROM zoopolis.person
                       ORDER BY per_id
                           LIMIT 1
                       """)
        person = cursor.fetchone()

        if person:
            print(f"Pessoa encontrada: {person[1]} (ID: {person[0]})")
            # Por enquanto, retornamos posição fixa [0,0]
            # Mais tarde podes adicionar coordenadas reais na tabela person
            return [0, 0]
        else:
            print("Nenhuma pessoa encontrada na base de dados")
            return [0, 0]

    except Exception as e:
        print(f"Erro ao buscar pessoa: {e}")
        return [0, 0]
    finally:
        if conn:
            conn.close()

def list_all_enclosures():
    """Lista todas as enclosures disponíveis"""
    try:
        conn = psycopg2.connect(**DB_CONFIG)
        cursor = conn.cursor()

        cursor.execute("""
                       SELECT enc_id, enc_name, enc_lat, enc_long
                       FROM zoopolis.enclosure
                       ORDER BY enc_id
                       """)

        enclosures = cursor.fetchall()

        print("\n📋 Enclosures disponíveis:")
        for enc in enclosures:
            enc_id, enc_name, enc_lat, enc_long = enc
            print(f"   {enc_id}. {enc_name}")

        return enclosures

    except Exception as e:
        print(f"Erro ao listar enclosures: {e}")
        return []
    finally:
        if conn:
            conn.close()

def select_enclosure_interactive():
    """Deixa o utilizador escolher uma enclosure"""
    enclosures = list_all_enclosures()

    if not enclosures:
        return None

    try:
        choice = int(input("\n🎯 Seleciona o ID da enclosure: "))

        # Encontrar a enclosure escolhida
        for enc in enclosures:
            if enc[0] == choice:
                enc_id, enc_name, enc_lat, enc_long = enc
                x = (enc_id * 3) % 15
                y = (enc_id * 7) % 15
                position = [x, y]
                print(f"✅ Selecionado: {enc_name} -> Posição: [{x}, {y}]")
                return position

        print("❌ ID não encontrado")
        return None

    except ValueError:
        print("❌ Por favor insere um número válido")
        return None

def get_visited_positions(person_id):
    """Busca posições já visitadas pela pessoa"""
    try:
        conn = psycopg2.connect(**DB_CONFIG)
        cursor = conn.cursor()

        cursor.execute("""
                       SELECT DISTINCT sa.sa_id, sa.sa_name
                       FROM zoopolis.visited v
                                JOIN zoopolis.sub_area sa ON v.vi_sa_id = sa.sa_id
                       WHERE v.vi_per_id = %s
                       """, (person_id,))

        visited = cursor.fetchall()
        positions = []

        for area in visited:
            # Mapear áreas visitadas para posições no grid
            # Por enquanto, posições aleatórias - podes ajustar
            sa_id, sa_name = area
            x = sa_id % 10
            y = (sa_id * 2) % 10
            positions.append([x, y])
            print(f"Área visitada: {sa_name} -> Posição: [{x}, {y}]")

        return positions

    except Exception as e:
        print(f"Erro ao buscar áreas visitadas: {e}")
        return []
    finally:
        if conn:
            conn.close()

def update_score(person_id, new_score):
    """Atualiza a pontuação da pessoa na base de dados"""
    try:
        conn = psycopg2.connect(**DB_CONFIG)
        cursor = conn.cursor()

        cursor.execute("""
                       UPDATE zoopolis.person
                       SET per_points = per_points + %s
                       WHERE per_id = %s
                       """, (new_score, person_id))

        conn.commit()
        print(f"Pontuação atualizada: +{new_score} pontos para a pessoa ID {person_id}")

    except Exception as e:
        print(f"Erro ao atualizar pontuação: {e}")
        conn.rollback()
    finally:
        if conn:
            conn.close()

def a_star(start, goals):
    """Algoritmo A* para um ou mais goals"""
    pq = PriorityQueue()
    pq.put((0, tuple(start)))
    came_from = {tuple(start): None}
    cost_so_far = {tuple(start): 0}

    while not pq.empty():
        current_cost, current_pos = pq.get()

        # Verificar se chegou a algum goal
        if list(current_pos) in goals:
            # Reconstruir caminho
            path = []
            while current_pos is not None:
                path.append(current_pos)
                current_pos = came_from[current_pos]
            return path[::-1]

        # Gerar vizinhos
        x, y = current_pos
        neighbors = [(x+1, y), (x-1, y), (x, y+1), (x, y-1)]

        for neighbor in neighbors:
            new_cost = cost_so_far[current_pos] + 1
            if neighbor not in cost_so_far or new_cost < cost_so_far[neighbor]:
                cost_so_far[neighbor] = new_cost
                # Heurística: distância para o goal mais próximo
                min_heuristic = min([abs(neighbor[0]-g[0]) + abs(neighbor[1]-g[1]) for g in goals])
                priority = new_cost + min_heuristic
                pq.put((priority, neighbor))
                came_from[neighbor] = current_pos

    return []  # Sem caminho encontrado

def main():
    """Função principal do programa"""
    print("🚀 Sistema de Pathfinding para Zoológico")

    # Buscar dados da base de dados
    print("\n=== Buscando dados da Base de Dados ===")
    person_position = get_person_position()

    # Selecionar enclosure específica
    enclosure_alvo = select_enclosure_interactive()

    if not enclosure_alvo:
        print("❌ Nenhuma enclosure selecionada. A terminar...")
        return

    # Usar apenas a enclosure selecionada
    enclosures_positions = [enclosure_alvo]
    visited_positions = get_visited_positions(1)  # Assumindo pessoa com ID 1

    print(f"\n📍 Posição inicial da pessoa: {person_position}")
    print(f"🎯 Enclosure alvo: {enclosure_alvo}")
    print(f"📌 Posições já visitadas: {visited_positions}")

    # Executar o algoritmo A*
    print("\n=== Executando Algoritmo A* ===")
    score = 10  # Pontuação por alcançar o alvo
    path = a_star(person_position, enclosures_positions)

    print("Caminho encontrado:", path)
    print("Score final:", score)
    print(f"📏 Distância: {len(path) - 1 if path else 0} passos")

    # Atualizar pontuação na base de dados
    if path:  # Só atualizar se encontrou caminho
        update_score(1, score)  # Assumindo pessoa com ID 1
    else:
        print("⚠️  Nenhum caminho encontrado - pontuação não atualizada")

# Executar o programa
if __name__ == "__main__":
    main()