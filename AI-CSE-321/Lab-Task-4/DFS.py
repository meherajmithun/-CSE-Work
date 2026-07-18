graph = {'A': [('B',5), ('C', 7)],
         'B': [('D',7),('E', 3)],
         'C': [('F',4)],
         'D': [],
         'E': [],
         'F': []
         }
def dfs(src, dest):
    visited=[]
    stack = []
    stack.append((src, 0))
    while stack:
        node , cost = stack.pop()
        if node not in visited:
            visited.append(node)
            print(node, end=" ")
            if node==dest:
                print("Destination Found")
                return
            for child, weight in reversed(graph[node]):
                if child not in visited:
                    stack.append((child, weight+cost))
        


    print("Destination Not Found")

dfs('A', 'F')

