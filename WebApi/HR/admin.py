from django.contrib import admin
from .models import Department,UserProfile,City,Ticket,ManagerDailyReport,Report,Field,CustomScoringField




    
@admin.register(ManagerDailyReport)
class manager_daily_report_admin(admin.ModelAdmin):
    list_display = ('sender','report_date')
    
@admin.register(UserProfile)
class user_profile_admin(admin.ModelAdmin):
    list_display = ('user','city','department','is_manager','is_main_manager')


@admin.register(Department)
class department_admin(admin.ModelAdmin):
    list_display = ('department_name',)


@admin.register(City)
class city_admin(admin.ModelAdmin):
    list_display = ('city_name',)


@admin.register(Ticket)
class ticket_admin(admin.ModelAdmin):
    list_display = ('sender','reciever','title','check_by_reciever')


#-------------
@admin.register(Report)
class report_admin(admin.ModelAdmin):
    list_display = ('user',)
@admin.register(CustomScoringField)
class csf_admin(admin.ModelAdmin):
    list_display = ('field_name','score')
@admin.register(Field)
class field_admin(admin.ModelAdmin):
    list_display = ('field_name',)