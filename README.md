# Loan Management System - Backend

This is the backend service for a full-stack Loan Management System built using Spring Boot. The application supports user registration, login with JWT authentication, and loan application management.

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- PostgreSQL / Neon
- Docker
- AWS ECR
- AWS ECS Fargate
- Application Load Balancer
- CloudWatch Logs
- Terraform

## Features

- User registration
- User login
- JWT-based authentication
- Apply for a loan
- View loan applications
- Search loan applications
- Update loan details
- Delete loan applications
- Health check endpoint

## Architecture

```text
React Frontend (Vercel)
        |
        v
AWS Application Load Balancer
        |
        v
ECS Fargate Task
        |
        v
Spring Boot Backend
        |
        v
Neon PostgreSQL Database
