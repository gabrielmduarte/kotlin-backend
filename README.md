# Kotlin Backend Code Challenge

This project is a small Kotlin backend service built with Spring Boot that simulates how a production service would run on AWS (EC2, S3, CloudWatch).

---

## Running Locally

### Using Gradle
    ./gradlew bootRun

The service will be available at:
    http://localhost:8080

### Using Docker
    docker build -t nike-demo .
    docker run -p 8080:8080 nike-demo

---

## API Endpoints

### POST /files

Uploads a file and stores it locally in the `/storage` directory.

Request body:

    {
      "filename": "test.txt",
      "content": "Hello Nike"
    }

Behavior:
- Saves the file to the local filesystem
- Simulates uploading a file to Amazon S3

---

### GET /files/{filename}

Retrieves the content of a previously uploaded file.

Behavior:
- Returns the file content if it exists
- Returns `404 Not Found` if the file does not exist

---

### GET /metrics

Returns in-memory metrics that simulate CloudWatch-style custom metrics.

Example response:

    {
      "uploads": 3,
      "reads": 5,
      "errors": 1
    }

---

## Metrics (CloudWatch-style)

The application tracks the following metrics in memory:
- Total number of uploaded files
- Total number of file reads
- Total number of errors (e.g. file not found)

No external monitoring tools, Prometheus, or AWS SDKs are used.

---

## Mapping Local Implementation to AWS

### EC2

- The application would run as a Docker container on EC2 instances
- An Application Load Balancer (ALB) would distribute incoming traffic
- An Auto Scaling Group would ensure availability and scalability

### S3

- The local `/storage` directory would be replaced by an S3 bucket
- POST /files would map to an S3 PutObject operation
- GET /files/{filename} would map to an S3 GetObject operation

### CloudWatch

- Application logs would be shipped to CloudWatch Logs
- The in-memory metrics would be published as CloudWatch Custom Metrics
- Alarms could be configured based on error rate or request volume

---

## CI/CD

A simple CI/CD pipeline (e.g. Jenkins) would include:
1. Build and test the application
2. Build the Docker image
3. Push the image to Amazon ECR
4. Deploy the new version to EC2 (or ECS)

This ensures consistent and repeatable deployments.

---

## Infrastructure as Code

Infrastructure would be managed using Terraform or CloudFormation, including:
- EC2 instances and Auto Scaling Groups
- Application Load Balancer
- S3 bucket
- IAM roles and policies
- CloudWatch log groups and alarms

This approach enables versioned, reproducible infrastructure.

---

## Production Metrics to Monitor

Key metrics to monitor in production:
- Upload count: measures system usage
- Read count: identifies access patterns
- Error rate: indicates application health
- Request latency: impacts user experience
- Storage errors: signals potential data or infrastructure issues

These metrics help ensure reliability, scalability, and observability.