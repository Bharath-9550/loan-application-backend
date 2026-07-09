resource "aws_ecs_service" "loan_service" {
  name            = "loan-backend-service"
  cluster         = aws_ecs_cluster.loan_cluster.id
  task_definition = aws_ecs_task_definition.loan_task.arn
  launch_type     = "FARGATE"
  desired_count   = 1

  network_configuration {
    subnets          = data.aws_subnets.default.ids
    security_groups  = [aws_security_group.ecs_sg.id]
    assign_public_ip = true
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.loan_tg.arn
    container_name   = "loan-backend"
    container_port   = 8080
  }

  depends_on = [
    aws_lb_listener.loan_listener
  ]
}