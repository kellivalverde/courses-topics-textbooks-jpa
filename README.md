# courses-topics-textbooks-jpa
Mod 7 Demo: Courses Project JPA enabled
(copied from WCCI README)

# JPA enabled courses-topics-textbooks

## Objectives
- Wire up a JPA enabled web application that will control entities for courses, topics and textbooks
- Introduction to test driving a JPA enabled app
- Develop an understanding of the annotations used to create the proper entity relationships

## Setup

Use the [Spring Initializr](https://start.spring.io) to create a `courses-topics-textbooks-jpa` project, including the following dependencies:
- Thymeleaf
- JPA
- H2
- Devtools


## The Project Explanation

A course can be a complicated POJO when we start to bring in object relationships. In this scenario, we are going to think about a course that has a name and a particular Topic or topics that it can fall under. Also, a textbook, along with it's title, will have a particular course that it is being utilized in. While there are many different ways we can begin to visual these object relationships, the tables below will certainly help to keep us all on the same page for this project. 

### The `Topic` Table

The simplest of the entities we will create, the `Topic` will contain a name.

|topic|
|----|
|Java|
|Spring|
|TDD|

### The `Textbook` Table

A `Textbook` has a name and and can be utilized in a number of different `Course`s. 

|title|`Course`|
|----|--------|
|Head First Java|java101|
|Head First Design Patterns|java102|
|Clean Code|java102|
|Intro to JPA|java102|


### The `Course` Table

A `Course` can belong to many different topics. 

|name|description|`Topic`|
|----|-----------|-------|
|"Intro to Java"|"description"|java|
|"Advanced Software Design"|"description"|java, tdd|

 

### The tests to build out this application 
We will write the following unit tests to drive the creation of this project

- save and load a topic
- generate a topic id
- save and load a course
- save a textbook to a particular course
- establish course to topics relationship
- establish topic to courses relationship
- find courses for a topic
- find courses for a particular topic id
