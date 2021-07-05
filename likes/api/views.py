from rest_framework.permissions import (
    AllowAny,
    IsAuthenticated,
    IsAuthenticatedOrReadOnly,

    )
from django.db.models import Q

from rest_framework.generics import (
    CreateAPIView,
    DestroyAPIView,
    ListAPIView, 
    UpdateAPIView,
    RetrieveAPIView,
    RetrieveUpdateAPIView
    )

from posts.api.permissions import IsOwnerOrReadOnly
from likes.models import Like
from .serializers import LikeListSerializer, create_like_serializer

class LikeCreateAPIView(CreateAPIView):
    queryset = Like.objects.all()
    permission_classes = [IsAuthenticated]

    def get_serializer_class(self):
        model_type = self.request.GET.get("type")
        obj_id = self.request.GET.get("obj_id")
        return create_like_serializer(
                model_type=model_type, 
                obj_id=obj_id, 
                user=self.request.user
                )

class LikeListAPIView(ListAPIView):
    serializer_class = LikeListSerializer
    permission_classes = [AllowAny]

    def get_queryset(self, *args, **kwargs):
        queryset_list = Like.objects.filter(id__gte=0) #filter(user=self.request.user)
        query = self.request.GET.get("q")
        if query:
            queryset_list = queryset_list.filter(
                    Q(user__username__icontains=query) 
                    ).distinct()
        return queryset_list

