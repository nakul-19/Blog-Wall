### Backend Setup Instructions

- Fork and Clone the repo using
```
$ git clone https://github.com/nakul-19/Blog-Wall.git
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
