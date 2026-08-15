from sklearn import preprocessing
from sklearn.naive_bayes import GaussianNB
age=['<=30','<=30','31-40','>40','>40','>40','31-40','<=30','<=30','>40','<=30','31-40','31-40','>40']
income=['H','H','H','M','L','L','L','M','L','M','M','M','H','M']
student=['N','N','N','N','Y','Y','Y','N','Y','Y','Y','N','Y','N']
credit=['F','E','F','F','F','E','E','F','F','F','E','E','F','E']
buy=['N','N','Y','Y','Y','N','Y','N','Y','Y','Y','Y','Y','N']

#Precprocess

age_encode = preprocessing.LabelEncoder().fit_transform(age)
income_encode = preprocessing.LabelEncoder().fit_transform(income)
student_encode = preprocessing.LabelEncoder().fit_transform(student)
credit_encode = preprocessing.LabelEncoder().fit_transform(credit)
buy_encode = preprocessing.LabelEncoder().fit_transform(buy)

print("Age Encode : ", age_encode)
print("Income Encode : ", income_encode)
print("Student Encode : ", student_encode)
print("Credit Encode : ", credit_encode)
print("Buy Encode : ", buy_encode)

#Lambda Function
x = lambda age_encode, income_encode,student_encode, credit_encode:[
    list(x) for x in zip(age_encode, income_encode, student_encode, credit_encode)
] 

feature = x(age_encode, income_encode, student_encode, credit_encode)

print("Features : ", feature)
print("Buy Enocde : ", buy_encode)

agent_model = GaussianNB().fit(feature, buy_encode)
prediction = agent_model.predict([[1,1,1,1]])

print("Prediction Output : ", "YESSSSS" if prediction[0]==1 else "NOOOO")
