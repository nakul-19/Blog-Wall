# Blog-Wall

Blog wall is a blogging android application where readers can find lots of insightful and informational articles related to a wide variety of topics that include technology, science, health, etc. Anyone can share their ideas through publishing their useful blogs and engage in conversation with a network of readers and writers by interacting with the content.

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


### Backend Setup Instructions

- Fork and Clone the repo using
```
$ git clone https://github.com/nakul-19/Blog-Wall.git
```
- Change Branch to `backend` using 
```
$ git checkout backend
```
- Setup Virtual environment
```
$ python3 -m venv env
```
- Activate the virtual environment
```
$ source env/bin/activate
```
- Install dependencies using
```
$ pip3 install -r requirements.txt
```
- Make migrations using
```
$ python3 manage.py makemigrations
```
- Migrate Database
```
$ python3 manage.py migrate
```
- Create a superuser
```
$ python3 manage.py createsuperuser
```
- Run server using
```
$ python3 manage.py runserver
``` 
## Developers:

> "Alone we can do so little; together we can do so much."

| S.No. | Name | Role | GitHub Username:octocat: |
| --------------- | --------------- | --------------- | --------------- |
| 1. | Nakul | Android Development | [@nakul-19](https://github.com/nakul-19) |
| 2. | Rudrakshi | Backend Development| [@rudrakshi99](https://github.com/rudrakshi99)  |


## Maintainers ✨

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
