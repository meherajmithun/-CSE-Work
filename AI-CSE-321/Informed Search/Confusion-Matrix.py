Predict = [1,1,1,0,1,0,0,1,0, 0]
Actual =  [1,0,1,0,0,1,0,1,0, 1]

matrix = {}
TP=0; TN=0; FP=0; FN=0
for i in range(10):
  if Predict[i] == Actual[i] and Predict[i]==1:
    TP+=1
  elif Predict[i] == Actual[i] and Predict[i]==0:
    TN+=1
  elif Predict[i]!=Actual[i] and Predict[i]==1 and Actual[i]==0:
    FP+=1
  else:
    FN+=1

matrix["TP"] = TP; matrix["TN"]=TN; matrix["FP"]=FP; matrix["FN"]=FN

precision = TP / (TP + FP)
recall = TP / (TP + FN)
f1 = 2 * precision * recall / (precision + recall)
accuracy = (TP + TN) / (TP + TN + FP + FN)

specificity = TN / (TN + FP)

print("Confusion Matrix:", matrix)

print("Accuracy :", accuracy)
print("Precision:", precision)
print("Recall   :", recall)
print("F1 Score :", f1)
print("Specificity:", specificity)