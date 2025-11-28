terraform {
  required_providers {
    azurerm = {
      source = "hashicorp/azurerm"
      version = "~> 4.0"
    }
  }
}

provider "azurerm" {
  features {}
  subscription_id = "******************************"
}

resource "azurerm_resource_group" "event-hub-rg" {
  name     = "event-hub-rg"
  location = "West Europe"
}

resource "azurerm_eventhub_namespace" "enot-hub-ns" {
  name                = "enothubns"
  location            = azurerm_resource_group.event-hub-rg.location
  resource_group_name = azurerm_resource_group.event-hub-rg.name
  sku                 = "Standard"
  capacity            = 1

  tags = {
    environment = "Production"
  }
}

resource "azurerm_eventhub" "enot-hub" {
  name              = "enothub"
  namespace_id      = azurerm_eventhub_namespace.enot-hub-ns.id
  partition_count   = 2
  message_retention = 1
}

resource "azurerm_eventhub_authorization_rule" "sender-rule" {
  name                = "senderpolicy"
  eventhub_name       = azurerm_eventhub.enot-hub.name
  namespace_name      = azurerm_eventhub_namespace.enot-hub-ns.name
  resource_group_name = azurerm_eventhub_namespace.enot-hub-ns.resource_group_name

  listen = false
  send   = true
  manage = false
}

resource "azurerm_eventhub_authorization_rule" "listener-rule" {
  name                = "listpolicy"
  eventhub_name       = azurerm_eventhub.enot-hub.name
  namespace_name      = azurerm_eventhub_namespace.enot-hub-ns.name
  resource_group_name = azurerm_eventhub_namespace.enot-hub-ns.resource_group_name

  listen = true
  send   = false
  manage = false
}

output "eventhub_connection_string_sender" {
  description = "Primary connection string for Event Hub senderpolicy"
  value       = azurerm_eventhub_authorization_rule.sender-rule.primary_connection_string
  sensitive   = true
}

output "eventhub_connection_string_listener" {
  description = "Primary connection string for Event Hub listener"
  value       = azurerm_eventhub_authorization_rule.listener-rule.primary_connection_string
  sensitive   = true
}

# terraform output eventhub_connection_string_sender