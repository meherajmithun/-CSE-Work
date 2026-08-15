def check_prime(n):
    for i in range(2,n):
        if(n%i==0):
            return 0
    return 1

print("Enter array size ", end=" ")
n = int(input())
arr=[]
print("Enter array element ", end=" ")
for i in range(n):
    x = int(input())
    arr.append(x)

#Check prime
prime=0
for i in range(n):
    if prime==0:
        if(check_prime(arr[i])):
            prime=1; break


#Check 2nd condition divisible by 3 or 5.
cnt = 0;
for i in range(n):
    if(arr[i]%3==0):
        cnt+=1
    elif(arr[i]%5==0):
        cnt+=1
    elif(arr[i]%15==0):
        cnt+=1

#Find minimum and Maximum 
mx=float('-inf'); mn = float('inf')

for i in range(n):
    mx = max(mx, arr[i])
    mn = min(mn,arr[i])

#Final Condition check
dif = mx-mn
sz = int(n/2)
# print(prime, " -> ", cnt, " -> ", dif, " -> ",sz)
if prime==1 and cnt>=sz and dif%2==1:
    print("Yes")
else:
    print("No")