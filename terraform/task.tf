resource "aws_cloudwatch_log_group" "loan_logs" {
  name              = "/ecs/loan-backend"
  retention_in_days = 7
}

resource "aws_ecs_task_definition" "loan_task" {
  family                   = "loan-backend-task"
  requires_compatibilities = ["FARGATE"]
  network_mode             = "awsvpc"
  cpu                      = "512"
  memory                   = "1024"
  execution_role_arn       = aws_iam_role.ecs_task_execution_role.arn

  container_definitions = jsonencode([
    {
      name      = "loan-backend"
      image     = "${aws_ecr_repository.loan_backend.repository_url}:latest"
      essential = true

      environment = [
        {
          name  = "DATABASE_URL"
          value = var.database_url
        },
        {
          name  = "DATABASE_USERNAME"
          value = var.database_username
        },
        {
          name  = "DATABASE_PASSWORD"
          value = var.database_password
        },
        {
          name  = "JWT_SECRET"
          value = var.jwt_secret
        }
      ]

      portMappings = [
        {
          containerPort = 8080
          hostPort      = 8080
          protocol      = "tcp"
        }
      ]

      logConfiguration = {
        logDriver = "awslogs"
        options = {
          awslogs-group         = aws_cloudwatch_log_group.loan_logs.name
          awslogs-region        = var.aws_region
          awslogs-stream-prefix = "ecs"
        }
      }
    }
  ])
}