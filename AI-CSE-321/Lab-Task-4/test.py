import heapq

graph = {'A': [('B',5), ('C', 7)],
         'B': [('D',7),('E', 3)],
         'C': [('F',4)],
         'D': [],
         'E': [],
         'F': []
         }

def DLS(src, dest, limit):
    stack=[]
    visited=[]
    stack.append((src, 0, 0, [src]))
    while stack:
        node, cost, depth , path = stack.pop()
        if node not in visited:
            visited.append(node)
            if node == dest:
                print("Destination Found")
                return
            if depth == limit:
                continue

            for child, weight in reversed(graph[node]):
                if child not in visited:
                    stack.append((child, cost+weight, depth+1, path+[child]))

    print("Destination Not Found")

DLS('A', 'K', 3)