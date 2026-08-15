def check_palindrome(n):
    reverse=0
    tmp=n # for comparison in next
    while n>0:
        reminder = n%10
        reverse = (reverse*10) + reminder
        n //= 10
    # print(reverse)
    return reverse==tmp

def check_prime(n):
    for i in range(2,n):
        if(n%i==0):
            return 0
    return 1

#main Function
print("Enter array size : ");
n = int(input())
arr=[]
for i in range(n):
    x = int(input())
    arr.append(x)

sum = 0
for i in range(n):
    sum += arr[i]

# print(sum)
if(check_palindrome(sum)):
    print("Array sum is palindrome")
else:
    print("Array sum is not palindrome")

if(check_prime(sum)):
    print("Array sum is prime")
else:
    print("Array sum is not prime")

if(sum%3==0):
    print("Array sum is divisible by 3")
else:
    print("Array sum is not divisble by 3")

digit_sum=0
while sum>0:
    digit_sum += (sum%10)
    sum //= 10
if(digit_sum%3==0):
    print("Digit sum is divisible by 3")
else:
    print("Digit sum is not divisble by 3")