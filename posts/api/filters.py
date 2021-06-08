import django_filters as filters
from posts.models import Post

class PostFilter(filters.FilterSet):
        class Meta:
            model = Post
            fields = ['id', 'user', 'title', 'topic',]
        

