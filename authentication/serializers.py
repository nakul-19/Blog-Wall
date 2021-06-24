from django.http.request import RAISE_ERROR
from rest_framework import serializers
from .models import User
from django.contrib import auth
from rest_framework.exceptions import AuthenticationFailed, ErrorDetail
from rest_framework_simplejwt.tokens import RefreshToken, TokenError
from django.utils.encoding import smart_str, force_str, smart_bytes, DjangoUnicodeDecodeError
from django.utils.http import urlsafe_base64_decode, urlsafe_base64_encode
from django.contrib.auth.tokens import PasswordResetTokenGenerator
from rest_framework.exceptions import ParseError
from django.core.validators import validate_email
from django.core.exceptions import ValidationError
from django.http import JsonResponse


class RegisterSerializer(serializers.ModelSerializer):
    password = serializers.CharField(max_length=68, write_only=True)
    class Meta:
        model = User
        fields = ['email', 'username', 'password']

    
    def validate(self, attrs):
        email = attrs.get('email', '')
        password = attrs.get('password', '')
        try:
            validate_email( email )
            if len(password) < 6:
                raise ParseError('Ensure this field has at least 6 characters.')    
            return attrs

        except ValidationError:
            raise ParseError('Enter a valid email.')

            
        

    def create(self, validated_data):
        return User.objects.create_user(**validated_data)


class EmailVerificationSerializer(serializers.ModelSerializer):
    token = serializers.CharField(max_length=555)
    email = serializers.EmailField(max_length=255, min_length=3)

    class Meta:
        model = User
        fields = ['email','token']

class UserSerializer(serializers.ModelSerializer):

    class Meta:
        model = User
        fields = ['id', 'email', 'password', 'username', 'created_at','profile_pic','last_login','is_verified','is_active','is_tk_send']
        # make password field
        extra_kwargs = {
            'password' : {
                # make password write only
                'write_only' : True,
                # set input style password
                'style' : {'input_type' : 'password'}
            },
            'is_verified' : {
                'read_only' : True
            }
        }

    
    def update(self, instance, validated_data):
        print('user updated')
        if 'password' in validated_data:
            password = validated_data.pop('password')
            instance.set_password(password)

        return super().update(instance, validated_data) 



class LoginSerializer(serializers.ModelSerializer):
    email = serializers.EmailField(max_length=255, min_length=3)
    password = serializers.CharField(max_length=68, min_length=6, write_only=True)
    username = serializers.CharField(max_length=255, min_length=3, read_only=True)
    tokens = serializers.SerializerMethodField()
    is_verified = serializers.BooleanField(default=False)
    created_at = serializers.DateTimeField(read_only=True)
    id = serializers.IntegerField(read_only=True)
    profile_pic = serializers.ImageField(read_only=True)
    
    def get_tokens(self, obj):

        user = User.objects.get(email=obj['email'])

        return {
            'refresh': user.tokens()['refresh'],
            'access': user.tokens()['access']
        }
    
    
    class Meta:
        model = User
        fields = ['id','email', 'password', 'username', 'tokens','is_verified','created_at','profile_pic']

    def validate(self, attrs):
        email = attrs.get('email', '')
        password = attrs.get('password', '')
        user = auth.authenticate(email=email, password=password)
        
        if not user:
            raise AuthenticationFailed('Invalid credentials, try again')
        if not user.is_active:
            raise AuthenticationFailed('Account disabled, contact admin')
        if not user.is_verified:
            raise AuthenticationFailed('Please verify your email. Check your inbox for details.')

        return {
            'email': user.email,
            'username': user.username,
            'tokens': user.tokens,
            'is_verified' : user.is_verified,
            'created_at' : user.created_at,
            'id': user.id,
            'profile_pic': user.profile_pic
        }

        return super().validate(attrs)


class ResetPasswordEmailRequestSerializer(serializers.Serializer):
    email = serializers.EmailField(min_length=2)

    redirect_url = serializers.CharField(max_length=500, required=False)

    class Meta:
        fields = ['email']


class SetNewPasswordSerializer(serializers.Serializer):
    password = serializers.CharField(
        min_length=6, max_length=68, write_only=True)
    token = serializers.CharField(
        min_length=1, write_only=True)
    uidb64 = serializers.CharField(
        min_length=1, write_only=True)

    class Meta:
        fields = ['password', 'token', 'uidb64']

    def validate(self, attrs):
        try:
            password = attrs.get('password')
            token = attrs.get('token')
            uidb64 = attrs.get('uidb64')

            id = force_str(urlsafe_base64_decode(uidb64))
            user = User.objects.get(id=id)
            if not PasswordResetTokenGenerator().check_token(user, token):
                raise AuthenticationFailed('The reset link is invalid', 401)

            user.set_password(password)
            user.save()

            return (user)
        except Exception as e:
            raise AuthenticationFailed('The reset link is invalid', 401)
        return super().validate(attrs)



class LogoutSerializer(serializers.Serializer):
    refresh = serializers.CharField()

    default_error_message = {
        'bad_token': ('Token is expired or invalid')
    }

    def validate(self, attrs):
        self.token = attrs['refresh']
        return attrs

    # def save(self, **kwargs):

    #     try:
    #         RefreshToken(self.token).blacklist()

    #     except TokenError:
    #         self.fail('bad_token')
