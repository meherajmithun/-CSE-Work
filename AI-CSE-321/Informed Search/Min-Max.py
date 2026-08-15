def min_max(n, arr, flag, idx, depth):
    if depth==0:
        return arr[idx]

    if flag:
        mx_score = float('-inf')
        for i in range(2):
            score = min_max(n, arr, 0, idx*2+i, depth-1)
            mx_score = max(mx_score, score)
        return mx_score

    else:
        mn_score = float('inf')
        for i in range(2):
            score = min_max(n, arr, 1, idx*2+i , depth-1)
            mn_score = min(mn_score, score)
        return mn_score


import math


# print("Enter array3  size : ",end=" ")
n = int(input())

depth = int(math.log2(n))
# print(n)
arr=[]
# print("Enter array Element : ",end=" ")
for i in range(0,n):
  x = int(input())
  arr.append(x)

# print("array :"," -> ".join(map(str,arr)))
# print(arr)

score = min_max(n, arr, 0, 0, depth)
print(score)
