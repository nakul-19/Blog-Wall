from authentication.serializers import UserSerializer
from rest_framework.serializers import (
    HyperlinkedIdentityField,
    ModelSerializer,
    SerializerMethodField,
    ValidationError
    )
from django.contrib.contenttypes.models import ContentType
from django.contrib.auth import get_user_model

from likes.models import Like
User = get_user_model()

def create_like_serializer(model_type='post', slug=None, user=None):
    class LikeCreateSerializer(ModelSerializer):
        class Meta:
            ref_name = "LikeCreate"
            model =     Like
            fields = [
                'id',
                'like',
                
            ]
        def __init__(self, *args, **kwargs):
            self.model_type = model_type
            self.slug = slug
            return super(LikeCreateSerializer, self).__init__(*args, **kwargs)

        def validate(self, data):
            model_type = self.model_type
            model_qs = ContentType.objects.filter(model=model_type)
            if not model_qs.exists() or model_qs.count() != 1:
                raise ValidationError("This is not a valid content type")
            SomeModel = model_qs.first().model_class()
            obj_qs = SomeModel.objects.filter(slug=self.slug)
            if not obj_qs.exists() or obj_qs.count() != 1:
                raise ValidationError("This is not a slug for this content type")
            return data

        def create(self, validated_data):
            like = validated_data.get("like")
            if user:
                main_user = user
            else:
                main_user = User.objects.all().first()
            model_type = self.model_type
            slug = self.slug
            comment = Like.objects.create_by_model_type(
                    model_type, slug, like, main_user,
                    )
            return comment

    return LikeCreateSerializer


class LikeListSerializer(ModelSerializer):
    user = UserSerializer(read_only=True)
    class Meta:
        model = Like
        fields = [
            'id',
            'like',
            'user'
        ]
    
    
