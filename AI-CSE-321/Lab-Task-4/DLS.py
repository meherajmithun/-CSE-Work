graph = {
    'A': [('B', 2), ('C', 4)],
    'B': [('D', 3), ('E', 1)],
    'C': [('F', 5)],
    'D': [],
    'E': [('G', 2)],
    'F': [],
    'G': []
}

def DLS(limit, src, des):
            #node , depth, cost, path
    stack = [(src, 0, 0, [src])]

    while stack:
        node, depth,cost, path = stack.pop()
    
        if node==des:
            print("Goal Found")
            print("Path : "," -> ".join(path))
            print("Total Cost : ",cost)
            return
    
        if limit < depth:
            print("Goal Not Found")
            continue
        
        for child, weight in reversed(graph[node]):
            stack.append((child, depth+1, cost+weight, path+[child]))

DLS(3, 'A', 'G')

