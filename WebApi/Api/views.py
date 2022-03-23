from django.db.models import query
from rest_framework.decorators import api_view,permission_classes
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework import status
from HR.models import Report,CustomScoringField,ManagerDailyReport,Field,Ticket,UserProfile,Department,City
from rest_framework.permissions import AllowAny,IsAuthenticated
from .serializers import ReportSerializer,CustomScoringFieldSerializer,ManagerDailyReportSerializer,FieldSerializer,TicketSerializer,ProfileSerializer,DepartmentSerializer,CitySerializer,UserSerializer
from rest_framework.generics import ListCreateAPIView,RetrieveDestroyAPIView, RetrieveUpdateDestroyAPIView, UpdateAPIView,RetrieveUpdateAPIView
from django.contrib.auth.models import User




#--------------user---------------
#http://127.0.0.1:8000/api/get-user?username=yasin
class UserDetail(APIView):
    def get(self,request):
        try:
            user=User.objects.get(username=request.query_params['username'])
        except:
            return Response(status=status.HTTP_404_NOT_FOUND)

        ser=UserSerializer(user)
        return Response(ser.data,status=status.HTTP_200_OK)

#http://127.0.0.1:8000/api/get-users  
class UserList(ListCreateAPIView):
    queryset=User.objects.all()
    serializer_class=UserSerializer


#-------------------------------
#http://127.0.0.1:8000/api/cities
class CityList(ListCreateAPIView):
    queryset=City.objects.all()
    serializer_class=CitySerializer

#---------------------------------
#http://127.0.0.1:8000/api/departments
class DepartmentList(ListCreateAPIView):
    queryset=Department.objects.all()
    serializer_class=DepartmentSerializer

#---------------Profile------------------
#http://127.0.0.1:8000/api/profiles
class ProfileList(ListCreateAPIView):
    queryset=UserProfile.objects.all()
    serializer_class=ProfileSerializer

#http://127.0.0.1:8000/api/get-profile?user_id=1
class ProfileDetail(APIView):
    def get(self,request):
        try:
            user=UserProfile.objects.get(user=request.query_params['user_id'])
        except:
            return Response(status=status.HTTP_404_NOT_FOUND)

        ser=ProfileSerializer(user)
        return Response(ser.data,status=status.HTTP_200_OK)

    

#------------------Reports--------------------------ok
#http://127.0.0.1:8000/api/user-reports?user_id=2
class UserReport(APIView):
    def get(self,request):
        try:
            reports=Report.objects.filter(user=request.query_params['user_id'])
        except:
            return Response(status=status.HTTP_404_NOT_FOUND)

        ser=ReportSerializer(reports,many=True)
        return Response(ser.data,status=status.HTTP_200_OK)

#http://127.0.0.1:8000/api/reports
class ReportList(ListCreateAPIView):
    queryset=Report.objects.all()
    serializer_class=ReportSerializer
#http://127.0.0.1:8000/api/report-detail/4
class ReportDetail(RetrieveUpdateDestroyAPIView):
    queryset=Report.objects.all()
    serializer_class=ReportSerializer



#---------------------Manager Daily Report------------------ok
#http://127.0.0.1:8000/api/manger-daily-reports
class ManagerDailyReportList(ListCreateAPIView):
    queryset=ManagerDailyReport.objects.all()
    serializer_class=ManagerDailyReportSerializer

#http://127.0.0.1:8000/api/manger-daily-report-detail/3
class ManagerDailyReportDetail(RetrieveUpdateDestroyAPIView):
    queryset=ManagerDailyReport.objects.all()
    serializer_class=ManagerDailyReportSerializer


#-----------------Scores----------------------- ok 
#http://127.0.0.1:8000/api/report-scores?report_id=6
class ReportScore(APIView):
    def get(self,request):
        try:
            scores=CustomScoringField.objects.filter(report=request.query_params['report_id'])
        except:
            return Response(status=status.HTTP_404_NOT_FOUND)

        ser=CustomScoringFieldSerializer(scores,many=True)
        return Response(ser.data,status=status.HTTP_200_OK)

#http://127.0.0.1:8000/api/scores/
class ScoreList(ListCreateAPIView):
    queryset=CustomScoringField.objects.all()
    serializer_class=CustomScoringFieldSerializer

class ScoreDetail(RetrieveUpdateDestroyAPIView):
    queryset=CustomScoringField.objects.all()
    serializer_class=CustomScoringFieldSerializer

#---------------------Fields--------------------ok
#http://127.0.0.1:8000/api/fields/

class FieldList(ListCreateAPIView):
    queryset=Field.objects.all()
    serializer_class=FieldSerializer




#--------------------------Tickets---------------- ok
#http://127.0.0.1:8000/api/ticket-detail/3
class TicketDetail(RetrieveUpdateDestroyAPIView):
    queryset=Ticket.objects.all()
    serializer_class=TicketSerializer

#http://127.0.0.1:8000/api/tickets
class TicketList(ListCreateAPIView):
    queryset=Ticket.objects.all()
    serializer_class=TicketSerializer    


