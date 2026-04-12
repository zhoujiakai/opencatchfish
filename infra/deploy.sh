#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR"

# Detect docker compose command (v2 plugin vs standalone v1)
if docker compose version &>/dev/null 2>&1; then
    COMPOSE_CMD="docker compose"
elif command -v docker-compose &>/dev/null; then
    COMPOSE_CMD="docker-compose"
else
    echo "Error: Docker Compose not found. Please install Docker Compose."
    exit 1
fi

echo "=== DonDonQiang 2.0 - Docker Compose Deployment ==="
echo ""

echo "[1/2] Building and starting containers..."
$COMPOSE_CMD up -d --build

echo ""
echo "[2/2] Checking container status..."
$COMPOSE_CMD ps

echo ""
echo "=== Deployment Complete ==="
echo "  Backend API: http://localhost:8080/DonDonQiang20_Server/LoginServlet"
echo "  MySQL:       localhost:3306  (root / 12345678)"
echo ""
echo "Useful commands:"
echo "  $COMPOSE_CMD -f infra/docker-compose.yml ps       # Check status"
echo "  $COMPOSE_CMD -f infra/docker-compose.yml logs     # View logs"
echo "  $COMPOSE_CMD -f infra/docker-compose.yml down     # Stop containers"
echo "  $COMPOSE_CMD -f infra/docker-compose.yml down -v  # Stop and remove data"
