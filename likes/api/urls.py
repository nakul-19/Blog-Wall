from django.contrib import admin
from django.urls.conf import path

from .views import (
    LikeListAPIView,
    LikeCreateAPIView  

    )
app_name = 'likes'

urlpatterns = [
    path('', LikeListAPIView.as_view(), name='list'),
    path('create/', LikeCreateAPIView.as_view(), name='create'),
    # path('<pk>', CommentDetailAPIView.as_view(), name='thread'),
]
