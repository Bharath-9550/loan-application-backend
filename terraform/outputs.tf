output "ecr_repository_url" {
  value = aws_ecr_repository.loan_backend.repository_url
}

output "ecs_cluster_name" {
  value = aws_ecs_cluster.loan_cluster.name
}

output "load_balancer_dns" {
  value = aws_lb.loan_alb.dns_name
}