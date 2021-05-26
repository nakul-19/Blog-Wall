from django.db import models
from django.db.models.deletion import CASCADE
from authentication.models import User

CATEGORY_OPTIONS = [
    ('TECHNOLOGY', 'TECHNOLOGY'),
    ('GENERAL', 'GENERAL'),
    ('SPORTS', 'SPORTS'),
    ('HEALTH', 'HEALTH'),
    ('BUSINESS','BUSINESS'),
    ('ENTERTAINMENT','ENTERTAINMENT')
]


class Post(models.Model):
    owner = models.ForeignKey(to=User, on_delete=CASCADE)
    like = models.IntegerField(default=0)
    category = models.CharField(choices=CATEGORY_OPTIONS, max_length=225)
    text = models.TextField()
    date = models.DateField(null=False, blank=False)

    class Meta:
        ordering: ('-date')
        
    def __str__(self):
        return str(self.owner)+'s post'


class Comments(models.Model):
    owner = models.ForeignKey(to=User, on_delete=CASCADE)
    like = models.IntegerField(default=0)
    text = models.TextField()
    post = models.ForeignKey(to=Post, on_delete=CASCADE )
    class Meta: 
        verbose_name = 'Comments'
        verbose_name_plural = 'Comments'

    def __str__(self):
        return str(self.owner)+'s comment'
