from profile import Profile
from typing import Text
from django.db import models
from django.contrib.auth.models import User
from django.db.models.deletion import CASCADE


class City(models.Model):
    city_name=models.CharField(max_length=100,verbose_name="نام شهر")
    def __str__(self):
        return self.city_name
    class Meta:
        verbose_name = 'شهر '
        verbose_name_plural = 'شهر ها'

class Department(models.Model):
    department_name=models.CharField(max_length=100,verbose_name="نام واحد")
    def __str__(self):
        return self.department_name
    class Meta:
        verbose_name = 'واحد '
        verbose_name_plural = 'واحد ها'

class UserProfile(models.Model):  
    user = models.ForeignKey(User, unique=True,on_delete=CASCADE,verbose_name="نام کاربر")
    first_name=models.CharField(verbose_name="نام",max_length=50,blank=True,null=True)
    last_name=models.CharField(verbose_name="نام خانوادگی",max_length=50,blank=True,null=True)
    city =models.ForeignKey(City,verbose_name="شهر",on_delete=CASCADE) 
    department = models.ForeignKey(Department,verbose_name="واحد",on_delete=CASCADE)
    is_manager = models.BooleanField(default=False,verbose_name="مدیر این واحد است")
    is_main_manager = models.BooleanField(default=False,verbose_name="از مدیران اصلی سیستم است")
    def __str__(self):
        return self.user.first_name+" "+self.user.last_name
    class Meta:
        verbose_name = 'عضو '
        verbose_name_plural = 'اعضا '

class Ticket(models.Model):
    sender=models.ForeignKey(UserProfile,related_name="sender_user",on_delete=CASCADE,verbose_name="فرستنده")
    reciever=models.ForeignKey(UserProfile,related_name="reciever_user",on_delete=CASCADE,verbose_name="دریافت کننده ")
    title=models.CharField(max_length=50,verbose_name="عنوان")
    text=models.TextField(verbose_name="متن")
    answer=models.TextField(verbose_name="پاسخ",blank=True)
    tag = models.CharField(max_length=20,choices=(( 'Important' , ' مهم'),('Urgent' ,'فوری ' ),('Normal','عادی')),verbose_name='درجه اهمیت ',default='Normal')
    check_by_reciever=models.BooleanField(default=False,verbose_name="بررسی شده توسط دریافت کننده")
    class Meta:
        verbose_name = 'درخواست '
        verbose_name_plural = ' درخواست ها'

class ManagerDailyReport(models.Model):
    sender=models.ForeignKey(UserProfile,on_delete=CASCADE,verbose_name="فرستنده")
    text=models.TextField(verbose_name="متن")
    answer=models.TextField(verbose_name="پاسخ",blank=True)
    report_date=models.DateTimeField(auto_now_add=True,verbose_name="تاریخ ثبت")

    class Meta:
        verbose_name = 'گزارش روزانه مدیر' 
        verbose_name_plural = '  گزارش های روزانه مدیران'

#-------------------------------------------Test---------------------
class Field(models.Model):
    field_name=models.CharField(max_length=100,verbose_name="نام فیلد")
    def __str__(self):
        return self.field_name
    class Meta:
        verbose_name = 'فیلد جدید  '
        verbose_name_plural = 'فیلد ها  '
class Report(models.Model):
    user = models.ForeignKey(UserProfile,related_name="user_profile_2",on_delete=CASCADE,verbose_name="نام کاربر")
    analyzer=models.ForeignKey(UserProfile,related_name="analyzer_user_2",on_delete=CASCADE,verbose_name="نام تحلیل گر")
    report_date=models.DateTimeField(auto_now_add=True,verbose_name="تاریخ ثبت")
    key_word=models.TextField(verbose_name="کلید واژه ها"     ,blank=True)
    urgent_need=models.TextField(verbose_name="نیاز فوری  ",blank=True)
    check_by_manager = models.BooleanField(default=False,verbose_name="بررسی شده توسط مدیر")
    department=models.ForeignKey(Department,on_delete=CASCADE,verbose_name="واحد",blank=True,null=True)
    def __str__(self):
        return self.user.user.first_name+" "+self.user.user.last_name
    class Meta:
        verbose_name = 'گزارش'
        verbose_name_plural = 'گزارش ها'
        
class CustomScoringField(models.Model):
    #report=models.ForeignKey(Report,related_name="report_field",on_delete=CASCADE,verbose_name="گزارش مربوطه")
    field_name=models.ForeignKey(Field,related_name="name_field",on_delete=CASCADE,verbose_name=" نام فیلد")
    score=models.IntegerField(verbose_name="امتیاز")
    user=models.ForeignKey(UserProfile,related_name="user_score",on_delete=CASCADE,verbose_name="کاربر")
    def __str__(self):
        return self.field_name.field_name
    class Meta:
        verbose_name = ' امتیاز دهی'
        verbose_name_plural = '  امتیاز ها '


    






   
