---
layout: page
title: Teesha's Project Portfolio Page
---

### Project: ClientNest

ClientNest is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

Given below are my contributions to the project.

* **New Feature**: Added support for the `birthday` field in the Person class.
  * What it does: Allows users to store a client’s birthday using the `b/` prefix, which is validated and parsed as a `LocalDate`.  
  * Justification: Birthdays are a key milestone for financial advisors to build rapport with clients. Capturing birthdays enables future features like reminders.  
  * Highlights: Built custom `Birthday` class with validation logic and integrated it into the data model, parser, storage, UI, and test suites.

* **New Feature**:New Feature: Implemented the **Upcoming Birthdays Panel** and backend logic.  
  * What it does: Displays clients with birthdays occurring in the next 30 days in a scrollable panel beside the person list. The panel updates dynamically when a birthday is added or edited.  
  * Justification: This improves user experience by proactively surfacing time-sensitive opportunities for client engagement.  
  * Highlights: Wrote the filtering logic in `ModelManager`, created a new `UpcomingBirthdaysPanel` UI component, and connected it through `Logic` and `UI`. Integrated with the existing GUI layout to ensure responsiveness and visual consistency.

* **New Feature**:New Feature: Integrated a customized version of the **TaskManager** from the original IP into ClientNest.  
  * What it does: Allows users to manage personal to-dos, deadlines, and events in a separate window with CLI support.  
  * Justification: Enables financial advisors to stay organized without needing an external planner.  
  * Highlights: Refactored the original IP To-Do List to better suit ClientNest's UX. Added links to open the panel via the top menu bar and improved styling, persistence, and task command feedback.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s2.github.io/tp-dashboard/#/widget/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=FabianHeng&tabRepo=AY2425S2-CS2103-F10-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false&chartGroupIndex=9&chartIndex=0)

* **Project management**:
  * Assisted releases `v1.3` - `v1.5rc` (3 releases) on GitHub
  * Set up the team **MarkBind documentation site**

* **Enhancements to existing features**:
  * Refactored `add` and `edit` commands to support the new `birthday` field, and improved validation and error handling (Pull request [\#45](https://github.com/AY2425S2-CS2103-F10-2/tp/pull/45))
  * Updated GUI layout to support the Upcoming Birthdays panel, ensuring it is responsive and scrollable (Pull request [\#92](https://github.com/AY2425S2-CS2103-F10-2/tp/pull/92))
  * Enhanced TaskManager integration by modifying command structure and window handling to match ClientNest’s design language (Pull request [\#164](https://github.com/AY2425S2-CS2103-F10-2/tp/pull/164))

* **Documentation**:

  **User Guide**
    * Authored  documentation related to the `birthday` field and  **Upcoming Birthdays Panel** section 
    * Integrated and rewrote the **TaskManager** section with consistent tone and examples 
    * Cleaned up the `add` command formatting and duplicate rules
    * Authored the Task Manager User Guide (`TaskManagerUserGuide.md`) with command formats, examples, and summary table (Pull request [\#175](https://github.com/AY2425S2-CS2103-F10-2/tp/pull/175))
  
  **Developer Guide**
  * Documented the `Birthday` field in the AddressBook model by updating the `ModelClassDiagram_AddressBook.puml` (Pull request [\#128](https://github.com/AY2425S2-CS2103-F10-2/tp/pull/128))
  * Added architecture and class diagrams for the **TaskManager** feature
  * Wrote internal description of `TaskManager`'s structure and its UI/logic independence 


* **Tools**:
  * Integrated date validation using Java's `LocalDate` APIs  ([\#42]())

