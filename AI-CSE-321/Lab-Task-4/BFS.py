graph = {'A': [('B',5), ('C', 7)],
         'B': [('D',7),('E', 3)],
         'C': [('F',4)],
         'D': [],
         'E': [],
         'F': []
         }
def bfs(src, dest):
    visited=[]
    queue=[]
    visited.append(src)
    queue.append((src, 0))
    while queue:
        node , cost = queue.pop(0)
        print(node, end=" ")
        if(node==dest):
            print("Destination Found")
            return
        
        for child, weight in graph[node]:
            if child not in visited:
                queue.append((child, weight+cost))
                visited.append(child)

    print("Destination Not Found")

bfs('A', 'F')

