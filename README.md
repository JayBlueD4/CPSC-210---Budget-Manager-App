# My Personal Project - David Na

## Project Proposal

My project will be a **budget manager application** that will not only allow the user to create a budget, but also carry out useful calculations, such as: 
- Income tax (which would likely be a method in the Income class).
- How long they would need to wait until they could buy a particular item or have a certain amount in savings (e.g. house, car, phone, computer, etc.)
- Spreading out a one-time expense over a year (12 months)
- Providing an overview of the distribution of expenses - i.e. how much percent of total monthly expenses the user is spending on each particular category, from the largest to the smallest percentage

This application can be for practically anyone who wants to create a budget. I would say that when developing this idea, I had the everyday person more in mind, as I found that there is no “budget maker” easily accessible that can perform useful operations like those mentioned above. It is for this reason that this project is of interest to me. I have been increasingly getting into making my own budgets on *Excel*/*Google Sheets*, and I through this I kept thinking that it would be so helpful to have an application that really does all the hard work (i.e. designing the budget spreadsheet, making calculations, projecting income, etc.) for the user. And thus, I thought, "Why not create such an application myself?"


## User Stories

- As a user, I want to be able to create and add expense items to the list of expense items that will be found in my budget.
- As a user, I want to be able to create and add income items to the list of income items that will be found in my budget.
- As a user, I want to be able to view the list of expenses in my budget.
- As a user, I want to be able to view the list of income in my budget.
- As a user, I want to be able to make a projection of how long I have to wait to purchase something based on my current monthly spending.
- As a user, I want to be able to calculate the provincial tax on all my income.
- As a user, I want to be able to calculate the federal tax on all my income.
- As a user, I want to be able to have the option to save the entire budget to file.
- As a user, I want to be able to have the option to reload a previously-saved budget from file and pick up from where I left off.

## Instructions for End User

- You can view the panel that displays the money items that have been added to their respective income & expense lists in the budget by pressing start and then creating/loading a budget.
- You can add an income or expense item to the budget by navigating to the budget manager panel (see above), clicking the "Add" tab in the menu bar at the top, clicking either the "Income" or "Expense" buttons, and then filling in the required info.
- You can calculate the overall tax on all income items added to the budget by clicking the "Tools" tab in the menu bar, and clicking the "Overall Tax" button.
- You can locate my visual component by clicking the "Tools" tab and clicking on the "Distributions" button. There are two separate tabs you can switch between: one for income and another for expenses.
- You can save the state of my application by clicking the "File" tab in the menu bar, clicking on the "Save" button, and inputting the required information.
- You can reload the state of my application by clicking the "File" tab in the menu bar, clicking on the "Load" button, and inputting the required information. There is also the option of using the "Menu" tab in the menu bar to navigate back to the main menu. You would then press the "Start" button and then can choose to load your newly saved budget rather than create one.

## Phase 4: Task 2
Wed Nov 26 14:08:54 PST 2025
New budget created

Wed Nov 26 14:08:54 PST 2025
Loaded budget (David Na, November)

Wed Nov 26 14:09:01 PST 2025
Description of (Hi) set

Wed Nov 26 14:09:01 PST 2025
Category of (Hi) set

Wed Nov 26 14:09:01 PST 2025
Frequency of (Hi) set

Wed Nov 26 14:09:01 PST 2025
Value of (Hi) set

Wed Nov 26 14:09:08 PST 2025
Expense removed from budget

Wed Nov 26 14:09:17 PST 2025
Projection made

Wed Nov 26 14:09:38 PST 2025
Calculated individual provincial tax for (Hi)

Wed Nov 26 14:09:38 PST 2025
Calculated individual federal tax for (Hi)

Wed Nov 26 14:09:38 PST 2025
Calculated individual provincial tax for (Hi)

Wed Nov 26 14:09:38 PST 2025
Calculated individual federal tax for (Hi)

Wed Nov 26 14:09:47 PST 2025
Expense added to budget

Wed Nov 26 14:09:56 PST 2025
Saved current budget

## Phase 4: Task 3
One refactoring change I would make in the future is to create a superclass for IncomeList and ExpenseList, similar to what I did for the Income and Expense classes using the MoneyItem class. This is because the IncomeList and ExpenseList classes share very similar functionality - i.e. they have several methods that essentially work the same way. Thus, by separating this similar functionality into two separate classes, I have higher coupling than is ideal. By creating a new class and then having IncomeList and ExpenseList extend that class, I would be able to reduce coupling in my program, easing the process of both testing and implementing new features. I originally avoided doing this, as I knew some methods in IncomeList and ExpenseList would need to call on methods uniquely belonging to the Income and Expense classes respectively, but having learnt that one can cast using customly-created objects, I would now be able to carry out this design improvement.

In regard to my other classes, there is not really any big changes necessary. I could argue that in IncomeList, the tax-related methods could be moved to a separate, new Tax/TaxCalculator class, to increase the level of cohesion in my code. While calculating tax is indeed related to income, is it technically a separate responsibility, one that it instead bestowed upon both the IncomeList and Income classes. So, taking this into account, I realize that moving this responsbility into a new class could improve my program design both in regard to cohesion and coupling.

Finally, if I were to add more features regarding projections, I may need to refactor the relevant methods from the Budget class into a new ProjectionMaker class.