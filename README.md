# Workforce Management API

A Spring Boot application for managing tasks and staff assignments in a logistics super-app.

## Features

### Core Functionality
- Create, assign, and track tasks for employees
- Manage staff information
- Task status management (ACTIVE, COMPLETED, CANCELLED)
- Task priority management (HIGH, MEDIUM, LOW)

### Bug Fixes
1. **Task Re-assignment Fix**: When reassigning tasks by customer reference, old tasks are now properly cancelled
2. **Cancelled Tasks Filter**: Cancelled tasks are excluded from task listings to reduce clutter

### New Features
1. **Smart Daily Task View**: Enhanced date-based task fetching that includes active tasks from previous periods
2. **Task Priority Management**: Set and update task priorities with dedicated endpoints
3. **Task Comments & Activity History**: Full audit trail with user comments and activity logging

## Technology Stack

- **Java 17**
- **Spring Boot 3.0.4**
- **Gradle**
- **Lombok** (for reducing boilerplate)
- **MapStruct** (for object mapping)
- **In-memory storage** (using Java collections)

## Project Structure

```
src/main/java/com/workforcemgmt/
├── WorkforcemgmtApplication.java
├── controller/
│   ├── TaskController.java
│   └── StaffController.java
├── service/
│   └── TaskService.java
├── model/
│   ├── Task.java
│   ├── Staff.java
│   ├── TaskStatus.java
│   ├── TaskPriority.java
│   ├── TaskActivity.java
│   └── TaskComment.java
├── dto/
│   ├── TaskDto.java
│   ├── StaffDto.java
│   ├── CreateTaskRequest.java
│   ├── UpdateTaskPriorityRequest.java
│   └── AddCommentRequest.java
├── mapper/
│   └── TaskMapper.java
└── exception/
    └── GlobalExceptionHandler.java
```

## Getting Started

### Prerequisites
- Java 17 or higher
- Gradle 7.0 or higher

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:
   ```bash
   ./gradlew bootRun
   ```
4. The API will be available at `http://localhost:8080`

### Building the Application
```bash
./gradlew build
```

## API Endpoints

### Tasks

#### Create Task
```
POST /api/tasks
Content-Type: application/json

{
  "title": "Task Title",
  "description": "Task Description",
  "priority": "HIGH",
  "startDate": "2024-01-01T09:00:00",
  "dueDate": "2024-01-02T17:00:00",
  "staffId": "staff1",
  "customerReference": "CUST001"
}
```

#### Get All Tasks
```
GET /api/tasks
```

#### Get Task by ID
```
GET /api/tasks/{taskId}
```

#### Get Tasks by Date Range (Smart Daily View)
```
GET /api/tasks/date-range?startDate=2024-01-01T00:00:00&endDate=2024-01-01T23:59:59
```

#### Get Tasks by Staff
```
GET /api/tasks/staff/{staffId}
```

#### Get Tasks by Priority
```
GET /api/tasks/priority/{priority}
```

#### Assign Task by Customer Reference (Bug Fix 1)
```
POST /api/tasks/assign-by-ref?customerReference=CUST001&newStaffId=staff2
```

#### Update Task Priority (Feature 2)
```
PUT /api/tasks/{taskId}/priority
Content-Type: application/json

{
  "priority": "HIGH"
}
```

#### Add Comment to Task (Feature 3)
```
POST /api/tasks/{taskId}/comments
Content-Type: application/json

{
  "content": "This is a comment",
  "author": "John Doe"
}
```

#### Complete Task
```
PUT /api/tasks/{taskId}/complete
```

#### Cancel Task
```
PUT /api/tasks/{taskId}/cancel
```

### Staff

#### Get All Staff
```
GET /api/staff
```

#### Get Staff by ID
```
GET /api/staff/{staffId}
```

## Sample Data

The application comes pre-loaded with sample data:

### Staff
- **staff1**: John Doe (Sales)
- **staff2**: Jane Smith (Operations)
- **staff3**: Bob Wilson (Sales)

### Sample Tasks
- Customer Follow-up (HIGH priority, assigned to John Doe)
- Inventory Check (MEDIUM priority, assigned to Jane Smith)

## Bug Fixes Demonstrated

### Bug Fix 1: Task Re-assignment Creates Duplicates
- **Before**: Reassigning a task by customer reference would create duplicates
- **After**: Old tasks are automatically cancelled when reassigning

### Bug Fix 2: Cancelled Tasks Clutter the View
- **Before**: Cancelled tasks appeared in all task listings
- **After**: Cancelled tasks are filtered out from task listings

## New Features Demonstrated

### Feature 1: Smart Daily Task View
- Returns tasks that started within the date range
- Also returns active tasks that started before the range but are still open

### Feature 2: Task Priority Management
- Tasks have HIGH, MEDIUM, LOW priorities
- Priority can be updated after task creation
- Filter tasks by priority

### Feature 3: Task Comments & Activity History
- Full audit trail of all task activities
- User comments on tasks
- Chronological history in task details

## Testing the API

You can test the API using tools like:
- Postman
- Insomnia
- cURL
- Any HTTP client

### Example cURL Commands

```bash
# Get all tasks
curl -X GET http://localhost:8080/api/tasks

# Create a new task
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "New Task",
    "description": "Task description",
    "priority": "HIGH",
    "startDate": "2024-01-01T09:00:00",
    "dueDate": "2024-01-02T17:00:00",
    "staffId": "staff1",
    "customerReference": "CUST002"
  }'

# Get tasks by priority
curl -X GET http://localhost:8080/api/tasks/priority/HIGH

# Add a comment to a task
curl -X POST http://localhost:8080/api/tasks/task1/comments \
  -H "Content-Type: application/json" \
  -d '{
    "content": "This is a test comment",
    "author": "John Doe"
  }'
```

## Error Handling

The API includes comprehensive error handling:
- Validation errors for request bodies
- Resource not found errors
- Generic error handling with appropriate HTTP status codes

All errors return JSON responses with:
- HTTP status code
- Error message
- Timestamp
- Error type
