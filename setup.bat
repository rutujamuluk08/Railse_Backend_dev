@echo off
echo Setting up Workforce Management API project...

echo Creating gradle wrapper directory...
if not exist "gradle\wrapper" mkdir gradle\wrapper

echo Downloading Gradle wrapper JAR...
powershell -Command "& {Invoke-WebRequest -Uri 'https://github.com/gradle/gradle/raw/v8.0.0/gradle/wrapper/gradle-wrapper.jar' -OutFile 'gradle\wrapper\gradle-wrapper.jar'}"

echo Setting up project...
gradlew.bat build

echo Setup complete!
echo.
echo To run the application, use: gradlew.bat bootRun
echo To build the project, use: gradlew.bat build
echo.
pause
