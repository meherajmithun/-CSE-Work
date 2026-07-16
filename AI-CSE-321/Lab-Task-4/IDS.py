graph = {
    'A': [('B', 2), ('C', 4)],
    'B': [('D', 3), ('E', 1)],
    'C': [('F', 5)],
    'D': [],
    'E': [('G', 2)],
    'F': [],
    'G': []
}

def dls(src, dest, limit):
    stack=[(src, 0, 0, [src])]
    while stack:
        node, depth, cost, path = stack.pop()
        if(node == dest):
            return path,cost
        
        if depth>=limit:
            continue

        for child, weight in reversed(graph[node]):
            stack.append((child, depth+1, cost+weight, path+[child]))

    return None,None

def ids(src, dest, mx_depth):
    for limit in range(mx_depth+1):
        print("Depth Limit : ", limit)

        path,cost = dls(src, dest, limit)
        if path:
            print("Goal Found")
            print("Path : "," -> ".join(path))
            print("Cost : ", cost)
            return
    
    print("Goal Not Found")

ids('A', 'G', 5)