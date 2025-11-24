#include<bits/stdc++.h>
using namespace std;

struct Tree
{
    string name;
    double seedPrice;
    string season;
    string category;
};

const int numTrees = 50;
Tree trees[numTrees] =
    {
        {"Mango", 50.0, "Monsoon (June to August)", "Fruit Plant"},
        {"Banana", 10.0, "Year-round (Best in rainy season)", "Fruit Plant"},
        {"Papaya", 5.0, "Rainy Season", "Fruit Plant"},
        {"Guava", 10.0, "Monsoon Season", "Fruit Plant"},
        {"Jackfruit", 20.0, "Rainy Season", "Fruit Plant"},
        {"Litchi", 50.0, "Winter (November to February)", "Fruit Plant"},
        {"Pineapple", 20.0, "Rainy Season", "Fruit Plant"},
        {"Lemon", 10.0, "Monsoon Season", "Fruit Plant"},
        {"Pomegranate", 30.0, "Rainy Season", "Fruit Plant"},
        {"Sapodilla", 20.0, "Monsoon Season", "Fruit Plant"},
        {"Olive", 100.0, "Rainy Season", "Fruit Plant"},
        {"Custard Apple", 10.0, "Rainy Season", "Fruit Plant"},
        {"Coconut", 30.0, "Monsoon Season", "Fruit Plant"},
        {"Starfruit", 10.0, "Rainy Season", "Fruit Plant"},
        {"Olive", 100.0, "Rainy Season", "Fruit Plant"},
        {"Cashew", 30.0, "Monsoon Season", "Fruit Plant"},
        {"Krishnachura", 20.0, "Spring (March to April)", "Flower Plant"},
        {"Sheuli", 10.0, "Year-round", "Flower Plant"},
        {"Radhachura", 15.0, "Spring (February to April)", "Flower Plant"},
        {"Kanchan", 20.0, "Spring (March to April)", "Flower Plant"},
        {"Jarul", 10.0, "Spring (March to April)", "Flower Plant"},
        {"Kadam", 10.0, "Spring (March to April)", "Flower Plant"},
        {"Shimul", 15.0, "Spring (March to April)", "Flower Plant"},
        {"Palash", 10.0, "Spring (March to April)", "Flower Plant"},
        {"Guloncho", 10.0, "Year-round", "Flower Plant"},
        {"Bokul", 10.0, "Year-round", "Flower Plant"},
        {"Ratnagiri", 10.0, "Spring (March to April)", "Flower Plant"},
        {"Sada-Chapa", 20.0, "Spring (March to April)", "Flower Plant"},
        {"Chandni", 20.0, "Spring (March to April)", "Flower Plant"},
        {"Akondo", 10.0, "Year-round", "Flower Plant"},
        {"Champa", 10.0, "Year-round", "Flower Plant"},
        {"Kodom", 10.0, "Spring (March to April)", "Flower Plant"},
        {"Madhabilata", 20.0, "Spring (March to April)", "Flower Plant"},
        {"Brinjal", 20.0, "Late winter to early spring (December to February)", "Vegetable Plant"},
        {"Tomato", 30.0, "Late winter to early spring (December to February)", "Vegetable Plant"},
        {"Cauliflower", 70.0, "Late summer to early autumn (July to September)", "Vegetable Plant"},
        {"Cabbage", 70.0, "Late summer to early autumn (July to September)", "Vegetable Plant"},
        {"Carrot", 40.0, "Late summer to early autumn (July to September)", "Vegetable Plant"},
        {"Radish", 30.0, "Late summer to early autumn (July to September)", "Vegetable Plant"},
        {"Spinach", 25.0, "Late winter to early spring (December to February)", "Vegetable Plant"},
        {"Okra", 30.0, "Late spring to early summer (March to May)", "Vegetable Plant"},
        {"Bitter Gourd", 25.0, "Late spring to early summer (March to May)", "Vegetable Plant"},
        {"Snake Gourd", 25.0, "Late spring to early summer (March to May)", "Vegetable Plant"},
        {"Bean", 30.0, "Late spring to early summer (March to May)", "Vegetable Plant"},
        {"Pumpkin", 30.0, "Late spring to early summer (March to May)", "Vegetable Plant"},
        {"Cucumber", 30.0, "Late spring to early summer (March to May)", "Vegetable Plant"},
        {"Bell Pepper", 40.0, "Late winter to early spring (December to February)", "Vegetable Plant"},
        {"Chili Pepper", 25.0, "Late winter to early spring (December to February)", "Vegetable Plant"},
        {"Lenol", 40.0, "Late autumn to early winter (October to November)", "Vegetable Plant"},
        {"Spinach", 25.0, "Late summer to early autumn (July to September)", "Vegetable Plant"}};
void displayWelcome();
void displayTreeNames(int category);
void displayTreeInfo(const string &treeName);

int main()
{
    displayWelcome();

    while (true)
    {
        cout << "Choose an option:\n";
        cout << "1. Flower Plants\n";
        cout << "2. Fruit Plants\n";
        cout << "3. Vegetable Plants\n";
        cout << "4. Search for a Tree\n";
        cout << "5. Add plants\n";
        cout << "5. Exit\n";

        cout << "Enter your choice (1-5): ";
        string choiceStr;
        getline(cin, choiceStr);

        if (choiceStr == "5")
        {
            cout << "Thank You for using TreeSorter!\n";
            break;
        }

        int choice;
        try
        {
            choice = stoi(choiceStr);
        }
        catch (const exception &e)
        {
            cout << "Invalid input. Please enter a valid number.\n";
            continue;
        }

        if (choice >= 1 && choice <= 3)
        {
            displayTreeNames(choice);
        }
        else if (choice == 4)
        {
            cout << "Enter the tree name to search: ";
            string searchTerm;
            getline(cin >> ws, searchTerm);
            displayTreeInfo(searchTerm);
        }
        else
        {
            cout << "Invalid choice. Please enter a valid number.\n";
        }
    }

    return 0;
}

void displayWelcome()
{
    cout << "==============================\n";
    cout << "      Welcome to TreeSorter\n";
    cout << "==============================\n";
}

void displayTreeNames(int category)
{
    string categoryName;
    switch (category)
    {
    case 1:
        categoryName = "Flower Plants";
        break;
    case 2:
        categoryName = "Fruit Plants";
        break;
    case 3:
        categoryName = "Vegetable Plants";
        break;
    }

    cout << "==============================\n";
    cout << "    " << categoryName << "\n";
    cout << "==============================\n";

    for (int i = 0; i < numTrees; ++i)
    {
        if ((category == 1 && trees[i].category == "Flower Plant") ||
            (category == 2 && trees[i].category == "Fruit Plant") ||
            (category == 3 && trees[i].category == "Vegetable Plant"))
        {
            cout << trees[i].name << endl;
        }
    }

    cout << "==============================\n";
    cout << "Enter the tree name for more details: ";
    string selectedTree;
    getline(cin >> ws, selectedTree);
    displayTreeInfo(selectedTree);
}

void displayTreeInfo(const string &treeName)
{
    cout << "==============================\n";
    cout << "      Tree Information\n";
    cout << "==============================\n";

    bool found = false;
    for (int i = 0; i < numTrees; ++i)
    {
        if (trees[i].name == treeName)
        {
            cout << "Name: " << trees[i].name << "." << endl
                 << "Seed Price: " << trees[i].seedPrice << " BDT." << endl
                 << "Season: " << trees[i].season << "." << endl;
            found = true;
            break;
        }
    }

    if (!found)
    {
        cout << "Tree not found.\n";
    }

    cout << "==============================\n";
}
