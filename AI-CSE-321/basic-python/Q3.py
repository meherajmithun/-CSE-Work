n = int(input())
sum = 0
while n>0:
    x = n%10
    sum = sum + x
    n = n// 10

# print(sum)
prime=1
for i in range(2,sum):
    if(sum%i==0):
        prime=0; break;

if prime:
    print("digit sum is Prime")
else:
    print("Digit sum is not prime")