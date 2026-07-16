library={}

def add_book(name,cnt):
    if name in library:
        library[name] += cnt
    else:
        library[name] = cnt

def searched(name):
    if name in library:
            return 1
    return 0

def borrowed(name):
    if name in library:
        library[name] -= 1;

def returned(name):
    library[name] += 1


add_book("dijkstra", 2)
add_book("DP", 2)
add_book("NT", 2)
add_book("Counting", 2)
add_book("Graph", 2)

print(library)

while 1:
    print("Enter choices : ")
    print("1. add book ")
    print("2. searched book")
    print("3. borrowed book")
    print("4. returned book")
    print("5. Exit")

    response = int(input())
    if response==1:
        print("Enter book name : ",end=" ")
        name = input()
        print("Enter book quantity : ",end=" ")
        cnt = int(input())
        add_book(name,cnt)

    elif response==2:
        print("Enter book name : ",end=" ")
        name = input()
        val = searched(name);
        if val==0:
            print("Book not found");
        else:
            print("Book found in the library")
            
    elif response==3:
        print("Enter book name : ",end=" ")
        name = input()
        val = searched(name);
        if val==0:
            print("Book not found");
        else:
            borrowed(name); print("Book borrowed successfully")

    elif response==4:
        print("Enter book name : ",end=" ")
        name = input()
        returned(name)
        print("Book returned Successfully")
    else:
        break