# Blog-Wall API

## Table of Contents

1. [Introduction](#introduction)
2. [Features](#features)
3. [Technology Stack to be used](#technology-stack-to-be-used)
4. [GitHub Repository Structure](#github-repository-structure)
5. [Getting Started](#getting-started)
	1. [Fork, clone locally and create a branch](#fork-clone-locally--create-a-branch)
	1. [Setting Environment First Time](#setting-environment-first-time)
		1. [Basic Requirements](#basic-requirements)
		1. [Creating Virtual Environment](#creating-virtual-environment)
		1. [Installing Requirements](#installing-requirements)
		1. [Setting up Environment File](#setting-up-environment-file)
		1. [Migrating Database](#migrating-database)
		1. [Create Superuser](#create-superuser)
	1. [Starting Development Server](#starting-development-server)
	1. [Leaving the virtual environment](#leaving-the-virtual-environment)
	1. [Update requirements file](#update-requirements-file-critical)
	1. [Update Database](#update-database)  
6. [Developers](#developers)
7. [Maintainers](#maintainers)



## Introduction

This is the backdoor REST API developed to  integrate and manage Blogger content for Blog wall  android app. 

Blog wall is a blogging android application where readers can find lots of insightful and informational articles related to a wide variety of topics that include technology, science, health, etc. Anyone can share their ideas through publishing their useful blogs and engage in conversation with a network of readers and writers by interacting with the content.

## Features

1. Login/Sign Up.
2. JWT Authentication.
3. Email Verification.
4. Add blog posts related to various categories like Technology, Sports, Global Affairs, etc.
5. Add comments to the post or reply to the existing comments.
6. Search blog posts of a particular category using the title.
7. Filter posts based on their category, title, author, etc.
8. Able to edit existing posts and comments.
9. Sort blog posts according to different fields in ascending or descending order.
10. View the list of comments and replies related to particular posts.
11. Search list of comments on several posts using author's email.
12. LimitOffsetPagination for custom pagination style.
13. Add likes to particular posts.
14. View the number of likes for a blog post by a particular user.

 
## Technology Stack to be used:

<img src="https://img.shields.io/badge/python%20-%2314354C.svg?&style=for-the-badge&logo=python&logoColor=white"/> <img src="https://img.shields.io/badge/django%20-%23092E20.svg?&style=for-the-badge&logo=django&logoColor=white"/>  <img src="https://img.shields.io/badge/markdown-%23000000.svg?&style=for-the-badge&logo=markdown&logoColor=white"/><img src="https://img.shields.io/badge/github%20-%23121011.svg?&style=for-the-badge&logo=github&logoColor=white"/> <img src="https://img.shields.io/badge/sqlite-0B96B2?style=for-the-badge&logo=sqlite&logoColor=white"/> <img src="https://img.shields.io/badge/Heroku-430098?style=for-the-badge&logo=heroku&logoColor=white"/>

[![View in Swagger](http://jessemillar.github.io/view-in-swagger-button/button.svg)](https://blogwall.herokuapp.com/swagger/)
[![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/14143990/UVXhrHVq)


- **Android**: Kotlin
- **Backend**: Django Rest Framework
- **IDE**: VS Code
- **API Testing & Documentation**: Swagger, Postman
- **Version Control**: Git and GitHub
- **Database**: Sqlite
- **Hosting**: Heroku

#### GitHub Repository Structure

| S.No. | Branch Name | Purpose |
| --------------- | --------------- | --------------- |
| 1. | [backend](https://github.com/nakul-19/Blog-Wall/tree/backend) | contains all backend code |
| 2. | [android](https://github.com/nakul-19/Blog-Wall/tree/android) | contains all android code |

## Getting Started

### Fork, clone locally & create a branch

Fork [Blog Wall Backend](https://github.com/nakul-19/Blog-Wall) repository and clone at your local 

- Fork and Clone the repo using
```
$ git clone https://github.com/nakul-19/Blog-Wall.git
```
- Change Branch to `backend` using 
```
$ git checkout backend
```
### Setting Environment First Time

#### Basic Requirements 
1. [Python](https://www.python.org/downloads/)
1. [pip](https://pip.pypa.io/en/stable/installation/)

#### Creating [Virtual Environment](https://docs.python.org/3/library/venv.html) 

A virtual environment is a tool that helps keep dependencies required and the project isolated. If you wish to install a new library and write
```
pip install name_of_library
``` 
on the terminal without activating an environment, all the packages will be installed globally which is not a good practice if you’re working with different projects on your computer.

If this sounds a bit complicated, don’t worry so much because a virtual environment is just a directory that will contain all the necessary files for our project to run.

**Installing venv (required once)**

**Windows**
```
py -m pip install --user virtualenv
py -m venv env
```
**Linux**
```
python3 -m pip install --user virtualenv
python3 -m venv env
```

You have to start virtual environment everytime you start new terminal -

**Windows**

Using gitbash
```
. env/Scripts/activate
```
Using Powershell
```
. env\Scripts\activate
```
**Linux**
```
source env/bin/activate
```

#### Installing Requirements 

**Windows**
```
pip install -r requirements.txt
```
**Linux**
```
pip3 install -r requirements.txt
```
#### Setting up Environment File

**Configuring Environment Variables**

Make environment file by copying the example file -
```
cp .env.example .env
``` 

**GMAIL Settings**

You need to [turn on less secure app access](https://support.google.com/accounts/answer/6010255?hl=en) for sending mails. 
```
EMAIL_HOST_USER = your_mail_id
EMAIL_HOST_PASSWORD = your_password
```

#### Migrating Database
**Windows**
```
py manage.py migrate
```
**Linux**
```
python3 manage.py migrate
```

#### Create Superuser
**Windows**
```
py manage.py createsupeser
```
**Linux**
```
python3 manage.py createsupeser
```

### Starting Development Server
**Windows**
```
py manage.py runserver
```
**Linux**
```
python3 manage.py runserver
``` 

### Leaving the virtual environment
```
deactivate
```

### Update requirements file (Critical)
If you have installed new dependency, the pip freeze command lists the third-party packages and versions installed in the environment. 

**Windows**
```
pip freeze > requirements.txt
```
**Linux**
```
pip3 freeze > requirements.txt
```

### Update Database  
Everytime you change db models, you need to run makemigrations and migrate to update on database.

**Windows**
```
py manage.py makemigrations
py manage.py migrate
```
**Linux**
```
python3 manage.py makemigrations
python3 manage.py migrate
``` 

## Developers:

> "Alone we can do so little; together we can do so much."

| S.No. | Name | Role | GitHub Username:octocat: |
| --------------- | --------------- | --------------- | --------------- |
| 1. | Nakul | Android Development | [@nakul-19](https://github.com/nakul-19) |
| 2. | Rudrakshi | Backend Development| [@rudrakshi99](https://github.com/rudrakshi99)  |


## Maintainers✨

<table>
  <tbody><tr>
    <td align="center"><a href="https://github.com/rudrakshi99"><img alt="" src="https://avatars.githubusercontent.com/rudrakshi99" width="100px;"><br><sub><b>Rudrakshi</b></sub></a><br><a href="https://github.com/nakul-19/Blog-Wall/commits/backend?author=rudrakshi99" title="Code">💻</a></td>
    <td align="center"><a href="https://github.com/nakul-19"><img alt="" src="https://avatars.githubusercontent.com/nakul-19" width="100px;"><br><sub><b>Nakul </b></sub></a><br><a href="https://github.com/nakul-19/Blog-Wall/commits?author=nakul-19" title="Code">💻</a></td>
  </tr>
</tbody></table>

[![Uses Git](https://forthebadge.com/images/badges/uses-git.svg)](https://github.com/nakul-19/Blog-Wall) 
[![ForTheBadge ANDROID](https://forthebadge.com/images/badges/built-for-android.svg)](https://github.com/nakul-19/Blog-Wall)
[![forthebadge](https://forthebadge.com/images/badges/made-with-python.svg)](https://github.com/nakul-19/Blog-Wall)
[![Built with love](https://forthebadge.com/images/badges/built-with-love.svg)](https://github.com/nakul-19/Blog-Wall) [![Built By Developers](https://forthebadge.com/images/badges/built-by-developers.svg)](https://github.com/nakul-19/Blog-Wall) 
