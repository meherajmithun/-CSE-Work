print("Input the total number of student : ",end=" ")
n = int(input())
# print(n)
mylist={}
for i in range(n):
    print("Enter student ",(i+1)," id : ",end=" ")
    id = int(input())
    print("Enter total subject : ",end=" ")
    sub = int(input())
    total = 0
    for x in range(sub):
        print("Mark in subject ",(x+1), ": ", end=" ")
        mark = int(input())
        total += mark

        
    avg = total/sub
    grade = 2*avg
    mylist[id] = [total, avg, grade]

print(mylist)
for x in mylist:
    print("ID ",x, " total mark : ",mylist[x][0])
    