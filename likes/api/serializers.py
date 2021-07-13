from authentication.serializers import UserSerializer
from rest_framework.serializers import (
    HyperlinkedIdentityField,
    ModelSerializer,
    Serializer,
    SerializerMethodField,
    ValidationError
    )

from django.contrib.contenttypes.models import ContentType
from django.contrib.auth import get_user_model

from likes.models import Like
from posts.models import Post
User = get_user_model()

def create_like_serializer(model_type='post',obj_id=None, user=None):
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
            self.obj_id =obj_id
            return super(LikeCreateSerializer, self).__init__(*args, **kwargs)

        def validate(self, data):
            model_type = self.model_type
            model_qs = ContentType.objects.filter(model=model_type)
            # print(ContentType.objects.filter(model='post'))
            if not model_qs.exists() or model_qs.count() != 1:
                raise ValidationError("This is not a valid content type")
            SomeModel = model_qs.first().model_class()
            obj_qs = SomeModel.objects.filter(id=self.obj_id)
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
            like_present = main_user.user_like.filter(object_id=obj_id)
            # print(like_present)
            if like_present:
                # like_updated = main_user.user_like.filter(object_id=obj_id)[0].like
                like_id = main_user.user_like.filter(object_id=obj_id)[0].id
                obj = Like.objects.get(id=like_id)
                obj.like = like+1
                obj.save()
                # print(like_id)
            else:
                like = Like.objects.create_by_model_type(
                        model_type,obj_id, like, main_user,
                        )
            return like
        

    return LikeCreateSerializer


class LikeListSerializer(ModelSerializer):
    # user = UserSerializer(read_only=True)
    class Meta:
        model = Like
        fields = [
            'id',
            'like',
            #  'user',
        ]
    
    
