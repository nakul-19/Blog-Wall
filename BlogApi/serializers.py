from rest_framework import serializers
from .models import Post, Comments

class CommentsSerializer(serializers.ModelSerializer):

    class Meta:
        model = Comments
        fields = ('__all__')
        


class PostSerializer(serializers.ModelSerializer):

    class Meta:
        model = Post
        fields = ('__all__')
