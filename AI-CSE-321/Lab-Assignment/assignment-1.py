print("Input the total number of students: ", end="")
n = int(input())

students = {}

for i in range(n):
    print("\nEnter student", i + 1)

    print("Enter student ID: ", end="")
    sid = int(input())

    print("Enter total subjects: ", end="")
    sub = int(input())

    total = 0

    for x in range(sub):
        print("Enter mark in subject", x + 1, ": ", end="")
        mark = int(input())
        total += mark

    avg = total / sub
    
    if avg >= 80:
        grade = 'A+'
    elif avg >= 70:
        grade = 'A'
    elif avg >= 60:
        grade = 'B'
    elif avg >= 50:
        grade = 'C'
    else:
        grade = 'F'

    students[sid] = [total, avg, grade]

# Find highest and lowest
highest_id = None
lowest_id = None
highest_total = -1
lowest_total = 100000

print("\nStudent Report")

for sid in students:
    total, avg, grade = students[sid]

    print("ID:", sid)
    print("Total Marks:", total)
    print("Average:", round(avg, 2))
    print("Grade:", grade)

    if grade == 'F':
        print("Status: Failed")
    else:
        print("Status: Passed")

    print()

    if total > highest_total:
        highest_total = total
        highest_id = sid

    if total < lowest_total:
        lowest_total = total
        lowest_id = sid

print("Highest Scorer")
print("Student ID:", highest_id)
print("Total Marks:", highest_total)

print()

print("Lowest Scorer")
print("Student ID:", lowest_id)
print("Total Marks:", lowest_total)