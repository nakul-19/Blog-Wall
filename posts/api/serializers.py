from rest_framework.serializers import (
    HyperlinkedIdentityField,
    ModelSerializer,
    SerializerMethodField
    )


from authentication.serializers import UserSerializer
from comments.api.serializers import CommentSerializer
from likes.api.serializers import LikeListSerializer
from comments.models import Comment

from posts.models import Post
from likes.models import Like

class PostCreateUpdateSerializer(ModelSerializer):
    class Meta:
        model = Post
        fields = [
            #'id',
            'title',
            #'slug',
            'content',
            'publish',
            'topic',
            'image'
        ]

    def get_image(self, obj):
        try:
            image = obj.image.url
        except:
            image = None
        return image




class PostDetailSerializer(ModelSerializer):

    url = HyperlinkedIdentityField(
        view_name='posts-api:detail',
        lookup_field='id'
        )
    user = UserSerializer(read_only=True)
    image = SerializerMethodField()
    html = SerializerMethodField()
    comments = SerializerMethodField()
    likes = SerializerMethodField()

    class Meta:
        model = Post
        fields = [
            'url',
            'id',
            'user',
            'title',
            # 'slug',
            'content',
            'html',
            'publish',
            'image',
            'comments',
            'topic',
            'likes'
        ]

    def get_html(self, obj):
        return obj.get_markdown()

    def get_image(self, obj):
        try:
            image = obj.image.url
        except:
            image = None
        return image

    def get_comments(self, obj):
        c_qs = Comment.objects.filter_by_instance(obj)
        comments = CommentSerializer(c_qs, many=True).data
        return comments
    
    def get_likes(self, obj):
        c_qs = Like.objects.filter_by_instance(obj)
        comments = LikeListSerializer(c_qs, many=True).data
        return comments




class PostListSerializer(ModelSerializer):
    
    url = HyperlinkedIdentityField(
        view_name='posts-api:detail',
        lookup_field='id'
        )

    user = UserSerializer(read_only=True)
    class Meta:
        model = Post
        fields = [
            'url',
            'user',
            'title',
            'content',
            'publish',
            'image',
            'topic'
        ]

    def get_image(self, obj):
        try:
            image = obj.image.url
        except:
            image = None
        return image
