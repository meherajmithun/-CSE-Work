l = int(input())
r = int(input())
prime=[]
for i in range(l,r+1):
    flag = 1
    for j in range(2,i):
        if i%j==0:
            flag=0
            break
    if flag and i>1:
        prime.append(i)

print(prime)