---
layout: page
title: Nithvin's Project Portfolio Page
---

### Project: ClientNest

ClientNest is a **desktop app for managing contacts, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). From adding clients to tracking birthdays and policies, ClientNest supports meaningful engagement and confident client management.

Given below are my contributions to the project.

* **New Feature**: Added the ability to track premiums for each client.
  * What it does: Introduced the premium attribute allowing users to track the premium name and amount each client has with them. 
  * Justification: Financial advisors often have multiple clients with premiums of the same name but different amounts. This feature enables them to have clear oversight to the exact premium and amount purchased by each client.
  * Highlights: Built custom `PremiumList` class that stores a list of a custom `Premium` class with validation logic and integrated it into the data model, parser, storage, UI, and test suites.

* **New Feature**: Added the ability to add, edit and remove premium for each client.
  * What it does: Allow users granular control over the premiums assigned to each client to ensure accuracy and ease of use to keep client's premiums up to date.
  * Justification: As a client stays with a Financial Advisor, over time, the Premiums they buy will change or they may choose to change the amount for a Premium. This requires the system to provide an easy way to update Client's premiums.
  * Highlights: Built 3 premium related commands, addpr, editpr and deletepr. Created their required command classes and parser classes with appropriate input and error handling.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2425s2.github.io/tp-dashboard/#/widget/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=FabianHeng&tabRepo=AY2425S2-CS2103-F10-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false&chartGroupIndex=9&chartIndex=3)

* **Project management**:
  * Managed releases `v1.5` (1 releases) on GitHub

* **Documentation**:
  * User Guide:
    * Added documentation for the field `pr` in Person [\#255](https://github.com/AY2425S2-CS2103-F10-2/tp/pull/255)
    * Added documentation for the feature `addpr`,`editpr`,`deletepr`: [\#255](https://github.com/AY2425S2-CS2103-F10-2/tp/pull/255)
  * Developer Guide:
    * Update UML diagram for Model (Pull Request [\#145](https://github.com/AY2425S2-CS2103-F10-2/tp/pull/145))

