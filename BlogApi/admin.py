from django.contrib import admin
from .models import Comments, Post

admin.site.register(Comments)
admin.site.register(Post)