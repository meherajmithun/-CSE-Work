Bank={}

def create_account():
    print("Enter account holder name : ",end=" ");
    name = input()
    print("Enter contact number : ",end=" ");
    num = input()
    print("Enter PIN : ",end=" ");
    pin = input()
    print("Enter opening balance : ",end=" ");
    balance = int(input())

    Bank[name]=[num, pin,balance]
    
def deposit_money():
    print("Enter amount : ",end=" ");
    money = int(input())
    print("Enter account holder name : ",end=" ");
    name = input()
    print("Enter PIN : ",end=" ");
    pin = (input())
    if name in Bank:
        if Bank[name][1]==pin:
            Bank[name][2] += money
            print("Money deposit successfully")
        else:
            print("PIN not matched")
    else:
        print("Account not found")

create_account()
print(Bank)
deposit_money()
print(Bank)