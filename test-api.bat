@echo off
echo Testing Workforce Management API...
echo.

echo 1. Getting all staff members...
curl -X GET http://localhost:8080/api/staff
echo.
echo.

echo 2. Getting all tasks...
curl -X GET http://localhost:8080/api/tasks
echo.
echo.

echo 3. Getting tasks by priority (HIGH)...
curl -X GET http://localhost:8080/api/tasks/priority/HIGH
echo.
echo.

echo 4. Getting tasks for staff1...
curl -X GET http://localhost:8080/api/tasks/staff/staff1
echo.
echo.

echo 5. Getting task details for task1...
curl -X GET http://localhost:8080/api/tasks/task1
echo.
echo.

echo API testing complete!
pause
