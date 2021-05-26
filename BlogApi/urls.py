from django.urls import path
from . import views


urlpatterns = [
    path('', views.PostListApiView.as_view(), name="posts"),
    path('comments/', views.CommentsListApiView.as_view(), name="comments"),
    path('<int:id>', views.PostDetailApiView.as_view(), name="posts"),
    path('comments/<int:id>', views.CommentsDetailApiView.as_view(), name="comments"),
    path('create-post/',views.PostCreateApiView.as_view(), name="posts"),
    path('create-comment/',views.CommentsCreateApiView.as_view(), name="comments")
]
