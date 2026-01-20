#!/bin/bash

# Helm Chart for Trading System
# Install: helm install trade-app ./helm-chart -n trading-system

set -e

HELM_CHART_DIR="helm-chart"

echo "Creating Helm Chart structure..."

# Create directory structure
mkdir -p $HELM_CHART_DIR/templates
mkdir -p $HELM_CHART_DIR/charts

echo "Chart structure created successfully!"
echo ""
echo "To create the Helm chart package:"
echo "  helm package $HELM_CHART_DIR"
echo ""
echo "To install the chart:"
echo "  helm install trade-app $HELM_CHART_DIR -n trading-system"
echo ""
echo "To upgrade the chart:"
echo "  helm upgrade trade-app $HELM_CHART_DIR -n trading-system"
echo ""
echo "To uninstall the chart:"
echo "  helm uninstall trade-app -n trading-system"
