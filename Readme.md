## Technology Stack to be used:

<img src="https://img.shields.io/badge/python%20-%2314354C.svg?&style=for-the-badge&logo=python&logoColor=white"/> <img src="https://img.shields.io/badge/django%20-%23092E20.svg?&style=for-the-badge&logo=django&logoColor=white"/>  <img src="https://img.shields.io/badge/markdown-%23000000.svg?&style=for-the-badge&logo=markdown&logoColor=white"/><img src="https://img.shields.io/badge/github%20-%23121011.svg?&style=for-the-badge&logo=github&logoColor=white"/> <img src="https://img.shields.io/badge/sqlite-0B96B2?style=for-the-badge&logo=sqlite&logoColor=white"/> <img src="https://img.shields.io/badge/Heroku-430098?style=for-the-badge&logo=heroku&logoColor=white"/>


- **Android**: Kotlin
- **Backend**: Django Rest Framework
- **IDE**: VS Code
- **API Testing & Documentation**: Swagger
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
