def minimax(depth, idx, flag, arr):
    n = len(arr)
    # if(idx>=n): return arr[idx]
    if(depth==3): return arr[idx]
    if flag:
        mnscore = 1000
        for i in range(2):
            score = minimax(depth+1, idx*2+i, 0, arr)
            mnscore = min(mnscore, score)

        return mnscore

    else:
        mxscore = -1000
        for i in range(2):
            score = minimax(depth+1, idx*2+i, 1, arr)
            mxscore = max(mxscore, score)

        return mxscore



arr = [-1, 8, -3, -1, 2, 1, -3, 4]

print("Final Score -> ",minimax(0, 0, 0, arr))