from django.contrib import admin
from django.urls.conf import path, include

from .views import (
    PostCreateAPIView,
    PostDeleteAPIView,
    PostDetailAPIView,
    PostListAPIView,
    PostUpdateAPIView,
    )
app_name = 'posts'

urlpatterns = [
    path('', PostListAPIView.as_view(), name='list'),
    path('create/',PostCreateAPIView.as_view(), name='create'),
    path('<slug>', PostDetailAPIView.as_view(), name='detail'),
    path('<slug>/edit/', PostUpdateAPIView.as_view(), name='update'),
    path('<slug>/delete/', PostDeleteAPIView.as_view(), name='delete'),
]