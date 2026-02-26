# Cheriie User Guide #

Cheriie is a personal task management chatbot designed to help you manage your daily tasks
such as your todos, deadlines, and events via a simple application installed in the Command Line Interface (˶ᵔ ᵕ ᵔ˶)!

---

## Table of Contents
- [Quick Start](#quick-start)
- [Features](#features)
  - [Displaying all available commands: `help`](#displaying-all-available-commands-help)
  - [Adding a Todo task: `todo`](#adding-a-todo-task-todo)
  - [Adding a Deadline task: `deadline`](#adding-a-deadline-task-deadline)
  - [Adding an Event task: `event`](#adding-an-event-task-event)
  - [Listing all tasks: `list`](#listing-all-tasks-list)
  - [Marking a task as done: `mark`](#marking-a-task-as-done-mark)
  - [Unmarking a task as not done: `unmark`](#unmarking-a-task-as-not-done-unmark)
  - [Finding a task: `find`](#finding-a-task-find)
  - [Deleting a task: `delete`](#deleting-a-task-delete)
  - [Exiting the application: `exit`](#exiting-the-application-exit)
  - [Saving your data](#saving-your-data)
- [Editing the Data File](#editing-the-data-file)
- [Command Summary](#command-summary)

---

## Quick Start
Ensure that you have Java 17 installed in your own personal computer. You may download Java 17 for your
respective system [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).
> You may want to use SDKMAN! to manage your Java installation in the event you have multiple versions [here](https://sdkman.io/).

1. Download the latest Cheriie JAR file from the **GitHub Releases Page** [here](https://github.com/benthejarofmint/ip/releases/).
2. Copy the `cheriie.jar` file into the folder that you want to use as the home folder for Cheriie.
3. Locate your JAR file and copy the filepath. Example: `/Users/{yourName}/Desktop/downloads/iP.jar`
4. Open the command line terminal 
   - *Windows*: Open Command Prompt
   - *macOS/Linux*: Open Terminal
5. Use the `cd` command to navigate to the folder where your JAR file is saved. For example:\
`cd /Users/{yourName}/Desktop/downloads/iP.jar`
6. Run the application in the command line using the following command:\
`java -jar cheriie.jar`
7. After a few seconds, the application will launch in your terminal. You should see the user interface output
for Cheriie as shown below:\
<img width="640" height="222" alt="launch" src="https://github.com/user-attachments/assets/bc8fb90f-b83d-4346-867d-69611053f74e" />\
8. Type the commands into the terminal and press **Enter** to execute the command.

---

## Features

> [!NOTE]
> ‼️ **Notes about the command format**:
> - Words in **UPPER_CASE** are placeholders to be supplied by you, the user.
> - For example: in `todo DESCRIPTION`, **DESCRIPTION** is a placeholder that should be replaced with the actual task
> - **Dates and Times** must follow specific formats, **else they will be treated as strings**:
>   - Date Formats:
>     - 5/2/2026 (d/m/yyyy)
>     - 01/03/2026 (dd/mm/yyyy)
>     - 1-3-2026 (d-m-yyyy)
>     - 2026-03-05 (yyyy-mm-dd)
>   - Time Formats:
>     - 930 (hmm)
>     - 0930 (hhmm)
>     - 9:30 (h:mm)
>     - 09:30 (hh:mm)
>     - 9:30AM (h:mma)
>     - 9AM / 9am (ha)

### Displaying all Available Commands: `help`
Displays all commands that the bot can understand.

**Format**: `help`

### Adding a Todo task: `todo`
Adds a general task without a deadline.

**Format**: `todo DESCRIPTION`

**Examples**:
- `todo play with cat` Adds a todo task named *“play with cat”* to the list.

### Adding a Deadline Task: `deadline`
Adds a task with a specific deadline. Great for time-sensitive tasks!

**Format**: `deadline DESCRIPTION /by DATE_TIME`

**Examples**:
- `deadline submit cs2113 assignment /by 06/03/2026 23:59` 
Adds a task named *“submit cs2113 assignment”* with a deadline of 6th March 2026 at 11:59PM to the list.
- `deadline feed cat /by 2026-03-01 12pm`
  Adds a task named *“feed cat”* with a deadline of 1st March 2026 at 12:00PM to the list.

>[!TIP]
> Ensure that the date and time follow the accepted formats, or they will be treated as strings. 
> Refer to the notes about the command format above for valid date and time formats.

### Adding an Event Task: `event`
Adds a task with both a start and end time. Ideal for scheduling events like group meetings!

**Format**: `event DESCRIPTION /from START_DATE_TIME /to END_DATE_TIME`

**Examples**:
- `event cs2113 project call /from 06/03/2026 15:30 /to 06/03/2026 16:30`
  Adds a task named *“cs2113 project call”* event happening from 6th March 2026 at 3:30PM to 6th March 2026 4:30PM to the list.

>[!TIP]
> Ensure that the date and time follow the accepted formats, or they will be treated as strings.
> Refer to the notes about the command format above for valid date and time formats.

### Listing all Tasks: `list`
Displays a list of all tasks and their status currently saved.

**Format**: `list`

**Examples**:
- `list`Lists all tasks in your task list, showing their status (done or pending).

### Marking a Task as Done: `mark`
Marks a selected task as completed.

**Format**: `mark TASK_NUMBER`

**Examples**:
- `mark 1` Marks the first task in the list as done.
  
>[!TIP]
> You can find the task number with the `list` operation!

### Unmarking a Task as Not Done: `unmark`
Unmarks a selected task as not completed.

**Format**: `unmark TASK_NUMBER`

**Examples**:
- `unmark 2` Unmarks the second task in the list as not done.

>[!TIP]
> You can find the task number with the `list` operation!

### Finding a Task: `find`
Searches for tasks that contain a specific keyword provided by you, the user.
The number shown on the result corresponds to the original list.

**Format**: `find KEYWORD`

**Examples**:
- `find cat` Searches for all tasks that contain the word “cat” and displays the results.

>[!NOTE]
> If there are no tasks matching your keywords in the current list, Cheriie will return an empty list.

### Deleting a Task: `delete`
Removes a task from the task list.

**Format**: `delete TASK_NUMBER`

**Examples**:
- `delete 1` deletes the 2nd task in the list.
- `find cs2113` followed by `delete 5` deletes the 5th task from the **original list**.

>[!TIP]
> You can find the task number with the `list` operation!

### Exiting the Application: `exit`
Exits the Cheriie application.

**Format**: `exit`

**Examples**:
- `exit` Closes the application.

### Saving Your Data
Cheriie automatically saves your tasks to the data/cheriie.txt file after any changes. There is **no need** for manual saving.

--- 
## Editing the Data File
Cheriie automatically saves your task data in a text file located at `./data/aether.txt`, relative to the directory where you run the program. 
Advanced users can update their tasks by directly editing this file.

> [!WARNING]
> Be cautious when editing the file directly, as improper formatting may cause errors or data loss when the application is next launched.

---

## Command Summary

| Action       | Format                                                      | Example                                           |
|--------------|-------------------------------------------------------------|---------------------------------------------------|
| **Help**     | `help`                                                      | —                                                 |
| **Todo**     | `todo DESCRIPTION`                                          | `todo drink water`                                |
| **Deadline** | `deadline DESCRIPTION /by DATE_TIME`                        | `deadline submit assignment /by 15/03/2026 23:59` |
| **Event**    | `event DESCRIPTION /from START_DATE_TIME /to END_DATE_TIME` | `event team call /from 2pm /to 4pm`               |
| **List**     | `list`                                                      | —                                                 |
| **Mark**     | `mark TASK_NUMBER`                                          | `mark 1`                                          |
| **Unmark**   | `unmark TASK_NUMBER`                                        | `unmark 1`                                        |
| **Find**     | `find KEYWORD`                                              | `find meeting`                                    |
| **Delete**   | `delete TASK_NUMBER`                                        | `delete 2`                                        |
| **Exit**     | `bye`                                                       | —                                                 |

---
