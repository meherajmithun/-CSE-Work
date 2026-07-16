import heapq

graph = {'A': [('B',5), ('C', 7)],
         'B': [('D', 7),('E', 3)],
         'C': [('F', 4)],
         'D': [],
         'E': [],
         'F': []
         }

def ucs(src, dest):
    pq = []
    heapq.heappush(pq, (0, src, [src]))
    visited=[]
    while pq:
        cost,node,path = heapq.heappop(pq)
        if node in visited:
            continue
        
        visited.append(node)
        print(node, end=" ")
        if node == dest:
            print(" -> Goal Reached")
            # print(path); 
            print("path : "," -> ".join(path))
            print("Total cost : ", cost)
            return;

        for neighbour, weight in graph[node]:
            if neighbour in visited:
                continue
            heapq.heappush(pq, (weight+cost, neighbour, path+[neighbour]))

    print("Goal Not Found")

ucs('A', 'F')
