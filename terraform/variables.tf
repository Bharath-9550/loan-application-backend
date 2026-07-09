variable "aws_region" {
  description = "AWS Region"
  type        = string
}

variable "database_url" {
  type      = string
  sensitive = true
}

variable "database_username" {
  type      = string
  sensitive = true
}

variable "database_password" {
  type      = string
  sensitive = true
}

variable "jwt_secret" {
  type      = string
  sensitive = true
}
