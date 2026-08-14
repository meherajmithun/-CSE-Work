from queue import PriorityQueue

graph = {
    'Arad': [('Zerind', 75), ('Sibiu', 140), ('Timisoara', 118)],
    'Zerind': [('Arad', 75), ('Oradea', 71)],
    'Oradea': [('Zerind', 71), ('Sibiu', 151)],
    'Sibiu': [('Arad', 140), ('Oradea', 151), ('Fagaras', 99), ('Rimnicu Vilcea', 80)],
    'Timisoara': [('Arad', 118), ('Lugoj', 111)],
    'Lugoj': [('Timisoara', 111), ('Mehadia', 70)],
    'Mehadia': [('Lugoj', 70), ('Drobeta', 75)],
    'Drobeta': [('Mehadia', 75), ('Craiova', 120)],
    'Craiova': [('Drobeta', 120), ('Rimnicu Vilcea', 146), ('Pitesti', 138)],
    'Rimnicu Vilcea': [('Sibiu', 80), ('Craiova', 146), ('Pitesti', 97)],
    'Fagaras': [('Sibiu', 99), ('Bucharest', 211)],
    'Pitesti': [('Rimnicu Vilcea', 97), ('Craiova', 138), ('Bucharest', 101)],
    'Bucharest': [('Fagaras', 211), ('Pitesti', 101), ('Giurgiu', 90)],
    'Giurgiu': [('Bucharest', 90)]
}


h = {
    'Arad': 366, 'Zerind': 374, 'Oradea': 380, 'Sibiu': 253, 'Timisoara': 329,
    'Lugoj': 244, 'Mehadia': 241, 'Drobeta': 242, 'Craiova': 160, 'Rimnicu Vilcea': 193,
    'Fagaras': 178, 'Pitesti': 98, 'Bucharest': 0, 'Giurgiu': 77
}

def a_star(src, dest):
    pq = PriorityQueue()
    pq.put((0,src))

    came_from={}
    cost_so_far={}
    came_from[src] = None; cost_so_far[src] = 0

    while not pq.empty():
        cuurent_cost,current_node = pq.get()
        if current_node==dest:
            break

        for next_node, cost in graph[current_node]:

            new_cost = cost_so_far[current_node] + cost

            if next_node not in cost_so_far or new_cost<cost_so_far[next_node]:

                cost_so_far[next_node] = new_cost
                total_cost = new_cost+h[next_node]
                pq.put((total_cost, next_node))
                came_from[next_node] = current_node
                

    path=[]
    x = dest
    while(dest!=None):
        path.append(dest)
        dest = came_from[dest]

    path.reverse()
    return path, cost_so_far[x]

des, value = a_star('Arad', 'Bucharest')
print(value)
print("Path ", "->".join(des))


