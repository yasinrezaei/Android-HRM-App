from django.db import models
from django.db.models import fields
from rest_framework import serializers
from HR.models import Field, Report,CustomScoringField,ManagerDailyReport,Ticket,UserProfile,Department,City
from django.contrib.auth.models import User

#------------user serializer---------
class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model=User
        fields = ('id','username' )

#-----------------------------------

class DepartmentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Department
        fields="__all__"
class CitySerializer(serializers.ModelSerializer):
    class Meta:
        model = City
        fields="__all__"

class ProfileSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserProfile
        fields="__all__"

class TicketSerializer(serializers.ModelSerializer):
    class Meta:
        model = Ticket
        fields="__all__"

class ReportSerializer(serializers.ModelSerializer):
    class Meta:
        model = Report
        fields="__all__"
class ManagerDailyReportSerializer(serializers.ModelSerializer):
    class Meta:
        model = ManagerDailyReport
        fields="__all__"
class CustomScoringFieldSerializer(serializers.ModelSerializer):
    class Meta:
        model = CustomScoringField
        fields="__all__"

class FieldSerializer(serializers.ModelSerializer):
    class Meta:
        model=Field
        fields="__all__"
     
  