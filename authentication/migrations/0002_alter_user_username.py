# Generated by Django 3.2.3 on 2021-06-22 04:40

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('authentication', '0001_initial'),
    ]

    operations = [
        migrations.AlterField(
            model_name='user',
            name='username',
            field=models.CharField(db_index=True, max_length=255),
        ),
    ]