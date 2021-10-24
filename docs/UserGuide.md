# User Guide

![Unimods](./resources/UnimodsLogo.png)

# About The Project

It’s August 5th, and the Academic Year is right around the corner!
ModReg is about to start, and you have no idea what modules to take and what your timetable might even look like.

Introducing **UNI Mods**, an easy to use application for NUS students that provides information on all available NUS
modules and lets you pick and choose the modules and classes you want to take for that semester!
Depending on the classes you decide to take, a timetable will be generated to keep track of your daily schedule and your
total workload:

![timetable](./resources/timetable.png)

<br>

---

# Table of Contents

- [Quick Start](#quick-start)
- [Features](#features)
    * [Viewing help](#viewing-help-help)
    * [Show Module Info](#display-module-info-show-module_code)
    * [Search Module](#search-module-search-keyword--l)
    * [Update Local Database](#update-local-database-update)
    * [Add to Timetable](#add-to-timetable-add-module_code)
    * [Delete from Timetable](#delete-from-timetable-delete-module_code)
    * [Clear Timetable](#clear-timetable-clear)
    * [View Timetable](#view-timetable-timetable)
    * [Exit](#exit-exit)
- [FAQ](#faq)
- [Command Summary]()

<br>

---

# Quick Start

1. Ensure you have **Java 11** or above installed in your Computer.
2. Download the latest **unimods.jar** from here. If it does not work, open your CLI of choice and run
   `java -jar unimods.jar`.

3. Copy the file to the folder you want to use as the home folder for your Unimods.
4. Double-click the file to start the app.
5. Type the command in the command box and press Enter to execute it.

   Some example commands you can try:

       - help : to list all the commands with their description
       - search <module_code> : lists module code based on the given partial regex
       - show <module_code>: displays the module information
       - add <module_code> : to add the module to the timetable
       - delete <module_code> : to remove the module from the timetable
       - timetable : lists all modules added to the timetable
       - exit : Exits the app.
       - Refer to the Features below for details of each command.

<br>

---

# Features

> :information_source: **Notes about the command formats**
> - Words in `<UPPER_CASE>` are the parameters to be given by the user. <br />
    > e.g. in `show <MODULE_CODE>`, <MODULE_CODE> is a parameter and be called like so : `show CS2113T`
    <br /><br />
> - Items in square brackets are optional <br />
    > e.g. find `search <KEYWORD> [-l]`
    > can be called as `search GEH` OR `search GEH -l`.
    <br /><br />
> - Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `bye`) will be ignored.<br />
    > e.g. `help abc` will be interpreted as  `help`

## Viewing Help: `help`

If you are stuck wondering how to even begin using this application, simply type `help` into the terminal and you will
be able to see all the available commands!
You will also be provided a link to the User Guide of this application which you can refer to, for detailed descriptions
of the commands.

Output:

```shell
~$ help
__________________________________________________________________________
	UNIMods accepts the following commands:-
		| No.| Command Syntax                |            Command Action                      |
		| 1. | search <module_code>          | Search module based on the given partial regex |
		| 2. | show <module_code>            | Display module information                     |
		| 3. | add <module_code>             | Add module to the Timetable                    |
		| 4. | delete <module_code>          | Deletes module from the Timetable              |
		| 5. | clear                         | Deletes all modules from the Timetable         |
		| 6. | timetable                     | Display the Timetable                          |
		| 7. | store <grade> > <module_code> | Stores the grades scored in the Transcript     |
		| 8. | remove <module_code>          | Remove the module from the Transcript          |
		| 9. | calculate cap                 | Displays the Cumulative Average Point          |
		| 10.| exit                          | Exit From Program                              |
	 ** Note: For details, refer to the User Guide of NUSModsLite at: 
		https://ay2122s1-cs2113t-w12-2.github.io/tp/UserGuide.html
__________________________________________________________________________


```

To begin, perhaps try looking up CS2113T by running the following command:

<br>

## Display Module Info: `show <MODULE_CODE>`

If you want to find out more about a module, type `show <module_code>` to display the following:

* Name
* MCs
* Department which offers the module
* Description
* Prerequisites
* S/U option Availability
* Semester Availability

For example, try typing `show CS2113T` and see the magic happen!

:information_source: Both command and module code is case-insensitive.

```shell
~$ show CS2113T
Title: Software Engineering & Object-Oriented Programming
MCs: 4
Department: Computer Science
This module introduces the necessary skills for systematic and
rigorous development of software systems. It covers requirements,
design, implementation, quality assurance, and project management
aspects of small-to-medium size multi-person software projects. The
module uses the Object Oriented Programming paradigm. Students of this
module will receive hands-on practice of tools commonly used in the
industry, such as test automation tools, build automation tools, and
code revisioning tools will be covered.
Prerequisites: CS2040C or ((CS2030 or its equivalent) and CS2040/S)
S/U able: No
Semester Availability: [1, 2]

```

<br>

## Search Module: `search <KEYWORD> [-l]`

Maybe you don't know what modules are out there, and want to know what GEH modules are available.

You can type `search GEH` to display all CG modules available:

```shell
~$ search GEH
GEH1001 Globalisation and New Media 4MC
GEH1002 Economic Issues in Dev World 4MC
GEH1004 Chinese Heritage: History and Literature 4MC
GEH1005 Crime Fiction in Eng & Chinese 4MC
GEH1006 Chinese Music, Language and Literature (in English) 4MC
GEH1007 Asian Cinema: The Silent Era 4MC
GEH1008 Nations & Nationalism in South Asia 4MC
GEH1009 Framing Bollywood: Unpacking The Magic 4MC
GEH1010 Beasts, People and Wild Environments in South Asia 4MC
GEH1011 Film and History 4MC
:
:
```

You can also apply the following flags to refine the search:

- **-l (small L) :** search for mods matching the level specified e.g `-l 3000`
- **-mc :** search for mods matching the number of MCs specified e.g `-mc 4`
- **-s :** search for mods offered in the semester e.g. `-s 2`
  (:information_source: 3 & 4 refer to Special Terms 1 and 2 respectively.)
- **-e :** search for mods that have/do not have exams. Specify with true/false e.g `-e false`
- **-f :** search for mods from a faculty e.g `-f Computing`
- **-d :** search for mods from a department `-d Computer Science`
- **-q :** performs a local search using locally saved module data which might not be the most updated version, but is
  very quick e.g. `-q`

:information_source: Command, search term and flag regex are case insensitive.

:warning: Flags are not case insensitive. Inputting an invalid flag will cause the invalid flag and its regex to be
ignored.

:warning: Broad queries may take up to ten minutes to execute, as UNI Mods will have to fetch information for every
single mod from NUSMods. You may wish to avoid broad queries, and if absolutely required, it is recommended to perform
them with the -q flag, which will execute in a matter of seconds.

<br>

## Update local database: `update`

Maybe you are going to be doing some work at the University Sports Center tomorrow. You forsee that you will lack a good
WiFi connection there, and want to access the latest information offline. Run update, and grab a cup of coffee while
waiting!

:information_source: Command is case insensitive.

:information_source:  You should rarely need to execute this command since every time UNI Mods retrieves data from
NUSMods, it will update that mod in the local database. You should only realistically only need to do this before the
start of a new semester, when mods are being updated for the coming semester.

<br>

## Add to timetable: `add <MODULE_CODE>`

You have finally decided on the modules you want to take. Try adding your first module to your timetable!

Let's add for example, `CG2271` to the timetable

You can type `add CG2271` to see all the available lessons.

```shell
~$ add CG2271
Now adding CG2271 into timetable
         Lecture Lesson Slots                         Tutorial Lesson Slots                         Laboratory Lesson Slots
1: Wednesday, 0900-1100, 01, E-Learn_A    |   1: Thursday, 1100-1200, 01, E-Learn_A     |   1: Friday, 0800-1000, 01, E4A-04-08       |   
_______________________________________   |   _______________________________________   |   _______________________________________   |   
                                              2: Tuesday, 0800-0900, 02, E-Learn_A      |   2: Friday, 1000-1200, 02, E4A-04-08       |   
                                              _______________________________________   |   _______________________________________   |   
                                              3: Friday, 1400-1500, 03, E-Learn_A       |   
                                              _______________________________________   | 
```

If lessons are found, a prompt to indicate a choice for each lesson type will be shown as such.

```shell
Which Lecture would you like to choose? 
```

<br>

## Delete from timetable: `delete <module_code>`

You can remove any module that you added to your timetable using this command.

For example: If you have CS2113T already added to your timetable. You can type
`delete CS2113T` to remove this module from your timetable.

```shell
~$ delete CS2113T
CS2113T is successfully deleted from your Timetable.
```

<br>

## Clear timetable: `clear`

You can remove **all** added modules from your timetable by typing `clear`. You can then view the empty timetable by
typing the command `timetable`.

For Example:

```shell
~$ clear
All modules have been successfully removed from your Timetable.

```

<br>

## View timetable: `timetable`

You can view the current timetable which details your daily schedule as well as the total MCs taken and classes and time
slots for each day of the week in a timetable structure.

Simply type `timetable` into the input and voila!

```shell
~$ timetable

				900             1000            1100            1200            1300            1400            1500            1600            1700            1800            
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				                                                |   CS2113T                     |                                                                               
		MON		                                                |   LEC[C01]                    |                                                                               
				                                                |   E-Learn_C                   |                                                                               
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				                                                                                                                                                                
		TUE		                                                                                                                                                                
				                                                                                                                                                                
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				                                                |   CS2113T     |                                                                                               
		WED		                                                |   LEC[C01]    |                                                                                               
				                                                |   E-Learn_C   |                                                                                               
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				                                                |   CS2113T                     |                                                                               
		THU		                                                |   LEC[C01]                    |                                                                               
				                                                |   E-Learn_C                   |                                                                               
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				                                                                                                                |   CS2113T                     |               
		FRI		                                                                                                                |   LEC[C01]                    |               
				                                                                                                                |   E-Learn_C                   |               
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				                                                                                                                                                                
		SAT		                                                                                                                                                                
				                                                                                                                                                                
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				                                                                                                                                                                
		SUN		                                                                                                                                                                
				                                                                                                                                                                

*******************
Modules taken this semester: 

CS2113T Software Engineering & Object-Oriented Programming 4MC

Total MCs taken this semester: 4.0

*******************

```

<br>

## Store grades secured in various modules: `store <GRADE> > <MODULE_CODE>`

You can store the grades you have secured for various modules by using this command.These will be stored in the list of
completed modules and will be used for CAP calculation.

For Example : Let's store `A+` grade for `CS2113T` module in the records.

You can type `store A+ > CS2113T` to store A+ grade for the module CS2113T in the records.

```shell
~$ store A+ > CS2113T
CS2113T with grade A+ has been added to the list of modules completed.
__________________________________________________________________________

```

You can also store modules with CS/CU grades for record purposes.These modules will not be considered for CAP
calculation.

For Example : Let's store `CS` grade for `CFG1002` module in the records.

```shell
~$ store CS > CFG1002
CFG1002 with grade CS has been added to the list of modules completed.
__________________________________________________________________________

```

You can choose to store modules for which you have exercised the S/U option. These modules will not be considered for
CAP calculation. For Example : Let's store `S` grade for `CS1231` module in the records.

```shell
~$ store S > CS1231
CS1231 with grade S has been added to the list of modules completed.
__________________________________________________________________________

```

<br>

## Remove modules from the list of completed modules: `remove <module_code>`

You can remove any module that you added to your list of completed modules using this command.

For example: If you have CS2113T already to your list of completed modules. You can type
`remove CS2113T` to remove this module and its corresponding grade from the list of modules completed.

```shell
~$ remove CS2113T
CS2113T is successfully removed from your Transcript.
__________________________________________________________________________
```

<br>

## Calculate CAP : `calculate cap`

You can calculate your **CAP** based on the list of modules you have completed and the corresponding grades scored in
them.

You can type `calculate cap` to view your Cumulative Average Point.

For Example:

```shell
~$ calculate cap
Cumulative Average Point : 3.0
__________________________________________________________________________

```

<br>

## View Unofficial Transcript : `transcript`

You can view your Unofficial Transcript to get an idea of your degree progress.This will disaply the modules you have
completed and their corresponding grades. The total number of credits completed and the CAP will also be displayed.

You can type `transcript` to view your Unofficial Transcript.

For Example:

```shell
~$==>transcript
								--	National University of Singapore	--
									--	Unofficial Transcript	--
									------------------------------

Date Issued : 25-10-2021

MODULE                                                                              GRADE  CREDITS

CS2113T     Software Engineering & Object-Oriented Programming                        A+    4.0
CS1231      Discrete Structures                                                       A-    4.0
CS1010      Programming Methodology                                                   C+    4.0
CG2271      Real-Time Operating Systems                                               A+    4.0
CG2271      Real-Time Operating Systems                                               C-    4.0
CS2102      Database Systems                                                          A     4.0
CG1111      Engineering Principles and Practice I                                     S     6.0
CG1112      Engineering Principles and Practice II                                    U     6.0

Total Credits Fulfilled : 36.0

Cumulative Average Point : 3.67

	This is not an official transcript issued by the Office of the Registrar.
__________________________________________________________________________

```

<br>

---

# FAQ

**Q**: How do I save my timetable so I don’t have to add all my modules again?</br>
**A**: The timetable is saved automatically on every update (add/delete etc.)

**Q**: How do I add additional comments/tags to my timetable so I can keep track of additional deadlines?</br>
**A**: Stay tuned for v2.0!

<br>

---

## Command Summary

| Command                   | Meaning                                                                                           |
| --------------            | ----------                                                                                        |
| `help`                    | Shows available commands and flags. </br> Example: `help`                                         |
| `search <KEYWORDd> [-l]`  | Lists modules that have partial matches by regex to the keyword. </br> Example: `search GEH -l`   |
| `show <MODULE_CODE>`      | Display relevant module information. </br> Example: `show CS2113T`                                |
| `add <MODULE_CODE>`       | Adds the module to the timetable. </br> Example: `add CS2113T`                                    |
| `delete <MODULE_CODE>`    | Deletes the module from the timetable. </br> Example: `delete CS2113T`                            |
| `clear`                   | Deletes all modules from the timetable. </br> Example: `clear`                                    |
| `timetable`               | Displays timetable with total MCs taken. </br> Example: `timetable`                               |
| `exit`                    | Exit the application. </br> Example:`exit`                                                        |

---
