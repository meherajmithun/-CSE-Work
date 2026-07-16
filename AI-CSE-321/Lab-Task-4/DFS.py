from queue import Queue
graph = {'A': [('B',5), ('C', 7)],
         'B': [('D',7),('E', 3)],
         'C': [('F',4)],
         'D': [],
         'E': [],
         'F': []
         }

def dfs(src, dest):
    stack = Queue()
    stack.put((src, 0))
    while stack:
        node, cost = stack.get()
        print(node, end=" ")
        if node == dest:
            print("\nDestination Found\nCost -> ",cost)
            return
        
        for child,weight in reversed(graph[node]):
            stack.put((child,cost+weight))

dfs('A', 'D')
            
