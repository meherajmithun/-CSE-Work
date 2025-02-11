#include <stdio.h>
#include <string.h>

struct Tree {
    char name[50];
    double seedPrice;
    char season[50];
    char category[50];
};

const int numTrees = 50;
struct Tree trees[50] = {
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
    {"Spinach", 25.0, "Late summer to early autumn (July to September)", "Vegetable Plant"}
};

void displayWelcome();
void displayTreeNames(int category);
void displayTreeInfo(const char *treeName);

int main() {
    displayWelcome();

    while (1) {
        printf("Choose an option:\n");
        printf("1. Flower Plants\n");
        printf("2. Fruit Plants\n");
        printf("3. Vegetable Plants\n");
        printf("4. Search for a Tree\n");
        printf("5. Exit\n");

        printf("Enter your choice (1-5): ");
        char choiceStr[10];
        fgets(choiceStr, sizeof(choiceStr), stdin);

        if (strcmp(choiceStr, "5\n") == 0) {
            printf("Thank You for using TreeSorter!\n");
            break;
        }

        int choice = atoi(choiceStr);

        if (choice >= 1 && choice <= 3) {
            displayTreeNames(choice);
        } else if (choice == 4) {
            printf("Enter the tree name to search: ");
            char searchTerm[50];
            fgets(searchTerm, sizeof(searchTerm), stdin);
            searchTerm[strcspn(searchTerm, "\n")] = '\0';
            displayTreeInfo(searchTerm);
        } else {
            printf("Invalid choice. Please enter a valid number.\n");
        }
    }

    return 0;
}

void displayWelcome() {
    printf("==============================\n");
    printf("      Welcome to TreeSorter\n");
    printf("==============================\n");
}

void displayTreeNames(int category) {
    const char *categoryName;
    switch (category) {
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

    printf("==============================\n");
    printf("    %s\n", categoryName);
    printf("==============================\n");

    for (int i = 0; i < numTrees; ++i) {
        if ((category == 1 && strcmp(trees[i].category, "Flower Plant") == 0) ||
            (category == 2 && strcmp(trees[i].category, "Fruit Plant") == 0) ||
            (category == 3 && strcmp(trees[i].category, "Vegetable Plant") == 0)) {
            printf("%s\n", trees[i].name);
        }
    }

    printf("==============================\n");
    printf("Enter the tree name for more details: ");
    char selectedTree[50];
    fgets(selectedTree, sizeof(selectedTree), stdin);
    selectedTree[strcspn(selectedTree, "\n")] = '\0';
    displayTreeInfo(selectedTree);
}

void displayTreeInfo(const char *treeName) {
    printf("==============================\n");
    printf("      Tree Information\n");
    printf("==============================\n");

    int found = 0;
    for (int i = 0; i < numTrees; ++i) {
        if (strcmp(trees[i].name, treeName) == 0) {
            printf("Name: %s.\n", trees[i].name);
            printf("Seed Price: %.2f BDT.\n", trees[i].seedPrice);
            printf("Season: %s.\n", trees[i].season);
            found = 1;
            break;
        }
    }

    if (!found) {
        printf("Tree not found.\n");
    }

    printf("==============================\n");
}
