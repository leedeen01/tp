# ClientNest User Guide

ClientNest is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `.jar` file from [here](https://github.com/AY2425S2-CS2103-F10-2/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar ClientNest.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Start typing commands in the input box and press Enter to execute them.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands such as `help`, `exit` and `clear` will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* Extraneous parameters for listing commands such as `list` and `listpolicy` will result in denied command.<br>
  e.g. if the command specifies `list 123`, the result display will notify user of the extraneous parameters and the command will be ignored.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a new contact to your ClientNest.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS b/BIRTHDAY pr/PREMIUM_NAME PREMIUM_AMOUNT [t/TAG]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 b/2002-11-24 pr/Gold 99999 t/friends t/owesMoney`
* `add n/Alice Pauline p/94351253 e/alice@example.com a/123, Jurong West Ave 6, #08-111 b/1990-01-01 pr/Silver 50000`

### Listing all persons : `list`

Displays all contacts stored in ClientNest..

Format: `list`

### Editing a person : `edit`

Edits details of an existing contact.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [b/BIRTHDAY] [pr/PREMIUM_NAME PREMIUM_AMOUNT] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person's tags by typing `t/` without
  specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Adding a premium to a person: `addpr`

Adds a premium to a person identified by the index number in the displayed person list. A premium represents an insurance policy or product assigned to a person in the address book.

Format: `addpr INDEX pr/PREMIUM_NAME PREMIUM_AMOUNT`

* Adds the specified premium to the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* The same premium cannot be added twice to the same person.

Examples:
* `list` followed by `addpr 1 pr/LifeShield $300` adds a premium named "LifeShield" with amount "$300" to the 1st person in the address book.
* `find John` followed by `addpr 1 pr/HealthPlus $500` adds a premium named "HealthPlus" with amount "$500" to the 1st person in the results of the `find` command.

### Editing a premium for a person: `editpr`

Edits the premium details of a person identified by the index number in the displayed person list.

Format: `editpr INDEX pr/PREMIUM_NAME PREMIUM_AMOUNT`

* Edits the specified premium for the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* The premium name specified must already exist for that person.
* Only the premium amount will be updated.

Examples:
* `list` followed by `editpr 1 pr/LifeShield $350` changes the amount of the "LifeShield" premium to "$350" for the 1st person in the address book.
* `find John` followed by `editpr 1 pr/HealthPlus $600` changes the amount of the "HealthPlus" premium to "$600" for the 1st person in the results of the `find` command.

### Deleting a premium from a person: `deletepr`

Deletes a specific premium from a person identified by the index number in the displayed person list.

Format: `deletepr INDEX pr/PREMIUM_NAME`

* Deletes the specified premium from the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​
* Only the premium name is required to identify which premium to delete.

Examples:
* `list` followed by `deletepr 1 pr/LifeShield` deletes the "LifeShield" premium from the 1st person in the address book.
* `find John` followed by `deletepr 1 pr/HealthPlus` deletes the "HealthPlus" premium from the 1st person in the results of the `find` command.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find Alice Hoe` returns `John Doe`, `Alice Pauline`<br>
  ![result for 'find alice doe'](images/findAliceDoeResult.png)

### Deleting a person : `delete`

Deletes the specified person from the client list.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Adding a policy: `addpolicy`

Adds a new policy to your ClientNest.

Format: `addpolicy n/POLICY_NAME pn/POLICY_NUMBER pc/PROVIDER_COMPANY pl/POLICY_LINK​`

* No 2 policy share the same policy number.
* The policy name is used in the `findpolicy` command.

</box>

Examples:
* `addpolicy pn/POL123 n/LifeShield pc/ShieldCorp pl/https://www.shieldcorp.com/policy123 `

### Listing all policies : `listpolicy`

Displays all policies details stored in ClientNest..

Format: `listpolicy`

### Editing a policy : `editpolicy`

Edits details of an existing policy details.

Format: `editpolicy INDEX [n/POLICY_NAME] [pn/POLICY_NUMBER] [pc/PROVIDER_COMPANY] [pl/POLICY_LINK]​`

* Edits the policy at the specified `INDEX`. The index refers to the index number shown in the displayed policy list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the fields must be provided.
* Existing values will be updated to the input values.
* Policy Link can optionally start with 'http://', 'https://', or 'ftp://' and may include 'www.'. The domain name should consist of alphanumeric characters, underscores, or hyphens, followed by one or more top-level domains (e.g., '.com', '.org'). You can also include a path (starting with '/')\n"

Examples:
*  `editpolicy 1 n/Life Shield pl/https://www.lifeshield.com` Edits the policy name and policy link of the 1st policy to be `Life Shield` and `https://www.lifeshield.com` respectively.

### Locating policy by name: `findpolicy`

Finds policies whose names contain any of the given keywords.

Format: `findpolicy KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `life` will match `Life`
* The order of the keywords does not matter. e.g. `Life Health` will match `Health Life`
* Only the name is searched.
* Full words and partial will be matched e.g. `Li` will match `Lifeshield`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Life Health` will return `LifeShield`, `HealthPlus`

Examples:
* `find John` returns `john` and `John Doe`
* `find Alice Hoe` returns `John Doe`, `Alice Pauline`<br>
  ![result for 'find alice doe'](images/findAliceDoeResult.png)

### Deleting a policy : `deletepolicy`

Deletes the specified policy from the policy list.

Format: `deletepolicy INDEX`

* Deletes the policy at the specified `INDEX`.
* The index refers to the index number shown in the displayed policy list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Clearing all person : `clear`

Clears all person from your ClientNest.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

ClientNest data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ClientNest data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, ClientNest will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the ClientNest to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>


### Editing User Profile : `profile`

Edits details of the current user's profile.

Format: `profile [n/NAME] [p/PHONE] [e/EMAIL]`

* Edits the current user's profile.
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* Edited values will be updated on the Hero Section of the main window.

Examples:
*  `edit n/John Doe p/91234567 e/johndoe@example.com` Edits the name, phone number and email address of the user to be `John Doe`, `91234567` and `johndoe@example.com` respectively.

### Saving the data

ClientNest user profile data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ClientNest user profile data are saved automatically as a JSON file `[JAR file location]/data/userprofile.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, ClientNest will discard all data and start with the default values for a user profile. Hence, it is recommended to take a backup of the file before editing it.<br><br>
The default values of a user profile is:<br>
Name: `Guest`<br>
Phone: `00000000`<br>
Email: `guest@example.com`<br>
<br>
Furthermore, certain edits can cause the ClientNest to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>
ClientNest includes a built-in **TaskManager** to help you stay on top of your personal tasks, deadlines, and events — all within the same application.


### Upcoming Birthdays Panel

The **Upcoming Birthdays** panel displays a list of clients whose birthdays fall within the next 30 days.

This panel appears to the right of the client list and updates automatically whenever a birthday is added or edited. You can scroll through the list to see upcoming birthdays and easily identify clients you may want to reach out to.

No commands are required — just ensure each contact has a valid birthday entered, and ClientNest will handle the rest.

### Viewing and Managing Tasks: `TaskManager`
<p align="center"> <img src="images/TaskManager.png" alt="Task Manager GUI" width="500"/> </p>

You can access the TaskManager panel from the top menu bar. A new window will open, allowing you to type commands and manage tasks independently of your main contact list.

In TaskManager, you can:

* Add To-Do, Deadline, and Event tasks
* Mark and unmark tasks as done
* Delete tasks
* Search for tasks by keyword

All commands are typed into the input box, similar to the main ClientNest window.

> 💡 **Tip:** Type `help` inside the TaskManager window to view all available task commands.

For more in-depth information about TaskManager commands and their formats, refer to the [ _Task Manager User Guide_ ](TaskManagerUserGuide.md)

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ClientNest home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary for ClientNest

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS b/BIRTHDAY pr/PREMIUM_NAME PREMIUM_AMOUNT [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 b/1995-05-10 pr/Platinum 150000 t/friend t/colleague`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit**   | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [b/BIRTHDAY] [pr/PREMIUM_NAME PREMIUM_AMOUNT] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find**   | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**Add Premium** | `addpr INDEX pr/PREMIUM_NAME PREMIUM_AMOUNT` <br> e.g., `addpr 1 pr/LifeShield $300`
**Edit Premium** | `editpr INDEX pr/PREMIUM_NAME PREMIUM_AMOUNT` <br> e.g., `editpr 1 pr/LifeShield $350`
**Delete Premium** | `deletepr INDEX pr/PREMIUM_NAME` <br> e.g., `deletepr 1 pr/LifeShield`
**Add Policy** | `addpolicy pn/POLICY_NUMBER n/PREMIUM_NAME pc/PROVIDER_COMPANY pl/POLICY_LINK` <br> e.g., `addpolicy pn/POL123 n/LifeShield pc/ShieldCorp pl/https://www.shieldcorp.com/policy123`
**Delete Policy** | `deletepolicy INDEX` <br> e.g., `deletepolicy 1`
**Edit Policy** | `edit INDEX [pn/POLICY_NUMBER] [n/PREMIUM_NAME] [pc/PROVIDER_COMPANY] [pl/POLICY_LINK]` <br> e.g., `editpolicy 1 n/Life Shield pl/https://www.lifeshield.com`
**Find Policy** | `findpolicy KEYWORD [MORE_KEYWORDS]` <br> e.g., `findpolicy Life Health`
**List**   | `list`
**Help**   | `help`
