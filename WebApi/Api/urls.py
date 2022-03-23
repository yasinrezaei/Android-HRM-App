from collections import UserList
from django.urls import path
from .views import ManagerDailyReportDetail, ReportDetail, ReportList,ManagerDailyReportList,FieldList, ScoreDetail, ScoreList, TicketDetail,TicketList,ProfileList,ProfileDetail,CityList,DepartmentList,UserList,UserDetail,ReportScore,UserReport
from rest_framework.authtoken.views import obtain_auth_token
urlpatterns = [
    path('reports/',ReportList.as_view(),name="report-list"),
    path('cities/',CityList.as_view(),name="city-list"),
    path('cities/',CityList.as_view(),name="city-list"),
    path('departments/',DepartmentList.as_view(),name="department-list"),
    path('profiles/',ProfileList.as_view(),name="profile-list"),
    path('profile/<int:pk>',ProfileDetail.as_view(),name="profile-detail"),
    path('tickets/',TicketList.as_view(),name="ticket-list"),
    path('ticket-detail/<int:pk>',TicketDetail.as_view(),name="ticket-detail"),
    path('fields/',FieldList.as_view(),name="field-list"),
    path('manger-daily-reports/',ManagerDailyReportList.as_view(),name="manager-daily-report-list"),
    path('manger-daily-report-detail/<int:pk>',ManagerDailyReportDetail.as_view(),name="manager-daily-report-detail"),
    path('scores/',ScoreList.as_view(),name="score-list"),
    path('score-detail/<int:pk>',ScoreDetail.as_view(),name="score-detail"),
    path('report-detail/<int:pk>',ReportDetail.as_view(),name="report-detail"),
    path('report-scores/',ReportScore.as_view(),name="report-score"),
    path('user-reports/',UserReport.as_view(),name="user-report"),
    path('api-token-auth/',obtain_auth_token), #Authentication
    path('get-profile/',ProfileDetail.as_view()),
    path('get-users/',UserList.as_view()),
    path('get-user/',UserDetail.as_view())
]