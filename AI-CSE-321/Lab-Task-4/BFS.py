from queue import Queue

graph = {'A': [('B',5), ('C', 7)],
         'B': [('D', 7),('E', 3)],
         'C': [('F', 4)],
         'D': [],
         'E': [],
         'F': []
         }

def bfs(src, dest):
    q = Queue()
    q.put((src,0))
    while not q.empty():
        node,cost = q.get()
        print(node, end=" ")
        if node == dest:
            print("\nDestination Found")
            print("Cost -> ",cost)
            return

        for child,weight in graph[node]:
            q.put((child,cost+weight))

    print("Destination not Found")

bfs('A', 'G')