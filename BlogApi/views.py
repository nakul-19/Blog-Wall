from django.shortcuts import render
from rest_framework.generics import ListAPIView, CreateAPIView, RetrieveUpdateDestroyAPIView
from .models import Comments, Post
from .serializers import PostSerializer, CommentsSerializer
from rest_framework import permissions
from .permissions import IsOwner

class PostListApiView(ListAPIView):
    serializer_class = PostSerializer
    queryset = Post.objects.all()
    permission_classes = [permissions.AllowAny]


class PostCreateApiView(CreateAPIView):
    serializer_class = PostSerializer
    queryset = Post.objects.all()
    permission_classes = (permissions.IsAuthenticated,)

    def perform_create(self, serializer):
        return serializer.save(owner=self.request.user)

    def get_queryset(self):
        return self.queryset.filter(owner=self.request.user)


class PostDetailApiView(RetrieveUpdateDestroyAPIView):
    serializer_class = PostSerializer
    permission_classes = (permissions.IsAuthenticated, IsOwner,)
    queryset = Post.objects.all()
    lookup_field = "id"
    
    def get_queryset(self):
        return self.queryset.filter(owner=self.request.user)


class CommentsListApiView(ListAPIView):
    serializer_class = CommentsSerializer
    queryset = Comments.objects.all()

    permission_classes = [permissions.AllowAny]


class CommentsCreateApiView(CreateAPIView):
    serializer_class = CommentsSerializer
    queryset = Comments.objects.all()
    
    permission_classes = (permissions.IsAuthenticated,)
    
    def perform_create(self, serializer):
        return serializer.save(owner=self.request.user)

    def get_queryset(self):
        return self.queryset.filter(owner=self.request.user)


class CommentsDetailApiView(RetrieveUpdateDestroyAPIView):
    serializer_class = CommentsSerializer
    permission_classes = (permissions.IsAuthenticated, IsOwner,)
    queryset = Comments.objects.all()
    lookup_field = "id"
    
    def get_queryset(self):
        return self.queryset.filter(owner=self.request.user)
